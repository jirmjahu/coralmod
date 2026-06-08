package net.coralmod.mod.render;

import java.awt.Color;

public interface FontRenderer {

    void draw(String text, float x, float y, Color color);

    void draw(String text, float x, float y, Color color, boolean shadow);

    float width(String text);

    float height();

}
