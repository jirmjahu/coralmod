package net.coralmod.mod.ui.screens.modmenu.setting;

import net.coralmod.mod.module.settings.BooleanSetting;
 import net.coralmod.mod.render.DrawContext;
import net.coralmod.mod.render.FontRenderer;
import net.coralmod.mod.ui.Widget;
import net.coralmod.mod.ui.screens.modmenu.ModMenuScreen;
import net.minecraft.client.input.MouseButtonEvent;

import java.awt.*;

public class BooleanSettingWidget extends Widget {

    private final BooleanSetting setting;

    public BooleanSettingWidget(BooleanSetting setting, int x, int y, int width, int height) {
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

        final String value = setting.value() ? "On" : "Off";
        font.draw(value, x + width - font.width(value) - 5, textY, setting.value() ? new Color(80, 255, 80) : new Color(255, 80, 80));
    }

    @Override
    public void mouseClicked(MouseButtonEvent event) {
        if (hovered && event.button() == 0) {
            setting.value(!setting.value());
        }
    }
}
