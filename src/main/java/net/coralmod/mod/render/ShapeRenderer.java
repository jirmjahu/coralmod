package net.coralmod.mod.render;

import java.awt.*;

public interface ShapeRenderer {

    void rect(int x, int y, int x1, int y1, Color color);

    void rectGradient(int x, int y, int x1, int y1, Color top, Color bottom);

    void outline(int x, int y, int x1, int y1, int size, Color color);

    void roundedRect(int x, int y, float width, float height, float radius, Color color);

}
