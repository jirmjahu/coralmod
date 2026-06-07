package net.coralmod.mod.config;

public class ConfigManager {

    private final ConfigStorage storage;
    private final Config config;

    public ConfigManager(ConfigStorage storage) {
        this.storage = storage;
        this.config = storage.load();
    }

    public Config config() {
        return config;
    }

    public void save() {
        storage.save(config);
    }
}
