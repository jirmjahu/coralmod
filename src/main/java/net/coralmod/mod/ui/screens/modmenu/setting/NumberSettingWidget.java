package net.coralmod.mod.ui.screens.modmenu.setting;

import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.settings.NumberSetting;
import net.coralmod.mod.render.DrawContext;
import net.coralmod.mod.render.FontRenderer;
import net.coralmod.mod.theme.Theme;
import net.coralmod.mod.ui.Widget;
import net.coralmod.mod.ui.screens.modmenu.ModMenuScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.util.Mth;

import java.awt.*;

public class NumberSettingWidget extends Widget {

    private final NumberSetting setting;
    private boolean sliding;

    public NumberSettingWidget(NumberSetting setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setting = setting;
    }

    @Override
    public void init() {
        sliding = false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, int scrollOffset) {
        super.render(context, mouseX, mouseY, scrollOffset);
        updateValue(mouseX);

        final Color baseGray = ModMenuScreen.BASE_GRAY;
        final Theme theme = CoralMod.instance().selectedTheme();

        final double renderWidth = (double) (width) * (setting.value() - setting.min()) / (setting.max() - setting.min());

        context.shapes().rectGradient(x, y, x + width, y + height, baseGray, baseGray.darker());
        context.shapes().rectGradient(x,
                y,
                (int) (x + renderWidth),
                y + height,
                theme.primaryColor(),
                theme.secondaryColor()
        );

        if (hovered) {
            context.shapes().rect(x, y, x + width, y + height, ModMenuScreen.HOVER_COLOR);
        }

        final FontRenderer font = context.fonts().minecraft();
        final int textY = y + (height - font.height()) / 2;

        font.draw(setting.name(), x + 5, textY, Color.WHITE);

        final String valueText = formatValue(setting.value()) + "/" + setting.max();
        font.draw(valueText, x + width - 5 - font.width(valueText), textY, Color.WHITE);
    }

    private String formatValue(double value) {
        if (setting.increment() < 1) {
            return String.format("%.2f", value);
        }
        return String.valueOf((int) value);
    }

    private void updateValue(int mouseX) {
        if (sliding) {
            final double diff = Math.clamp(mouseX - x, 0, width);
            final double range = setting.max() - setting.min();
            final double newValue = setting.min() + (diff / width) * range;
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
        if (hovered && event.button() == 0) {
            sliding = true;
        }
    }

    @Override
    public void mouseReleased(MouseButtonEvent event) {
        sliding = false;
        super.mouseReleased(event);
    }
}
