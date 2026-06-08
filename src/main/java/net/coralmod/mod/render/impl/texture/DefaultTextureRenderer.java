package net.coralmod.mod.render.impl.texture;

import net.coralmod.mod.render.TextureRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix3x2fStack;

public class DefaultTextureRenderer implements TextureRenderer {

    private final GuiGraphicsExtractor graphics;

    public DefaultTextureRenderer(GuiGraphicsExtractor graphics) {
        this.graphics = graphics;
    }

    @Override
    public void draw(Identifier texture, int x, int y, int width, int height) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, 0.0F, 0, width, height, width, height, -1);
    }

    @Override
    public void item(Item item, int x, int y, float scale) {
        final Matrix3x2fStack stack = graphics.pose();
        final float itemSize = 16 * scale;

        stack.pushMatrix();
        stack.translate(x - itemSize / 2f, y - itemSize / 2f);
        stack.scale(scale, scale);
        graphics.item(new ItemStack(item), 0, 0);
        stack.popMatrix();
    }
}
