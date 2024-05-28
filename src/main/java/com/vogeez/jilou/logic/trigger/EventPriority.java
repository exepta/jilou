package com.vogeez.jilou.logic.trigger;

import lombok.Getter;

@Getter
public enum EventPriority {

    LOWEST(10),
    LOW(5),
    MEDIUM(4),
    MODERATE(3),
    HIGH(2),
    HIGHEST(1),
    ABSOLUTE_FIRST(0);

    private final Integer rating;

    EventPriority(final Integer rating) {
        this.rating = rating;
    }

}