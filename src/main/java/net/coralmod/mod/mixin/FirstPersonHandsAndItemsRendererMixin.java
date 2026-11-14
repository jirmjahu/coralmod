package net.coralmod.mod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.modules.ViewTweaksModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FirstPersonHandsAndItemsRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.FirstPersonHandsAndItemsRenderState;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FirstPersonHandsAndItemsRenderer.class)
public class FirstPersonHandsAndItemsRendererMixin {

    @Inject(
            method = "submitArmWithItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState;submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;III)V"
            )
    )
    public void onSubmitItem(PlayerRenderState playerState, FirstPersonHandsAndItemsRenderState handsState, float frameInterp, float pitch, InteractionHand hand, float attack, ItemStack itemStack, float inverseArmHeight, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, CallbackInfo info) {
        final ViewTweaksModule module = CoralMod.instance().moduleManager().module(ViewTweaksModule.class);

        if (!module.enabled() || !module.lowerShield().value()) {
            return;
        }
        if (!Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            return;
        }
        if (!(itemStack.getItem() instanceof ShieldItem)) {
            return;
        }

        poseStack.translate(0, -0.10F, 0);
    }
}
