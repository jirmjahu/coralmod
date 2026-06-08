package net.coralmod.mod.render;

import java.awt.*;

public interface ShapeRenderer {

    void rect(float x, float y, float width, float height, Color color);

    void roundedRect(float x, float y, float width, float height, float radius, Color color);

}
