package net.coralmod.mod.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;

public final class Notification {

    private Notification() {
    }

    public static void send(String title, String text) {
        Minecraft.getInstance().gui.toastManager()
                .addToast(new SystemToast(SystemToast.SystemToastId.NARRATOR_TOGGLE, Component.literal(title), Component.literal(text)));
    }
}
