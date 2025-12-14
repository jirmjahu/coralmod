package net.coralmod.mod.module.modules;

import net.coralmod.mod.module.HudModule;
import net.coralmod.mod.module.ModuleInfo;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

@ModuleInfo(name = "Coordinates", description = "Shows your coordinates")
public class CoordinatesModule extends HudModule {

    public CoordinatesModule() {
        super(20, 20);

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (!isEnabled()) {
                return;
            }

            if (client.player == null) {
                return;
            }

            long x = Math.round(client.player.getX());
            long y = Math.round(client.player.getY());
            long z = Math.round(client.player.getZ());

            setText("X: " + x + " Y: " + y + " Z: " + z);
        });
    }
}

