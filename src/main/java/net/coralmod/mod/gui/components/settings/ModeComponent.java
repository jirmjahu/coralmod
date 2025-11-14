package net.coralmod.mod.gui.components.settings;

import net.coralmod.mod.gui.ClickGuiScreen;
import net.coralmod.mod.module.settings.ModeSetting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;
import java.util.List;

public class ModeComponent extends SettingComponent<ModeSetting> {

    public ModeComponent(ModeSetting setting, int x, int y, int width, int height) {
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

        String value = setting.getValue();
        int valueX = x + width - font.width(value) - 5;
        guiGraphics.drawString(font, value, valueX, textY, Color.LIGHT_GRAY.getRGB());
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY)) {
            List<String> modes = setting.getModes();
            int currentIndex = modes.indexOf(setting.getValue());

            if (button == 0) {
                int nextIndex = (currentIndex + 1) % modes.size();
                setting.setValue(modes.get(nextIndex));
            } else if (button == 1) {
                int prevIndex = (currentIndex - 1 + modes.size()) % modes.size();
                setting.setValue(modes.get(prevIndex));
            }
        }
    }
}
