package com.vogeez.jilou.math.align;

import lombok.Getter;

/**
 * @since 0.1.0-alpha
 * @author exepta
 */
@Getter
public enum Vertical {

    /**
     * Represents nothing.
     */
    NOTHING(-1),

    /**
     * Indicates top vertical position
     */
    TOP(0),

    /**
     * Indicates centered vertical position
     */
    CENTER(1),

    /**
     * Indicates bottom vertical position
     */
    BOTTOM(2);

    private final int index;

    Vertical(int index) {
        this.index = index;
    }


}