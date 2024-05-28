package com.vogeez.jilou;

import com.vogeez.jilou.events.JILOUPreLoadEvent;
import com.vogeez.jilou.events.internal.WidgetInternalListener;
import com.vogeez.jilou.logic.trigger.EventManager;
import lombok.Getter;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 0.1.0-alpha
 * @author exepta
 */
public final class JILOUApplication {

    private static final Logger LOG = LoggerFactory.getLogger(JILOUApplication.class);

    @Getter
    private static Class<?> applicationClass;

    @Getter
    private static boolean loaded;

    /**
     * Function for handle applications with need an ui. You need to call this in your main
     * function. This function is{@link Thread}safe because the ui handling is working in
     * a separate{@link Thread}. More about ui handling below this.
     * @param args Program arguments form your main or given array.
     * @apiNote visit our website for mor information's ( <a href="https://www.vogeez-dev.com/docs/jilou/getting-started">Getting Started</a> )
     */
    public static void load(String[] args) {
        if(loaded) {
            LOG.warn("Application is already loaded. Skipping loading. by [ {} ]", applicationClass.getSimpleName());
            return;
        }
        initializeListener();
        LOG.info("Loading application...");
        Configuration.setFromArguments(args);
        applicationClass = getApplication();
        if(applicationClass.equals(JILOUApplication.class)) {
            throw new RuntimeException("The application class is not a UI application.");
        }
        EventManager.callEvent(new JILOUPreLoadEvent(applicationClass));
        initializeLWJGL();
        Configuration.printVersion();
        loaded = true;
        LOG.info("Application loaded. [ {} ]", applicationClass.getSimpleName());
    }

    /**
     * Function for initialize OpenGL with LWJGL as provider.
     * This is called by {@link #load(String[])} function and is absolute needed for handling gui's.
     * @throws RuntimeException if {@link GLFW#glfwInit()} failed to initialize.
     */
    public static void initializeLWJGL() {
        if(!(GLFW.glfwInit())) {
            throw new RuntimeException("GLFW failed to initialize");
        }
        GLFW.glfwDefaultWindowHints();
    }

    /**
     * Function for fetch the current class which is using this application ui management.
     * The function will return{@link JILOUApplication}if there was no class found by the
     * given{@link StackTraceElement}.
     * @return {@link Class}- application class.
     * @apiNote This function is useful if you need your main class as reference in other classes
     * which using our ui.
     */
    public static Class<?> getApplication() {
        String path;
        Class<?> applicationClass;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if (stackTraceElements.length > 1) {
            path = stackTraceElements[stackTraceElements.length - 1].getClassName();
        } else {
            path = stackTraceElements[0].getClassName();
        }
        try {
            applicationClass = Class.forName(path);
        } catch (ClassNotFoundException exception) {
            LOG.error("Could not load application class", exception);
            applicationClass = JILOUApplication.class;
        }
        return applicationClass;
    }

    private static void initializeListener() {
        EventManager.registerListener(new WidgetInternalListener());
    }

}
