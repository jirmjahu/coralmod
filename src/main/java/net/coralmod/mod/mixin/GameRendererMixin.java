package net.coralmod.mod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.util.Identifier;
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
    protected CubeMapRenderer panoramaRenderer;

    @Mutable
    @Shadow
    @Final
    protected RotatingCubeMapRenderer rotatingPanoramaRenderer;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(
            MinecraftClient minecraftClient,
            HeldItemRenderer heldItemRenderer,
            BufferBuilderStorage bufferBuilderStorage,
            BlockRenderManager blockRenderManager,
            CallbackInfo info
    ) {
        final Identifier panoramaId = Identifier.of("coralmod", "textures/gui/title/background/panorama");
        final CubeMapRenderer cubeMapRenderer = new CubeMapRenderer(panoramaId);

        this.panoramaRenderer = cubeMapRenderer;
        this.rotatingPanoramaRenderer = new RotatingCubeMapRenderer(cubeMapRenderer);
    }
}
