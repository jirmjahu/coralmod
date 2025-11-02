package net.coralmod.mod.mixin;

import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.HudModule;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow
    public abstract Font getFont();

    @Inject(method = "render", at = @At("RETURN"))
    private void onRender(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo info) {
        for (HudModule hudModule : CoralMod.getInstance().getModuleManager().getHudModules()) {
            if (!hudModule.isEnabled()) {
                continue;
            }
            hudModule.render(guiGraphics, getFont());
        }
    }
}
