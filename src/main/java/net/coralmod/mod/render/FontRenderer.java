package net.coralmod.mod.render;

import java.awt.Color;

public interface FontRenderer {

    void draw(String text, int x, int y, Color color);

    void draw(String text, int x, int y, Color color, boolean shadow);

    void draw(String text, int x, int y, float scale, Color color);

    void draw(String text, int x, int y, float scale, Color color, boolean shadow);

    int width(String text, float scale);

    int height(float scale);

    default int width(String text) {
        return width(text, 1.0F);
    }

    default int height() {
        return height(1.0F);
    }
}
