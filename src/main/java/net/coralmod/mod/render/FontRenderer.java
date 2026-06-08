package net.coralmod.mod.render;

import java.awt.Color;

public interface FontRenderer {

    void draw(String text, int x, int y, Color color);

    void draw(String text, int x, int y, Color color, boolean shadow);

    void draw(String text, int x, int y, float scale, Color color);

    void draw(String text, int x, int y, float scale, Color color, boolean shadow);

    float width(String text, float scale);

    float height(float scale);

}
