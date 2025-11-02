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
     * @reason Change the window title
     */
    @Overwrite
    private String createTitle() {
        final String minecraftVersion = SharedConstants.getCurrentVersion().name();

        return CoralMod.MOD_NAME + " " + minecraftVersion + " (v" + CoralMod.MOD_VERSION + ")";
    }
}
