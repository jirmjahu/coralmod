package net.coralmod.mod.mixin;

import net.coralmod.mod.CoralMod;
import net.coralmod.mod.utils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.URI;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {

    private static final Identifier GITHUB_LOGO = Identifier.of("coralmod", "textures/github.png");
    private static final int LOGO_SIZE = 20;
    private static final String GITHUB_REPO = "https://github.com/jirmjahu/coralmod";

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "render", at = @At("HEAD"))
    public void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo info) {
        context.drawText(
                textRenderer,
                CoralMod.MOD_NAME + " v" + CoralMod.MOD_VERSION,
                2,
                height - 10 - textRenderer.fontHeight,
                -1,
                true
        );

        RenderUtils.drawTexture(context, GITHUB_LOGO, 0, 0, LOGO_SIZE);

        addDrawableChild(RenderUtils.pressableText(textRenderer, Text.of("CoralMod on Github"), 20,  (LOGO_SIZE - textRenderer.fontHeight) / 2 + 2, () -> {
            try {
                Util.getOperatingSystem().open(new URI(GITHUB_REPO));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }
}
