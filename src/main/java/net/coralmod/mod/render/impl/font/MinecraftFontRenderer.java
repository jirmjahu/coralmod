package net.coralmod.mod.render.impl.font;

import net.coralmod.mod.render.FontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.awt.*;

public class MinecraftFontRenderer implements FontRenderer {

    private final GuiGraphicsExtractor graphics;

    public MinecraftFontRenderer(GuiGraphicsExtractor graphics) {
        this.graphics = graphics;
    }

    @Override
    public void draw(String text, float x, float y, Color color) {
        draw(text, x, y, color, true);
    }

    @Override
    public void draw(String text, float x, float y, Color color, boolean shadow) {
        graphics.text(Minecraft.getInstance().font, text, (int) x, (int) y, color.getRGB(), shadow);
    }

    @Override
    public float width(String text) {
        return Minecraft.getInstance().font.width(text);
    }

    @Override
    public float height() {
        return Minecraft.getInstance().font.lineHeight;
    }
}
