package net.coralmod.mod.ui;

import net.coralmod.mod.ui.screens.modmenu.ModMenuScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
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
    protected int height = ModMenuScreen.MENU_HEIGHT - ModMenuScreen.MENU_TITLE_BAR_HEIGHT;

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
            maxScroll = Math.max(maxScroll, (widget.y + widget.height) - (y + height));
        }
        maxScroll = Math.max(0, maxScroll + 10);
    }

    public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        final int contentY = y + ModMenuScreen.BUTTON_TOP_MARGIN;
        final int contentHeight = height - ModMenuScreen.BUTTON_TOP_MARGIN;

        graphics.enableScissor(x, contentY, x + width, y + height);
        for (Widget widget : widgets) {
            widget.render(graphics, mouseX, mouseY, scrollOffset);
        }
        graphics.disableScissor();

        if (maxScroll > 10) {
            final int barWidth = 2;
            final int barX = x + width - barWidth - 2;
            final int barHeight = (int) ((float) contentHeight / (contentHeight + maxScroll) * contentHeight);
            final int barY = contentY + (int) ((float) scrollOffset / maxScroll * (contentHeight - barHeight));

            graphics.fill(barX, contentY, barX + barWidth, contentY + contentHeight, ModMenuScreen.BASE_GRAY.darker().getRGB());
            graphics.fill(barX, barY, barX + barWidth, barY + barHeight, Color.GRAY.getRGB());
        }
    }

    public void mouseClicked(MouseButtonEvent event) {
        for (Widget widget : widgets) {
            if (widget.hovered) {
                widget.mouseClicked(event);
            }
        }
    }

    public void mouseReleased(MouseButtonEvent event) {
        for (Widget widget : widgets) {
            widget.mouseReleased(event);
        }
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
