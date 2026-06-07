package net.coralmod.mod.ui.modmenu.setting;

import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.settings.ModeSetting;
import net.coralmod.mod.ui.Widget;
import net.coralmod.mod.ui.modmenu.ModMenuScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
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
    public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, int scrollOffset) {
        super.render(graphics, mouseX, mouseY, scrollOffset);

        if (hovered) {
            graphics.fill(x, y, x + width, y + height, ModMenuScreen.HOVER_COLOR.getRGB());
        }

        final Font font = Minecraft.getInstance().font;
        final int textY = y + (height - font.lineHeight) / 2;

        graphics.text(font, setting.name(), x + 5, textY, Color.WHITE.getRGB());

        final String value = setting.value();
        graphics.text(font, value, x + width - font.width(value) - 5, textY,
                CoralMod.instance().selectedTheme().primaryColor().brighter().getRGB());
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
