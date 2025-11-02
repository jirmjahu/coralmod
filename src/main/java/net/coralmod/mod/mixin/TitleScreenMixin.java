package net.coralmod.mod.mixin;

import net.coralmod.mod.CoralMod;
import net.coralmod.mod.utils.RenderUtils;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.URI;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {

    private static final ResourceLocation GITHUB_LOGO = ResourceLocation.fromNamespaceAndPath("coralmod", "textures/github.png");
    private static final int LOGO_SIZE = 20;
    private static final String GITHUB_REPO = "https://github.com/jirmjahu/coralmod";

    protected TitleScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "render", at = @At("HEAD"))
    public void onRender(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo info) {
        guiGraphics.drawString(
                font,
                CoralMod.MOD_NAME + " v" + CoralMod.MOD_VERSION,
                2,
                height - 10 - font.lineHeight,
                -1,
                true
        );

        RenderUtils.drawTexture(guiGraphics, GITHUB_LOGO, 0, 0, LOGO_SIZE);

        addRenderableWidget(RenderUtils.pressableText(font, Component.literal("CoralMod on Github"), 20,  (LOGO_SIZE - font.lineHeight) / 2 + 2, () -> {
            try {
                Util.getPlatform().openUri(new URI(GITHUB_REPO));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }
}
