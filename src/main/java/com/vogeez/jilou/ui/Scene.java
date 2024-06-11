package com.vogeez.jilou.ui;

import com.vogeez.jilou.logic.graphics.AbstractWidgetRenderer;
import com.vogeez.jilou.ui.widgets.AbstractContainerWidget;
import com.vogeez.jilou.ui.widgets.AbstractWidget;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Scene extends AbstractContainerWidget {

    private final List<AbstractWidget> allChildren;

    public Scene() {
        this(null);
    }

    public Scene(String localizedName) {
        super(localizedName);
        this.allChildren = new ArrayList<>();
    }

    @Override
    public void update() {
        for(AbstractWidget widget : allChildren) {
            widget.update();
        }
    }

    public void pack() {
        collect(this, allChildren);
    }

    /**
     * Function to unmount all {@link AbstractWidget}'s from all children. This is needed
     * for the {@link AbstractWidgetRenderer} to work.
     * @param root parent {@link AbstractContainerWidget} which contains other {@link AbstractWidget}.
     * @param widgets the child {@link AbstractWidget}'s to store.
     */
    private void collect(AbstractWidget root, List<AbstractWidget> widgets) {
        if(root instanceof AbstractContainerWidget containerWidget) {
            if(!containerWidget.isFirstLayerWidget()) {
                widgets.add(containerWidget);
            }
        } else {
            widgets.add(root);
        }
        if(root instanceof AbstractContainerWidget containerWidget) {
            for(AbstractWidget widget : containerWidget.getChildren()) {
                collect(widget, allChildren);
            }
        }
    }
}
