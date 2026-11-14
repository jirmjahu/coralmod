package net.coralmod.mod.ui.screens.modmenu.setting;

import com.mojang.blaze3d.platform.InputConstants;
import net.coralmod.mod.module.settings.NumberSetting;
import net.coralmod.mod.render.DrawContext;
import net.coralmod.mod.render.FontRenderer;
import net.coralmod.mod.ui.screens.modmenu.ModMenuScreen;
import net.coralmod.mod.utils.MouseUtils;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.util.Mth;

import java.awt.*;

public class NumberSettingWidget extends SettingWidget<NumberSetting> {

    private static final int SLIDER_WIDTH = 100;
    private static final int SLIDER_BAR_HEIGHT = 4;
    private static final int SLIDER_KNOB_SIZE = 8;

    private boolean sliding;

    public NumberSettingWidget(NumberSetting setting, int x, int y, int width, int height) {
        super(setting, x, y, width, height);
    }

    @Override
    public void init() {
        sliding = false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, int scrollOffset) {
        super.render(context, mouseX, mouseY, scrollOffset);
        updateValue(mouseX);

        final int sliderX = sliderX();
        final int barY = controlY + (CONTROL_HEIGHT - SLIDER_BAR_HEIGHT) / 2;
        final double progress = (setting.value() - setting.min()) / (setting.max() - setting.min());
        final int filledWidth = (int) (SLIDER_WIDTH * progress);

        context.shapes().roundedRect(sliderX, barY, SLIDER_WIDTH, SLIDER_BAR_HEIGHT, SLIDER_BAR_HEIGHT / 2F, CONTROL_BACKGROUND_COLOR);
        context.shapes().roundedRect(sliderX, barY, Math.max(filledWidth, SLIDER_BAR_HEIGHT), SLIDER_BAR_HEIGHT, SLIDER_BAR_HEIGHT / 2F, accentColor());

        final int knobX = Mth.clamp(sliderX + filledWidth - SLIDER_KNOB_SIZE / 2, sliderX, sliderX + SLIDER_WIDTH - SLIDER_KNOB_SIZE);
        final int knobY = controlY + (CONTROL_HEIGHT - SLIDER_KNOB_SIZE) / 2;
        context.shapes().roundedRect(knobX, knobY, SLIDER_KNOB_SIZE, SLIDER_KNOB_SIZE, SLIDER_KNOB_SIZE / 2F, Color.WHITE);

        final FontRenderer font = font(context);
        final String valueText = formatValue(setting.value());
        font.draw(
                valueText,
                sliderX - ModMenuScreen.PADDING - font.width(valueText, TEXT_SCALE),
                centeredTextY(controlY, CONTROL_HEIGHT),
                TEXT_SCALE,
                accentColor().brighter(),
                false
        );
    }

    private int sliderX() {
        return controlRightX() - SLIDER_WIDTH;
    }

    private String formatValue(double value) {
        if (setting.increment() < 1) {
            return String.format("%.2f", value);
        }
        return String.valueOf((int) value);
    }

    private void updateValue(int mouseX) {
        if (sliding) {
            final double diff = Math.clamp(mouseX - sliderX(), 0, SLIDER_WIDTH);
            final double range = setting.max() - setting.min();
            final double newValue = setting.min() + (diff / SLIDER_WIDTH) * range;
            setting.value(roundToIncrement(Mth.clamp(newValue, setting.min(), setting.max())));
        }
    }

    private double roundToIncrement(double value) {
        final double increment = setting.increment();
        return Math.round(value / increment) * increment;
    }

    @Override
    public void mouseClicked(MouseButtonEvent event) {
        super.mouseClicked(event);

        final boolean clickedSlider = MouseUtils.isMouseOver(
                event.x(), event.y(), sliderX() - SLIDER_KNOB_SIZE / 2F, controlY, SLIDER_WIDTH + SLIDER_KNOB_SIZE, CONTROL_HEIGHT
        );

        if (clickedSlider && event.button() == InputConstants.MOUSE_BUTTON_LEFT) {
            sliding = true;
        }
    }

    @Override
    public void mouseReleased(MouseButtonEvent event) {
        sliding = false;
        super.mouseReleased(event);
    }
}
