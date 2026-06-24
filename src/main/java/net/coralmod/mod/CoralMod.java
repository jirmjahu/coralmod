package net.coralmod.mod;

import net.coralmod.mod.command.CoralModCommand;
import net.coralmod.mod.config.Config;
import net.coralmod.mod.config.ConfigManager;
import net.coralmod.mod.config.ConfigStorage;
import net.coralmod.mod.config.profile.ProfileManager;
import net.coralmod.mod.config.profile.ProfileStorage;
import net.coralmod.mod.event.KeyPressedEvent;
import net.coralmod.mod.module.ModuleManager;
import net.coralmod.mod.theme.Theme;
import net.coralmod.mod.ui.screens.editor.EditHudScreen;
import net.coralmod.mod.ui.screens.modmenu.ModMenuScreen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoralMod implements ModInitializer {

    public static final String MOD_ID = "coralmod";
    public static final String MOD_NAME = "CoralMod";
    public static final String MOD_VERSION = "0.8.0";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final KeyMapping ZOOM_KEY_MAPPING = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "Zoom",
            GLFW.GLFW_KEY_C,
            KeyMapping.Category.register(Identifier.parse("coralmod.key.zoom"))
    ));

    public static final KeyMapping MODMENU_KEY_MAPPING = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "Mod Menu",
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            KeyMapping.Category.register(Identifier.parse("coralmod.key.modmenu"))
    ));

    public static final KeyMapping HUD_EDITOR_KEY_MAPPING = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "Hud Editor",
            GLFW.GLFW_KEY_P,
            KeyMapping.Category.register(Identifier.parse("coralmod.key.editor"))
    ));

    private static CoralMod instance;

    private ModuleManager moduleManager;
    private ConfigStorage configStorage;
    private ConfigManager configManager;
    private ProfileStorage profileStorage;
    private ProfileManager profileManager;

    private Theme selectedTheme;

    @Override
    public void onInitialize() {
        LOGGER.info("Starting {} v{}...", MOD_NAME, MOD_VERSION);

        instance = this;

        ClientLifecycleEvents.CLIENT_STARTED.register(_ -> {
            moduleManager = new ModuleManager();
            configStorage = new ConfigStorage();
            configManager = new ConfigManager(configStorage);
            profileStorage = new ProfileStorage(moduleManager);
            profileManager = new ProfileManager(config(), configStorage, profileStorage, moduleManager);

            selectedTheme(Theme.valueOf(config().selectedTheme()));

            new CoralModCommand();

            LOGGER.info("Successfully initialized {}", MOD_NAME);
        });

        KeyPressedEvent.KEY_PRESSED_EVENT.register(key -> {
            if (Minecraft.getInstance().gui.screen() != null) {
                return;
            }

            if (key == KeyMappingHelper.getBoundKeyOf(MODMENU_KEY_MAPPING).getValue()) {
                Minecraft.getInstance().gui.setScreen(new ModMenuScreen());
            }

            if (key == KeyMappingHelper.getBoundKeyOf(HUD_EDITOR_KEY_MAPPING).getValue()) {
                Minecraft.getInstance().gui.setScreen(new EditHudScreen());
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("Shutting down, saving...");
            save();
        }));
    }

    /**
     * Saves the current profile including module states, positions, and config.
     */
    public void save() {
        profileManager.saveCurrentProfile();
        configManager.save();
    }

    public void selectedTheme(Theme theme) {
        this.selectedTheme = theme;
        config().selectedTheme(theme.toString());
    }

    public static CoralMod instance() {
        return instance;
    }

    public ModuleManager moduleManager() {
        return moduleManager;
    }

    public Theme selectedTheme() {
        return selectedTheme;
    }

    public Config config() {
        return configManager.config();
    }
}
