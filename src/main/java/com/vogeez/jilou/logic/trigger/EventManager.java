package com.vogeez.jilou.logic.trigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class EventManager {

    private static final Logger LOG = LoggerFactory.getLogger(EventManager.class);

    private static final Map<Class<? extends Event>, Set<CallbackHandler>> HANDLERS;

    static {
        HANDLERS = new ConcurrentHashMap<>();
    }

    public static void registerListener(Object listener) {
        for(Method method : listener.getClass().getDeclaredMethods()) {
            if(isEventHandler(method)) {
                registerImplListener(method, listener);
            }
        }
    }

    public static void unregisterAllListeners() {
        HANDLERS.clear();
    }

    public static void unregisterListener(Object listener) {
        for(Iterator<Set<CallbackHandler>> iterator = HANDLERS.values().iterator(); iterator.hasNext();) {
            Set<CallbackHandler> entries = iterator.next();
            entries.removeIf(handler -> handler.listener().equals(listener));
            if(entries.isEmpty()) {
                iterator.remove();
            }
        }
    }

    public static void callEvent(Event event) {
        Event out = callReturnedEvent(event);
        LOG.info("Event [ {} ] was called!", event.getName());
    }

    public static <T extends Event> T callReturnedEvent(T event) {
        List<CallbackHandler> sortedHandlers = new ArrayList<>();
        Class<?> cls = event.getClass();

        while (cls != Object.class) {
            Set<CallbackHandler> classHandlers = HANDLERS.get(cls);
            if(classHandlers != null)
                sortedHandlers.addAll(classHandlers);

            for(Class<?> c : cls.getInterfaces()) {
                Set<CallbackHandler> interfaceHandlers = HANDLERS.get(c);
                if(interfaceHandlers != null)
                    sortedHandlers.addAll(interfaceHandlers);
            }

            cls = cls.getSuperclass();
        }

        if(!sortedHandlers.isEmpty()) {
            sortedHandlers.sort(Comparator.comparing(CallbackHandler::getPriority));
            Cancelable cancelable = event instanceof Cancelable ? (Cancelable) event : null;
            for(CallbackHandler handler : sortedHandlers) {
                if(cancelable != null && !handler.isIgnoreCancelled() && cancelable.isCancelled())
                    continue;

                invoke(handler, event);
            }
        }

        return event;
    }

    private static void registerImplListener(Method method, Object listener) {
        Class<? extends Event> cls = method.getParameterTypes()[0].asSubclass(Event.class);
        if(!method.canAccess(listener))
            method.setAccessible(true);

        CallbackHandler handler = new CallbackHandler(listener, method, method.getAnnotation(EventHandler.class));
        HANDLERS.computeIfAbsent(cls, k -> new HashSet<>()).add(handler);
    }

    private static void invoke(CallbackHandler handler, Event event) {
        try {
            handler.method().invoke(handler.listener(), event);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            LOG.error("Invoke failed: {}", exception.getMessage());
        }
    }

    private static boolean isEventHandler(Method method) {
        Class<?>[] parameters = method.getParameterTypes();
        return parameters.length == 1 && Event.class.isAssignableFrom(parameters[0]) && method.isAnnotationPresent(EventHandler.class);
    }

}
