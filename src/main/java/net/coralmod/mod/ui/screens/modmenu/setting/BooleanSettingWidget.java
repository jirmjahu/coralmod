package net.coralmod.mod.ui.screens.modmenu.setting;

import com.mojang.blaze3d.platform.InputConstants;
import net.coralmod.mod.module.settings.BooleanSetting;
import net.coralmod.mod.render.DrawContext;
import net.minecraft.client.input.MouseButtonEvent;

import java.awt.*;

public class BooleanSettingWidget extends SettingWidget<BooleanSetting> {

    private static final int TOGGLE_WIDTH = 22;
    private static final int TOGGLE_KNOB_PADDING = 2;
    private static final int TOGGLE_KNOB_SIZE = CONTROL_HEIGHT - TOGGLE_KNOB_PADDING * 2;

    public BooleanSettingWidget(BooleanSetting setting, int x, int y, int width, int height) {
        super(setting, x, y, width, height);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, int scrollOffset) {
        super.render(context, mouseX, mouseY, scrollOffset);

        final boolean enabled = setting.value();
        final int toggleX = controlRightX() - TOGGLE_WIDTH;
        final Color toggleColor = enabled ? accentColor() : CONTROL_BACKGROUND_COLOR;

        context.shapes().roundedRect(toggleX, controlY, TOGGLE_WIDTH, CONTROL_HEIGHT, CONTROL_HEIGHT / 2F, toggleColor);

        final int knobX = enabled
                ? toggleX + TOGGLE_WIDTH - TOGGLE_KNOB_PADDING - TOGGLE_KNOB_SIZE
                : toggleX + TOGGLE_KNOB_PADDING;
        final Color knobColor = enabled ? Color.WHITE : SECONDARY_TEXT_COLOR;

        context.shapes().roundedRect(knobX, controlY + TOGGLE_KNOB_PADDING, TOGGLE_KNOB_SIZE, TOGGLE_KNOB_SIZE, TOGGLE_KNOB_SIZE / 2F, knobColor);
    }

    @Override
    public void mouseClicked(MouseButtonEvent event) {
        if (hovered && event.button() == InputConstants.MOUSE_BUTTON_LEFT) {
            setting.value(!setting.value());
        }
    }
}
