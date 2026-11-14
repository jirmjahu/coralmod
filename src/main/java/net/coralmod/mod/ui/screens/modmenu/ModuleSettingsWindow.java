package net.coralmod.mod.ui.screens.modmenu;

import net.coralmod.mod.module.Module;
import net.coralmod.mod.module.settings.BooleanSetting;
import net.coralmod.mod.module.settings.ModeSetting;
import net.coralmod.mod.module.settings.NumberSetting;
import net.coralmod.mod.module.settings.Setting;
import net.coralmod.mod.render.DrawContext;
import net.coralmod.mod.render.FontRenderer;
import net.coralmod.mod.ui.Widget;
import net.coralmod.mod.ui.Window;
import net.coralmod.mod.ui.screens.modmenu.setting.BooleanSettingWidget;
import net.coralmod.mod.ui.screens.modmenu.setting.ModeSettingWidget;
import net.coralmod.mod.ui.screens.modmenu.setting.NumberSettingWidget;
import net.coralmod.mod.ui.screens.modmenu.setting.SettingWidget;
import net.coralmod.mod.utils.ColorUtils;
import net.coralmod.mod.utils.MouseUtils;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.resources.Identifier;

import java.awt.*;

public class ModuleSettingsWindow extends Window {

    private static final int TOP_OFFSET = 8;
    private static final int HEADER_BUTTON_SIZE = 20;
    private static final int HEADER_ICON_PADDING = 3;
    private static final int RESET_BUTTON_WIDTH = 45;
    private static final int DIVIDER_OFFSET = 8;
    private static final int SETTING_GAP = 4;

    private static final float MODULE_NAME_SCALE = 0.6F;
    private static final float MODULE_DESCRIPTION_SCALE = 0.45F;

    private static final Color RESET_HOVER_COLOR = new Color(160, 70, 70);
    private static final Color DIVIDER_COLOR = new Color(255, 255, 255, 20);

    private static final Identifier BACK_ICON = Identifier.fromNamespaceAndPath("coralmod", "textures/gui/sidebar/back.png");

    private final Module module;

    public ModuleSettingsWindow(ModMenuScreen parent, Module module, String name, int x, int y) {
        super(parent, name, x, y);
        this.module = module;
    }

    @Override
    protected int contentTop() {
        return dividerY() + DIVIDER_OFFSET;
    }

    @Override
    public void init() {
        super.init();

        int currentWidgetY = contentTop();

        final int widgetX = x + ModMenuScreen.PADDING;
        final int widgetWidth = ModMenuScreen.MENU_WIDTH - 2 * ModMenuScreen.PADDING;
        final int widgetHeight = SettingWidget.ROW_HEIGHT;

        for (Setting<?> setting : module.settings()) {
            Widget settingWidget = null;

            if (setting instanceof BooleanSetting booleanSetting) {
                settingWidget = new BooleanSettingWidget(booleanSetting, widgetX, currentWidgetY, widgetWidth, widgetHeight);
            } else if (setting instanceof ModeSetting modeSetting) {
                settingWidget = new ModeSettingWidget(modeSetting, widgetX, currentWidgetY, widgetWidth, widgetHeight);
            } else if (setting instanceof NumberSetting numberSetting) {
                settingWidget = new NumberSettingWidget(numberSetting, widgetX, currentWidgetY, widgetWidth, widgetHeight);
            }

            if (settingWidget != null) {
                addWidget(settingWidget);
                currentWidgetY += widgetHeight + SETTING_GAP;
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY) {
        int currentWidgetY = contentTop();
        for (Widget widget : widgets) {
            widget.y(currentWidgetY);
            currentWidgetY += widget.height() + SETTING_GAP;
        }

        final int contentBottom = y + height - ModMenuScreen.PADDING;
        maxScroll = Math.max(0, currentWidgetY - SETTING_GAP - contentBottom);
        scrollOffset = Math.min(scrollOffset, maxScroll);

        super.render(context, mouseX, mouseY);

        final FontRenderer font = SettingWidget.font(context);

        if (module.settings().isEmpty()) {
            font.draw(
                    "This module has no settings",
                    x + ModMenuScreen.MENU_WIDTH / 2 - font.width("This module has no settings", SettingWidget.TEXT_SCALE) / 2,
                    parent.height / 2,
                    SettingWidget.TEXT_SCALE,
                    SettingWidget.SECONDARY_TEXT_COLOR,
                    false
            );
        }

        final int buttonY = y + TOP_OFFSET;

        final int backX = backButtonX();
        final int resetX = resetButtonX();

        final boolean hoverBack = MouseUtils.isMouseOver(
                mouseX, mouseY, backX, buttonY, HEADER_BUTTON_SIZE, HEADER_BUTTON_SIZE
        );

        final boolean hoverReset = MouseUtils.isMouseOver(
                mouseX, mouseY, resetX, buttonY, RESET_BUTTON_WIDTH, HEADER_BUTTON_SIZE
        );

        Color backColor = ModMenuScreen.BASE_GRAY;
        if (hoverBack) {
            backColor = ColorUtils.blendColors(backColor, ModMenuScreen.HOVER_COLOR);
        }

        Color resetColor = ModMenuScreen.BASE_GRAY;
        if (hoverReset) {
            resetColor = RESET_HOVER_COLOR;
        }

        final int backIconSize = HEADER_BUTTON_SIZE - HEADER_ICON_PADDING * 2;
        context.shapes().roundedRect(backX, buttonY, HEADER_BUTTON_SIZE, HEADER_BUTTON_SIZE, 5, backColor);
        context.textures().draw(BACK_ICON, backX + HEADER_ICON_PADDING, buttonY + HEADER_ICON_PADDING, backIconSize, backIconSize);

        if (!module.settings().isEmpty()) {
            context.shapes().roundedRect(resetX, buttonY, RESET_BUTTON_WIDTH, HEADER_BUTTON_SIZE, 5, resetColor);
            font.draw(
                    "Reset",
                    resetX + RESET_BUTTON_WIDTH / 2 - font.width("Reset", SettingWidget.TEXT_SCALE) / 2,
                    SettingWidget.centeredTextY(buttonY, HEADER_BUTTON_SIZE),
                    SettingWidget.TEXT_SCALE,
                    Color.WHITE,
                    false
            );
        }

        final int moduleIconX = backX + HEADER_BUTTON_SIZE + ModMenuScreen.PADDING;
        final int moduleTextX = moduleIconX + HEADER_BUTTON_SIZE + ModMenuScreen.PADDING;
        final int halfButtonSize = HEADER_BUTTON_SIZE / 2;
        final String description = module.description().isBlank() ? module.category().displayName() : module.description();

        context.textures().draw(
                Identifier.fromNamespaceAndPath("coralmod", "textures/module/" + module.name().replaceAll("\\s", "").toLowerCase() + ".png"),
                moduleIconX,
                buttonY,
                HEADER_BUTTON_SIZE,
                HEADER_BUTTON_SIZE
        );

        context.fonts().interExtraBold().draw(
                module.name(),
                moduleTextX,
                SettingWidget.centeredTextY(buttonY, halfButtonSize),
                MODULE_NAME_SCALE,
                Color.WHITE,
                false
        );

        context.fonts().inter().draw(
                description,
                moduleTextX,
                SettingWidget.centeredTextY(buttonY + halfButtonSize, halfButtonSize),
                MODULE_DESCRIPTION_SCALE,
                SettingWidget.SECONDARY_TEXT_COLOR,
                false
        );

        final int dividerY = dividerY();
        context.shapes().rect(
                x + ModMenuScreen.PADDING,
                dividerY,
                x + ModMenuScreen.MENU_WIDTH - ModMenuScreen.PADDING,
                dividerY + 1,
                DIVIDER_COLOR
        );
    }

    private int backButtonX() {
        return x + ModMenuScreen.PADDING;
    }

    private int resetButtonX() {
        return x + ModMenuScreen.MENU_WIDTH - ModMenuScreen.PADDING - RESET_BUTTON_WIDTH;
    }

    private int dividerY() {
        return y + TOP_OFFSET + HEADER_BUTTON_SIZE + DIVIDER_OFFSET;
    }

    @Override
    public void mouseClicked(MouseButtonEvent event) {
        super.mouseClicked(event);

        final int buttonY = y + TOP_OFFSET;

        final boolean hoverBack = MouseUtils.isMouseOver(
                event.x(), event.y(), backButtonX(), buttonY, HEADER_BUTTON_SIZE, HEADER_BUTTON_SIZE
        );

        final boolean hoverReset = MouseUtils.isMouseOver(
                event.x(), event.y(), resetButtonX(), buttonY, RESET_BUTTON_WIDTH, HEADER_BUTTON_SIZE
        );

        if (hoverBack) {
            parent.switchToModulesTab();
        }

        if (hoverReset) {
            module.reset();
        }
    }
}
