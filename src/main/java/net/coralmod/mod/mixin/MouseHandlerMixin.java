package net.coralmod.mod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.coralmod.mod.CoralMod;
import net.coralmod.mod.event.MouseButtonClickedEvent;
import net.coralmod.mod.module.modules.ZoomModule;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.MouseButtonInfo;
import com.mojang.blaze3d.platform.InputConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @Inject(method = "onButton", at = @At("HEAD"))
    public void onButton(long l, MouseButtonInfo mouseButtonInfo, int action, CallbackInfo info) {
        if (action == InputConstants.PRESS) {
            MouseButtonClickedEvent.MOUSE_BUTTON_CLICKED_EVENT.invoker().onMouseClicked(mouseButtonInfo);
        }
    }

    @ModifyExpressionValue(
            method = "turnPlayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;",
                    ordinal = 0
            )
    )
    private Object modifySensitivity(Object sensitivity) {
        return isZooming() ? ZoomModule.ZOOM_SENSITIVITY : sensitivity;
    }

    @ModifyExpressionValue(
            method = "turnPlayer",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/Options;smoothCamera:Z"
            )
    )
    private boolean modifySmoothCamera(boolean smoothCamera) {
        return smoothCamera || isZooming();
    }

    @Unique
    private static boolean isZooming() {
        final ZoomModule module = CoralMod.instance().moduleManager().module(ZoomModule.class);
        return module != null && module.enabled() && module.zooming();
    }
}
