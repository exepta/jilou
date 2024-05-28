package com.vogeez.jilou.example;

import com.vogeez.jilou.ApplicationFactory;
import com.vogeez.jilou.JILOUApplication;
import com.vogeez.jilou.style.Color;
import com.vogeez.jilou.ui.Window;
import com.vogeez.jilou.ui.widgets.shapes.Rectangle;

public class FirstWindowExample {

    public static void main(String[] args) {
        JILOUApplication.load(args);
        Window window = ApplicationFactory.createWindow(null, "Test", Window.class);
        window.setBackground(Color.randomRGB());

        Rectangle rectangle = new Rectangle();
        window.getScene().addChild(rectangle);
    }

}
