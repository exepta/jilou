package com.vogeez.jilou.logic.graphics;

import com.vogeez.jilou.logic.graphics.components.NanoBackground;
import com.vogeez.jilou.ui.AbstractWindowFrame;
import com.vogeez.jilou.ui.widgets.AbstractWidget;

import java.util.List;

public class WidgetBackgroundRenderer extends AbstractWidgetRenderer {

    private NanoBackground background;

    public WidgetBackgroundRenderer() {
        super(null);
    }

    @Override
    public void render(List<AbstractWidget> widgetList) {
        widgetList.forEach(widget -> {
            background.draw(widget.getPositionX(), widget.getPositionY(),
                    widget.getWidth(), widget.getHeight(),
                    widget.getStylesheet().getBackground());
        });
    }

    @Override
    public void preLoad(AbstractWindowFrame window) {
        background = new NanoBackground(window);
    }

    @Override
    public void dispose() {

    }
}
