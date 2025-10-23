package net.coralmod.mod.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

@Getter
@Setter
@AllArgsConstructor
public abstract class HudModule extends Module {

    private int x;
    private int y;
    private int width;
    private int height;
    private String message;

    public HudModule(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void render(DrawContext context, TextRenderer textRenderer) {
    }
}
