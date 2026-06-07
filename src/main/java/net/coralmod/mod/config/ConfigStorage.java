package net.coralmod.mod.config;

import net.coralmod.mod.CoralMod;
import net.coralmod.mod.utils.JsonUtils;

import java.nio.file.Path;

public class ConfigStorage {

    private static final Path CONFIG_PATH = Path.of("coralmod", "config.json");

    public Config load() {
        Config config = JsonUtils.loadFromJson(CONFIG_PATH, Config.class);

        if (config == null) {
            config = new Config();
            save(config);
            return config;
        }

        if (config.version() != Config.VERSION) {
            CoralMod.LOGGER.warn(
                   "Config version does not match the expected version! Expected {}, got {}",
                    Config.VERSION,
                    config.version()
            );
        }

        config.version(Config.VERSION);
        save(config);
        return config;
    }

    public void save(Config config) {
        JsonUtils.saveToJson(CONFIG_PATH, config);
    }
}
