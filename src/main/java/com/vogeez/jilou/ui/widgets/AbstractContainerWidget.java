package com.vogeez.jilou.ui.widgets;

import com.vogeez.jilou.events.widgets.WidgetAddEvent;
import com.vogeez.jilou.events.widgets.WidgetRemoveEvent;
import com.vogeez.jilou.logic.trigger.EventManager;
import com.vogeez.jilou.ui.Scene;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public abstract class AbstractContainerWidget extends AbstractWidget {

    private final Logger LOG = LoggerFactory.getLogger(AbstractContainerWidget.class);

    private final List<AbstractWidget> children = new ArrayList<>();

    public AbstractContainerWidget() {
        this(null);
    }

    public AbstractContainerWidget(String name) {
        super(name);
    }

    public void addChild(AbstractWidget widget) {
        if(!hasChild(widget.getLocalizedID())) {
            EventManager.callEvent(new WidgetAddEvent(widget, this, getContainer()));
            widget.setParent(this);
            children.add(widget);
            if(isFirstLayerWidget()) {
                widget.setContainer(this);
                loadContainer(this);
            } else {
                widget.setContainer(getContainer());
            }
            return;
        }
        LOG.warn("Duplicate child ID: [ {} ] type [ {} ]", widget.getLocalizedID(), widget.getWidgetName());
    }

    public void removeChild(AbstractWidget widget) {
        if(hasChild(widget.getLocalizedID())) {
            EventManager.callEvent(new WidgetRemoveEvent(widget, this, null));
            children.remove(widget);
            return;
        }
        LOG.warn("No child found ID [ {} ] type [ {} ]", widget.getLocalizedID(), widget.getWidgetName());
    }

    public boolean hasChild(String localizedID) {
        return getChild(localizedID) != null;
    }

    public AbstractWidget getChild(String localizedID) {
        Optional<AbstractWidget> result = children.stream()
                .findFirst().filter(child -> child.getLocalizedID().equals(localizedID));
        return result.orElse(null);
    }

    public boolean isFirstLayerWidget() {
        return getClass().getName().equals(Scene.class.getName());
    }

    protected void loadContainer(AbstractContainerWidget container) {
        for(AbstractWidget widget : children) {
            widget.setContainer(container);
            if(widget instanceof AbstractContainerWidget containerWidget) {
                containerWidget.loadContainer(container);
            }
        }
    }

}
