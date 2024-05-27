package com.vogeez.jilou;

import com.vogeez.jilou.ui.AbstractWindow;
import com.vogeez.jilou.ui.Window;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class ApplicationFactory {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractWindow.class);

    public static final Map<String, Thread> THREAD_MAP = new ConcurrentHashMap<>();
    public static final Map<String, AbstractWindow> WINDOW_MAP = new ConcurrentHashMap<>();

    /* ############################################################################################
     *
     *                                    Window Handle
     *
     * ############################################################################################ */

    /**
     * Function creates a new window by specified credentials.
     * It is recommended to use this function to create windows because it will register and handle
     * your window. The created window will return as the type your give as parameter. This means
     * if your type for example {@link Window} than is the return type this too.
     * @param uid the named identifier can be null to auto generate.
     * @param type the window specified type. Need to inherit {@link AbstractWindow}.
     * @return T - the create window as correct java object type.
     * @param <T> override the function return value to your class type.
     * @apiNote Note that this function need a type! Please don't use null at the type param!
     * @see AbstractWindow
     */
    public static <T extends AbstractWindow> T createWindow(String uid, Class<T> type) {
        return createWindow(uid, null, type);
    }

    /**
     * Function creates a new window by specified credentials.
     * It is recommended to use this function to create windows because it will register and handle
     * your window. The created window will return as the type your give as parameter. This means
     * if your type for example {@link Window} than is the return type this too.
     * @param uid the named identifier can be null to auto generate.
     * @param title the title of the window, can be null will be replaced by {@link Class#getSimpleName()}.
     * @param type the window specified type. Need to inherit {@link AbstractWindow}.
     * @return T - the create window as correct java object type.
     * @param <T> override the function return value to your class type.
     * @apiNote Note that this function need a type! Please don't use null at the type param!
     * @see AbstractWindow
     */
    public static <T extends AbstractWindow> T createWindow(String uid, String title, Class<T> type) {
        return createWindow(uid, title, AbstractWindow.DEFAULT_WIDTH, AbstractWindow.DEFAULT_HEIGHT, type);
    }

    /**
     * Function creates a new window by specified credentials.
     * It is recommended to use this function to create windows because it will register and handle
     * your window. The created window will return as the type your give as parameter. This means
     * if your type for example {@link Window} than is the return type this too.
     * @param uid the named identifier can be null to auto generate.
     * @param title the title of the window, can be null will be replaced by {@link Class#getSimpleName()}.
     * @param width the window frame width.
     * @param height the window frame height.
     * @param type the window specified type. Need to inherit {@link AbstractWindow}.
     * @return T - the create window as correct java object type.
     * @param <T> override the function return value to your class type.
     * @apiNote Note that this function need a type! Please don't use null at the type param!
     * @see AbstractWindow
     */
    public static <T extends AbstractWindow> T createWindow(String uid, String title, int width, int height, Class<T> type) {
        T window;
        try {
            window = type.cast(Class.forName(type.getName()).getDeclaredConstructor(String.class).newInstance(uid));
        } catch (ClassNotFoundException | NoSuchMethodException
                 | InstantiationException | IllegalAccessException |
                 InvocationTargetException exception) {
            LOG.error("Window creation failed! Initialize default window...", exception);
            window = type.cast(new Window(uid));
        }

        window.setTitle(title);
        window.setWidth(width);
        window.setHeight(height);
        registerWindow(window);
        return window;
    }

    /**
     * Function for give one window specified by his uid {@link AbstractWindow#getUid()}
     * @param uid the window specified id.
     * @return {@link AbstractWindow}- the result window.
     */
    public static AbstractWindow getWindow(String uid) {
        return WINDOW_MAP.get(uid);
    }

    /**
     * @return {@link HashSet}- all registered {@link AbstractWindow}'s
     */
    public static Set<AbstractWindow> getWindows() {
        return new HashSet<>(WINDOW_MAP.values());
    }

    /**
     * Function for handle registering windows. This is absolute needed because we need to save the {@link AbstractWindow}'s temporary.
     * This is useful to handle window sharing or call the window at another function without param selection.
     * This function is used by {@link #createWindow(String, String, int, int, Class)} for creating safe windows. You a recommend to use
     * this function for creating your windows.
     * @param window the window which will be registered.
     * @see AbstractWindow
     */
    public static void registerWindow(AbstractWindow window) {
        if(WINDOW_MAP.containsKey(window.getUid())) {
            LOG.warn("Window already exists! [ {} ]", window.getUid());
            return;
        }

        WINDOW_MAP.put(window.getUid(), window);
        LOG.info("Registered window [ {} ]", window.getUid());
    }

    /**
     * Function for handle window unregistering. This is needed for example destroy or clear functions.
     * For direct unregistering use {@link #unregisterWindow(String)} which is working with the {@link AbstractWindow#getUid()}
     * directly.
     * @param window the window as object itself.
     * @see AbstractWindow
     */
    public static void unregisterWindow(AbstractWindow window) {
        unregisterWindow(window.getUid());
    }

    /**
     * Function for handle window unregistering. This is needed for example destroy or clear functions.
     * For safer unregistering use {@link #unregisterWindow(AbstractWindow)} which is working with the {@link AbstractWindow}
     * directly.
     * @param uid the identifier for the window.
     * @see AbstractWindow
     */
    public static void unregisterWindow(String uid) {
        if(!WINDOW_MAP.containsKey(uid)) {
            LOG.warn("Window does not exist! [ {} ]", uid);
            return;
        }
        WINDOW_MAP.remove(uid);
        LOG.info("Unregistered window [ {} ]", uid);
    }

    /* ############################################################################################
     *
     *                                    Threads Handle
     *
     * ############################################################################################ */

    /**
     * Function for generating a new {@link Thread} for the given {@link AbstractWindow} and his run function.
     * Needed for handle the window. If the Thread limit reached, there will no window created.
     * The Thread limit is configurable at the jilou.toml configuration.
     * @param run the run function from current {@link AbstractWindow}.
     * @param abstractWindow the holder {@link AbstractWindow}
     * @return {@link Thread}- the generated and ready to start {@link Thread}.
     * @see Thread
     * @see AbstractWindow
     */
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

    /**
     * Function to check if there is an {@link Thread} stored by the {@link AbstractWindow#getUid()}.
     * @param uid the window specifier.
     * @return {@link Boolean}- true it was found.
     */
    public static boolean hasThread(String uid) {
        return THREAD_MAP.containsKey(uid);
    }

    /**
     * Function for get a specific {@link Thread} from the uid {@link AbstractWindow#getUid()} of the holder window.
     * @param uid the window specifier the same as {@link Thread#getName()}
     * @return {@link Thread}- current running {@link Thread} can be the Thread of default window if the uid wasn't found.
     * @see Thread
     * @see AbstractWindow
     */
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
