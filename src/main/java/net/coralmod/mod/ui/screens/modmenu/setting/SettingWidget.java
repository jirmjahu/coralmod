package net.coralmod.mod.ui.screens.modmenu.setting;

import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.settings.Setting;
import net.coralmod.mod.render.DrawContext;
import net.coralmod.mod.render.FontRenderer;
import net.coralmod.mod.ui.Widget;
import net.coralmod.mod.ui.screens.modmenu.ModMenuScreen;
import net.coralmod.mod.utils.ColorUtils;

import java.awt.*;

public abstract class SettingWidget<T extends Setting<?>> extends Widget {

    public static final int ROW_HEIGHT = 22;
    public static final int CONTROL_HEIGHT = 12;
    public static final float TEXT_SCALE = 0.5F;

    public static final Color ROW_BACKGROUND_COLOR = ModMenuScreen.BASE_GRAY;
    public static final Color ROW_HOVER_COLOR = ColorUtils.blendColors(ModMenuScreen.BASE_GRAY, ColorUtils.modifyAlpha(ModMenuScreen.HOVER_COLOR, 8));
    public static final Color CONTROL_BACKGROUND_COLOR = ColorUtils.blendColors(ModMenuScreen.BASE_GRAY, ModMenuScreen.HOVER_COLOR);
    public static final Color SECONDARY_TEXT_COLOR = new Color(255, 255, 255, 100);

    protected final T setting;
    protected int renderY;
    protected int controlY;

    public SettingWidget(T setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setting = setting;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, int scrollOffset) {
        super.render(context, mouseX, mouseY, scrollOffset);

        renderY = y - scrollOffset;
        controlY = renderY + (ROW_HEIGHT - CONTROL_HEIGHT) / 2;

        final Color rowColor = hovered ? ROW_HOVER_COLOR : ROW_BACKGROUND_COLOR;
        context.shapes().roundedRect(x, renderY, width, height, 5, rowColor);
        font(context).draw(setting.name(), x + ModMenuScreen.PADDING, centeredTextY(renderY, ROW_HEIGHT), TEXT_SCALE, Color.WHITE, false);
    }

    protected int controlRightX() {
        return x + width - ModMenuScreen.PADDING;
    }

    protected static Color accentColor() {
        return CoralMod.instance().selectedTheme().primaryColor();
    }

    public static FontRenderer font(DrawContext context) {
        return context.fonts().interBold();
    }

    public static int centeredTextY(int boxY, int boxHeight) {
        return boxY + boxHeight / 2 - 1;
    }
}
