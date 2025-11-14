package net.coralmod.mod.gui.components.theme;

import lombok.Getter;
import lombok.Setter;
import net.coralmod.mod.theme.CoralEnum;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ThemePanel {

    private static final int PADDING = 5;
    private static final int COLUMNS = 4;
    private static final int HORIZONTAL_GAP = 5;
    private static final int VERTICAL_GAP = 5;
    private static final int BUTTON_HEIGHT = 40;

    private final List<ThemeButton> themeButtons;
    private int x, y, width, height;

    public ThemePanel(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.themeButtons = new ArrayList<>();

        int availableWidth = width - 2 * PADDING;
        int buttonWidth = (availableWidth - (COLUMNS - 1) * HORIZONTAL_GAP) / COLUMNS;

        CoralEnum[] themes = CoralEnum.values();
        for (int i = 0; i < themes.length; i++) {
            int row = i / COLUMNS;
            int col = i % COLUMNS;
            int buttonX = x + PADDING + col * (buttonWidth + HORIZONTAL_GAP);
            int buttonY = y + PADDING + row * (BUTTON_HEIGHT + VERTICAL_GAP);
            themeButtons.add(new ThemeButton(themes[i], buttonX, buttonY, buttonWidth, BUTTON_HEIGHT));
        }
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.fill(x, y, x + width, y + height, new Color(60, 60, 60).getRGB());

        for (ThemeButton button : themeButtons) {
            button.render(guiGraphics, mouseX, mouseY);
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (!isMouseOver(mouseX, mouseY)) {
            return;
        }

        for (ThemeButton themeButton : themeButtons) {
            if (themeButton.isMouseOver(mouseX, mouseY)) {
                themeButton.mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
        for (ThemeButton button : themeButtons) {
            button.move(dx, dy);
        }
    }

    private boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
