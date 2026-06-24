package net.coralmod.mod.ui.screens.modmenu;

import net.coralmod.mod.theme.Theme;
import net.coralmod.mod.ui.Window;

import java.util.Arrays;

public class ThemesTabWindow extends Window {

    public ThemesTabWindow(ModMenuScreen parent, String name, int x, int y) {
        super(parent, name, x, y);
    }

    @Override
    public void init() {
        GridUtil.layoutGrid(
                Arrays.stream(Theme.values()).toList(),
                x,
                y,
                (theme, pos) -> addWidget(new ThemeButtonWidget(
                        theme,
                        pos.x(),
                        pos.y(),
                        pos.width(),
                        pos.height()
                ))
        );

        super.init();
    }
}
