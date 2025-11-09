package net.coralmod.mod.config;

import net.coralmod.mod.utils.JsonUtils;

import java.io.File;

public class ConfigStorage {

    private static final File CONFIG_FILE = new File("coralmod/config.json");
    private Config config;

    public Config load() {
        config = JsonUtils.loadFromJson(CONFIG_FILE, Config.class);
        if (config == null) {
            config = new Config();
            save();
        }
        return config;
    }

    public void save() {
        JsonUtils.saveToJson(CONFIG_FILE, config);
    }
}
