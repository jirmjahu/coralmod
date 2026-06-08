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
    public void roundedRect(float x, float y, float width, float height, float radius, Color color) {
        RoundedRectRenderer.render(graphics, x, y, width, height, radius, color);
    }
}
