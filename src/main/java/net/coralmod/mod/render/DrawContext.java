package net.coralmod.mod.render;

public interface DrawContext {

    ShapeRenderer shapes();

    FontManager fonts();

    TextureRenderer textures();

    void enableScissor(int x, int y, int x1, int y1);

    void disableScissor();

}
