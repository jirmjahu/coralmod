package net.coralmod.mod.gui.components;

import lombok.Getter;
import lombok.Setter;
import net.coralmod.mod.gui.ClickGuiScreen;
import net.coralmod.mod.gui.components.settings.BooleanComponent;
import net.coralmod.mod.gui.components.settings.ModeComponent;
import net.coralmod.mod.gui.components.settings.NumberComponent;
import net.coralmod.mod.gui.components.settings.SettingComponent;
import net.coralmod.mod.module.Module;
import net.coralmod.mod.module.settings.BooleanSetting;
import net.coralmod.mod.module.settings.ModeSetting;
import net.coralmod.mod.module.settings.NumberSetting;
import net.coralmod.mod.module.settings.Setting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SettingsPanel {

    private static final int HEADER_HEIGHT = 22;
    private static final int COMPONENT_HEIGHT = 12;
    private static final int BACK_BUTTON_X_OFFSET = 5;
    private static final int BACK_BUTTON_Y_OFFSET = 5;
    private static final int BACK_BUTTON_WIDTH = 40;
    private static final int BACK_BUTTON_HEIGHT = 12;

    private final Module module;
    private final List<SettingComponent<?>> settingComponents;
    private final Runnable backAction;

    private int x, y, width, height;

    public SettingsPanel(Module module, int x, int y, int width, int height, Runnable backAction) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.backAction = backAction;
        this.settingComponents = new ArrayList<>();

        populateSettings();
    }

    private void populateSettings() {
        int currentY = y + HEADER_HEIGHT;

        for (Setting<?> setting : module.getSettings()) {
            SettingComponent<?> component = null;
            if (setting instanceof BooleanSetting) {
                component = new BooleanComponent((BooleanSetting) setting, x, currentY, width, COMPONENT_HEIGHT);
            } else if (setting instanceof ModeSetting) {
                component = new ModeComponent((ModeSetting) setting, x, currentY, width, COMPONENT_HEIGHT);
            } else if (setting instanceof NumberSetting) {
                component = new NumberComponent((NumberSetting) setting, x, currentY, width, COMPONENT_HEIGHT);
            }

            if (component != null) {
                settingComponents.add(component);
                currentY += COMPONENT_HEIGHT;
            }
        }
    }


    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.fill(x, y, x + width, y + height, new Color(60, 60, 60).getRGB());
        renderBackButton(guiGraphics, mouseX, mouseY);

        for (SettingComponent<?> component : settingComponents) {
            component.render(guiGraphics, mouseX, mouseY);
        }
    }

    private void renderBackButton(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        Font font = ClickGuiScreen.INSTANCE.getFont();
        int backButtonX = x + BACK_BUTTON_X_OFFSET;
        int backButtonY = y + BACK_BUTTON_Y_OFFSET;

        boolean hovered = isBackButtonHovered(mouseX, mouseY);
        int buttonColor = hovered ? ClickGuiScreen.INSTANCE.getSelectedTheme().getColor() : new Color(100, 100, 100).getRGB();
        guiGraphics.fill(backButtonX, backButtonY, backButtonX + BACK_BUTTON_WIDTH, backButtonY + BACK_BUTTON_HEIGHT, buttonColor);

        String backText = "← Back";
        int textX = backButtonX + (BACK_BUTTON_WIDTH - font.width(backText)) / 2;
        int textY = backButtonY + (BACK_BUTTON_HEIGHT - font.lineHeight) / 2 + 1;
        guiGraphics.drawString(font, backText, textX, textY, Color.WHITE.getRGB());
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isBackButtonHovered(mouseX, mouseY) && button == 0) {
            backAction.run();
            return;
        }

        if (isContentArea(mouseX, mouseY)) {
            for (SettingComponent<?> component : settingComponents) {
                component.mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY)) {
            for (SettingComponent<?> component : settingComponents) {
                component.mouseReleased(mouseX, mouseY, button);
            }
        }
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
        for (SettingComponent<?> component : settingComponents) {
            component.move(dx, dy);
        }
    }

    public boolean isBackButtonHovered(double mouseX, double mouseY) {
        int backButtonX = x + BACK_BUTTON_X_OFFSET;
        int backButtonY = y + BACK_BUTTON_Y_OFFSET;
        return mouseX >= backButtonX && mouseX <= backButtonX + BACK_BUTTON_WIDTH && mouseY >= backButtonY && mouseY <= backButtonY + BACK_BUTTON_HEIGHT;
    }

    private boolean isContentArea(double mouseX, double mouseY) {
        return isMouseOver(mouseX, mouseY) && mouseY > y + HEADER_HEIGHT;
    }

    private boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
