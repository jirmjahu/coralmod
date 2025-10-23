package net.coralmod.mod;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoralMod implements ModInitializer {

    public static final String MOD_ID = "coralmod";
    public static final String MOD_NAME = "CoralMod";
    public static final String MOD_VERSION = "0.0.1";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static CoralMod instance;

	@Override
	public void onInitialize() {
        LOGGER.info("Starting {} v{}...", MOD_NAME, MOD_VERSION);

        instance = this;

        LOGGER.info("Successfully started {}", MOD_NAME);
	}
}