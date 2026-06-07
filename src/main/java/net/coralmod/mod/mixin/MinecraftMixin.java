package net.coralmod.mod.mixin;

import net.coralmod.mod.CoralMod;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    /**
     * @author jirmjahu
     * @reason Change the window title to display the mod name and version
     */
    @Overwrite
    private String createTitle() {
        return CoralMod.MOD_NAME + " " + SharedConstants.getCurrentVersion().name() + " (v" + CoralMod.MOD_VERSION + ")";
    }
}
