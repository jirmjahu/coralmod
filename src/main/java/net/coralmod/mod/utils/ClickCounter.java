package net.coralmod.mod.utils;

import com.mojang.blaze3d.platform.InputConstants;
import net.coralmod.mod.event.MouseButtonClickedEvent;

import java.util.ArrayList;
import java.util.List;

public final class ClickCounter {

    private static final List<Long> LEFT_CLICKS = new ArrayList<>();
    private static final List<Long> RIGHT_CLICKS = new ArrayList<>();

    private ClickCounter() {
    }

    public static void init() {
        MouseButtonClickedEvent.MOUSE_BUTTON_CLICKED_EVENT.register(event -> {
            if (event.button() == InputConstants.MOUSE_BUTTON_LEFT) {
                LEFT_CLICKS.add(System.currentTimeMillis());
            } else if (event.button() == InputConstants.MOUSE_BUTTON_RIGHT) {
                RIGHT_CLICKS.add(System.currentTimeMillis());
            }
        });
    }

    public static int leftCps() {
        return cps(LEFT_CLICKS);
    }

    public static int rightCps() {
        return cps(RIGHT_CLICKS);
    }

    private static int cps(List<Long> clicks) {
        clicks.removeIf(time -> System.currentTimeMillis() - time > 1000);
        return clicks.size();
    }
}
