package net.coralmod.mod.ui;

import net.coralmod.mod.render.DrawContext;
import net.coralmod.mod.utils.MouseUtils;
import net.minecraft.client.input.MouseButtonEvent;

public class Widget {

    protected final int x;
    protected final int y;
    protected int width;
    protected int height;
    protected boolean hovered;
    protected Window parent;

    public Widget(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void init() {}

    public void render(DrawContext context, int mouseX, int mouseY, int scrollOffset) {
        hovered = MouseUtils.isMouseOver(mouseX, mouseY, x, y - scrollOffset, width, height);
    }

    public void mouseClicked(MouseButtonEvent event) {}
    public void mouseReleased(MouseButtonEvent event) {}
}
