package com.vogeez.jilou.style;

import org.jetbrains.annotations.Nullable;

/**
 * This class was created for helping the Gradient class.
 * The gradient can display more than 2 colors with this class.
 * For more information to this class (record) look for aur website: [LINK].
 * This record controls a color with stops.
 * The stops mean that this color have an area.
 * @since 0.1.0-alpha
 * @see Color
 * @author exepta
 * @param color - the color.
 * @param portion - the portion of the colored area.
 */
@Nullable
public record ColorStop(Color color, float portion) {

    /**
     * Record for check the given inputs. Change things if there was set a null or lower than zero.
     * @see Color
     * @param color {@link Color}
     * @param portion the specified area
     */
    public ColorStop {
        if(color == null) {
            color = Color.GREY;
        }
        if(portion < 0) {
            portion = 0;
        }
    }

}
