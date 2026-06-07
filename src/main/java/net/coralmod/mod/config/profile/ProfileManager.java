package net.coralmod.mod.config.profile;

import net.coralmod.mod.config.Config;
import net.coralmod.mod.config.ConfigStorage;
import net.coralmod.mod.module.ModuleManager;

import java.util.ArrayList;
import java.util.List;

public class ProfileManager {

    public static final String DEFAULT_PROFILE_NAME = "Default";

    private final Config config;
    private final ConfigStorage configStorage;
    private final ProfileStorage storage;
    private final ModuleManager moduleManager;

    private Profile currentProfile;
    private final List<Profile> profiles;

    public ProfileManager(
            Config config,
            ConfigStorage configStorage,
            ProfileStorage storage,
            ModuleManager moduleManager
    ) {
        this.config = config;
        this.configStorage = configStorage;
        this.storage = storage;
        this.moduleManager = moduleManager;

        this.profiles = new ArrayList<>(storage.loadProfiles());

        if (profiles.isEmpty()) {
            createProfile(DEFAULT_PROFILE_NAME);
        }

        load(config.currentProfile());
    }

    public Profile currentProfile() {
        return currentProfile;
    }

    public Profile profile(String name) {
        return profiles.stream()
                .filter(profile -> profile.name().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void createProfile(String name) {
        final Profile profile = new Profile(name, moduleManager.enabledModules());
        storage.save(profile);
        profiles.add(profile);
    }

    public void saveProfile(Profile profile) {
        storage.save(profile);
    }

    public void saveCurrentProfile() {
        saveProfile(currentProfile);
    }

    public void load(String name) {
        final Profile profile = storage.load(name);

        if (profile != null) {
            currentProfile(profile);
        }
    }

    public void currentProfile(Profile profile) {
        this.currentProfile = profile;
        config.currentProfile(profile.name());
        saveCurrentProfile();
    }
}
