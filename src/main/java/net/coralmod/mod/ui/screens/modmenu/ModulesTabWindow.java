package net.coralmod.mod.ui.screens.modmenu;

import net.coralmod.mod.CoralMod;
import net.coralmod.mod.ui.Window;

public class ModulesTabWindow extends Window {

    public ModulesTabWindow(ModMenuScreen parent, String title, int x, int y) {
        super(parent, title, x, y);
    }

    @Override
    public void init() {
        GridUtil.layoutGrid(
                CoralMod.instance().moduleManager().modules(),
                x,
                y,
                (module, pos) -> {
                    addWidget(new ModuleButtonWidget(
                            module,
                            pos.x(),
                            pos.y(),
                            pos.width(),
                            pos.height()
                    ));
                }
        );

        super.init();
    }
}
