package net.coralmod.mod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.modules.ViewTweaksModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @Inject(method = "renderItem", at = @At("HEAD"))
    public void onRenderItem(LivingEntity mob, ItemStack itemStack, ItemDisplayContext type, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, CallbackInfo info) {
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
