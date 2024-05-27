package com.vogeez.jilou.style;

import lombok.Getter;

import java.util.List;

/**
 * Class for storing color gradients. This will work with css to!
 * If you wish that your window or component background look boring anymore, then use this class!
 * Note that this class contains functions which need to be care! Don't include null or empty things
 * to the params!
 * @since 0.1.0-alpha
 * @see Color
 * @see ColorStop
 * @author exepta
 */
@SuppressWarnings("unused")
@Getter
public class ColorGradient {

    private final Color start;
    private final Color end;
    private final ColorStop[] gradient;
    private Direction direction;
    private float angle;

    /**
     * Constructor creates a new gradient with the specified colors.
     * @param colors - choose 2 or more colors for create a color gradient.
     */
    public ColorGradient(Color... colors) {
        if(colors.length == 2) {
            start = colors[0];
            end = colors[1];
        } else {
            start = colors[0];
            end = colors[colors.length - 1];
        }

        this.direction = Direction.TOP_TO_BOTTOM;
        setAngle(direction.getAngle());
        this.gradient = new ColorStop[colors.length];
        for(int i = 0; i < colors.length; i++) {
            gradient[i] = new ColorStop(colors[i], (float) i / (float) (colors.length - 1));
        }
    }

    /**
     * Function to change the current {@link Direction} of the gradient but for experts.
     * You can change the direction in steps. For easy usage we recommend to use {@link #setAngle(Direction)}.
     * @param angle the correct angle from 0 to 360.
     */
    public void setAngle(float angle) {
        if(angle < 0 || angle > 360) {
            angle = Direction.TOP_TO_BOTTOM.getAngle();
        }
        this.angle = angle;
        this.direction = Direction.get(angle);
    }

    /**
     * Function for set the {@link Direction} of this gradient.
     * @param direction new direction.
     */
    public void setAngle(Direction direction) {
        this.setAngle(direction.getAngle());
    }

    /**
     * Enum constance stored the known css gradient values.
     * With these values you can change the displayed direction of your gradient.
     * Note that this enum is only used for the gradient class and will not work if you try it in other classes.
     * @since 0.1.0-alpha
     * @author exepta
     */
    @Getter
    public enum Direction {
        TOP_LEFT_TO_BOTTOM_RIGHT("to bottom right", 45),
        TOP_TO_BOTTOM("to bottom", 90),
        TOP_RIGHT_TO_BOTTOM_LEFT("to bottom left", 135),
        CENTER_RIGHT_TO_CENTER_LEFT("to left", 180),
        BOTTOM_RIGHT_TO_TOP_LEFT("to top left", 225),
        BOTTOM_TO_TOP("to top", 270),
        BOTTOM_LEFT_TO_TOP_RIGHT("to top right", 315),
        CENTER_LEFT_TO_CENTER_RIGHT("to right", 360),
        NONE("none", 0);

        private final String cssName;
        private final float angle;

        Direction(String cssName, float angle) {
            this.cssName = cssName;
            this.angle = angle;
        }

        /**
         * Function returned the founded enum object by css code.
         * @param css the linear-gradient(to right) code.
         * @return {@link Direction}- the founded direction.
         */
        public static Direction get(String css) {
            Direction direction = NONE;
            for(Direction directions : values()) {
                if(directions.getCssName().equals(css)) {
                    direction = directions;
                    break;
                }
            }
            return direction;
        }

        /**
         * Function returned the founded enum object by angle.
         * @param angle the angle direction.
         * @return {@link Direction}- the founded direction.
         */
        public static Direction get(float angle) {
            Direction direction = NONE;
            for(Direction directions : values()) {
                if(directions.getAngle() == angle) {
                    direction = directions;
                    break;
                }
            }
            return direction;
        }
    }

    /**
     * Function to create a simple {@link ColorGradient}.
     * @param start color at gradient start.
     * @param end color at gradient end.
     * @return {@link ColorGradient}- the gradient color object
     */
    public static ColorGradient generate(Color start, Color end) {
        return new ColorGradient(start, end);
    }

    /**
     * Function to create a complex gradient with more than 2 colors.
     * @param colors your wish colors.
     * @return {@link ColorGradient}- the gradient color object
     */
    public static ColorGradient generate(Color... colors) {
        return new ColorGradient(colors);
    }

    /**
     * Function to create a complex gradient with more than 2 colors.
     * @param colors your wish colors.
     * @return {@link ColorGradient}- the gradient color object
     */
    public static ColorGradient generate(List<Color> colors) {
        Color[] tmp = new Color[colors.size()];
        for(int i = 0; i < colors.size(); i++) {
            tmp[i] = colors.get(i);
        }
        return generate(tmp);
    }

}
