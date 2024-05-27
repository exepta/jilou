package com.vogeez.jilou.math;

import lombok.Getter;

/**
 * Inset's class is used to calculate and create margin and padding.
 * The use of this class is mainly represented in the field of styling.
 * @since 0.1.0-alpha
 * @author exepta
 */
@Getter
@SuppressWarnings("unused")
public class Insets {

    private double top;
    private double bottom;
    private double left;
    private double right;
    private int hash;

    /**
     * Constructor creates a new insets object.
     * @param top the top distance.
     * @param bottom the bottom distance.
     * @param left the left distance.
     * @param right the right distance.
     */
    public Insets(double top, double bottom, double left, double right) {
        this.set(top, bottom, left, right);
    }

    /**
     * Constructor creates a new insets object.
     * @param vertical the vertical distance.
     * @param horizontal the horizontal distance.
     */
    public Insets(double vertical, double horizontal) {
        this.set(vertical, horizontal);
    }

    /**
     * Constructor creates a new insets object.
     * @param all the all sides distance.
     */
    public Insets(double all) {
        this.set(all);
    }

    /**
     * Function change the current insets witch was set by the constructor.
     * @param top the top distance.
     * @param bottom the bottom distance.
     * @param left the left distance.
     * @param right the right distance.
     */
    public void set(double top, double bottom, double left, double right) {
        this.top = Maths.changeNegative(top);
        this.bottom = Maths.changeNegative(bottom);
        this.left = Maths.changeNegative(left);
        this.right = Maths.changeNegative(right);
        this.hash = 0;
    }

    /**
     * Function change the current insets witch was set by the constructor.
     * @param vertical the vertical distance.
     * @param horizontal the horizontal distance.
     */
    public void set(double vertical, double horizontal) {
        this.set(vertical, vertical, horizontal, horizontal);
    }

    /**
     * Function change the current insets witch was set by the constructor.
     * @param all the all sides distance.
     */
    public void set(double all) {
        this.set(all, all);
    }

    /**
     * Function was override to manage it better.
     * @param obj the object to check it is the same or not.
     * @return {@link Boolean} - true if they object the same.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(obj instanceof Insets other) {
            return top == other.top && bottom == other.bottom && left == other.left && right == other.right;
        }

        return false;
    }

    /**
     * @return {@link Integer} - the converted hashcode.
     */
    @Override
    public int hashCode() {
        if (hash == 0) {
            long bits = 17L;
            bits = 37L * bits + Double.doubleToLongBits(top);
            bits = 37L * bits + Double.doubleToLongBits(right);
            bits = 37L * bits + Double.doubleToLongBits(bottom);
            bits = 37L * bits + Double.doubleToLongBits(left);
            hash = (int) (bits ^ (bits >> 32));
        }
        return hash;
    }

    /**
     * @return {@link String} - the insets object as string.
     */
    @Override
    public String toString() {
        return "Insets [top=" + top + ", bottom=" + bottom + ", left=" + left + ", right=" + right + "]";
    }

    /**
     * @return {@link Double} - the inset width.
     */
    public double getWidth() {
        return left + right;
    }

    /**
     * @return {@link Double} - the inset height.
     */
    public double getHeight() {
        return top + bottom;
    }

}
