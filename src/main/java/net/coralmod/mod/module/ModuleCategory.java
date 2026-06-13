package net.coralmod.mod.module;

import java.awt.*;

public enum ModuleCategory {

    HUD("HUD", new Color(100, 160, 240)),
    RENDER("Render", new Color(160, 110, 240)),
    PLAYER("Player", new Color(90, 210, 130)),
    MISC("Misc", new Color(230, 160, 70));

    private final String displayName;
    private final Color color;

    ModuleCategory(String displayName, Color color) {
        this.displayName = displayName;
        this.color = color;
    }

    public String displayName() {
        return displayName;
    }

    public Color color() {
        return color;
    }
}
