package com.vogeez.jilou.math.align;

import lombok.Getter;

/**
 * @since 0.1.0-alpha
 * @author exepta
 */
@Getter
@SuppressWarnings("unused")
public enum Horizontal {

    /**
     * Represents nothing.
     */
    NOTHING(-1),

    /**
     * Indicates left horizontal position.
     */
    LEFT(0),

    /**
     * Indicates centered horizontal position.
     */
    CENTER(1),

    /**
     * Indicates right horizontal position.
     */
    RIGHT(2);

    private final int index;

    Horizontal(int index) {
        this.index = index;
    }

}