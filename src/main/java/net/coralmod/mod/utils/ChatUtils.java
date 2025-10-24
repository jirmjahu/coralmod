package net.coralmod.mod.utils;

import lombok.experimental.UtilityClass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

@UtilityClass
public class ChatUtils {

    public void sendToPlayer(String message) {
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(message));
    }

    public void sendAsPlayer(String message) {
        final ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) {
            return;
        }
        player.networkHandler.sendChatMessage(message);
    }
}