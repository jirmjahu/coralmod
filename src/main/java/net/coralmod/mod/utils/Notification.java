package net.coralmod.mod.utils;

import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;

@UtilityClass
public class Notification {

    public void sendNotification(String title, String text) {
        Minecraft.getInstance().gui.toastManager()
                .addToast(new SystemToast(SystemToast.SystemToastId.NARRATOR_TOGGLE, Component.literal(title), Component.literal(text)));
    }
}
