package net.coralmod.mod.module;

import net.coralmod.mod.module.settings.BooleanSetting;
import net.coralmod.mod.utils.MouseUtils;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.awt.*;

public abstract class HudModule extends Module {

    private int x;
    private int y;
    private int width;
    private int height;

    protected BooleanSetting background = new BooleanSetting("Background", false);
    protected BooleanSetting brackets = new BooleanSetting("Text Brackets", true);
    protected BooleanSetting textShadow = new BooleanSetting("Text Shadow", true);

    public HudModule(int x, int y) {
        this.x = x;
        this.y = y;
        addSettings(background, brackets, textShadow);
    }

    public void render(GuiGraphicsExtractor graphics, Font font) {
        final String text = brackets.value() ? "[" + getText() + "]" : getText();

        final int textWidth = font.width(text);
        final int textHeight = font.lineHeight;
        final int padding = background.value() ? 4 : 0;

        width = textWidth + padding * 2;
        height = textHeight + padding * 2;

        if (background.value()) {
            graphics.fill(
                    x,
                    y,
                    x + width,
                    y + height,
                    new Color(0, 0, 0, 140).getRGB()
            );
        }

        final int textX = x + (width - textWidth) / 2;
        final int textY = y + (height - textHeight) / 2 + 1;

        graphics.text(font, text, textX, textY, -1, textShadow.value());
    }

    public abstract String getText();

    public boolean hovered(int mouseX, int mouseY) {
        return MouseUtils.isMouseOver(mouseX, mouseY, x, y, width, height);
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public void x(int x) {
        this.x = x;
    }

    public void y(int y) {
        this.y = y;
    }

    public void width(int width) {
        this.width = width;
    }

    public void height(int height) {
        this.height = height;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void reset() {
        super.reset();
        move(20, 20);
    }
}
