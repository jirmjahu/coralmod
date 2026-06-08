package net.coralmod.mod.render.impl.font;

import net.coralmod.mod.render.FontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.joml.Matrix3x2fStack;

import java.awt.*;

public class MinecraftFontRenderer implements FontRenderer {

    private final GuiGraphicsExtractor graphics;

    public MinecraftFontRenderer(GuiGraphicsExtractor graphics) {
        this.graphics = graphics;
    }

    @Override
    public void draw(String text, int x, int y, Color color) {
        draw(text, x, y, color, true);
    }

    @Override
    public void draw(String text, int x, int y, Color color, boolean shadow) {
        draw(text, x, y, 1.0F, color, shadow);
    }

    @Override
    public void draw(String text, int x, int y, float scale, Color color) {
        draw(text, x, y, scale, color, true);
    }

    @Override
    public void draw(String text, int x, int y, float scale, Color color, boolean shadow) {
        if (scale == 1.0F) {
            graphics.text(Minecraft.getInstance().font, text, x, y, color.getRGB(), shadow);
            return;
        }

        final Matrix3x2fStack stack = graphics.pose();
        stack.pushMatrix();
        stack.translate(x, y);
        stack.scale(scale, scale);
        graphics.text(Minecraft.getInstance().font, text, 0, 0, color.getRGB(), shadow);
        stack.popMatrix();
    }

    @Override
    public float width(String text, float scale) {
        return Minecraft.getInstance().font.width(text) * scale;
    }

    @Override
    public float height(float scale) {
        return Minecraft.getInstance().font.lineHeight * scale;
    }
}
