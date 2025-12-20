package net.coralmod.mod.mixin;

import net.coralmod.mod.event.KeyPressedEvent;
import net.coralmod.mod.ui.editor.EditHudScreen;
import net.coralmod.mod.ui.modmenu.ModMenuScreen;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {

    @Inject(method = "keyPress", at = @At("HEAD"))
    private void onKeyPress(long l, int i, KeyEvent keyEvent, CallbackInfo info) {
        KeyPressedEvent.KEY_PRESSED_EVENT.invoker().onKeyPressed(keyEvent.key());

        //TODO USE CONFIGURED KEYS

        if (Minecraft.getInstance().screen != null) {
            return;
        }

        if (keyEvent.key() == GLFW.GLFW_KEY_RIGHT_SHIFT) {
            Minecraft.getInstance().setScreen(ModMenuScreen.INSTANCE);
        }

        if (keyEvent.key() == GLFW.GLFW_KEY_P) {
            Minecraft.getInstance().setScreen(EditHudScreen.INSTANCE);
        }
    }
}
