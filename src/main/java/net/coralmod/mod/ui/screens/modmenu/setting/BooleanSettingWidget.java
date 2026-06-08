package net.coralmod.mod.ui.screens.modmenu.setting;

import net.coralmod.mod.module.settings.BooleanSetting;
import net.coralmod.mod.ui.Widget;
import net.coralmod.mod.ui.screens.modmenu.ModMenuScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;

import java.awt.*;

public class BooleanSettingWidget extends Widget {

    private final BooleanSetting setting;

    public BooleanSettingWidget(BooleanSetting setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setting = setting;
    }

    @Override
    public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, int scrollOffset) {
        super.render(graphics, mouseX, mouseY, scrollOffset);

        if (hovered) {
            graphics.fill(x, y, x + width, y + height, ModMenuScreen.HOVER_COLOR.getRGB());
        }

        final Font font = Minecraft.getInstance().font;
        final int textY = y + (height - font.lineHeight) / 2;

        graphics.text(font, setting.name(), x + 5, textY, Color.WHITE.getRGB());

        final String value = setting.value() ? "On" : "Off";
        final int valueColor = setting.value() ? new Color(80, 255, 80).getRGB() : new Color(255, 80, 80).getRGB();
        graphics.text(font, value, x + width - font.width(value) - 5, textY, valueColor);
    }

    @Override
    public void mouseClicked(MouseButtonEvent event) {
        if (hovered && event.button() == 0) {
            setting.value(!setting.value());
        }
    }
}
