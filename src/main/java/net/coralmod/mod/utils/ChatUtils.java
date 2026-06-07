package net.coralmod.mod.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public final class ChatUtils {

    private ChatUtils() {
    }

    public static void sendToPlayer(String message) {
        Minecraft.getInstance().gui.hud.getChat().addClientSystemMessage(Component.literal(message));
    }

    public static void sendAsPlayer(String message) {
        final LocalPlayer player = Minecraft.getInstance().player;

        if (player == null) {
            return;
        }

        player.connection.sendChat(message);
    }
}