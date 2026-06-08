package net.coralmod.mod.module;

import net.coralmod.mod.module.settings.BooleanSetting;
import net.coralmod.mod.render.DrawContext;
import net.coralmod.mod.render.FontRenderer;
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

    public void render(DrawContext context) {
        final String text = brackets.value() ? "[" + getText() + "]" : getText();
        final FontRenderer font = context.fonts().cascadiaCode();

        final int textWidth = font.width(text);
        final int textHeight = font.height();
        final int padding = background.value() ? 4 : 0;

        width = textWidth + padding * 2;
        height = textHeight + padding * 2;

        if (background.value()) {
            context.shapes().rect(
                    x,
                    y,
                    x + width,
                    y + height,
                    new Color(0, 0, 0, 140)
            );
        }

        final int textX = x + (width - textWidth) / 2;
        final int textY = y + (height - textHeight) / 2 + 1;

        font.draw(text, textX, textY, Color.WHITE, textShadow.value());
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
