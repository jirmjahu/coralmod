package net.coralmod.mod.module.modules;

import net.coralmod.mod.module.HudModule;
import net.coralmod.mod.module.ModuleCategory;
import net.coralmod.mod.module.ModuleInfo;
import net.coralmod.mod.module.settings.BooleanSetting;
import net.coralmod.mod.render.DrawContext;
import net.coralmod.mod.render.FontRenderer;
import net.coralmod.mod.utils.ClickCounter;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;

import java.awt.*;

@ModuleInfo(name = "Keystrokes", description = "Shows your movement keys and mouse buttons", category = ModuleCategory.HUD)
public final class KeystrokesModule extends HudModule {

    private static final int KEY_SIZE = 22;
    private static final int GAP = 2;
    private static final int TOTAL_WIDTH = KEY_SIZE * 3 + GAP * 2;
    private static final int MOUSE_WIDTH = (TOTAL_WIDTH - GAP) / 2;
    private static final int SPACE_HEIGHT = 12;
    private static final float CPS_SCALE = 0.75F;

    private static final Color RELEASED_COLOR = new Color(0, 0, 0, 120);
    private static final Color PRESSED_COLOR = new Color(255, 255, 255, 170);

    private final BooleanSetting showMouse = new BooleanSetting("Show Mouse Buttons", true);
    private final BooleanSetting showCps = new BooleanSetting("Show CPS", true);
    private final BooleanSetting showSpace = new BooleanSetting("Show Space", true);

    public KeystrokesModule() {
        super(20, 20);
        settings().remove(brackets);
        settings().remove(background);
        addSettings(showMouse, showCps, showSpace);
    }

    @Override
    public void render(DrawContext context) {
        final Options options = mc.options;
        final int x = x();
        int y = y();

        key(context, options.keyUp, x + KEY_SIZE + GAP, y);
        y += KEY_SIZE + GAP;

        key(context, options.keyLeft, x, y);
        key(context, options.keyDown, x + KEY_SIZE + GAP, y);
        key(context, options.keyRight, x + (KEY_SIZE + GAP) * 2, y);
        y += KEY_SIZE + GAP;

        if (showMouse.value()) {
            box(context, x, y, MOUSE_WIDTH, KEY_SIZE, options.keyAttack.isDown(), "LMB", cpsText(ClickCounter.leftCps()));
            box(context, x + MOUSE_WIDTH + GAP, y, MOUSE_WIDTH, KEY_SIZE, options.keyUse.isDown(), "RMB", cpsText(ClickCounter.rightCps()));
            y += KEY_SIZE + GAP;
        }

        if (showSpace.value()) {
            key(context, options.keyJump, x, y, TOTAL_WIDTH, SPACE_HEIGHT);
            y += SPACE_HEIGHT + GAP;
        }

        width(TOTAL_WIDTH);
        height(y - GAP - y());
    }

    private void key(DrawContext context, KeyMapping key, int x, int y) {
        key(context, key, x, y, KeystrokesModule.KEY_SIZE, KeystrokesModule.KEY_SIZE);
    }

    private void key(DrawContext context, KeyMapping key, int x, int y, int width, int height) {
        box(context, x, y, width, height, key.isDown(), key.getTranslatedKeyMessage().getString(), null);
    }

    private void box(DrawContext context, int x, int y, int width, int height, boolean pressed, String label, String subLabel) {
        context.shapes().rect(x, y, x + width, y + height, pressed ? PRESSED_COLOR : RELEASED_COLOR);

        final FontRenderer font = context.fonts().minecraft();
        final Color textColor = pressed ? Color.BLACK : Color.WHITE;
        final boolean shadow = textShadow.value() && !pressed;

        final int textHeight = font.height() + (subLabel != null ? font.height(CPS_SCALE) : 0);
        final int textY = y + (height - textHeight) / 2 + 1;

        font.draw(label, x + (width - font.width(label)) / 2, textY, textColor, shadow);

        if (subLabel != null) {
            font.draw(subLabel, x + (width - font.width(subLabel, CPS_SCALE)) / 2, textY + font.height(), CPS_SCALE, textColor, shadow);
        }
    }

    private String cpsText(int cps) {
        return showCps.value() ? cps + " CPS" : null;
    }

    @Override
    public String getText() {
        return "";
    }
}
