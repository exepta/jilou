package com.vogeez.jilou;

import com.vogeez.jilou.ui.AbstractWindow;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ApplicationFactory {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractWindow.class);

    public static final Map<String, Thread> THREAD_MAP = new ConcurrentHashMap<>();
    public static final Map<String, AbstractWindow> WINDOW_MAP = new ConcurrentHashMap<>();

    public static Thread generateThread(Runnable run, AbstractWindow abstractWindow) {
        if(hasThread(abstractWindow.getUid())) {
            LOG.warn("Thread {} already exists", abstractWindow.getUid());
            return abstractWindow.getThread();
        }

        if(activeThreadCount() > 5) {
            LOG.warn("Max Thread count reached! Close/Destroy windows for make a new one!");
            return null;
        }

        Thread thread = new Thread(run, abstractWindow.getUid());
        THREAD_MAP.put(abstractWindow.getUid(), thread);
        LOG.info("Created new thread {}", abstractWindow.getUid());
        return thread;
    }

    public static boolean hasThread(String uid) {
        return THREAD_MAP.containsKey(uid);
    }

    @Nullable
    public static Thread getThread(String uid) {
        return THREAD_MAP.getOrDefault(uid, null); //ToDo: get default window.
    }

    /**
     * @return {@link Integer}- active thread count from{@link Map#size()}.
     */
    public static int activeThreadCount() {
        return THREAD_MAP.size();
    }
}
