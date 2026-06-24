package net.coralmod.mod.module;

public enum ModuleCategory {

    HUD("HUD"),
    RENDER("Render"),
    PLAYER("Player"),
    MISC("Misc");

    private final String displayName;

    ModuleCategory(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}
