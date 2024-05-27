package com.vogeez.jilou.logic;

import lombok.Getter;

/**
 * Enum to specified which level on important the given {@link Renderer} is.
 * This is helpful if you wish to order your {@link Renderer} like background renderer then
 * font renderer etc...
 * @since 0.1.0-alpha
 * @author exepta
 */
@Getter
public enum RenderPriority {

    LOWEST(0),
    LOW(1),
    MEDIUM(2),
    MODERATE(3),
    HIGH(4),
    HIGHEST(5);

    private final int rate;

    RenderPriority(int rate) {
        this.rate = rate;
    }

}
