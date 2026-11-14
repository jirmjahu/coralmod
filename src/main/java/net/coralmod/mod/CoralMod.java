package net.coralmod.mod;

import net.coralmod.mod.command.CoralModCommand;
import net.coralmod.mod.config.Config;
import net.coralmod.mod.config.ConfigManager;
import net.coralmod.mod.config.ConfigStorage;
import net.coralmod.mod.config.profile.ProfileManager;
import net.coralmod.mod.config.profile.ProfileStorage;
import net.coralmod.mod.module.ModuleManager;
import net.coralmod.mod.theme.Theme;
import net.coralmod.mod.ui.screens.editor.EditHudScreen;
import net.coralmod.mod.ui.screens.modmenu.ModMenuScreen;
import net.coralmod.mod.utils.ClickCounter;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import com.mojang.blaze3d.platform.InputConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CoralMod implements ClientModInitializer {

    public static final String MOD_ID = "coralmod";
    public static final String MOD_NAME = "CoralMod";
    public static final String MOD_VERSION = FabricLoader.getInstance()
            .getModContainer(MOD_ID)
            .map(container -> container.getMetadata().getVersion().getFriendlyString())
            .orElse("unknown");

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final KeyMapping ZOOM_KEY_MAPPING = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "Zoom",
            InputConstants.KEY_C,
            KeyMapping.Category.register(Identifier.parse("coralmod.key.zoom"))
    ));

    public static final KeyMapping MODMENU_KEY_MAPPING = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "Mod Menu",
            InputConstants.KEY_RSHIFT,
            KeyMapping.Category.register(Identifier.parse("coralmod.key.modmenu"))
    ));

    public static final KeyMapping HUD_EDITOR_KEY_MAPPING = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "Hud Editor",
            InputConstants.KEY_P,
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
    public void onInitializeClient() {
        LOGGER.info("Starting {} v{}...", MOD_NAME, MOD_VERSION);

        instance = this;

        ClickCounter.init();

        ClientLifecycleEvents.CLIENT_STARTED.register(_ -> {
            moduleManager = new ModuleManager();
            configStorage = new ConfigStorage();
            configManager = new ConfigManager(configStorage);
            profileStorage = new ProfileStorage(moduleManager);
            profileManager = new ProfileManager(config(), configStorage, profileStorage, moduleManager);

            selectedTheme(loadConfiguredTheme());

            new CoralModCommand();

            LOGGER.info("Successfully initialized {}", MOD_NAME);
        });

        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            if (MODMENU_KEY_MAPPING.consumeClick()) {
                mc.gui.setScreen(new ModMenuScreen());
            }
            if (HUD_EDITOR_KEY_MAPPING.consumeClick()) {
                mc.gui.setScreen(new EditHudScreen());
            }
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(_ -> {
            LOGGER.info("Shutting down, saving...");
            save();
        });
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

    private Theme loadConfiguredTheme() {
        final String configuredTheme = config().selectedTheme();

        if (configuredTheme == null) {
            return Theme.TUBE;
        }

        try {
            return Theme.valueOf(configuredTheme);
        } catch (IllegalArgumentException exception) {
            return Theme.TUBE;
        }
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
