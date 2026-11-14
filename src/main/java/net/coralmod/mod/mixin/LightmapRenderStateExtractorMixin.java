package net.coralmod.mod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.modules.FullBrightModule;
import net.minecraft.client.renderer.LightmapRenderStateExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LightmapRenderStateExtractor.class)
public class LightmapRenderStateExtractorMixin {

    @ModifyExpressionValue(
            method = "extract",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;",
                    ordinal = 1
            )
    )
    private Object modifyGamma(Object gamma) {
        final FullBrightModule module = CoralMod.instance().moduleManager().module(FullBrightModule.class);
        if (!module.enabled()) {
            return gamma;
        }
        return FullBrightModule.GAMMA;
    }
}
