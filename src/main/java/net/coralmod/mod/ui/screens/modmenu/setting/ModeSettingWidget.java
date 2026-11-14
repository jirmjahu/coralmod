package net.coralmod.mod.ui.screens.modmenu.setting;

import com.mojang.blaze3d.platform.InputConstants;
import net.coralmod.mod.module.settings.ModeSetting;
import net.coralmod.mod.render.DrawContext;
import net.coralmod.mod.render.FontRenderer;
import net.coralmod.mod.ui.screens.modmenu.ModMenuScreen;
import net.coralmod.mod.utils.MouseUtils;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.resources.Identifier;

import java.awt.*;
import java.util.List;

public class ModeSettingWidget extends SettingWidget<ModeSetting> {

    private static final int OPTION_HEIGHT = 14;
    private static final int RADIO_SIZE = 8;
    private static final int RADIO_DOT_SIZE = 4;
    private static final int ARROW_ICON_PADDING = 2;
    private static final int CONTROL_GAP = 4;

    private static final Identifier ARROW_DOWN_ICON = Identifier.fromNamespaceAndPath("coralmod", "textures/gui/setting/chevron_down.png");
    private static final Identifier ARROW_UP_ICON = Identifier.fromNamespaceAndPath("coralmod", "textures/gui/setting/chevron_up.png");

    private boolean expanded;

    public ModeSettingWidget(ModeSetting setting, int x, int y, int width, int height) {
        super(setting, x, y, width, height);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, int scrollOffset) {
        height = expanded ? ROW_HEIGHT + setting.modes().size() * OPTION_HEIGHT + CONTROL_GAP : ROW_HEIGHT;
        super.render(context, mouseX, mouseY, scrollOffset);

        final FontRenderer font = font(context);

        final int arrowX = arrowButtonX();
        final int arrowIconSize = CONTROL_HEIGHT - ARROW_ICON_PADDING * 2;
        context.shapes().roundedRect(arrowX, controlY, CONTROL_HEIGHT, CONTROL_HEIGHT, 4, CONTROL_BACKGROUND_COLOR);
        context.textures().draw(
                expanded ? ARROW_UP_ICON : ARROW_DOWN_ICON,
                arrowX + ARROW_ICON_PADDING,
                controlY + ARROW_ICON_PADDING,
                arrowIconSize,
                arrowIconSize
        );

        final String currentMode = setting.value();
        final int currentModeWidth = font.width(currentMode, TEXT_SCALE) + ModMenuScreen.PADDING;
        final int currentModeX = arrowX - CONTROL_GAP - currentModeWidth;
        context.shapes().roundedRect(currentModeX, controlY, currentModeWidth, CONTROL_HEIGHT, 4, CONTROL_BACKGROUND_COLOR);
        font.draw(
                currentMode,
                currentModeX + ModMenuScreen.PADDING / 2,
                centeredTextY(controlY, CONTROL_HEIGHT),
                TEXT_SCALE,
                accentColor().brighter(),
                false
        );

        if (!expanded) {
            return;
        }

        final List<String> modes = setting.modes();
        for (int i = 0; i < modes.size(); i++) {
            final String mode = modes.get(i);
            final boolean selected = mode.equals(currentMode);
            final int optionY = renderY + ROW_HEIGHT + i * OPTION_HEIGHT;
            final int radioX = x + ModMenuScreen.PADDING;
            final int radioY = optionY + (OPTION_HEIGHT - RADIO_SIZE) / 2;

            context.shapes().roundedRect(radioX, radioY, RADIO_SIZE, RADIO_SIZE, RADIO_SIZE / 2F, selected ? accentColor() : CONTROL_BACKGROUND_COLOR);

            if (selected) {
                final int dotOffset = (RADIO_SIZE - RADIO_DOT_SIZE) / 2;
                context.shapes().roundedRect(radioX + dotOffset, radioY + dotOffset, RADIO_DOT_SIZE, RADIO_DOT_SIZE, RADIO_DOT_SIZE / 2F, Color.WHITE);
            }

            font.draw(
                    mode,
                    radioX + RADIO_SIZE + CONTROL_GAP,
                    centeredTextY(optionY, OPTION_HEIGHT),
                    TEXT_SCALE,
                    selected ? Color.WHITE : SECONDARY_TEXT_COLOR,
                    false
            );
        }
    }

    private int arrowButtonX() {
        return controlRightX() - CONTROL_HEIGHT;
    }

    @Override
    public void mouseClicked(MouseButtonEvent event) {
        final List<String> modes = setting.modes();
        final boolean clickedTopRow = event.y() < renderY + ROW_HEIGHT;
        final boolean clickedArrow = MouseUtils.isMouseOver(event.x(), event.y(), arrowButtonX(), controlY, CONTROL_HEIGHT, CONTROL_HEIGHT);

        if (event.button() == InputConstants.MOUSE_BUTTON_RIGHT || clickedArrow) {
            expanded = !expanded;
        } else if (event.button() == InputConstants.MOUSE_BUTTON_LEFT && clickedTopRow) {
            final int nextIndex = (modes.indexOf(setting.value()) + 1) % modes.size();
            setting.value(modes.get(nextIndex));
        } else if (event.button() == InputConstants.MOUSE_BUTTON_LEFT) {
            final int clickedIndex = (int) (event.y() - renderY - ROW_HEIGHT) / OPTION_HEIGHT;
            setting.value(modes.get(Math.min(clickedIndex, modes.size() - 1)));
        }
    }
}
