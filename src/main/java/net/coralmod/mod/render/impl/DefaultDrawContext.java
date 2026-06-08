package net.coralmod.mod.render.impl;

import net.coralmod.mod.render.FontManager;
import net.coralmod.mod.render.DrawContext;
import net.coralmod.mod.render.ShapeRenderer;
import net.coralmod.mod.render.TextureRenderer;
import net.coralmod.mod.render.impl.font.DefaultFontManager;
import net.coralmod.mod.render.impl.shape.DefaultShapeRenderer;
import net.coralmod.mod.render.impl.texture.DefaultTextureRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class DefaultDrawContext implements DrawContext {

    private final GuiGraphicsExtractor graphics;
    private final ShapeRenderer shapeRenderer;
    private final FontManager fontManager;
    private final TextureRenderer textureRenderer;

    public DefaultDrawContext(GuiGraphicsExtractor graphics) {
        this.graphics = graphics;
        this.shapeRenderer = new DefaultShapeRenderer(graphics);
        this.fontManager = new DefaultFontManager(graphics);
        this.textureRenderer = new DefaultTextureRenderer(graphics);
    }

    @Override
    public ShapeRenderer shapes() {
        return shapeRenderer;
    }

    @Override
    public FontManager fonts() {
        return fontManager;
    }

    @Override
    public TextureRenderer textures() {
        return textureRenderer;
    }

    @Override
    public void enableScissor(int x, int y, int x1, int y1) {
        graphics.enableScissor(x, y, x1, y1);
    }

    @Override
    public void disableScissor() {
        graphics.disableScissor();
    }
}
