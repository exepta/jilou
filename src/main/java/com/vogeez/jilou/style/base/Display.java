package com.vogeez.jilou.style.base;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class Display {

    private Type type;

    public Display() {
        this("block");
    }

    public Display(Type type) {
        this(type.getCssAttribute());
    }

    public Display(String display) {
        this.type = Type.fetch(display);
    }

    @Getter
    public enum Type {
        BLOCK("block"),
        FLEX("flex"),
        GRID("");

        private final String cssAttribute;

        Type(String cssAttribute) {
            this.cssAttribute = cssAttribute;
        }

        public static Type fetch(String cssAttribute) {
            return Arrays
                    .stream(values()).findFirst()
                    .filter(f -> f.getCssAttribute().equalsIgnoreCase(cssAttribute))
                    .orElse(Type.BLOCK);
        }
    }

    public static Display block() {
        return new Display("block");
    }

    public static Display flex() {
        return new Display("flex");
    }

    public static Display grid() {
        return new Display("grid");
    }

}
