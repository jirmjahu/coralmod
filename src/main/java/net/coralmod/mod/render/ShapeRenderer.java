package net.coralmod.mod.render;

import java.awt.*;

public interface ShapeRenderer {

    void rect(float x, float y, float width, float height, Color color);

    void rectGradient(float x, float y, float width, float height, Color top, Color bottom);

    void outline(float x, float y, float width, float height, int thickness, Color color);

    void roundedRect(float x, float y, float width, float height, float radius, Color color);

}
