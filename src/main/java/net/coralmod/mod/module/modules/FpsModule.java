package net.coralmod.mod.module.modules;

import net.coralmod.mod.module.HudModule;
import net.coralmod.mod.module.ModuleInfo;
import net.minecraft.client.Minecraft;

@ModuleInfo(name = "FPS", description = "Displays your FPS")
public class FpsModule extends HudModule {

    public FpsModule() {
        super(20, 20);
    }

    @Override
    public String getText() {
        return "FPS: " + Minecraft.getInstance().getFps();
    }
}
