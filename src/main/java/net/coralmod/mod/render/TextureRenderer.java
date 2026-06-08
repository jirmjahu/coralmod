package net.coralmod.mod.render;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

public interface TextureRenderer {

    void draw(Identifier texture, int x, int y, int width, int height);

    void item(Item item, int x, int y, float scale);

}
