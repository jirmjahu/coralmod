package net.coralmod.mod.ui.screens.modmenu;

import net.coralmod.mod.render.DrawContext;
import net.coralmod.mod.ui.Widget;
import net.coralmod.mod.utils.ColorUtils;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.resources.Identifier;

import java.awt.*;

public class SidebarButtonWidget extends Widget {

    private static final Color BASE_COLOR = new Color(50, 50, 50);
    private static final int RADIUS = 5;
    private static final int ICON_PADDING = 3;

    private final Identifier icon;
    private final Runnable onClick;

    public SidebarButtonWidget(int x, int y, int width, int height, Identifier icon, Runnable onClick) {
        super(x, y, width, height);
        this.icon = icon;
        this.onClick = onClick;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, int scrollOffset) {
        super.render(context, mouseX, mouseY, scrollOffset);

        Color color = BASE_COLOR;
        if (hovered) {
            color = ColorUtils.blendColors(color, ModMenuScreen.HOVER_COLOR);
        }

        context.shapes().roundedRect(x, y, width, height, RADIUS, color);

        final int iconSize = Math.min(width, height) - ICON_PADDING * 2;
        final int iconX = x + (width - iconSize) / 2;
        final int iconY = y + (height - iconSize) / 2;
        context.textures().draw(icon, iconX, iconY, iconSize, iconSize);
    }

    @Override
    public void mouseClicked(MouseButtonEvent event) {
        if (hovered && event.button() == 0) {
            onClick.run();
        }
    }
}
