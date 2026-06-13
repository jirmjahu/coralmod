package net.coralmod.mod.module.modules;

import net.coralmod.mod.event.MouseButtonClickedEvent;
import net.coralmod.mod.module.ModuleCategory;
import net.coralmod.mod.module.HudModule;
import net.coralmod.mod.module.ModuleInfo;
import net.coralmod.mod.module.settings.BooleanSetting;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(name = "CPS", description = "Shows your CPS", category = ModuleCategory.HUD)
public class CPSModule extends HudModule {

    private final BooleanSetting showCpsText = new BooleanSetting("Show CPS Text", true);
    private final BooleanSetting showLeftButton = new BooleanSetting("Show Left CPS", true);
    private final BooleanSetting showRightButton = new BooleanSetting("Show Right CPS", true);

    private final List<Long> leftPresses = new ArrayList<>();
    private final List<Long> rightPresses = new ArrayList<>();

    public CPSModule() {
        super(20, 20);
        addSettings(showCpsText, showLeftButton, showRightButton);

        MouseButtonClickedEvent.MOUSE_BUTTON_CLICKED_EVENT.register(event -> {
            final long now = System.currentTimeMillis();
            if (event.button() == GLFW.GLFW_MOUSE_BUTTON_1) {
                leftPresses.add(now);
            } else if (event.button() == GLFW.GLFW_MOUSE_BUTTON_2) {
                rightPresses.add(now);
            }
        });

        ClientTickEvents.START_CLIENT_TICK.register(_ -> {
            leftPresses.removeIf(lastPress -> System.currentTimeMillis() - lastPress > 1000);
            rightPresses.removeIf(lastPress -> System.currentTimeMillis() - lastPress > 1000);
        });
    }

    @Override
    public String getText() {
        final StringBuilder builder = new StringBuilder();

        if (showCpsText.value()) {
            builder.append("CPS: ");
        }
        if (showLeftButton.value()) {
            builder.append(leftPresses.size());
        }
        if (showLeftButton.value() && showRightButton.value()) {
            builder.append(" | ");
        }
        if (showRightButton.value()) {
            builder.append(rightPresses.size());
        }

        return builder.toString();
    }
}
