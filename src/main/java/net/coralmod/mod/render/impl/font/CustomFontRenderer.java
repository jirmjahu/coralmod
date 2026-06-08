package net.coralmod.mod.render.impl.font;

import net.coralmod.mod.render.FontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;

import java.awt.Color;

public class CustomFontRenderer implements FontRenderer {

    private final GuiGraphicsExtractor graphics;
    private final Style fontStyle;

    public CustomFontRenderer(GuiGraphicsExtractor graphics, Identifier fontId) {
        this.graphics = graphics;
        this.fontStyle = Style.EMPTY.withFont(new FontDescription.Resource(fontId));
    }

    @Override
    public void draw(String text, float x, float y, Color color) {
        draw(text, x, y, color, true);
    }

    @Override
    public void draw(String text, float x, float y, Color color, boolean shadow) {
        if (shadow) {
            graphics.text(
                    Minecraft.getInstance().font,
                    Component.literal(text).withStyle(fontStyle),
                    (int) x + 1,
                    (int) y + 1,
                    Color.BLACK.getRGB(),
                    false
            );
        }

        graphics.text(
                Minecraft.getInstance().font,
                Component.literal(text).withStyle(fontStyle),
                (int) x,
                (int) y,
                color.getRGB(),
                false
        );
    }

    @Override
    public float width(String text) {
        return Minecraft.getInstance().font.width(Component.literal(text).withStyle(fontStyle));
    }

    @Override
    public float height() {
        return 0; // todo
    }
}
