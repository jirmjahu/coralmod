package net.coralmod.mod.ui.screens.modmenu;

import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.Module;
import net.coralmod.mod.render.DrawContext;
import net.coralmod.mod.render.FontRenderer;
import net.coralmod.mod.theme.Theme;
import net.coralmod.mod.ui.Widget;
import net.coralmod.mod.utils.ColorUtils;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.resources.Identifier;

import java.awt.*;

public class ModuleButtonWidget extends Widget {

    private static final float NAME_SCALE = 0.55f;
    private static final float TAG_SCALE = 0.45f;
    private static final int TAG_PADDING_X = 5;
    private static final int TAG_PADDING_Y = 3;

    private final Module module;

    public ModuleButtonWidget(Module module, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.module = module;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, int scrollOffset) {
        super.render(context, mouseX, mouseY, scrollOffset);

        final int renderY = y - scrollOffset;
        final Theme theme = CoralMod.instance().selectedTheme();
        final Color baseGray = ModMenuScreen.BASE_GRAY;

        Color backgroundColor = baseGray;

        if (module.enabled()) {
            final Color themeColor = ColorUtils.modifyAlpha(theme.primaryColor(), 100);
            backgroundColor = ColorUtils.blendColors(baseGray, themeColor);
        }

        if (hovered) {
            backgroundColor = ColorUtils.blendColors(backgroundColor, ModMenuScreen.HOVER_COLOR);
        }

        context.shapes().roundedRect(
                x,
                renderY,
                width,
                height,
                6,
                backgroundColor
        );

        final int margin = 8;
        final int iconSize = width / 6;
        context.textures().draw(
                Identifier.fromNamespaceAndPath("coralmod", "textures/module/" + module.name().replaceAll("\\s", "").toLowerCase() + ".png"),
                x + margin,
                renderY + (height - iconSize) / 2,
                iconSize,
                iconSize
        );

        final FontRenderer font = context.fonts().interExtraBold();
        final int nameHeight = font.height(NAME_SCALE);
        final int tagHeight = font.height(TAG_SCALE) + TAG_PADDING_Y * 2;

        final int contentHeight = nameHeight + 4 + tagHeight;
        final int contentY = renderY + (height - contentHeight) / 2;

        final int textX = x + iconSize + margin * 2;

        font.draw(module.name(), textX, contentY, NAME_SCALE, Color.WHITE, false);

        drawTag(context, module.category().displayName(), theme.primaryColor().brighter(), textX, contentY + nameHeight + 4);
    }

    private void drawTag(DrawContext context, String text, Color tagColor, int x, int y) {
        final FontRenderer font = context.fonts().interExtraBold();

        final int textWidth = font.width(text, TAG_SCALE);
        final int tagWidth = textWidth + TAG_PADDING_X * 2;
        final int tagHeight = font.height(TAG_SCALE) + TAG_PADDING_Y * 2;

        final Color background = new Color(
                tagColor.getRed(),
                tagColor.getGreen(),
                tagColor.getBlue(),
                60
        );

        context.shapes().roundedRect(x, y, tagWidth, tagHeight, 3, background);

        font.draw(
                text,
                x + TAG_PADDING_X,
                y + TAG_PADDING_Y + 1,
                0.4F,
                tagColor,
                false
        );
    }

    @Override
    public void mouseClicked(MouseButtonEvent event) {
        if (hovered) {
            if (event.button() == 0) {
                module.enabled(!module.enabled());
            } else if (event.button() == 1) {
                parent.parent().switchWindow(new ModuleSettingsWindow(parent.parent(), module, "Settings", parent.x(), parent.y()));
            }
        }
    }
}
