package net.coralmod.mod.utils;

import lombok.experimental.UtilityClass;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@UtilityClass
public class RenderUtils {

    public void drawTexture(DrawContext context, Identifier texture, int x, int y, int size) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, texture, x, y, 0.0F, 0, size, size, size, size, -1);
    }

    public static PressableTextWidget pressableText(TextRenderer textRenderer, Text text, int x, int y, Runnable onClick) {
        return new PressableTextWidget(x, y, textRenderer.getWidth(text), textRenderer.fontHeight, text, (button) -> onClick.run(), textRenderer);
    }
}
