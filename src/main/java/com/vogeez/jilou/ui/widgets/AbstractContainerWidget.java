package com.vogeez.jilou.ui.widgets;

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
            children.add(widget);
            return;
        }
        LOG.warn("Duplicate child ID: [ {} ] type [ {} ]", widget.getLocalizedID(), widget.getWidgetName());
    }

    public void removeChild(AbstractWidget widget) {
        if(hasChild(widget.getLocalizedID())) {
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

}
