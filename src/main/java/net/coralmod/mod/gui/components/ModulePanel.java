package net.coralmod.mod.gui.components;

import lombok.Getter;
import lombok.Setter;
import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.Module;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
@Setter
public class ModulePanel {

    private static final int PADDING = 5;
    private static final int COLUMNS = 4;
    private static final int HORIZONTAL_GAP = 5;
    private static final int VERTICAL_GAP = 5;
    private static final int BUTTON_HEIGHT = 40;

    private final List<ModuleButton> moduleButtons;
    private final Consumer<Module> onModuleClick;
    private int x, y, width, height;

    public ModulePanel(int x, int y, int width, int height, Consumer<Module> onModuleClick) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.onModuleClick = onModuleClick;
        this.moduleButtons = new ArrayList<>();

        initButtons();
    }

    private void initButtons() {
        int availableWidth = width - 2 * PADDING;
        int buttonWidth = (availableWidth - (COLUMNS - 1) * HORIZONTAL_GAP) / COLUMNS;

        List<Module> modules = CoralMod.getInstance().getModuleManager().getModules();
        for (int i = 0; i < modules.size(); i++) {
            int row = i / COLUMNS;
            int col = i % COLUMNS;
            int buttonX = x + PADDING + col * (buttonWidth + HORIZONTAL_GAP);
            int buttonY = y + PADDING + row * (BUTTON_HEIGHT + VERTICAL_GAP);
            moduleButtons.add(new ModuleButton(modules.get(i), buttonX, buttonY, buttonWidth, BUTTON_HEIGHT));
        }
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.fill(x, y, x + width, y + height, new Color(60, 60, 60).getRGB());

        for (ModuleButton button : moduleButtons) {
            button.render(guiGraphics, mouseX, mouseY);
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (!isMouseOver(mouseX, mouseY)) {
            return;
        }

        for (ModuleButton moduleButton : moduleButtons) {
            if (moduleButton.isMouseOver(mouseX, mouseY)) {
                moduleButton.mouseClicked(mouseX, mouseY, button);
                if (button == 0 && !moduleButton.isToggleHovered(mouseX, mouseY)) {
                    onModuleClick.accept(moduleButton.getModule());
                }
            }
        }
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
        for (ModuleButton button : moduleButtons) {
            button.move(dx, dy);
        }
    }

    private boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
