package com.vogeez.jilou.example;

import com.vogeez.jilou.ApplicationFactory;
import com.vogeez.jilou.JILOUApplication;
import com.vogeez.jilou.style.attributes.Color;
import com.vogeez.jilou.style.base.Background;
import com.vogeez.jilou.ui.Window;
import com.vogeez.jilou.ui.layout.Pane;
import com.vogeez.jilou.ui.widgets.shapes.Rectangle;

public class FirstWindowExample {

    public static void main(String[] args) {
        JILOUApplication.load(args);
        Window window = ApplicationFactory.createWindow(null, "Test", Window.class);
        window.setBackground(Color.randomRGB());

        Rectangle rectangle = new Rectangle();
        rectangle.getStylesheet().setBackground(Background.color(Color.CORAL));
        Pane pane = new Pane();
        pane.addChild(rectangle);
        window.getScene().addChild(pane);
    }

}
