package net.coralmod.mod.ui.screens.modmenu;

import net.coralmod.mod.CoralMod;
import net.coralmod.mod.render.DrawContext;
import net.coralmod.mod.render.FontRenderer;
import net.coralmod.mod.theme.Theme;
import net.coralmod.mod.ui.Widget;
import net.coralmod.mod.utils.ColorUtils;
import net.coralmod.mod.utils.Notification;
import net.minecraft.client.input.MouseButtonEvent;

import java.awt.*;

public class ThemeButtonWidget extends Widget {

    private final Theme theme;
    private static final int BORDER_THICKNESS = 2;
    private static final float NAME_SCALE = 0.55f;

    public ThemeButtonWidget(Theme theme, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.theme = theme;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, int scrollOffset) {
        super.render(context, mouseX, mouseY, scrollOffset);

        final int renderY = y - scrollOffset;
        final Color themeColor = theme.primaryColor();

        Color borderColor = themeColor;
        Color backgroundColor = ColorUtils.blendColors(ModMenuScreen.BASE_GRAY, ColorUtils.modifyAlpha(themeColor, 100));

        if (hovered) {
            backgroundColor = ColorUtils.blendColors(backgroundColor, ModMenuScreen.HOVER_COLOR);
            borderColor = ColorUtils.blendColors(borderColor, ModMenuScreen.HOVER_COLOR);
        }

        context.shapes().roundedRect(
                x,
                renderY,
                width,
                height,
                6,
                borderColor
        );

        context.shapes().roundedRect(
                x + BORDER_THICKNESS,
                renderY + BORDER_THICKNESS,
                width - BORDER_THICKNESS * 2,
                height - BORDER_THICKNESS * 2,
                4,
                backgroundColor
        );

        final FontRenderer font = context.fonts().interExtraBold();

        context.textures().item(
                theme.displayItem(),
                x + width / 2,
                renderY + height / 2 - 7,
                1.5f
        );

        font.draw(
                theme.displayName(),
                x + width / 2 - font.width(theme.displayName(), NAME_SCALE) / 2,
                renderY + height / 2 + 13,
                NAME_SCALE,
                Color.WHITE,
                false
        );
    }

    @Override
    public void mouseClicked(MouseButtonEvent event) {
        CoralMod.instance().selectedTheme(theme);
        Notification.send("Updated Theme", "Theme was updated to: " + theme.displayName());
    }
}
