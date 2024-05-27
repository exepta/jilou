package com.vogeez.jilou.ui;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public abstract class AbstractUIContainedWindow extends AbstractWindow {

    public AbstractUIContainedWindow() {
        this(null);
    }

    public AbstractUIContainedWindow(String uid) {
        super(uid);
    }

    @Override
    protected void initialize() {
        build();
        defaultConfigure();
    }

    @Override
    protected void update() {
        while (!shouldClose()) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            GL11.glClearColor(background.getRedPercent(), background.getGreenPercent()
                    , background.getBluePercent(), background.getAlphaPercent());

            GLFW.glfwSwapBuffers(openglID);
            GLFW.glfwPollEvents();
        }
    }
}
