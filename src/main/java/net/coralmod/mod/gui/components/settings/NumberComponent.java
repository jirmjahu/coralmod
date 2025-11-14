package net.coralmod.mod.gui.components.settings;

import net.coralmod.mod.gui.ClickGuiScreen;
import net.coralmod.mod.module.settings.NumberSetting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

import java.awt.*;

public class NumberComponent extends SettingComponent<NumberSetting> {

    private static final String Format = "%.2f";
    private boolean sliding;

    public NumberComponent(NumberSetting setting, int x, int y, int width, int height) {
        super(setting, x, y, width, height);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        updateValue(mouseX);

        double renderWidth = (double) (width) * (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin());
        guiGraphics.fill(x, y, x + width, y + height, new Color(50, 50, 50).getRGB());
        guiGraphics.fill(x, y, (int) (x + renderWidth), y + height, ClickGuiScreen.INSTANCE.getSelectedTheme().getColor());

        if (isMouseOver(mouseX, mouseY)) {
            guiGraphics.fill(x, y, x + width, y + height, new Color(150, 150, 150, 50).getRGB());
        }

        Font font = ClickGuiScreen.INSTANCE.getFont();
        int textY = y + (height - font.lineHeight) / 2 + 1;
        String valueStr = String.format(Format, setting.getValue());

        guiGraphics.drawString(font, setting.getName(), x + 5, textY, Color.WHITE.getRGB());
        guiGraphics.drawString(font, valueStr, x + width - font.width(valueStr) - 5, textY, Color.WHITE.getRGB());
    }

    private void updateValue(int mouseX) {
        if (sliding) {
            double diff = Math.min(width, Math.max(0, mouseX - x));
            double range = setting.getMax() - setting.getMin();
            double newValue = setting.getMin() + (diff / width) * range;
            setting.setValue(roundToIncrement(Mth.clamp(newValue, setting.getMin(), setting.getMax())));
        }
    }

    private double roundToIncrement(double value) {
        double increment = setting.getIncrement();
        return Math.round(value / increment) * increment;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            sliding = true;
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        sliding = false;
    }
}
