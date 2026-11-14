package net.coralmod.mod.module.modules;

import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.ModuleCategory;
import net.coralmod.mod.module.Module;
import net.coralmod.mod.module.ModuleInfo;

@ModuleInfo(name = "Zoom", description = "Zooms in the game", category = ModuleCategory.PLAYER)
public class ZoomModule extends Module {

    public static final int ZOOM_FOV = 30;
    public static final double ZOOM_SENSITIVITY = 0.2;

    public boolean zooming() {
        return CoralMod.ZOOM_KEY_MAPPING.isDown();
    }
}
