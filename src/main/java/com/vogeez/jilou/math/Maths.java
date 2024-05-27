package com.vogeez.jilou.math;

/**
 * Math's class is a collection class.
 * It has different formulas and ways of calculating which we can often use.
 * This class can be constantly expanded if required.
 * @since 0.1.0-alpha
 * @author exepta
 */
public class Maths {

    public static final float _90 = (float) (Math.PI / 2);

    public static final float _270 = (float) (3 * Math.PI / 2);

    /**
     * Function checks if the value negative.
     * If the value negative so change the method it to value 0.
     * @param value the value to check.
     * @return {@link Double} - the result of the value.
     */
    public static double changeNegative(double value) {
        return value < 0 ? 0 : value;
    }

}