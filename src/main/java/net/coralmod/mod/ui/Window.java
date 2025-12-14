package net.coralmod.mod.ui;

import net.coralmod.mod.ui.modmenu.ModMenuScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.input.MouseButtonEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class Window {

    protected final ModMenuScreen parent;
    protected final String name;
    protected final int x;
    protected int y;

    protected final List<Widget> widgets = new CopyOnWriteArrayList<>();

    public Window(ModMenuScreen parent, String name, int x, int y) {
        this.parent = parent;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public void init() {
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        for (Widget widget : widgets) {
            widget.render(guiGraphics, mouseX, mouseY);
        }
    }

    public void mouseClicked(MouseButtonEvent mouseButtonEvent) {
        for (Widget widget : widgets) {
            if (widget.hovered) {
                widget.mouseClicked(mouseButtonEvent);
            }
        }
    }

    protected void addWidget(Widget widget) {
        widgets.add(widget);
    }
}
