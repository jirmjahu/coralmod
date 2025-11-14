package net.coralmod.mod.gui.components.settings;

import net.coralmod.mod.gui.ClickGuiScreen;
import net.coralmod.mod.module.settings.BooleanSetting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

public class BooleanComponent extends SettingComponent<BooleanSetting> {

    public BooleanComponent(BooleanSetting setting, int x, int y, int width, int height) {
        super(setting, x, y, width, height);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (isMouseOver(mouseX, mouseY)) {
            guiGraphics.fill(x, y, x + width, y + height, new Color(150, 150, 150, 50).getRGB());
        }

        Font font = ClickGuiScreen.INSTANCE.getFont();
        int textY = y + (height - font.lineHeight) / 2 + 1;

        guiGraphics.drawString(font, setting.getName(), x + 5, textY, Color.WHITE.getRGB());

        String value = setting.getValue() ? "On" : "Off";
        int valueColor = setting.getValue() ? new Color(80, 255, 80).getRGB() : new Color(255, 80, 80).getRGB();
        int valueX = x + width - font.width(value) - 5;

        guiGraphics.drawString(font, value, valueX, textY, valueColor);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            setting.setValue(!setting.getValue());
        }
    }
}
