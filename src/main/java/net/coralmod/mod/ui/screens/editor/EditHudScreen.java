package net.coralmod.mod.ui.screens.editor;

import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.HudModule;
import net.coralmod.mod.module.Module;
import net.coralmod.mod.render.DrawContext;
import net.coralmod.mod.ui.CoralScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class EditHudScreen extends CoralScreen {

    private final Minecraft mc = Minecraft.getInstance();

    private HudModule selectedModule;
    private int offsetX;
    private int offsetY;

    public EditHudScreen() {
        super(Component.literal("Edit HUD"));
    }

    @Override
    protected void render(DrawContext context, int mouseX, int mouseY) {
        for (HudModule hud : CoralMod.instance().moduleManager().hudModules()) {
            if (!hud.enabled()) {
                continue;
            }

            hud.render(context);

            final Color outlineColor = hud == selectedModule
                    ? CoralMod.instance().selectedTheme().primaryColor().brighter()
                    : Color.WHITE;

            context.shapes().outline(hud.x(), hud.y(), hud.x() + hud.width(), hud.y() + hud.height(), 1, outlineColor);
        }
    }

    @Override
    public boolean mouseClicked(@NonNull MouseButtonEvent event, boolean b) {
        if (event.button() == 0) {
            final HudModule module = moduleAt((int) event.x(), (int) event.y());
            if (module != null) {
                selectedModule = module;
                offsetX = (int) event.x() - selectedModule.x();
                offsetY = (int) event.y() - selectedModule.y();
                return true;
            }
        }
        return super.mouseClicked(event, b);
    }

    @Override
    public boolean mouseReleased(@NonNull MouseButtonEvent event) {
        if (event.button() == 0 && selectedModule != null) {
            selectedModule = null;
            return true;
        }
        return super.mouseReleased(event);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double d, double e) {
        if (event.button() == GLFW.GLFW_MOUSE_BUTTON_LEFT && selectedModule != null) {
            int newX = (int) event.x() - offsetX;
            int newY = (int) event.y() - offsetY;

            newX = Math.clamp(newX, 0, mc.getWindow().getScreenWidth() - selectedModule.width());
            newY = Math.clamp(newY, 0, mc.getWindow().getScreenHeight() - selectedModule.height());

            final int snap = 5;

            for (HudModule other : CoralMod.instance().moduleManager().hudModules()) {
                if (other == selectedModule || !other.enabled()) {
                    continue;
                }

                if (Math.abs(newX - (other.x() + other.width())) <= snap) {
                    newX = other.x() + other.width();
                } else if (Math.abs((newX + selectedModule.width()) - other.x()) <= snap) {
                    newX = other.x() - selectedModule.width();
                }

                if (Math.abs(newY - (other.y() + other.height())) <= snap) {
                    newY = other.y() + other.height();
                } else if (Math.abs((newY + selectedModule.height()) - other.y()) <= snap) {
                    newY = other.y() - selectedModule.height();
                }
            }

            selectedModule.move(newX, newY);
            return true;
        }
        return super.mouseDragged(event, d, e);
    }

    private HudModule moduleAt(int mouseX, int mouseY) {
        return CoralMod.instance().moduleManager().hudModules().stream()
                .filter(Module::enabled)
                .filter(m -> m.hovered(mouseX, mouseY))
                .findFirst()
                .orElse(null);
    }

    @Override
    protected void extractBlurredBackground(@NonNull GuiGraphicsExtractor graphics) {}

}
