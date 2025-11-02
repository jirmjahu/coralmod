package net.coralmod.mod.mixin;

import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.modules.NametagsModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {

    @Inject(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;D)Z", at = @At("HEAD"), cancellable = true)
    public void hasLabel(T livingEntity, double d, CallbackInfoReturnable<Boolean> info) {
        final NametagsModule module = CoralMod.getInstance().getModuleManager().getModule(NametagsModule.class);
        if (!module.isEnabled()) {
            return;
        }

        final MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null && livingEntity.getId() != client.player.getId()) {
            return;
        }

        info.setReturnValue(true);
    }
}
