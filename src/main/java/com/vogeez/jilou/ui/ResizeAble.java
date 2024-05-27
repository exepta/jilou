package com.vogeez.jilou.ui;

/**
 * Interface for force resizeable functions to the implemented class.
 * Is used at {@link AbstractWindowFrame}
 * @since 1.0.0
 * @author Daniel Ramke
 */
public interface ResizeAble {

    /**
     * Function to set the resizeable state.
     * @param resizeable the new state.
     */
    void setResizeable(boolean resizeable);

    /**
     * @return {@link Boolean} - the current resizeable state.
     */
    boolean isResizeable();
}
