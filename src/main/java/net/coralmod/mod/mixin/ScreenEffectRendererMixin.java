package net.coralmod.mod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.modules.ViewTweaksModule;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenEffectRenderer.class)
public class ScreenEffectRendererMixin {

    @Inject(method = "submitFire", at = @At("HEAD"))
    private static void onRenderFire(PoseStack poseStack, SubmitNodeCollector collector, TextureAtlasSprite sprite, CallbackInfo ci) {
        final ViewTweaksModule module = CoralMod.instance().moduleManager().module(ViewTweaksModule.class);
        if (module.enabled() && module.lowerFire().value()) {
            poseStack.translate(0.0F, -0.3F, 0.0F);
        }
    }
}
