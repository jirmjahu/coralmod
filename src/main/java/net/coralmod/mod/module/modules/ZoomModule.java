package net.coralmod.mod.module.modules;

import lombok.Getter;
import net.coralmod.mod.CoralMod;
import net.coralmod.mod.event.KeyPressedEvent;
import net.coralmod.mod.module.Module;
import net.coralmod.mod.module.ModuleInfo;
import net.coralmod.mod.utils.KeyUtils;
import net.minecraft.client.Options;

@ModuleInfo(name = "Zoom", description = "Zooms in the game")
public class ZoomModule extends Module {

    @Getter
    private boolean zooming = false;

    private int oldFov;
    private double oldSensitivity;

    public ZoomModule() {
        KeyPressedEvent.KEY_PRESSED_EVENT.register(key -> {
            if (!isEnabled()) {
                return;
            }

            final boolean keyPressed = KeyUtils.isKeyPressed(CoralMod.ZOOM_KEY_MAPPING.getDefaultKey().getValue());

            if (keyPressed && !zooming) {
                startZooming();
            } else if (!keyPressed && zooming) {
                stopZooming();
            }
        });
    }

    @Override
    public void onDisable() {
        if (zooming) {
            stopZooming();
        }
    }

    private void startZooming() {
        if (mc == null) {
            return;
        }

        if (mc.screen != null) {
            return;
        }

        final Options options = mc.options;

        oldFov = options.fov().get();
        oldSensitivity = options.sensitivity().get();

        options.fov().set(30);
        options.smoothCamera = true;
        options.sensitivity().set(0.2);

        zooming = true;
    }

    private void stopZooming() {
        if (mc == null) {
            return;
        }

        final Options options = mc.options;

        options.fov().set(oldFov);
        options.smoothCamera = false;
        options.sensitivity().set(oldSensitivity);

        zooming = false;
    }
}
