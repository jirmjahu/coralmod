package net.coralmod.mod;

import lombok.Getter;
import net.coralmod.mod.command.CoralModCommand;
import net.coralmod.mod.module.ModuleManager;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class CoralMod implements ModInitializer {

    public static final String MOD_ID = "coralmod";
    public static final String MOD_NAME = "CoralMod";
    public static final String MOD_VERSION = "0.1.0";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Getter
    private static CoralMod instance;

    private ModuleManager moduleManager;

	@Override
	public void onInitialize() {
        LOGGER.info("Starting {} v{}...", MOD_NAME, MOD_VERSION);

        instance = this;

        moduleManager = new ModuleManager();

        LOGGER.info("Successfully started {}", MOD_NAME);
	}
}