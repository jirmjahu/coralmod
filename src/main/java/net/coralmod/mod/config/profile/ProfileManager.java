package net.coralmod.mod.config.profile;

import lombok.Getter;
import net.coralmod.mod.config.Config;
import net.coralmod.mod.config.ConfigStorage;
import net.coralmod.mod.module.HudModule;
import net.coralmod.mod.module.Module;
import net.coralmod.mod.module.ModuleManager;
import net.coralmod.mod.module.settings.BooleanSetting;
import net.coralmod.mod.module.settings.ModeSetting;
import net.coralmod.mod.module.settings.NumberSetting;
import net.coralmod.mod.module.settings.Setting;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
public class ProfileManager {

    private static final String DEFAULT_PROFILE = "Default";

    private final Config config;
    private final ConfigStorage configStorage;
    private final ModuleManager moduleManager;

    private final ProfileStorage storage;
    private final Set<String> profiles;
    private Profile activeProfile;

    public ProfileManager(Config config, ConfigStorage configStorage, ModuleManager moduleManager) {
        this.config = config;
        this.configStorage = configStorage;
        this.moduleManager = moduleManager;

        storage = new ProfileStorage();
        profiles = config.getProfiles();

        if (profiles.isEmpty()) {
            saveProfile(DEFAULT_PROFILE);
        }

        activeProfile = getProfile(config.getCurrentProfile());

        if (activeProfile == null) {
            saveProfile(DEFAULT_PROFILE);
            config.setCurrentProfile(DEFAULT_PROFILE);
            configStorage.save();
            activeProfile = getProfile(DEFAULT_PROFILE);
        }
    }

    public void saveProfile(String name) {
        final Map<String, Boolean> modules = new HashMap<>();
        final Map<String, Map<String, Object>> moduleSettings = new HashMap<>();
        final Map<String, Map<String, Integer>> hudPositions = new HashMap<>();

        for (Module module : moduleManager.getModules()) {
            modules.put(module.getName(), module.isEnabled());

            final Map<String, Object> settings = new HashMap<>();
            for (Setting<?> setting : module.getSettings()) {
                settings.put(setting.getName(), setting.getValue());
            }
            moduleSettings.put(module.getName(), settings);

            final Map<String, Integer> positions = new HashMap<>();
            if (module instanceof HudModule hudModule) {
                positions.put("x", hudModule.getX());
                positions.put("y", hudModule.getY());
                hudPositions.put(module.getName(), positions);
            }
        }

        final Profile profile = new Profile(name, modules, moduleSettings, hudPositions);
        storage.saveProfile(profile);
        profiles.add(profile.getName());

        if (!config.getProfiles().contains(profile.getName())) {
            config.getProfiles().add(profile.getName());
            configStorage.save();
        }
    }

    public void loadProfile(String name) {
        final Profile profile = getProfile(name);
        if (profile == null) {
            return;
        }

        activeProfile = profile;

        for (Module module : moduleManager.getModules()) {
            final Map<String, Object> settings = profile.getModuleSettings().get(module.getName());

            if (settings != null) {
                settings.forEach((key, value) -> {
                    final Setting<?> setting = module.getSetting(key);

                    if (setting instanceof BooleanSetting booleanSetting && value instanceof Boolean b) {
                        booleanSetting.setValue(b);
                    } else if (setting instanceof NumberSetting numberSetting && value instanceof Double d) {
                        numberSetting.setValue(d);
                    } else if (setting instanceof ModeSetting modeSetting && value instanceof String s) {
                        modeSetting.setValue(s);
                    }
                });
            }

            if (module instanceof HudModule hudModule) {
                final Map<String, Integer> positions = profile.getHudPositions().get(module.getName());

                if (positions != null) {
                    hudModule.setX(positions.get("x"));
                    hudModule.setY(positions.get("y"));
                }
            }


            if (profile.getModules().get(module.getName()) != null) {
                module.setEnabled(profile.getModules().get(module.getName()));
            }
        }
    }

    public Profile getProfile(String name) {
        if (!profiles.contains(name)) {
            return null;
        }
        return storage.loadProfile(name);
    }
}
