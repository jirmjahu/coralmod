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
    public void rect(int x, int y, float width, float height, Color color) {
        graphics.fill(x, y, Math.round(x + width), Math.round(y + height), color.getRGB());
    }

    @Override
    public void rectGradient(int x, int y, float width, float height, Color top, Color bottom) {
        graphics.fillGradient(x, y, Math.round(x + width), Math.round(y + height), top.getRGB(), bottom.getRGB());
    }

    @Override
    public void outline(int x, int y, int x2, int y2, int size, Color color) {
        graphics.fill(x, y, x2, y + size, color.getRGB());
        graphics.fill(x, y2 - size, x2, y2, color.getRGB());
        graphics.fill(x, y + size, x + size, y2 - size, color.getRGB());
        graphics.fill(x2 - size, y + size, x2, y2 - size, color.getRGB());
    }

    @Override
    public void roundedRect(int x, int y, float width, float height, float radius, Color color) {
        RoundedRectRenderer.render(graphics, x, y, width, height, radius, color);
    }
}
