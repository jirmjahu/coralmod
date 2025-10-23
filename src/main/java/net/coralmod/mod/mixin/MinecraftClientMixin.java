package net.coralmod.mod.mixin;

import net.coralmod.mod.CoralMod;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    /**
     * @author jirmjahu
     * @reason Change the window title
     */
    @Overwrite
    private String getWindowTitle() {
        final String minecraftVersion = SharedConstants.getGameVersion().comp_4025();

        return CoralMod.MOD_NAME + " " + minecraftVersion + " (v" + CoralMod.MOD_VERSION + ")";
    }
}
