package net.coralmod.mod.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Mutable
    @Shadow
    @Final
    protected CubeMap cubeMap;

    @Mutable
    @Shadow
    @Final
    protected PanoramaRenderer panorama;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(
            Minecraft minecraft,
            ItemInHandRenderer itemInHandRenderer,
            RenderBuffers renderBuffers,
            BlockRenderDispatcher blockRenderDispatcher,
            CallbackInfo info
    ) {
        final ResourceLocation panoramaTexture = ResourceLocation.fromNamespaceAndPath("coralmod", "textures/gui/title/background/panorama");
        final CubeMap customCubeMap = new CubeMap(panoramaTexture);

        this.cubeMap = customCubeMap;
        this.panorama = new PanoramaRenderer(customCubeMap);
    }
}
