package net.coralmod.mod.utils;

import lombok.experimental.UtilityClass;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

@UtilityClass
public class RenderUtils {

    public void drawTexture(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int size) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, 0.0F, 0, size, size, size, size, -1);
    }

    public static PlainTextButton pressableText(Font textRenderer, Component text, int x, int y, Runnable onClick) {
        return new PlainTextButton(x, y, textRenderer.width(text), textRenderer.lineHeight, text, (button) -> onClick.run(), textRenderer);
    }
}
