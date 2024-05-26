package com.vogeez.jilou;

import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 0.1.0-alpha
 * @author exepta
 */
public final class UIApplication {

    private static final Logger LOG = LoggerFactory.getLogger(UIApplication.class);

    private static Class<?> applicationClass;
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
        LOG.info("Loading application...");
        Configuration.setFromArguments(args);
        applicationClass = getApplication();
        if(applicationClass.equals(UIApplication.class)) {
            throw new RuntimeException("The application class is not a UI application.");
        }
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
     * The function will return{@link UIApplication}if there was no class found by the
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
            applicationClass = UIApplication.class;
        }
        return applicationClass;
    }

    /**
     * @return {@link Class}- is better than call {@link #getApplication()}
     * because it will fetch and update the class which is useless.
     */
    public static Class<?> getApplicationClass() {
        return applicationClass;
    }

    /**
     * @return {@link Boolean}- true if the application already running by a parent application.
     */
    public static boolean isLoaded() {
        return loaded;
    }
}
