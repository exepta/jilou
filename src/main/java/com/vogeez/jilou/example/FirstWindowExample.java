package com.vogeez.jilou.example;

import com.vogeez.jilou.ApplicationFactory;
import com.vogeez.jilou.UIApplication;
import com.vogeez.jilou.style.Color;
import com.vogeez.jilou.ui.Window;

public class FirstWindowExample {

    public static void main(String[] args) {
        UIApplication.load(args);
        Window window = ApplicationFactory.createWindow(null, "Test", Window.class);
        window.setBackground(Color.CORAL);
    }

}
