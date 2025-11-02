package net.coralmod.mod.module.modules;

import net.coralmod.mod.module.Module;
import net.coralmod.mod.module.ModuleInfo;
import net.coralmod.mod.module.settings.BooleanSetting;

@ModuleInfo(name = "Nametags", description = "Modify nametags")
public class NametagsModule extends Module {

    private final BooleanSetting nametagInPerspective = new BooleanSetting("Nametag in Perspective", true);

    public NametagsModule() {
        addSettings(nametagInPerspective);
    }
}
