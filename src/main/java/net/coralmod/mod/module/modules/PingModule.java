package net.coralmod.mod.module.modules;

import net.coralmod.mod.module.HudModule;
import net.coralmod.mod.module.ModuleInfo;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.multiplayer.PlayerInfo;

@ModuleInfo(name = "Ping", description = "Displays your Ping")
public class PingModule extends HudModule {

    public PingModule() {
        super(20, 20);

        ClientTickEvents.START_CLIENT_TICK.register(mc -> {
            if (mc.player == null || mc.getConnection() == null) {
                return;
            }

            final PlayerInfo info = mc.getConnection().getPlayerInfo(mc.player.getUUID());

            if (info == null) {
                return;
            }

            final String ping = "Ping: " + info.getLatency() + "ms";
            setText(ping);
        });
    }
}
