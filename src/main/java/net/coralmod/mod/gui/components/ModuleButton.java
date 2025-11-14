package net.coralmod.mod.gui.components;

import lombok.Getter;
import lombok.Setter;
import net.coralmod.mod.gui.ClickGuiScreen;
import net.coralmod.mod.module.Module;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

@Getter
@Setter
public class ModuleButton {

    private static final int TOGGLE_HEIGHT = 14;
    private final Module module;
    private int x, y, width, height;

    public ModuleButton(Module module, int x, int y, int width, int height) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        Font font = ClickGuiScreen.INSTANCE.getFont();
        boolean toggleHovered = isToggleHovered(mouseX, mouseY);
        boolean mainHovered = isMouseOver(mouseX, mouseY) && !toggleHovered;

        Color backgroundColor = mainHovered ? new Color(80, 80, 80) : new Color(70, 70, 70);
        guiGraphics.fill(x, y, x + width, y + height, backgroundColor.getRGB());

        String name = module.getName();
        int nameX = x + (width - font.width(name)) / 2;
        int nameY = y + (height - TOGGLE_HEIGHT - font.lineHeight) / 2 + 1;
        guiGraphics.drawString(font, name, nameX, nameY, Color.WHITE.getRGB());

        int toggleY = y + height - TOGGLE_HEIGHT;
        Color toggleColor = module.isEnabled() ? new Color(80, 175, 80) : new Color(175, 80, 80);
        if (toggleHovered) {
            toggleColor = toggleColor.brighter();
        }
        guiGraphics.fill(x, toggleY, x + width, toggleY + TOGGLE_HEIGHT, toggleColor.getRGB());

        String status = module.isEnabled() ? "Enabled" : "Disabled";
        int statusX = x + (width - font.width(status)) / 2;
        int statusY = toggleY + (TOGGLE_HEIGHT - font.lineHeight) / 2 + 1;
        guiGraphics.drawString(font, status, statusX, statusY, Color.WHITE.getRGB());
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isToggleHovered(mouseX, mouseY) && button == 0) {
            module.setEnabled(!module.isEnabled());
        }
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public boolean isToggleHovered(double mouseX, double mouseY) {
        int toggleY = y + height - TOGGLE_HEIGHT;
        return mouseX >= x && mouseX <= x + width && mouseY >= toggleY && mouseY <= toggleY + TOGGLE_HEIGHT;
    }
}
