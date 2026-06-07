package net.coralmod.mod.command.sub;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.Module;
import net.coralmod.mod.utils.Notification;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class ResetCommand {

    public static LiteralArgumentBuilder<FabricClientCommandSource> build() {
        return ClientCommands.literal("reset")
                .executes(_ -> {
                    // reset all module states and positions
                    for (Module module : CoralMod.instance().moduleManager().modules()) {
                        module.enabled(false);
                        module.reset();
                    }

                    CoralMod.instance().config().resetToDefaults();
                    CoralMod.instance().save();

                    Notification.send("Settings reset", "All settings have been reset");
                    return 1;
                });
    }
}
