package com.vogeez.jilou.logic.graphics.components;

import com.vogeez.jilou.math.Radius;
import com.vogeez.jilou.style.Color;
import com.vogeez.jilou.style.ColorGradient;
import com.vogeez.jilou.style.ColorStop;
import com.vogeez.jilou.style.base.Background;
import com.vogeez.jilou.ui.AbstractWindowFrame;
import lombok.AccessLevel;
import lombok.Getter;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class NanoBackground {

    @Getter(AccessLevel.NONE)
    private final Logger LOG = LoggerFactory.getLogger(NanoBackground.class);

    private final long handle;
    private final AbstractWindowFrame window;


    public NanoBackground(AbstractWindowFrame window) {
        this.window = window;
        this.handle = window.getContext().nvgID();
        if(handle <= MemoryUtil.NULL) {
            LOG.error("NanoBackground Error: InitializeState >>> handle={}", handle);
        }
    }

    public void draw(double x, double y, double w, double h, Background background) {
        if(background == null) {
            drawColor(x, y, w, h, Color.GREY, Radius.FALLBACK_RADIUS);
            return;
        }

        Radius radius = background.getRadius();
        switch (background.getType()) {
            case COLOR -> drawColor(x, y, w, h, background.getColor(), radius);
            case LINEAR_GRADIENT -> drawGradient(x, y, w, h, background.getGradient(), radius);
        }
    }

    public void drawColor(double x, double y, double w, double h, Color color, Radius radius) {
        if(color == null) {
            color = Color.GREY;
        }

        if (radius == null) {
            radius = Radius.FALLBACK_RADIUS;
        }

        NanoVG.nvgBeginPath(handle);
        NanoVG.nvgPathWinding(handle, NanoVG.NVG_SOLID);
        createShape(x, y, w, h, radius);
        NanoVG.nvgFillColor(handle, color.getNanoColor());
        NanoVG.nvgFill(handle);
        NanoVG.nvgClosePath(handle);
    }

    public void drawGradient(double x, double y, double w, double h, ColorGradient gradient, Radius radius) {
        if(gradient == null) {
            drawColor(x, y, w, h, Color.GREY, radius);
            return;
        }

        if(radius == null) {
            radius = Radius.FALLBACK_RADIUS;
        }

        ColorStop[] colors = gradient.getGradient();
        for(int i = 0; i < colors.length; i++) {
            try (NVGPaint paint = NVGPaint.calloc()) {
                float angle = gradient.getAngle();

                float centerX = (float) x + (float) w * 0.5f;
                float centerY = (float) y + (float) h * 0.5f;
                float tmpX = centerX - (float) ((Math.cos(Math.toRadians(angle)) * w) * 0.5 + 0.5);
                float tmpY = centerY - (float) ((Math.sin(Math.toRadians(angle)) * h) * 0.5 + 0.5);
                float dirX = (float) Math.cos(Math.toRadians(angle));
                float dirY = (float) Math.sin(Math.toRadians(angle));
                float range = colors[i].portion();
                float next = colors[i + 1].portion();

                float startX = tmpX + (dirX * range * (float) w);
                float startY = tmpY + (dirY * range * (float) h);
                float endX = tmpX + (dirX * next * (float) w);
                float endY = tmpY + (dirY * next * (float) h);

                Color start = colors[i].color();
                if(i > 0) {
                    start = Color.transparent;
                }

                Color end = colors[i + 1].color();
                NanoVG.nvgLinearGradient(handle, startX, startY, endX, endY
                        , start.getNanoColor(), end.getNanoColor(), paint);
                NanoVG.nvgBeginPath(handle);
                createShape(x, y, w, h, radius);
                NanoVG.nvgFillPaint(handle, paint);
                NanoVG.nvgFill(handle);
                NanoVG.nvgClosePath(handle);
            }
        }
    }

    private void createShape(double x, double y, double w, double h, Radius radius) {
        NanoVG.nvgRoundedRectVarying(handle, (float) x, (float) y, (float) w, (float) h
                , (float) radius.getTopLeft()
                , (float) radius.getTopRight()
                , (float) radius.getBottomRight()
                , (float) radius.getBottomLeft());
    }

}
