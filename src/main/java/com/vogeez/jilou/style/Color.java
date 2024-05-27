package com.vogeez.jilou.style;

import lombok.AccessLevel;
import lombok.Getter;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Color Library was created for the use with JILOU Library. You can choose many color options
 * like hsb, rgb, rgba, hexadecimal and primitive (Minecraft).
 * The library is used as the default one in this project. If you wish to work with {@link java.awt.Color}
 * from the AWT team, use {@link #asAWTColor(Color)}.
 * The library have the function to work with {@link NanoVG} from the LWJGL team. This needs color codes
 * int percent mode. You can use {@link #getNanoColor()} to send the color as {@link NVGColor} object.
 * For creating a color you can call a constructor, or you use one of them:
 * <ul>
 *     <li>{@link #rgb(int, int, int)}- for standard RGB format.</li>
 *     <li>{@link #rgba(int, int, int, int)}- for standard RGBA format.</li>
 *     <li>{@link #rgb(double, double, double)}- for RGB format which using percent numbers (1.0 = 255).</li>
 *     <li>{@link #rgba(double, double, double, double)}- for RGBA format which using percent numbers (1.0 = 255).</li>
 *     <li>{@link #hexadecimal(String)}- for hex code usage.</li>
 *     <li>{@link #hsb(float[])}- for hsb code usage.</li>
 *     <li>{@link #hsb(float, float, float)}- for hsb code usage.</li>
 *     <li>{@link #primitive(int)}- for primitive code usage like minecraft.</li>
 *     <li>{@link #randomRGB()}- for the not creative ones under us :D.</li>
 * </ul>
 * @since 0.1.0-alpha
 * @see Color
 * @see java.awt.Color
 * @see NanoVG
 * @author exepta
 */
@SuppressWarnings("unused")
@Getter
public class Color {

    @Getter(AccessLevel.NONE)
    private final Logger LOG = LoggerFactory.getLogger(Color.class);

    private int red, green, blue, alpha;
    private int primitiveCode;
    private float[] hsb;
    private String hexadecimal;

    private NVGColor nanoColor;

    /* Default Colors */
    public static final Color RED = new Color("#FF0000");
    public static final Color GREEN = new Color("#00FF00");
    public static final Color BLUE = new Color("#0000FF");
    public static final Color YELLOW = new Color("#FFFF00");
    public static final Color ORANGE = new Color("#FFA500");
    public static final Color PURPLE = new Color("#800080");
    public static final Color PINK = new Color("#FFC0CB");
    public static final Color WHITE = new Color("#FFFFFF");
    public static final Color BLACK = new Color("#000000");

    /* CSS Colors */
    public static final Color CORAL = new Color("#FF7F50");
    public static final Color CORNFLOWERBLUE = new Color("#6495ED");
    public static final Color MAGENTA = new Color("#FF00FF");
    public static final Color ROSE_RED = new Color("#C21E56");
    public static final Color GRAY = new Color("#808080");
    public static final Color GREY = new Color("#808080");
    public static final Color SEAGREEN = new Color("#2E8B57");
    public static final Color FORESTGREEN = new Color("#228B22");

    /* Special Colors */
    public static final Color NAVY = new Color("#000080");
    public static final Color DARKBLUE = new Color("#00008B");
    public static final Color MEDIUMBLUE = new Color("#0000CD");
    public static final Color CYAN = new Color("#00FFFF");
    public static final Color AQUA = new Color("#00FFFF");
    public static final Color POWDERBLUE = new Color("#B0E0E6");
    public static final Color LIGHTCYAN = new Color("#E0FFFF");
    public static final Color BLOOD_RED = new Color("#8a0303");

    /**
     * Constructor created a color from the primitive type.
     * Primitive numbers can be found online or can random set by the user.
     * @param primitive the needed value.
     */
    public Color(int primitive) {
        this.set(primitive);
    }

    /**
     * Constructor created a color from the hexadecimal type.
     * Hexadecimal type from #FFF - #FFFFFFFF.
     *
     * @param hexadecimal your hexadecimal code.
     */
    public Color(String hexadecimal) {
        this.setHexadecimal(hexadecimal);
    }

    /**
     * Constructor created a color from the given integers.
     * The range value is 0 - 255.
     * @param red   value.
     * @param green value.
     * @param blue  value.
     */
    public Color(int red, int green, int blue) {
        this(red, green, blue, 255);
    }

    /**
     * Constructor created a color from the given doubles.
     * The range value is 0.0 - 1.0 (0.0 = 0%, 1.0 = 100%)
     * @param red   value.
     * @param green value.
     * @param blue  value.
     */
    public Color(double red, double green, double blue) {
        this(red, green, blue, 1.0);
    }

    /**
     * Constructor created a color from the given floats.
     * The range value is 0.0f - 1.0f (0.0 = 0%, 1.0 = 100%)
     * @param hue        value.
     * @param saturation value.
     * @param brightness value.
     */
    public Color(float hue, float saturation, float brightness) {
        this.convertHsbToRgb(hue, saturation, brightness);
    }

    /**
     * Constructor created a color from the given integers.
     * The range value is 0 - 255.
     * @param red   value.
     * @param green value.
     * @param blue  value.
     * @param alpha value.
     */
    public Color(int red, int green, int blue, int alpha) {
        int rgba = ((alpha & 0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | ((blue & 0xFF));

        if(validation(red, green, blue, alpha)) {
            this.set(rgba);
            return;
        }
        this.set(0, 0, 0, 255);
    }

    /**
     * Constructor created a color from the given doubles.
     * The range value is 0.0 - 1.0 (0.0 = 0%, 1.0 = 100%)
     * @param red   value.
     * @param green value.
     * @param blue  value.
     * @param alpha value.
     */
    public Color(double red, double green, double blue, double alpha) {
        this((int) (red * 255), (int) (green * 255), (int) (blue * 255), (int) (alpha * 255));
    }

    /**
     * Function creates a clone of this object.
     * @return {@link Color}- this color class.
     */
    public Color copy() {
        return new Color(this.getPrimitiveCode());
    }

    /**
     * Function to set primitive codes. This functions called {@link #set(int, int, int, int)}.
     * @param primitive one {@link Integer} code.
     */
    public void set(int primitive) {
        this.primitiveCode = primitive;

        int red = (primitive >> 16) & 0xFF;
        int green = (primitive >> 8) & 0xFF;
        int blue = (primitive) & 0xFF;
        int alpha = (primitive >> 24) & 0xFF;

        this.set(red, green, blue, alpha);
    }

    /**
     * Function to set rgba codes. Note to use the correct code format as RGBA standard.
     * @param red value
     * @param green value
     * @param blue value
     * @param alpha value
     */
    public void set(int red, int green, int blue, int alpha) {
        float percentRed = red / 255.0f;
        float percentGreen = green / 255.0f;
        float percentBlue = blue / 255.0f;
        float percentAlpha = alpha / 255.0f;

        if(nanoColor == null) {
            nanoColor = NVGColor.calloc();
        }

        nanoColor.r(percentRed);
        nanoColor.g(percentGreen);
        nanoColor.b(percentBlue);
        nanoColor.a(percentAlpha);

        this.setRed(red);
        this.setGreen(green);
        this.setBlue(blue);
        this.setAlpha(alpha);
        this.generatePrimitiveCode(red, green, blue, alpha);
        this.convertRgbToHex(red, green, blue, alpha);
        this.convertRgbToHsb(red, green, blue);
    }

    /**
     * Function to load hexadecimal codes and convert them to RGBA standard format.
     * @param hexadecimal {@link String} hex code.
     */
    public void setHexadecimal(String hexadecimal) {
        hexadecimal = hexadecimal.toUpperCase();

        if (!(hexadecimal.startsWith("#"))) {
            hexadecimal = "#" + hexadecimal;
        }

        if (hexadecimal.length() == 9) {
            this.set(Integer.valueOf(hexadecimal.substring(1, 3), 16),
                    Integer.valueOf(hexadecimal.substring(3, 5), 16),
                    Integer.valueOf(hexadecimal.substring(5, 7), 16),
                    Integer.valueOf(hexadecimal.substring(7, 9), 16));
        } else if (hexadecimal.length() >= 7) {
            this.set(Integer.valueOf(hexadecimal.substring(1, 3), 16),
                    Integer.valueOf(hexadecimal.substring(3, 5), 16),
                    Integer.valueOf(hexadecimal.substring(5, 7), 16), 255);
        } else if (hexadecimal.length() >= 4) {
            String hexadecimalRed = hexadecimal.substring(1, 2);
            String hexadecimalGreen = hexadecimal.substring(2, 3);
            String hexadecimalBlue = hexadecimal.substring(3, 4);

            hexadecimalRed += hexadecimalRed;
            hexadecimalGreen += hexadecimalGreen;
            hexadecimalBlue += hexadecimalBlue;

            this.set(Integer.valueOf(hexadecimalRed, 16),
                    Integer.valueOf(hexadecimalGreen, 16),
                    Integer.valueOf(hexadecimalBlue, 16), 255);
        } else {
            LOG.error("Cannot parse hexadecimal code [ {} ]", hexadecimal);
            this.set(0, 0, 0, 255);
        }
        this.convertRgbToHex(getRed(), getGreen(), getBlue(), getAlpha());
    }

    /**
     * Function to generate a hexadecimal {@link String} from given RGBA code.
     * @param red value
     * @param green value
     * @param blue value
     * @param alpha value
     */
    public void convertRgbToHex(int red, int green, int blue, int alpha) {
        this.hexadecimal = "#" + Integer.toHexString(red)
                + Integer.toHexString(green)
                + Integer.toHexString(blue)
                + Integer.toHexString(alpha);
    }

    /**
     * Function to generate HSB from RGB.
     * @param red value
     * @param green value
     * @param blue value
     */
    public void convertRgbToHsb(int red, int green, int blue) {
        float hue;
        float saturation;
        float brightness;

        if (this.hsb == null) {
            this.hsb = new float[3];
        }

        int cMax = Math.max(red, green);
        if (blue > cMax) {
            cMax = blue;
        }

        int cMin = Math.min(red, green);
        if (blue < cMin) {
            cMin = blue;
        }

        brightness = ((float) cMax) / 255.0f;
        if (cMax != 0) {
            saturation = ((float) (cMax - cMin)) / ((float) cMax);
        } else {
            saturation = 0;
        }

        if (saturation == 0) {
            hue = 0;
        } else {
            float redComponent = ((float) (cMax - red)) / ((float) (cMax - cMin));
            float greenComponent = ((float) (cMax - green)) / ((float) (cMax - cMin));
            float blueComponent = ((float) (cMax - blue)) / ((float) (cMax - cMin));
            if (red == cMax) {
                hue = blueComponent - greenComponent;
            } else if (green == cMax) {
                hue = 2.0f + redComponent - blueComponent;
            } else {
                hue = 4.0f + greenComponent - redComponent;
            }
            hue = hue / 6.0f;
            if (hue < 0) {
                hue = hue + 1.0f;
            }
        }

        hsb[0] = hue;
        hsb[1] = saturation;
        hsb[2] = brightness;
    }

    /**
     * Function to generate RGB from HSB.
     * @param hue value
     * @param saturation value
     * @param brightness value
     */
    public void convertHsbToRgb(float hue, float saturation, float brightness) {
        int red = 0;
        int green = 0;
        int blue = 0;

        if (saturation == 0) {
            red = green = blue = (int) (brightness * 255.0f + 0.5f);
        } else {
            float tempH = (hue - (float) Math.floor(hue)) * 6.0f;
            float tempF = tempH - (float) Math.floor(tempH);
            float tempP = brightness * (1.0f - saturation);
            float tempQ = brightness * (1.0f - saturation * tempF);
            float tempT = brightness * (1.0f - (saturation * (1.0f - tempF)));

            switch ((int) tempH) {
                case 0 -> {
                    red = (int) (brightness * 255.0f + 0.5f);
                    green = (int) (tempT * 255.0f + 0.5f);
                    blue = (int) (tempP * 255.0f + 0.5f);
                }
                case 1 -> {
                    red = (int) (tempQ * 255.0f + 0.5f);
                    green = (int) (brightness * 255.0f + 0.5f);
                    blue = (int) (tempP * 255.0f + 0.5f);
                }
                case 2 -> {
                    red = (int) (tempP * 255.0f + 0.5f);
                    green = (int) (brightness * 255.0f + 0.5f);
                    blue = (int) (tempT * 255.0f + 0.5f);
                }
                case 3 -> {
                    red = (int) (tempP * 255.0f + 0.5f);
                    green = (int) (tempQ * 255.0f + 0.5f);
                    blue = (int) (brightness * 255.0f + 0.5f);
                }
                case 4 -> {
                    red = (int) (tempT * 255.0f + 0.5f);
                    green = (int) (tempP * 255.0f + 0.5f);
                    blue = (int) (brightness * 255.0f + 0.5f);
                }
                case 5 -> {
                    red = (int) (brightness * 255.0f + 0.5f);
                    green = (int) (tempP * 255.0f + 0.5f);
                    blue = (int) (tempQ * 255.0f + 0.5f);
                }
            }
        }
        this.set((red << 16), (green << 8), (blue), 0xff000000);
    }

    /**
     * Function for generating primitive color codes from RGBA code. This can be used for special color
     * models or languages which only supported one {@link Integer} value.
     * @param red value
     * @param green value
     * @param blue value
     * @param alpha value
     */
    public void generatePrimitiveCode(int red, int green, int blue, int alpha) {
        this.primitiveCode = ((alpha << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | ((blue & 0xFF)));
    }

    /**
     * Function for validate RGBA format standard's. This is helpful for handle
     * background colors or user feedback.
     * @param red value
     * @param green value
     * @param blue value
     * @param alpha value
     * @return {@link Boolean}- true if the code is valid.
     */
    public boolean validation(int red, int green, int blue, int alpha) {
        StringBuilder errorBuilder = new StringBuilder();

        if(alpha < 0 || alpha > 255) {
            errorBuilder.append(" ALPHA");
        }

        if(red < 0 || red > 255) {
            errorBuilder.append(" RED");
        }

        if(green < 0 || green > 255) {
            errorBuilder.append(" GREEN");
        }

        if(blue < 0 || blue > 255) {
            errorBuilder.append(" BLUE");
        }

        if(errorBuilder.isEmpty()) {
            LOG.warn("Color values out side of RGBA standard range [{} ]", errorBuilder);
            return false;
        }
        return true;
    }

    /**
     * Function for set alpha value. This value is checked for correct format,
     * this means it will be lowed or equal than 255, and higher or equal 0.
     * @param alpha value
     */
    public void setAlpha(int alpha) {
        if(alpha > 255) {
            alpha = 255;
        } else if(alpha < 0) {
            alpha = 0;
        }
        this.alpha = alpha;
    }

    /**
     * @return {@link Float}- the alpha value as float for 1.0 as 255.
     */
    public float getAlphaPercent() {
        return alpha / 255f;
    }

    /**
     * Function for set blue value. This value is checked for correct format,
     * this means it will be lowed or equal than 255, and higher or equal 0.
     * @param blue value
     */
    public void setBlue(int blue) {
        if(blue > 255) {
            blue = 255;
        } else if(blue < 0) {
            blue = 0;
        }
        this.blue = blue;
    }

    /**
     * @return {@link Float}- the blue value as float for 1.0 as 255.
     */
    public float getBluePercent() {
        return blue / 255f;
    }

    /**
     * Function for set green value. This value is checked for correct format,
     * this means it will be lowed or equal than 255, and higher or equal 0.
     * @param green value
     */
    public void setGreen(int green) {
        if(green > 255) {
            green = 255;
        } else if(green < 0) {
            green = 0;
        }
        this.green = green;
    }

    /**
     * @return {@link Float}- the green value as float for 1.0 as 255.
     */
    public float getGreenPercent() {
        return green / 255f;
    }

    /**
     * Function for set red value. This value is checked for correct format,
     * this means it will be lowed or equal than 255, and higher or equal 0.
     * @param red value
     */
    public void setRed(int red) {
        if(red > 255) {
            red = 255;
        } else if(red < 0) {
            red = 0;
        }
        this.red = red;
    }

    /**
     * @return {@link Float}- the red value as float for 1.0 as 255.
     */
    public float getRedPercent() {
        return red / 255f;
    }

    /**
     * Create a new color from the given int values. The color range for rgb (0 - 255).
     * If the value outside the range, the result color will be different to the wish color!
     * The alpha value is by default 255 (MAX)
     * @param red   the red value
     * @param green the green value
     * @param blue  the blue value
     * @return {@link Color}- finished color from the given values.
     */
    public static Color rgb(int red, int green, int blue) {
        return rgba(red, green, blue, 255);
    }

    /**
     * Function create a new color from the given int values. The color range for rgba (0 - 255).
     * If the value outside the range, the result color will be different to the wish color!
     * @param red   the red value
     * @param green the green value
     * @param blue  the blue value
     * @param alpha the alpha value
     * @return {@link Color}- finished color from the given values.
     */
    public static Color rgba(int red, int green, int blue, int alpha) {
        return new Color(red, green, blue, alpha);
    }

    /**
     * Function create a new color from the given int values. The color range for rgb (0.0 - 1.0).
     * If the value outside the range, the result color will be different to the wish color!
     * The alpha value is by default 1.0 (MAX)
     * @param red   the red value
     * @param green the green value
     * @param blue  the blue value
     * @return {@link Color}- finished color from the given values.
     */
    public static Color rgb(double red, double green, double blue) {
        return rgba(red, green, blue, 1.0);
    }

    /**
     * Function create a new color from the given int values. The color range for rgba (0.0 - 1.0).
     * If the value outside the range, the result color will be different to the wish color!
     * @param red   the red value
     * @param green the green value
     * @param blue  the blue value
     * @param alpha the alpha value
     * @return {@link Color}- finished color from the given values.
     */
    public static Color rgba(double red, double green, double blue, double alpha) {
        return new Color(red, green, blue, alpha);
    }

    /**
     * Function creates a color from the given hsb values.
     * Note this method is raw and not all user use this, The reason is we have rgba support.
     * @param hue        the color temperature
     * @param saturation the color saturation
     * @param brightness the color lightning
     * @return {@link Color}- finished color from the given hsb codes.
     */
    public static Color hsb(float hue, float saturation, float brightness) {
        return new Color(hue, saturation, brightness);
    }

    /**
     * Function creates a color from the given hsb values.
     * Note this method is raw and not all user use this, The reason is we have rgba support.
     * @param hsb an array with length 3;
     * @return {@link Color}- finished color from the given hsb codes.
     */
    public static Color hsb(float[] hsb) {
        if (hsb.length < 3) {
            return Color.BLACK;
        }
        return hsb(hsb[0], hsb[1], hsb[2]);
    }

    /**
     * Function creates a color from the given hexadecimal string value.
     * @param hexadecimal e.g #FFFFFF for white color.
     * @return {@link Color}- finished color from the given hex code.
     */
    public static Color hexadecimal(String hexadecimal) {
        return new Color(hexadecimal);
    }

    /**
     * Function create a new color form the primitive int value. This means you can create some color from all components.
     * The components (red green blue, alpha).
     * @param primitive the primitive number like -100225667
     * @return {@link Color}- finished color from the given components.
     */
    public static Color primitive(int primitive) {
        return new Color(primitive);
    }

    /**
     * Function creates a new color from a java Random. The color value is created by mixing the {@link Color#rgb(int, int, int)} method.
     * @return {@link Color}- finished color from the random.
     */
    public static Color randomRGB() {
        Random random = new Random();
        return rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }


    /**
     * @param color {@link Color} our color class object filled with a color.
     * @return {@link java.awt.Color}- Java standard color library.
     */
    public static java.awt.Color asAWTColor(Color color) {
        return new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

}
