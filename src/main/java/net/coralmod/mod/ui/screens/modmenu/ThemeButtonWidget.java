package net.coralmod.mod.ui.screens.modmenu;

import net.coralmod.mod.CoralMod;
import net.coralmod.mod.theme.Theme;
import net.coralmod.mod.ui.Widget;
import net.coralmod.mod.utils.ColorUtils;
import net.coralmod.mod.utils.Notification;
import net.coralmod.mod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;

import java.awt.*;

public class ThemeButtonWidget extends Widget {

    private final Theme theme;
    private static final int BORDER_THICKNESS = 2;

    public ThemeButtonWidget(Theme theme, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.theme = theme;
    }

    @Override
    public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, int scrollOffset) {
        super.render(graphics, mouseX, mouseY, scrollOffset);

        int borderColor = theme.primaryColor().getRGB();
        int backgroundColor = ColorUtils.blendColors(ModMenuScreen.BASE_GRAY, ColorUtils.modifyAlpha(theme.primaryColor(), 100)).getRGB();

        if (hovered) {
            backgroundColor = ColorUtils.blendColors(new Color(backgroundColor, true), ModMenuScreen.HOVER_COLOR).getRGB();
            borderColor = ColorUtils.blendColors(new Color(borderColor, true), ModMenuScreen.HOVER_COLOR).getRGB();
        }

        graphics.fill(x, y, x + width, y + height, borderColor);
        graphics.fill(
                x + BORDER_THICKNESS,
                y + BORDER_THICKNESS,
                x + width - BORDER_THICKNESS,
                y + height - BORDER_THICKNESS,
                backgroundColor
        );

        RenderUtils.scaledItem(
                graphics.pose(),
                graphics,
                theme.displayItem(),
                x + width / 2,
                y + height / 2,
                2
        );

        final Font font = Minecraft.getInstance().font;
        graphics.text(
                font,
                theme.displayName(),
                x + width / 2 - font.width(theme.displayName()) / 2,
                y + height - (BORDER_THICKNESS * 2) - 10,
                -1,
                true
        );
    }


    @Override
    public void mouseClicked(MouseButtonEvent event) {
        CoralMod.instance().selectedTheme(theme);
        Notification.send("Updated Theme", "Theme was updated to: " + theme.name());
    }
}
