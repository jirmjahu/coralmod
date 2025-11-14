package net.coralmod.mod.gui;

import lombok.Getter;
import lombok.Setter;
import net.coralmod.mod.CoralMod;
import net.coralmod.mod.gui.components.Window;
import net.coralmod.mod.theme.CoralEnum;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public class ClickGuiScreen extends Screen {

    public static final ClickGuiScreen INSTANCE = new ClickGuiScreen();
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 300;

    private final Window window;

    @Getter
    @Setter
    private CoralEnum selectedTheme = CoralEnum.TUBE;

    private ClickGuiScreen() {
        super(Component.literal("CoraalClickgGui"));
        this.window = new Window(CoralMod.MOD_NAME + " v" + CoralMod.MOD_VERSION, 200, 200, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.window.render(guiGraphics, mouseX, mouseY);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean bl) {
        this.window.mouseClicked(mouseButtonEvent.x(), mouseButtonEvent.y(), mouseButtonEvent.button());
        return super.mouseClicked(mouseButtonEvent, bl);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent mouseButtonEvent) {
        this.window.mouseReleased(mouseButtonEvent.x(), mouseButtonEvent.y(), mouseButtonEvent.button());
        return super.mouseReleased(mouseButtonEvent);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
