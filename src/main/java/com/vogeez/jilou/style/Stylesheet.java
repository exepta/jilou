package com.vogeez.jilou.style;

import com.vogeez.jilou.math.Insets;
import com.vogeez.jilou.style.attributes.Color;
import com.vogeez.jilou.style.base.Background;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Stylesheet {

    @Builder.Default
    private int zIndex = 0;

    @Builder.Default
    private Background background = Background.color(Color.GREY);

    @Builder.Default
    private double fontSize = 12.0;

    @Builder.Default
    private String fontWeight = "normal";

    @Builder.Default
    private Color color = Color.BLACK;

    @Builder.Default
    private Insets padding = new Insets(0);

    @Builder.Default
    private Insets margin = new Insets(0);

    @Builder.Default
    private String position = "relative";

    @Builder.Default
    private String display = "block";

}
