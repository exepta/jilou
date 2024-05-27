package com.vogeez.jilou.logic;

import com.vogeez.jilou.ApplicationFactory;
import com.vogeez.jilou.ui.AbstractWindowFrame;
import lombok.AccessLevel;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 0.1.0-alpha
 * @author exepta
 */
@Getter
public abstract class AbstractRenderer implements Renderer {

    @Getter(AccessLevel.NONE)
    private final Logger LOG = LoggerFactory.getLogger(AbstractRenderer.class);

    private final String localizedID;
    private final String name;

    private RenderPriority priority;
    private AbstractWindowFrame window;

    private boolean initialized;
    private boolean needPatch;

    public AbstractRenderer(String name) {
        this.localizedID = ApplicationFactory.giveRenderID();
        this.name = name == null ? getClass().getSimpleName() : name;
        this.priority = RenderPriority.MODERATE;
        this.initialized = false;
        this.needPatch = false;
    }

    public abstract void preLoad(AbstractWindowFrame window);
    protected abstract void func(AbstractWindowFrame window);

    @Override
    public void initialize() {
        if(initialized) {
            return;
        }
        initialized = true;
        LOG.info("Initializing Renderer for [ {} ]", localizedID);
    }

    @Override
    public void render(AbstractWindowFrame window) {
        if(window == null) {
            return;
        }

        if(this.window == null) {
            this.window = window;
            preLoad(window);
        }

        func(window);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setPriority(RenderPriority priority) {
        this.priority = priority;
        this.setNeededPatch(true);
    }

    @Override
    public RenderPriority getPriority() {
        return priority;
    }

    @Override
    public void setNeededPatch(boolean need) {
        this.needPatch = need;
    }

    @Override
    public boolean neededPatch() {
        return needPatch;
    }

}
