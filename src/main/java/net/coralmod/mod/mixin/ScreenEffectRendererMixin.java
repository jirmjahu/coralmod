package net.coralmod.mod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.Module;
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
    private static void onRenderFire(PoseStack poseStack,
                                     SubmitNodeCollector submitNodeCollector,
                                     TextureAtlasSprite sprite,
                                     CallbackInfo ci) {

        final Module viewTweaksModule = CoralMod.getInstance().getModuleManager().getModule(ViewTweaksModule.class);

        if (!viewTweaksModule.isEnabled()) {
            return;
        }

        if (!(boolean) viewTweaksModule.getSetting("Lower Fire").getValue()) {
            return;
        }

        poseStack.translate(0.0F, -0.3F, 0.0F);
    }
}