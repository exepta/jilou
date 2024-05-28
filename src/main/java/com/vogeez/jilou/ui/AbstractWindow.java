package com.vogeez.jilou.ui;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.GL11;

@Getter
@Setter
public abstract class AbstractWindow extends AbstractWindowFrame {

    private boolean enableNVG;
    private boolean renderingAtMinimized;
    private boolean enableUI;

    private Scene scene;

    public AbstractWindow() {
        this(null);
    }

    public AbstractWindow(String uid) {
        super(uid);
        this.enableNVG = true;
        this.enableUI = true;
        this.renderingAtMinimized = false;
        this.scene = new Scene();
    }

    protected abstract void setup();
    protected abstract void update(float delta);
    protected abstract void render();

    public void setScene(Scene scene) {
        this.scene = scene;
        scene.update();
    }

    @Override
    protected void initialize() {
        build();
        defaultConfigure();
        setup();
    }

    @Override
    protected void update() {
        long lastTime = System.nanoTime();
        double fps = 60.0; //Todo: function for manipulate.
        double nanos = 1_000_000_000.0 / fps;
        double delta = 0;

        while (!isClosing()) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nanos;
            lastTime = now;

            if(delta >= 1.0) {
                update((float) (delta * fps));
                if(enableUI) {
                    if(scene == null) {
                        scene = new Scene();
                    }
                    scene.update();
                } else {
                    this.scene = null;
                }
                delta = 0;
            }

            rendererInitialize();
            if(!isMinimized()) {
                renderIntern();
            } else {
                if(renderingAtMinimized) {
                    renderIntern();
                }
            }

            GLFW.glfwSwapBuffers(openglID);
            GLFW.glfwPollEvents();
        }
    }

    private void renderIntern() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glClearColor(background.getRedPercent(), background.getGreenPercent()
                , background.getBluePercent(), background.getAlphaPercent());
        if(enableNVG) {
            NanoVG.nvgBeginFrame(context.nvgID(), getWidth(), getHeight(), 1f);

            render();

            NanoVG.nvgRestore(context.nvgID());
            NanoVG.nvgEndFrame(context.nvgID());
            return;
        }
        render();
    }

    private void rendererInitialize() {

    }
}
