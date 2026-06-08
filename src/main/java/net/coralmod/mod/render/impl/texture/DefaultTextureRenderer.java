package net.coralmod.mod.render.impl.texture;

import net.coralmod.mod.render.TextureRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class DefaultTextureRenderer implements TextureRenderer {

    private final GuiGraphicsExtractor graphics;

    public DefaultTextureRenderer(GuiGraphicsExtractor graphics) {
        this.graphics = graphics;
    }

    @Override
    public void draw(Identifier texture, float x, float y, int width, int height) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, (int) x, (int) y, 0.0F, 0, width, height, width, height, -1);
    }
}
