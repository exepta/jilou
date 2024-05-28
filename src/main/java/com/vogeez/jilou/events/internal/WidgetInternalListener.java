package com.vogeez.jilou.events.internal;

import com.vogeez.jilou.events.widgets.WidgetAddEvent;
import com.vogeez.jilou.events.widgets.WidgetRemoveEvent;
import com.vogeez.jilou.logic.trigger.EventHandler;
import com.vogeez.jilou.logic.trigger.EventPriority;
import com.vogeez.jilou.ui.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WidgetInternalListener {

    private final Logger LOG = LoggerFactory.getLogger(WidgetInternalListener.class);

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWidgetAdd(WidgetAddEvent event) {
        if(event.container() != null) {
            if(event.container() instanceof Scene scene) {
                scene.pack();
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWidgetRemove(WidgetRemoveEvent event) {
        if(event.container() != null) {
            if(event.container() instanceof Scene scene) {
                scene.pack();
            }
        }
    }

}
