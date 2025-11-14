package net.coralmod.mod.module.modules;

import net.coralmod.mod.module.Module;
import net.coralmod.mod.module.ModuleInfo;
import net.coralmod.mod.module.settings.BooleanSetting;
import net.coralmod.mod.module.settings.ModeSetting;
import net.coralmod.mod.module.settings.NumberSetting;

import java.util.List;

@ModuleInfo(name = "Nametags", description = "Modify nametags")
public class NametagsModule extends Module {

    private final BooleanSetting nametagInPerspective = new BooleanSetting("Nametag in Perspective", true);

    // remove
    private final NumberSetting test = new NumberSetting("test double", 1.0, 0.0, 5.0, 0.1);
    private final ModeSetting test2 = new ModeSetting("test stringlist", "Ok", List.of("Ok", "Okkk+", "dexter"));

    public NametagsModule() {
        addSettings(nametagInPerspective, test, test2);
    }
}
