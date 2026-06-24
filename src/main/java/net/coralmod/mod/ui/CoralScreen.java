package net.coralmod.mod.ui;

import net.coralmod.mod.render.DrawContext;
import net.coralmod.mod.render.impl.DefaultDrawContext;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public class CoralScreen extends Screen {

    private Window currentWindow = null;

    protected CoralScreen(Component title) {
        super(title);
    }

    @Override
    public final void extractRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        final DrawContext context = new DefaultDrawContext(graphics);
        render(context, mouseX, mouseY);
        if (currentWindow != null) {
            currentWindow.render(context, mouseX, mouseY);
        }
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
    }

    protected void render(DrawContext context, int mouseX, int mouseY) {}

    public void switchWindow(Window window) {
        currentWindow = window;
        window.init();
    }

    @Override
    public boolean mouseClicked(@NonNull MouseButtonEvent event, boolean b) {
        if (currentWindow != null) {
            currentWindow.mouseClicked(event);
        }
        return super.mouseClicked(event, b);
    }

    @Override
    public boolean mouseReleased(@NonNull MouseButtonEvent event) {
        if (currentWindow != null) {
            currentWindow.mouseReleased(event);
        }
        return super.mouseReleased(event);
    }

    @Override
    public boolean mouseScrolled(double x, double y, double scrollX, double scrollY) {
        if (currentWindow != null) {
            currentWindow.mouseScrolled(scrollY);
        }
        return super.mouseScrolled(x, y, scrollX, scrollY);
    }

    @Override
    protected void extractBlurredBackground(@NonNull GuiGraphicsExtractor graphics) {
        super.extractBlurredBackground(graphics);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
