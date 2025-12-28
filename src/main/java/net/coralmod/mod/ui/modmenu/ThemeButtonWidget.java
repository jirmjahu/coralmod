package net.coralmod.mod.ui.modmenu;

import net.coralmod.mod.CoralMod;
import net.coralmod.mod.theme.Theme;
import net.coralmod.mod.ui.Widget;
import net.coralmod.mod.utils.ColorUtils;
import net.coralmod.mod.utils.Notification;
import net.coralmod.mod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
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
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, int scrollOffset) {
        super.render(guiGraphics, mouseX, mouseY, scrollOffset);

        final Color baseGray = ModMenuScreen.BASE_GRAY;

        int borderColor = theme.getPrimaryColor().getRGB();
        final Color themeColor = ColorUtils.setAlpha(theme.getPrimaryColor(), 100);
        int backgroundColor = ColorUtils.blendColors(baseGray, themeColor).getRGB();

        if (hovered) {
            backgroundColor = ColorUtils.blendColors(new Color(backgroundColor, true), ModMenuScreen.HOVER_COLOR).getRGB();
            borderColor = ColorUtils.blendColors(new Color(borderColor, true), ModMenuScreen.HOVER_COLOR).getRGB();
        }

        guiGraphics.fill(x, y, x + width, y + height, borderColor);
        guiGraphics.fill(x + BORDER_THICKNESS, y + BORDER_THICKNESS, x + width - BORDER_THICKNESS, y + height - BORDER_THICKNESS, backgroundColor);

        final Font font = Minecraft.getInstance().font;

        RenderUtils.scaledItem(guiGraphics.pose(), guiGraphics, theme.getDisplayItem(), x + width / 2, y + height / 2, 2);

        guiGraphics.drawString(font,
                theme.getName(),
                x + width / 2 - font.width(theme.getName()) / 2,
                y + height - (BORDER_THICKNESS * 2) - 10,
                -1,
                true
        );
    }


    @Override
    public void mouseClicked(MouseButtonEvent event) {
        CoralMod.SELECTED_THEME = theme;
        Notification.sendNotification("Updated Theme", "Theme was updated to: " + theme.getName());
    }
}
