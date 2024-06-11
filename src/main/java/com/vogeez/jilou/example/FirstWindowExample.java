package com.vogeez.jilou.example;

import com.vogeez.jilou.ApplicationFactory;
import com.vogeez.jilou.JILOUApplication;
import com.vogeez.jilou.math.align.Alignment;
import com.vogeez.jilou.style.attributes.Color;
import com.vogeez.jilou.style.base.Background;
import com.vogeez.jilou.ui.Window;
import com.vogeez.jilou.ui.layout.AlignPane;
import com.vogeez.jilou.ui.widgets.elements.DivWidget;

public class FirstWindowExample {

    public static void main(String[] args) {
        JILOUApplication.load(args);
        Window window = ApplicationFactory.createWindow(null, "Test", Window.class);
        window.setBackground(Color.rgb(30, 30, 40));

        DivWidget widget01 = new DivWidget();
        widget01.getStylesheet().setBackground(Background.color(Color.CORAL));
        widget01.setSize(200);
        widget01.getStylesheet().setAlignment(Alignment.CENTER);

/*        DivWidget widget02 = new DivWidget();
        widget02.getStylesheet().setBackground(Background.color(Color.RED));
        widget02.setPositionX(200);*/

        AlignPane pane = new AlignPane();
        pane.addChild(widget01);

        window.getScene().addChild(pane);
    }

}
