package net.coralmod.mod.module.modules;

import net.coralmod.mod.CoralMod;
import net.coralmod.mod.event.KeyPressedEvent;
import net.coralmod.mod.module.ModuleCategory;
import net.coralmod.mod.module.Module;
import net.coralmod.mod.module.ModuleInfo;
import net.coralmod.mod.utils.KeyUtils;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;

@ModuleInfo(name = "Zoom", description = "Zooms in the game", category = ModuleCategory.PLAYER)
public class ZoomModule extends Module {

    public static final int ZOOM_FOV = 30;
    public static final double ZOOM_SENSITIVITY = 0.2;

    private boolean zooming = false;

    public ZoomModule() {
        KeyPressedEvent.KEY_PRESSED_EVENT.register(key -> {
            if (!enabled()) {
                return;
            }

            final boolean keyPressed = KeyUtils.isKeyPressed(KeyMappingHelper.getBoundKeyOf(CoralMod.ZOOM_KEY_MAPPING).getValue());

            if (keyPressed && !zooming && mc.gui.screen() == null) {
                zooming = true;
            } else if (!keyPressed && zooming) {
                zooming = false;
            }
        });
    }

    @Override
    public void onDisable() {
        zooming = false;
    }

    public boolean zooming() {
        return zooming;
    }
}
