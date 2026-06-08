package net.coralmod.mod.render;

import java.awt.*;

public interface ShapeRenderer {

    void rect(int x, int y, float width, float height, Color color);

    void rectGradient(int x, int y, float width, float height, Color top, Color bottom);

    void outline(int x, int y, int x2, int y2, int size, Color color);

    void roundedRect(int x, int y, float width, float height, float radius, Color color);

}
