package com.vogeez.jilou.ui.layout;

public class Pane extends Layout {

    public Pane() {
        this(null);
    }

    public Pane(String name) {
        super(name);
        this.setSize(350);
    }

    @Override
    protected void logic() {
        /* simple logic [--][---][-----]
         *              [----]
         *              [---------]
         * this layout force the components to hold here width and height. */
    }
}
