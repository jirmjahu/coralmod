package net.coralmod.mod.render;

import net.minecraft.resources.Identifier;

public interface TextureRenderer {

    void draw(Identifier texture, float x, float y, int width, int height);

}
