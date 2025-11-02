package net.coralmod.mod.module.modules;

import net.coralmod.mod.module.HudModule;
import net.coralmod.mod.module.ModuleInfo;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;

@ModuleInfo(name = "FPS", description = "Displays your FPS")
public class FpsModule extends HudModule {

    public FpsModule() {
        super(10, 10);
        setEnabled(true);

        ClientTickEvents.START_CLIENT_TICK.register(mc -> {
            final String fps = "FPS: " + Minecraft.getInstance().getFps();
            setText(fps);
        });
    }
}
