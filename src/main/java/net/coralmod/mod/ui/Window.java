package net.coralmod.mod.ui;

import net.coralmod.mod.CoralMod;
import net.coralmod.mod.render.DrawContext;
import net.coralmod.mod.ui.screens.modmenu.ModMenuScreen;
import net.minecraft.client.input.MouseButtonEvent;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Window {

    protected final ModMenuScreen parent;
    protected final String name;
    protected final int x;
    protected int y;

    protected int width = ModMenuScreen.MENU_WIDTH;
    protected int height = ModMenuScreen.MENU_HEIGHT;

    protected int scrollOffset = 0;
    protected int maxScroll = 0;

    protected final List<Widget> widgets = new CopyOnWriteArrayList<>();

    public Window(ModMenuScreen parent, String name, int x, int y) {
        this.parent = parent;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public void init() {
        for (Widget widget : widgets) {
            widget.init();
            maxScroll = Math.max(maxScroll, (widget.y + widget.height) - (y + height - ModMenuScreen.PADDING));
        }
        maxScroll = Math.max(0, maxScroll);
    }

       public void render(DrawContext context, int mouseX, int mouseY) {
        final int contentY = contentTop();
        final int contentBottom = y + height - ModMenuScreen.PADDING;
        final int contentHeight = contentBottom - contentY;

        // widgets scrolled out of the visible area shouldn't be hovered, so hide the mouse from them
        final boolean mouseInContent = mouseY >= contentY && mouseY <= contentBottom;
        final int widgetMouseY = mouseInContent ? mouseY : Integer.MIN_VALUE / 2;

        context.enableScissor(x, contentY, x + width, contentBottom);
        for (Widget widget : widgets) {
            widget.render(context, mouseX, widgetMouseY, scrollOffset);
        }
        context.disableScissor();

        if (maxScroll > 10) {
            final int barWidth = 3;
            final int barX = x + width - barWidth - 2;
            final int barHeight = (int) ((float) contentHeight / (contentHeight + maxScroll) * contentHeight);
            final int barY = contentY + (int) ((float) scrollOffset / maxScroll * (contentHeight - barHeight));

            context.shapes().roundedRect(barX, contentY, barWidth, contentHeight, 2, ModMenuScreen.BASE_GRAY);
            context.shapes().roundedRect(barX, barY, barWidth, barHeight, 2, CoralMod.instance().selectedTheme().secondaryColor());
        }
    }

    protected int contentTop() {
        return y + ModMenuScreen.PADDING;
    }

    public void mouseClicked(MouseButtonEvent event) {
        widgets.stream().filter(widget -> widget.hovered).forEach(widget -> widget.mouseClicked(event));
    }

    public void mouseReleased(MouseButtonEvent event) {
        widgets.forEach(widget -> widget.mouseReleased(event));
    }

    protected void addWidget(Widget widget) {
        widgets.add(widget);
        widget.parent = this;
    }

    public void mouseScrolled(double delta) {
        scrollOffset = Math.clamp(scrollOffset - (int) (delta * 10), 0, maxScroll);
    }

    public ModMenuScreen parent() {
        return parent;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int scrollOffset() {
        return scrollOffset;
    }
}
