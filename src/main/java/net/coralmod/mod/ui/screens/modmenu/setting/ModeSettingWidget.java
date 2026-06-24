package net.coralmod.mod.ui.screens.modmenu.setting;

import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.settings.ModeSetting;
import net.coralmod.mod.render.DrawContext;
import net.coralmod.mod.render.FontRenderer;
import net.coralmod.mod.ui.Widget;
import net.coralmod.mod.ui.screens.modmenu.ModMenuScreen;
import net.minecraft.client.input.MouseButtonEvent;

import java.awt.*;
import java.util.List;

public class ModeSettingWidget extends Widget {

    private final ModeSetting setting;

    public ModeSettingWidget(ModeSetting setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setting = setting;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, int scrollOffset) {
        super.render(context, mouseX, mouseY, scrollOffset);

        if (hovered) {
            context.shapes().rect(x, y, x + width, y + height, ModMenuScreen.HOVER_COLOR);
        }

        final FontRenderer font = context.fonts().minecraft();
        final int textY = y + (height - font.height()) / 2;

        font.draw(setting.name(), x + 5, textY, Color.WHITE);

        final String value = setting.value();
        font.draw(value, x + width - font.width(value) - 5, textY, CoralMod.instance().selectedTheme().primaryColor().brighter());
    }

    @Override
    public void mouseClicked(MouseButtonEvent event) {
        final List<String> modes = setting.modes();
        final int current = modes.indexOf(setting.value());

        if (event.button() == 0) {
            setting.value(modes.get((current + 1) % modes.size()));
        } else if (event.button() == 1) {
            setting.value(modes.get((current - 1 + modes.size()) % modes.size()));
        }
    }
}
