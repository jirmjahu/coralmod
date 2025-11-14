package net.coralmod.mod.gui.components;

import lombok.Getter;
import lombok.Setter;
import net.coralmod.mod.gui.ClickGuiScreen;
import net.coralmod.mod.gui.components.theme.ThemePanel;
import net.coralmod.mod.module.Module;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

@Getter
@Setter
public class Window {
    private static final int TITLE_BAR_HEIGHT = 20;
    private static final float TITLE_SCALE = 1.5f;
    private static final int THEME_BUTTON_HEIGHT = 12;
    private static final int THEME_BUTTON_PADDING = 10;
    private static final int THEME_BUTTON_X_OFFSET = 4;

    private final String title;
    private final ModulePanel modulePanel;
    private final ThemePanel themePanel;
    private int x, y, width, height;

    private SettingsPanel settingsPanel;
    private boolean showingThemes = false;
    private boolean dragging;
    private int dragX, dragY;

    public Window(String title, int x, int y, int width, int height) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        int panelY = y + TITLE_BAR_HEIGHT;
        int panelHeight = height - TITLE_BAR_HEIGHT;
        this.modulePanel = new ModulePanel(x, panelY, width, panelHeight, this::openSettings);
        this.themePanel = new ThemePanel(x, panelY, width, panelHeight);
        this.settingsPanel = null;
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (dragging) {
            move(mouseX + dragX - this.x, mouseY + dragY - this.y);
        }

        int x1 = x - 3;
        int y1 = y - 3;
        int x2 = x + width + 3;
        int y2 = y + height + 3;
        guiGraphics.fill(x1, y1, x2, y2, ClickGuiScreen.INSTANCE.getSelectedTheme().getColor());

        guiGraphics.fill(x, y, x + width, y + TITLE_BAR_HEIGHT, new Color(40, 40, 40).getRGB());
        renderTitle(guiGraphics);
        renderThemeToggleButton(guiGraphics, mouseX, mouseY);

        if (showingThemes) {
            themePanel.render(guiGraphics, mouseX, mouseY);
        } else if (settingsPanel != null) {
            settingsPanel.render(guiGraphics, mouseX, mouseY);
        } else {
            modulePanel.render(guiGraphics, mouseX, mouseY);
        }
    }

    private void renderTitle(GuiGraphics guiGraphics) {
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(x + 5, y + (TITLE_BAR_HEIGHT - 8f * TITLE_SCALE) / 2);
        guiGraphics.pose().scale(TITLE_SCALE, TITLE_SCALE);
        guiGraphics.drawString(ClickGuiScreen.INSTANCE.getFont(), title, 0, 0, ClickGuiScreen.INSTANCE.getSelectedTheme().getColor());
        guiGraphics.pose().popMatrix();
    }

    private void renderThemeToggleButton(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        Font font = ClickGuiScreen.INSTANCE.getFont();
        String themeButtonText = showingThemes ? "Modules" : "Themes";
        int buttonWidth = font.width(themeButtonText) + THEME_BUTTON_PADDING;
        int buttonX = x + width - buttonWidth - THEME_BUTTON_X_OFFSET;
        int buttonY = y + (TITLE_BAR_HEIGHT - THEME_BUTTON_HEIGHT) / 2;

        boolean hovered = isPointInRect(mouseX, mouseY, buttonX, buttonY, buttonWidth, THEME_BUTTON_HEIGHT);
        Color buttonColor = hovered ? new Color(100, 100, 100) : new Color(80, 80, 80);
        guiGraphics.fill(buttonX, buttonY, buttonX + buttonWidth, buttonY + THEME_BUTTON_HEIGHT, buttonColor.getRGB());

        int textX = buttonX + (buttonWidth - font.width(themeButtonText)) / 2;
        int textY = buttonY + (THEME_BUTTON_HEIGHT - font.lineHeight) / 2 + 1;
        guiGraphics.drawString(font, themeButtonText, textX, textY, Color.WHITE.getRGB());
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isTitleBarHovered(mouseX, mouseY) && button == 0) {
            if (isThemeToggleButtonHovered(mouseX, mouseY)) {
                showingThemes = !showingThemes;
                settingsPanel = null;
            } else {
                dragging = true;
                dragX = (int) (x - mouseX);
                dragY = (int) (y - mouseY);
            }
            return;
        }

        if (showingThemes) {
            themePanel.mouseClicked(mouseX, mouseY, button);
        } else if (settingsPanel != null) {
            settingsPanel.mouseClicked(mouseX, mouseY, button);
        } else {
            modulePanel.mouseClicked(mouseX, mouseY, button);
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        dragging = false;
        if (settingsPanel != null && !showingThemes) {
            settingsPanel.mouseReleased(mouseX, mouseY, button);
        }
    }

    private void move(int deltaX, int deltaY) {
        this.x += deltaX;
        this.y += deltaY;
        modulePanel.move(deltaX, deltaY);
        themePanel.move(deltaX, deltaY);
        if (settingsPanel != null) {
            settingsPanel.move(deltaX, deltaY);
        }
    }

    private void openSettings(Module module) {
        if (!module.getSettings().isEmpty()) {
            int panelY = y + TITLE_BAR_HEIGHT;
            int panelHeight = height - TITLE_BAR_HEIGHT;
            this.settingsPanel = new SettingsPanel(module, x, panelY, width, panelHeight, () -> this.settingsPanel = null);
        }
    }

    private boolean isTitleBarHovered(double mouseX, double mouseY) {
        return isPointInRect(mouseX, mouseY, x, y, width, TITLE_BAR_HEIGHT);
    }

    private boolean isThemeToggleButtonHovered(double mouseX, double mouseY) {
        Font font = ClickGuiScreen.INSTANCE.getFont();
        String themeButtonText = showingThemes ? "Modules" : "Themes";
        int buttonWidth = font.width(themeButtonText) + THEME_BUTTON_PADDING;
        int buttonX = x + width - buttonWidth - THEME_BUTTON_X_OFFSET;
        int buttonY = y + (TITLE_BAR_HEIGHT - THEME_BUTTON_HEIGHT) / 2;
        return isPointInRect(mouseX, mouseY, buttonX, buttonY, buttonWidth, THEME_BUTTON_HEIGHT);
    }

    private boolean isPointInRect(double px, double py, int rx, int ry, int rw, int rh) {
        return px >= rx && px <= rx + rw && py >= ry && py <= ry + rh;
    }
}
