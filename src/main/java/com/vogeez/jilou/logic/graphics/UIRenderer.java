package com.vogeez.jilou.logic.graphics;

import com.vogeez.jilou.logic.AbstractRenderer;
import lombok.AccessLevel;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class UIRenderer extends AbstractRenderer {

    @Getter(AccessLevel.NONE)
    private final Logger LOG = LoggerFactory.getLogger(AbstractRenderer.class);

    public UIRenderer(String name) {
        super(name);
    }


}
