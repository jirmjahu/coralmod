package net.coralmod.mod.render.impl.font;

import net.coralmod.mod.render.FontManager;
import net.coralmod.mod.render.FontRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.util.HashMap;
import java.util.Map;

public class DefaultFontManager implements FontManager {

    private final Map<String, FontRenderer> fonts = new HashMap<>();

    public DefaultFontManager(GuiGraphicsExtractor graphics) {
        fonts.put("minecraft", new MinecraftFontRenderer(graphics));
    }

    @Override
    public FontRenderer get(String id) {
        return fonts.get(id);
    }

    @Override
    public FontRenderer minecraft() {
        return fonts.get("minecraft");
    }
}
