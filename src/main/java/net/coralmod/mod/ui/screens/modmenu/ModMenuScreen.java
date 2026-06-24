package net.coralmod.mod.ui.screens.modmenu;

import net.coralmod.mod.render.DrawContext;
import net.coralmod.mod.ui.CoralScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

import java.awt.*;

public class ModMenuScreen extends CoralScreen {

    private final Minecraft mc = Minecraft.getInstance();

    public static final int MENU_WIDTH = 400;
    public static final int MENU_HEIGHT = 200;

    private static final int SIDEBAR_WIDTH = 25;
    private static final int GAP = 5;

    private static final int SIDEBAR_BUTTON_SIZE = 15;
    private static final int SIDEBAR_BUTTON_GAP = 5;
    private static final int SIDEBAR_BUTTON_MARGIN = 5;

    public static final int BUTTONS_PER_ROW = 3;
    public static final int BUTTON_SPACING = 10;
    public static final int BUTTON_HEIGHT = 50;
    public static final int BUTTON_TOP_MARGIN = 5;

    public static final Color HOVER_COLOR = new Color(255, 255, 255, 30);
    public static final Color BASE_GRAY = new Color(20, 20, 20);

    private int startX;
    private int startY;

    private SidebarButtonWidget modulesButton;
    private SidebarButtonWidget themesButton;
    private SidebarButtonWidget closeButton;

    public ModMenuScreen() {
        super(Component.literal("Mod Menu"));
    }

    @Override
    protected void init() {
        if (mc.gui.screen() == null) {
            return;
        }

        startX = (mc.gui.screen().width - (SIDEBAR_WIDTH + GAP + MENU_WIDTH)) / 2;
        startY = (mc.gui.screen().height - MENU_HEIGHT) / 2;

        final int buttonX = startX + (SIDEBAR_WIDTH - SIDEBAR_BUTTON_SIZE) / 2;


        // todo cleanup by adding widget system directly to the CoralScreen
        modulesButton = new SidebarButtonWidget(
                buttonX,
                startY + SIDEBAR_BUTTON_MARGIN,
                SIDEBAR_BUTTON_SIZE,
                SIDEBAR_BUTTON_SIZE,
                this::switchToModulesTab
        );

        themesButton = new SidebarButtonWidget(
                buttonX,
                startY + SIDEBAR_BUTTON_MARGIN + SIDEBAR_BUTTON_SIZE + SIDEBAR_BUTTON_GAP,
                SIDEBAR_BUTTON_SIZE,
                SIDEBAR_BUTTON_SIZE,
                this::switchToThemesTab
        );

        closeButton = new SidebarButtonWidget(
                buttonX,
                startY + MENU_HEIGHT - SIDEBAR_BUTTON_MARGIN - SIDEBAR_BUTTON_SIZE,
                SIDEBAR_BUTTON_SIZE,
                SIDEBAR_BUTTON_SIZE,
                () -> mc.gui.setScreen(null)
        );

        switchToModulesTab();
    }

    @Override
    protected void render(DrawContext context, int mouseX, int mouseY) {
        context.shapes().roundedRect(
                startX,
                startY,
                SIDEBAR_WIDTH,
                MENU_HEIGHT,
                6,
                new Color(26, 26, 30)
        );

        context.shapes().roundedRect(
                startX + SIDEBAR_WIDTH + GAP,
                startY,
                MENU_WIDTH,
                MENU_HEIGHT,
                6,
                new Color(26, 26, 30)
        );

        modulesButton.render(context, mouseX, mouseY, 0);
        themesButton.render(context, mouseX, mouseY, 0);
        closeButton.render(context, mouseX, mouseY, 0);

        super.render(context, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(@NonNull MouseButtonEvent event, boolean b) {
        modulesButton.mouseClicked(event);
        themesButton.mouseClicked(event);
        closeButton.mouseClicked(event);
        return super.mouseClicked(event, b);
    }

    public void switchToModulesTab() {
        switchWindow(new ModulesTabWindow(this, "Modules", startX + SIDEBAR_WIDTH + GAP, startY));
    }

    public void switchToThemesTab() {
        switchWindow(new ThemesTabWindow(this, "Themes", startX + SIDEBAR_WIDTH + GAP, startY));
    }
}
