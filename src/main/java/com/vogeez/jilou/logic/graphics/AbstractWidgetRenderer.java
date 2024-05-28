package com.vogeez.jilou.logic.graphics;

import com.vogeez.jilou.logic.AbstractRenderer;
import com.vogeez.jilou.ui.AbstractWindow;
import com.vogeez.jilou.ui.AbstractWindowFrame;
import com.vogeez.jilou.ui.Scene;
import com.vogeez.jilou.ui.widgets.AbstractWidget;
import lombok.AccessLevel;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Getter
public abstract class AbstractWidgetRenderer extends AbstractRenderer {

    @Getter(AccessLevel.NONE)
    private final Logger LOG = LoggerFactory.getLogger(AbstractRenderer.class);

    public AbstractWidgetRenderer(String name) {
        super(name);
    }

    public abstract void render(List<AbstractWidget> widgetList);

    @Override
    protected void func(AbstractWindowFrame window) {
        if(window instanceof AbstractWindow uiWindow) {
            Scene scene = uiWindow.getScene();
            if(scene == null) return;
            render(scene.getAllChildren());
        }
    }
}
