package net.coralmod.mod.module.modules;

import net.coralmod.mod.module.ModuleCategory;
import net.coralmod.mod.module.Module;
import net.coralmod.mod.module.ModuleInfo;
import net.coralmod.mod.module.settings.NumberSetting;

@ModuleInfo(name = "Aspect", description = "Change your aspect factor/ratio", category = ModuleCategory.RENDER)
public class AspectModule extends Module {

    private final NumberSetting stretchFactor = new NumberSetting("Stretch Factor", 100, 1, 1000, 1);

    public AspectModule() {
        addSettings(stretchFactor);
    }

    public double stretchFactor() {
        return stretchFactor.value() / 100;
    }
}
