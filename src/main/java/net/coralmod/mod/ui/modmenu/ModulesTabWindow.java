package net.coralmod.mod.ui.modmenu;

import net.coralmod.mod.CoralMod;
import net.coralmod.mod.ui.Window;

public class ModulesTabWindow extends Window {

    public ModulesTabWindow(ModMenuScreen parent, String title, int x, int y) {
        super(parent, title, x, y);
    }

    @Override
    public void init() {
        super.init();

        GridUtil.layoutGrid(
                CoralMod.getInstance().getModuleManager().getModules(),
                x,
                y,
                (module, pos) -> {
                    addWidget(new ModuleButtonWidget(
                            module,
                            pos.getX(), pos.getY(),
                            pos.getWidth(), pos.getHeight()
                    ));
                }
        );
    }
}
