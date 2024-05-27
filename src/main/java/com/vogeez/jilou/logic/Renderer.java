package com.vogeez.jilou.logic;

import com.vogeez.jilou.ui.AbstractWindowFrame;

/**
 * Interface is used to create new renderers for the ui system.
 * If you implemented this interface you will get all needed render functions.
 * This can be used for {@link AbstractWindowFrame}'s to create games or ui components.
 * Note that if you wish to create your own render system you need to write all the stuff
 * to render our components or other thinks. This make no sense if you use our library because why you use this
 * lib then?
 * @since 0.1.0-alpha
 * @see RenderPriority
 * @see AbstractWindowFrame
 * @author exepta
 */
@SuppressWarnings("unused")
public interface Renderer {

    /**
     * Function returned the name of the renderer.
     * This name need be final and can't duplicate!
     * @return {@link String}- the final name as identifier.
     */
    String getName();

    /**
     * Function returned the render priority of the renderer.
     * This can be change all time.
     * @return {@link RenderPriority}- the current render priority.
     */
    default RenderPriority getPriority() {
        return RenderPriority.MODERATE;
    }

    /**
     * Function change the current render priority.
     * @param priority the new render priority.
     */
    void setPriority(RenderPriority priority);

    /**
     * Function boolean will be return true if it was priority changes detected.
     * @return {@link Boolean}- true by change detect.
     */
    boolean neededPatch();

    /**
     * Function is used by the render() method in the window class.
     * Please don't change thinks here!
     * @param need - needed an update or not (Priority update!).
     */
    void setNeededPatch(boolean need);

    /**
     * Function returned true if the renderer was successfully initialized.
     * @return {@link Boolean}- the state of initialization.
     */
    boolean isInitialized();

    /**
     * Function is called at first if the renderer is starting.
     * Include here methods to called one time by starting the renderer.
     * Don't loop thinks in this method!
     */
    void initialize();

    /**
     * Function is called every tick of system.
     * This is needed to update the render all time and render graphics.
     * @param window the window witch need to be rendered.
     */
    void render(AbstractWindowFrame window);

    /**
     * This method clear and destroyed the current renderer.
     * After calling this method, the renderer is not enabled more and can't be drawing thinks.
     */
    void dispose();
}