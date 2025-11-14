package net.coralmod.mod.gui.components.settings;

import lombok.Getter;
import lombok.Setter;
import net.coralmod.mod.module.settings.Setting;
import net.minecraft.client.gui.GuiGraphics;

@Getter
@Setter
public abstract class SettingComponent<T extends Setting<?>> {

    protected final T setting;
    protected int x, y, width, height;

    public SettingComponent(T setting, int x, int y, int width, int height) {
        this.setting = setting;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void render(GuiGraphics guiGraphics, int mouseX, int mouseY);

    public abstract void mouseClicked(double mouseX, double mouseY, int button);

    public void mouseReleased(double mouseX, double mouseY, int button) {
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
}
