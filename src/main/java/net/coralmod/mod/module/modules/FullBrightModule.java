package net.coralmod.mod.module.modules;

import net.coralmod.mod.module.ModuleCategory;
import net.coralmod.mod.module.Module;
import net.coralmod.mod.module.ModuleInfo;

@ModuleInfo(name = "FullBright", description = "Makes everything bright", category = ModuleCategory.RENDER)
public class FullBrightModule extends Module {

    public static final double GAMMA = 100000.0;
}
