package net.coralmod.mod.mixin;

import com.mojang.renderpearl.api.textures.GpuTextureView;
import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.modules.ZoomModule;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Inject(method = "renderItemInHand", at = @At("HEAD"), cancellable = true)
    public void onRenderItemInHand(CameraRenderState cameraState, PlayerRenderState playerState, GpuTextureView textureView, CallbackInfo info) {
        final ZoomModule module = CoralMod.instance().moduleManager().module(ZoomModule.class);
        if (module != null && module.enabled() && module.zooming()) {
            info.cancel();
        }
    }
}
