package net.coralmod.mod;

import lombok.Getter;
import net.coralmod.mod.command.CoralModCommand;
import net.coralmod.mod.config.Config;
import net.coralmod.mod.config.ConfigStorage;
import net.coralmod.mod.config.profile.ProfileManager;
import net.coralmod.mod.module.ModuleManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class CoralMod implements ModInitializer {

    public static final String MOD_ID = "coralmod";
    public static final String MOD_NAME = "CoralMod";
    public static final String MOD_VERSION = "0.2.1";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static KeyMapping ZOOM_KEY_MAPPING = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            "Zoom",
            GLFW.GLFW_KEY_C,
            KeyMapping.Category.register(ResourceLocation.parse("coralmod"))
    ));

    @Getter
    private static CoralMod instance;

    private ModuleManager moduleManager;
    private Config config;
    private ConfigStorage configStorage;
    private ProfileManager profileManager;

    @Override
    public void onInitialize() {
        LOGGER.info("Starting {} v{}...", MOD_NAME, MOD_VERSION);

        instance = this;

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            moduleManager = new ModuleManager();
            configStorage = new ConfigStorage();
            config = configStorage.load();
            profileManager = new ProfileManager(config, configStorage, moduleManager);

            profileManager.loadProfile(config.getCurrentProfile());

            new CoralModCommand();

            LOGGER.info("Successfully started {}", MOD_NAME);
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("Shutting down...");
            LOGGER.info("Saving modules...");
            profileManager.saveProfile(config.getCurrentProfile());
            configStorage.save();
        }));
    }
}