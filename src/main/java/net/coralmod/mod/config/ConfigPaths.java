package net.coralmod.mod.config;

import net.coralmod.mod.CoralMod;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public final class ConfigPaths {

    public static final Path ROOT = FabricLoader.getInstance().getConfigDir().resolve(CoralMod.MOD_ID);
    public static final Path CONFIG_FILE = ROOT.resolve("config.json");
    public static final Path PROFILES_DIR = ROOT.resolve("profiles");

    private ConfigPaths() {
    }
}
