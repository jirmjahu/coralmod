package net.coralmod.mod.utils;

import lombok.experimental.UtilityClass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;

@UtilityClass
public class Notification {

    public void sendNotification(String title, String text) {
        MinecraftClient.getInstance().getToastManager().add(
                SystemToast.create(MinecraftClient.getInstance(), SystemToast.Type.NARRATOR_TOGGLE, Text.of(title), Text.of(text)));
    }
}
