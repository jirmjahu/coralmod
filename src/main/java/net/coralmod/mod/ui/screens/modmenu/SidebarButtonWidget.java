package net.coralmod.mod.ui.screens.modmenu;

import net.coralmod.mod.render.DrawContext;
import net.coralmod.mod.ui.Widget;
import net.coralmod.mod.utils.ColorUtils;
import net.minecraft.client.input.MouseButtonEvent;

import java.awt.*;

public class SidebarButtonWidget extends Widget {

    private static final Color BASE_COLOR = new Color(50, 50, 50);
    private static final int RADIUS = 5;

    private final Runnable onClick;

    public SidebarButtonWidget(int x, int y, int width, int height, Runnable onClick) {
        super(x, y, width, height);
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
    }

    @Override
    public void mouseClicked(MouseButtonEvent event) {
        if (hovered && event.button() == 0) {
            onClick.run();
        }
    }
}
