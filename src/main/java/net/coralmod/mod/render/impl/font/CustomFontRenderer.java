package net.coralmod.mod.render.impl.font;

import net.coralmod.mod.render.FontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import org.joml.Matrix3x2fStack;

import java.awt.Color;

public class CustomFontRenderer implements FontRenderer {

    private final GuiGraphicsExtractor graphics;
    private final Style fontStyle;

    public CustomFontRenderer(GuiGraphicsExtractor graphics, Identifier fontId) {
        this.graphics = graphics;
        this.fontStyle = Style.EMPTY.withFont(new FontDescription.Resource(fontId));
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
        final Matrix3x2fStack stack = graphics.pose();
        stack.pushMatrix();
        stack.translate(x, y);
        stack.scale(scale, scale);

        if (shadow) {
            graphics.text(
                    Minecraft.getInstance().font,
                    Component.literal(text).withStyle(fontStyle),
                    1, 1,
                    Color.BLACK.getRGB(),
                    false
            );
        }

        graphics.text(
                Minecraft.getInstance().font,
                Component.literal(text).withStyle(fontStyle),
                0, 0,
                color.getRGB(),
                false
        );

        stack.popMatrix();
    }

    @Override
    public int width(String text, float scale) {
        return (int) (Minecraft.getInstance().font.width(Component.literal(text).withStyle(fontStyle)) * scale);
    }

    @Override
    public int height(float scale) {
        return (int) (Minecraft.getInstance().font.lineHeight * scale);
    }
}
