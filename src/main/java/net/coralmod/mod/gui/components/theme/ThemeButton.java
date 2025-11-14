package net.coralmod.mod.gui.components.theme;

import lombok.Getter;
import lombok.Setter;
import net.coralmod.mod.gui.ClickGuiScreen;
import net.coralmod.mod.theme.CoralEnum;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

@Getter
@Setter
public class ThemeButton {

    private static final int OUTLINE_WIDTH = 2;
    private static final int ITEM_SIZE = 16;

    private final CoralEnum theme;
    private int x, y, width, height;

    public ThemeButton(CoralEnum theme, int x, int y, int width, int height) {
        this.theme = theme;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        boolean isSelected = ClickGuiScreen.INSTANCE.getSelectedTheme() == theme;
        Color color = new Color(theme.getColor());

        if (isMouseOver(mouseX, mouseY)) {
            color = color.brighter();
        }

        guiGraphics.fill(x, y, x + width, y + height, color.getRGB());

        if (isSelected) {
            renderSelectionOutline(guiGraphics);
        }

        Font font = ClickGuiScreen.INSTANCE.getFont();
        String name = theme.getName();

        int itemX = x + (width - ITEM_SIZE) / 2;
        int itemY = y + (height - ITEM_SIZE - font.lineHeight) / 2;
        guiGraphics.renderItem(new ItemStack(theme.getItem()), itemX, itemY);

        int textX = x + (width - font.width(name)) / 2;
        int textY = itemY + ITEM_SIZE + 3;
        guiGraphics.drawString(font, name, textX, textY, Color.WHITE.getRGB());
    }

    private void renderSelectionOutline(GuiGraphics guiGraphics) {
        guiGraphics.fill(x, y, x + width, y + OUTLINE_WIDTH, Color.WHITE.getRGB());
        guiGraphics.fill(x, y + height - OUTLINE_WIDTH, x + width, y + height, Color.WHITE.getRGB());
        guiGraphics.fill(x, y + OUTLINE_WIDTH, x + OUTLINE_WIDTH, y + height - OUTLINE_WIDTH, Color.WHITE.getRGB());
        guiGraphics.fill(x + width - OUTLINE_WIDTH, y + OUTLINE_WIDTH, x + width, y + height - OUTLINE_WIDTH, Color.WHITE.getRGB());
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            ClickGuiScreen.INSTANCE.setSelectedTheme(theme);
        }
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
