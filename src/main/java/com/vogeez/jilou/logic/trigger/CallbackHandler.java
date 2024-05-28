package com.vogeez.jilou.logic.trigger;

import java.lang.reflect.Method;

public record CallbackHandler(Object listener, Method method, EventHandler eventHandler) {

    public Integer getPriority() {
        return eventHandler.priority().getRating();
    }

    public boolean isIgnoreCancelled() {
        return eventHandler.ignoreCancelled();
    }

}
