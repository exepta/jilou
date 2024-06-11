package com.vogeez.jilou.style.base;

import com.vogeez.jilou.math.Radius;
import com.vogeez.jilou.style.attributes.Color;
import com.vogeez.jilou.style.attributes.ColorGradient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Background {

    private Type type;

    private Color color;
    private ColorGradient gradient;
    private Radius radius;

    public Background(Type type) {
        this.type = type;
        this.setRadius(new Radius(0));
        this.setColor(Color.GREY);
        this.setGradient(ColorGradient.generate(Color.GREY, Color.rgb(30, 30, 30)));
    }

    public Background(Color color) {
        this(Type.COLOR);
        this.setColor(color);
    }

    public Background(ColorGradient gradient) {
        this(Type.LINEAR_GRADIENT);
        this.setGradient(gradient);
    }

    /**
     * Enum provides the existing background types.
     * The types ar needed for the renderer it will call the correct methods.
     * In the type enum, you can get the attribute names from css for help.
     * If you use css instanceof the default builder system note that not all attribute names exist!
     */
    @Getter
    public enum Type {

        IMAGE("background-image", new String[]{"url"}),
        COLOR("background-color", new String[]{"rgb", "rgba", "#"}),
        LINEAR_GRADIENT("background-color", new String[]{"linear-gradient"});

        private final String tag;

        private final String[] css_func_aliases;

        Type(String tag, String[] css_func_aliases) {
            this.tag = tag;
            this.css_func_aliases = css_func_aliases;
        }

    }

    /**
     * Function create a {@link Background} from the given {@link Color}.
     * @param color background color
     * @return {@link Background} - background object
     */
    public static Background color(Color color) {
        return new Background(color);
    }

    /**
     * Function create a {@link Background} from the given {@link ColorGradient}.
     * @param gradient background gradient
     * @return {@link Background} - background object
     */
    public static Background gradient(ColorGradient gradient) {
        return new Background(gradient);
    }
}
