package net.coralmod.mod.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.coralmod.mod.event.AddChatMessageEvent;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.chat.GuiMessage;
import net.minecraft.client.multiplayer.chat.GuiMessageSource;
import net.minecraft.client.multiplayer.chat.GuiMessageTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public class ChatComponentMixin {

    @Inject(method = "addMessage", at = @At("HEAD"))
    public void onAddMessage(Component contents, @Nullable MessageSignature signature, GuiMessageSource source, @Nullable GuiMessageTag tag, CallbackInfo info) {
        AddChatMessageEvent.ADD_CHAT_MESSAGE_EVENT.invoker().onChatMessage(contents.getString());
    }

    @ModifyArg(
            method = "addMessage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/chat/GuiMessage;<init>(ILnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/multiplayer/chat/GuiMessageSource;Lnet/minecraft/client/multiplayer/chat/GuiMessageTag;)V"
            ),
            index = 4
    )
    private GuiMessageTag removeTag(GuiMessageTag tag) {
        return null;
    }
}
