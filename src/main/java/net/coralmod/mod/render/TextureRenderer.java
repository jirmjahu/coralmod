package net.coralmod.mod.render;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

public interface TextureRenderer {

    void draw(Identifier texture, float x, float y, int width, int height);

    void item(Item item, float x, float y, float scale);

}
