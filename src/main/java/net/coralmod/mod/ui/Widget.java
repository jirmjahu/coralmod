package net.coralmod.mod.ui;

import lombok.RequiredArgsConstructor;
import net.coralmod.mod.utils.MouseUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.input.MouseButtonEvent;

@RequiredArgsConstructor
public class Widget {

    protected final int x;
    protected final int y;
    protected int width;
    protected int height;
    protected boolean hovered;

    public Widget(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        hovered = MouseUtils.isMouseOver(mouseX, mouseY, x, y, width, height);
    }

    public void mouseClicked(MouseButtonEvent mouseButtonEvent) {
    }

}
