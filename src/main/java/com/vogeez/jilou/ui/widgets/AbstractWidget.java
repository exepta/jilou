package com.vogeez.jilou.ui.widgets;

import com.vogeez.jilou.ApplicationFactory;
import com.vogeez.jilou.style.Stylesheet;
import com.vogeez.jilou.ui.ResizeAble;
import com.vogeez.jilou.ui.Window;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

@Getter
public abstract class AbstractWidget implements ResizeAble {

    private final String localizedID;

    private double width;
    private double height;

    @Setter
    private double positionX;
    @Setter
    private double positionY;

    private String name;

    @Setter
    private AbstractContainerWidget parent;
    @Setter
    private AbstractContainerWidget container;

    private Stylesheet stylesheet;

    /* ############################################################################################
     *
     *                                           Constructors
     *
     * ############################################################################################ */

    /**
     * Constructor create new widget by using this class. You can create you own widget's and use there with
     * a normal {@link Window} class. This is simple, and you can show examples in the example package.
     * @param name the name of this created widget. (by null it the name of widget itself)
     */
    public AbstractWidget(String name) {
        this.localizedID = ApplicationFactory.giveUniqueID();
        this.setSize(0);
        this.setPosition(0);
        this.setName(name);
        this.parent = null;
        this.container = null;
        this.stylesheet = Stylesheet.builder().build();
    }

    /* ############################################################################################
     *
     *                                           Getter / Setter
     *
     * ############################################################################################ */

    /**
     * @return {@link String}- {@link Class#getSimpleName()} is the name of widget.
     */
    public String getWidgetName() {
        return getClass().getSimpleName();
    }

    /**
     * Function to set the size every time to the same.
     * This mean one value for width and height.
     * @param size the one dimension for the widget.
     */
    public void setSize(double size) {
        this.setSize(size, size);
    }

    /**
     * Function to set the width and height in one function.
     * @param width new widget width.
     * @param height new widget height.
     */
    public void setSize(double width, double height) {
        this.setWidth(width);
        this.setHeight(height);
    }

    /**
     * Function set simple the width of the widget.
     * @param width new widget width.
     */
    public void setWidth(double width) {
        if(width < 0) {
            width = 0;
        }
        this.width = width;
    }

    /**
     * Function set simple the height of the widget.
     * @param height new widget height.
     */
    public void setHeight(double height) {
        if(height < 0) {
            height = 0;
        }
        this.height = height;
    }

    /**
     * Function to set the widget in the coordinate system at an exact point.
     * x and y will be the same.
     * @param position the correct widget position.
     */
    public void setPosition(double position) {
        this.setPosition(position, position);
    }

    /**
     * Function to set the x and y position displayed in the coordinate system.
     * @param x new x position.
     * @param y new y position.
     */
    public void setPosition(double x, double y) {
        this.setPositionX(x);
        this.setPositionY(y);
    }

    /**
     * Function to set a name. Is {@link #getWidgetName()} if the param null.
     * @param name widget name readable and set by the programmer.
     */
    public void setName(String name) {
        if(name == null || name.isBlank() || name.isEmpty()) {
            name = getWidgetName();
        }
        this.name = name;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public void setStylesheet(@Nullable Stylesheet stylesheet) {
        if(stylesheet == null) {
            stylesheet = Stylesheet.builder().build();
        }
        this.stylesheet = stylesheet;
    }

    @Override
    public void setResizeable(boolean resizeable) {

    }

    @Override
    public boolean isResizeable() {
        return false;
    }
}
