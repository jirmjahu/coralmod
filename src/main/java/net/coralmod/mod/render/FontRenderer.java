package net.coralmod.mod.render;

import java.awt.Color;

public interface FontRenderer {

    void draw(String text, float x, float y, Color color);

    float width(String text);

    float height();

}
