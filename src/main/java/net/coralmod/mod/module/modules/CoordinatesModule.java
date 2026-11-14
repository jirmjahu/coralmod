package net.coralmod.mod.module.modules;

import net.coralmod.mod.module.ModuleCategory;
import net.coralmod.mod.module.HudModule;
import net.coralmod.mod.module.ModuleInfo;
import net.coralmod.mod.module.settings.ModeSetting;

import java.util.List;

@ModuleInfo(name = "Coordinates", description = "Shows your coordinates", category = ModuleCategory.HUD)
public class CoordinatesModule extends HudModule {

    private final ModeSetting format = new ModeSetting("Format", "Full", List.of("Full", "Compact", "Block"));

    public CoordinatesModule() {
        super(20, 20);
        addSettings(format);
    }

    @Override
    public String getText() {
        if (mc.player == null) {
            return null;
        }

        final long x = Math.round(mc.player.getX());
        final long y = Math.round(mc.player.getY());
        final long z = Math.round(mc.player.getZ());

        return switch (format.value()) {
            case "Compact" -> x + ", " + y + ", " + z;
            case "Block" -> "XYZ: " + mc.player.getBlockX() + " / " + mc.player.getBlockY() + " / " + mc.player.getBlockZ();
            default -> "X: " + x + " Y: " + y + " Z: " + z;
        };
    }
}
