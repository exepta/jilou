package com.vogeez.jilou.math;

import lombok.Getter;

/**
 * Radius class creates an object that calculates all corners.
 * You can use it in the styling section for backgrounds and borders.
 * This class is designed to improve CSS styling and make it easier.
 * @since 0.1.0-alpha
 * @author exepta
 */
@Getter
@SuppressWarnings("unused")
public class Radius {

    public static final Radius FALLBACK_RADIUS = new Radius(0);

    private double topLeft;

    private double topRight;

    private double bottomLeft;

    private double bottomRight;

    /**
     * Constructor created an empty radius. default set 0.
     */
    public Radius() {
        this.set(0);
    }

    /**
     * Constructor created a radius object with all sides the same.
     * @param radius of all sides.
     */
    public Radius(double radius) {
        this.set(radius);
    }

    /**
     * Constructor created a radius object with new top and bottom radius.
     * @param top the top radius.
     * @param bottom the bottom radius.
     */
    public Radius(double top, double bottom) {
        this.set(top, bottom);
    }

    /**
     * Constructor created a radius object with new top left and right, bottom left and right radius.
     * @param topLeft the top left radius.
     * @param topRight the top right radius.
     * @param bottomLeft the bottom left radius.
     * @param bottomRight the bottom right radius.
     */
    public Radius(double topLeft, double topRight, double bottomLeft, double bottomRight) {
        this.set(topLeft, topRight, bottomLeft, bottomRight);
    }

    /**
     * Function change the current radius to the same.
     * @param radius the new radius for all sides.
     */
    public void set(double radius) {
        this.set(radius, radius);
    }

    /**
     * Function change the top and bottom radius.
     * @param top the new top radius.
     * @param bottom the new bottom radius.
     */
    public void set(double top, double bottom) {
        this.set(top, top, bottom, bottom);
    }

    /**
     * Function change all sides with differed values.
     * @param topLeft the top left radius.
     * @param topRight the top right radius.
     * @param bottomLeft the bottom left radius.
     * @param bottomRight the bottom right radius.
     */
    public void set(double topLeft, double topRight, double bottomLeft, double bottomRight) {
        this.topLeft = Maths.changeNegative(topLeft);
        this.topRight = Maths.changeNegative(topRight);
        this.bottomLeft = Maths.changeNegative(bottomLeft);
        this.bottomRight = Maths.changeNegative(bottomRight);
    }

}