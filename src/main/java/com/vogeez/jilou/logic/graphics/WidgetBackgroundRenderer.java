package com.vogeez.jilou.logic.graphics;

import com.vogeez.jilou.logic.graphics.components.NanoBackground;
import com.vogeez.jilou.style.Color;
import com.vogeez.jilou.style.ColorGradient;
import com.vogeez.jilou.style.base.Background;
import com.vogeez.jilou.ui.AbstractWindowFrame;
import com.vogeez.jilou.ui.widgets.AbstractWidget;

import java.util.List;

public class WidgetBackgroundRenderer extends AbstractWidgetRenderer {

    private NanoBackground background;

    private final Background b = Background.gradient(ColorGradient.generate(Color.randomRGB(), Color.randomRGB(), Color.randomRGB()));

    public WidgetBackgroundRenderer() {
        super(null);
    }

    @Override
    public void render(List<AbstractWidget> widgetList) {
        widgetList.forEach(widget -> {
            background.draw(widget.getPositionX(), widget.getPositionY(),
                    widget.getWidth(), widget.getHeight(), b);
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
