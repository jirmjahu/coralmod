package net.coralmod.mod.render.impl.font;

import net.coralmod.mod.render.FontManager;
import net.coralmod.mod.render.FontRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;

public class DefaultFontManager implements FontManager {

    public static String MINECRAFT_FONT = "minecraft";
    public static String INTER_FONT = "inter_18pt-semibold";
    public static String INTER_BOLD = "inter_18pt-bold";
    public static String INTER_EXTRA_BOLD = "inter_18pt-extrabold";
    public static String CASCADIA_CODE_FONT = "cascadiacode-semibold";

    private final Map<String, FontRenderer> fonts = new HashMap<>();

    public DefaultFontManager(GuiGraphicsExtractor graphics) {
        fonts.put(MINECRAFT_FONT, new MinecraftFontRenderer(graphics));
        fonts.put(INTER_FONT, new CustomFontRenderer(graphics, Identifier.fromNamespaceAndPath("coralmod", INTER_FONT)));
        fonts.put(INTER_BOLD, new CustomFontRenderer(graphics, Identifier.fromNamespaceAndPath("coralmod", INTER_BOLD)));
        fonts.put(INTER_EXTRA_BOLD, new CustomFontRenderer(graphics, Identifier.fromNamespaceAndPath("coralmod", INTER_EXTRA_BOLD)));
        fonts.put(CASCADIA_CODE_FONT, new CustomFontRenderer(graphics, Identifier.fromNamespaceAndPath("coralmod", CASCADIA_CODE_FONT)));
    }

    @Override
    public FontRenderer get(String id) {
        return fonts.get(id);
    }

    @Override
    public FontRenderer minecraft() {
        return fonts.get(MINECRAFT_FONT);
    }

    @Override
    public FontRenderer inter() {
        return fonts.get(INTER_FONT);
    }

    @Override
    public FontRenderer interBold() {
        return fonts.get(INTER_BOLD);
    }

    @Override
    public FontRenderer interExtraBold() {
        return fonts.get(INTER_EXTRA_BOLD);
    }

    @Override
    public FontRenderer cascadiaCode() {
        return fonts.get(CASCADIA_CODE_FONT);
    }
}
