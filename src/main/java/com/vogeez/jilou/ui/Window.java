package com.vogeez.jilou.ui;

public class Window extends AbstractWindow {

    public Window() {
        this(null);
    }

    public Window(String uid) {
        super(uid);
        this.setEnableNVG(true);
        this.setRenderingAtMinimized(false);
    }

    @Override
    protected void setup() {

    }

    @Override
    protected void update(float delta) {

    }

    @Override
    protected void render() {

    }

    @Override
    public void destroy() {

    }
}
