package net.coralmod.mod.module.modules;

import net.coralmod.mod.module.Module;
import net.coralmod.mod.module.ModuleInfo;
import net.minecraft.client.OptionInstance;

@ModuleInfo(name = "FullBright", description = "Makes everything bright")
public class FullBrightModule extends Module {

    private double oldGamma;

    @Override
    public void onEnable() {
        final OptionInstance<Double> gamma = mc.options.gamma();

        oldGamma = gamma.get();
        gamma.set(100000.0);
    }

    @Override
    public void onDisable() {
        mc.options.gamma().set(oldGamma);
    }
}
