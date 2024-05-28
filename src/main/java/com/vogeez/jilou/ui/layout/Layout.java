package com.vogeez.jilou.ui.layout;

import com.vogeez.jilou.style.attributes.Color;
import com.vogeez.jilou.style.base.Background;
import com.vogeez.jilou.ui.widgets.AbstractContainerWidget;

public abstract class Layout extends AbstractContainerWidget {

    public Layout(String name) {
        super(name);
        getStylesheet().setBackground(Background.color(Color.GRAY));
    }

    protected abstract void logic();

    @Override
    public void update() {
        logic();
    }
}
