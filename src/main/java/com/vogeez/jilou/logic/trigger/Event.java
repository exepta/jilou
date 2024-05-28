package com.vogeez.jilou.logic.trigger;

public interface Event {

    default String getName() {
        return getClass().getSimpleName();
    }

}
