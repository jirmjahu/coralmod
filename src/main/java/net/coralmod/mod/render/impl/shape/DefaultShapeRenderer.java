package net.coralmod.mod.render.impl.shape;

import net.coralmod.mod.render.ShapeRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.joml.Vector4f;

import java.awt.*;

public class DefaultShapeRenderer implements ShapeRenderer {

    private final GuiGraphicsExtractor graphics;

    public DefaultShapeRenderer(GuiGraphicsExtractor graphics) {
        this.graphics = graphics;
    }

    @Override
    public void rect(float x, float y, float width, float height, Color color) {
        graphics.fill(Math.round(x), Math.round(y), Math.round(x + width), Math.round(y + height), color.getRGB());
    }

    @Override
    public void rectGradient(float x, float y, float width, float height, Color top, Color bottom) {
        graphics.fillGradient(Math.round(x), Math.round(y), Math.round(x + width), Math.round(y + height), top.getRGB(), bottom.getRGB());
    }

    @Override
    public void outline(float x, float y, float width, float height, int thickness, Color color) {
        int x1 = Math.round(x), y1 = Math.round(y), x2 = Math.round(x + width), y2 = Math.round(y + height);
        graphics.fill(x1, y1, x2, y1 + thickness, color.getRGB());
        graphics.fill(x1, y2 - thickness, x2, y2, color.getRGB());
        graphics.fill(x1, y1 + thickness, x1 + thickness, y2 - thickness, color.getRGB());
        graphics.fill(x2 - thickness, y1 + thickness, x2, y2 - thickness, color.getRGB());
    }

    @Override
    public void roundedRect(float x, float y, float width, float height, float radius, Color color) {
        RoundedRectRenderer.render(graphics, x, y, width, height, radius, color);
    }
}
