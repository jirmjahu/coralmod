package net.coralmod.mod.mixin;

import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.modules.AspectModule;
import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Camera.class)
public class CameraMixin {

    @ModifyArg(
            method = "update",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Camera;setupPerspective(FFFFF)V"
            ),
            index = 3
    )
    private float modifyWidth(float width) {
        final AspectModule module = CoralMod.getInstance()
                .getModuleManager()
                .getModule(AspectModule.class);

        if (module == null || !module.isEnabled()) {
            return width;
        }

        return width / (float) module.getStretchFactor();
    }

}
