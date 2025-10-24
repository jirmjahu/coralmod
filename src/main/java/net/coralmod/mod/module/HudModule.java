package net.coralmod.mod.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.coralmod.mod.module.settings.BooleanSetting;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

@Getter
@Setter
@AllArgsConstructor
public abstract class HudModule extends Module {

    private int x;
    private int y;
    private int width;
    private int height;
    private String text;

    protected BooleanSetting background = new BooleanSetting("Background", true);
    protected BooleanSetting brackets = new BooleanSetting("Brackets", false);
    protected BooleanSetting textShadow = new BooleanSetting("Text Shadow", true);

    public HudModule(int x, int y) {
        this.x = x;
        this.y = y;
        addSettings(background, brackets, textShadow);
    }

    public void render(DrawContext context, TextRenderer textRenderer) {
        final String text = brackets.getValue() ? "[" + this.text + "]" : this.text;

        final int textWidth = textRenderer.getWidth(text);
        final int textHeight = textRenderer.fontHeight;

        final int padding = background.getValue() ? 4 : 0;

        width = textWidth + padding * 2;
        height = textHeight + padding * 2;

        if (background.getValue()) {
            context.fill(
                    x,
                    y,
                    x + width,
                    y + height,
                    new Color(0, 0, 0, 140).getRGB()
            );
        }

        final int textX = x + (width - textWidth) / 2;
        final int textY = y + (height - textHeight) / 2 + 1;

        context.drawText(
                textRenderer,
                text,
                textX,
                textY,
                -1,
                textShadow.getValue()
        );
    }
}
