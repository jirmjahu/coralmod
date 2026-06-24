package net.coralmod.mod.render.impl.shape;

import net.coralmod.mod.render.ShapeRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.awt.*;

public class DefaultShapeRenderer implements ShapeRenderer {

    private final GuiGraphicsExtractor graphics;

    public DefaultShapeRenderer(GuiGraphicsExtractor graphics) {
        this.graphics = graphics;
    }

    @Override
    public void rect(int x, int y, int x1, int y1, Color color) {
        graphics.fill(x, y, x1, y1, color.getRGB());
    }

    @Override
    public void rectGradient(int x, int y, int x1, int y1, Color top, Color bottom) {
        graphics.fillGradient(x, y, x1, y1, top.getRGB(), bottom.getRGB());
    }

    @Override
    public void outline(int x, int y, int x1, int y1, int size, Color color) {
        graphics.fill(x, y, x1, y + size, color.getRGB());
        graphics.fill(x, y1 - size, x1, y1, color.getRGB());
        graphics.fill(x, y + size, x + size, y1 - size, color.getRGB());
        graphics.fill(x1 - size, y + size, x1, y1 - size, color.getRGB());
    }

    @Override
    public void roundedRect(int x, int y, float width, float height, float radius, Color color) {
        RoundedRectRenderer.render(graphics, x, y, width, height, radius, color);
    }
}
