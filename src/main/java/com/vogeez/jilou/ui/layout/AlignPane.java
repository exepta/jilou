package com.vogeez.jilou.ui.layout;

import com.vogeez.jilou.style.Stylesheet;
import com.vogeez.jilou.ui.Scene;
import com.vogeez.jilou.ui.widgets.AbstractContainerWidget;
import com.vogeez.jilou.ui.widgets.AbstractWidget;

public class AlignPane extends Layout {

    public AlignPane() {
        this(null);
    }

    public AlignPane(String name) {
        super(name);
        this.setSize(350);
    }

    // Todo: This is not good enough because it will overlap widgets.
    @Override
    protected void logic() {
        if(getContainer() instanceof Scene scene) {
            for(AbstractWidget widget : scene.getAllChildren()) {
                if(widget.getLocalizedID().equals(getLocalizedID())) {
                    continue;
                }
                Stylesheet sheet = widget.getStylesheet();
                AbstractContainerWidget parent = widget.getParent();
                double minX = parent.getPositionX();
                double minY = parent.getPositionY();
                double maxX = ((parent.getPositionX() + parent.getWidth()) - (widget.getWidth()));
                double maxY = ((parent.getPositionY() + parent.getHeight()) - (widget.getHeight()));

                double widgetX = minX + widget.getPositionX();
                double widgetY = minY + widget.getPositionY();

                widgetX = Math.min(widgetX, maxX);
                widgetX = Math.max(widgetX, minX);
                widgetY = Math.min(widgetY, maxY);
                widgetY = Math.max(widgetY, minY);

                switch (sheet.getAlignment()) {
                    case TOP_LEFT -> widget.setPosition(minX, minY);
                    case TOP_RIGHT -> widget.setPosition(maxX, minY);
                    case TOP_CENTER -> {
                        double center = minX + (parent.getWidth() / 2) - (widget.getWidth() / 2);
                        widget.setPosition(center, minY);
                    }
                    case BOTTOM_LEFT -> widget.setPosition(minX, maxY);
                    case BOTTOM_RIGHT -> widget.setPosition(maxX, maxY);
                    case BOTTOM_CENTER -> {
                        double center = minX + (parent.getWidth() / 2) - (widget.getWidth() / 2);
                        widget.setPosition(center, maxY);
                    }
                    case CENTER_LEFT -> {
                        double centerY = minY + (parent.getHeight() / 2) - (widget.getHeight() / 2);
                        widget.setPosition(minX, centerY);
                    }
                    case CENTER_RIGHT -> {
                        double centerY = minY + (parent.getHeight() / 2) - (widget.getHeight() / 2);
                        widget.setPosition(maxX, centerY);
                    }
                    case CENTER -> {
                        double centerX = minX + (parent.getWidth() / 2) - (widget.getWidth() / 2);
                        double centerY = minY + (parent.getHeight() / 2) - (widget.getHeight() / 2);
                        widget.setPosition(centerX, centerY);
                    }
                    case NOTHING -> widget.setPosition(widgetX, widgetY);
                }
            }
        }
    }
}
