package com.vogeez.jilou.ui;

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

    public void update() {

    }

    public void mountChildren() {

    }
}
