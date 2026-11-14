package net.coralmod.mod.config.profile;

import com.google.gson.*;
import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.HudModule;
import net.coralmod.mod.module.Module;
import net.coralmod.mod.module.ModuleManager;
import net.coralmod.mod.module.settings.Setting;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProfileTypeAdapter implements JsonSerializer<Profile>, JsonDeserializer<Profile> {

    private static final int VERSION = 1;

    private final ModuleManager moduleManager;

    public ProfileTypeAdapter(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    @Override
    public JsonElement serialize(Profile src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject root = new JsonObject();
        root.addProperty("version", VERSION);
        root.addProperty("name", src.name());

        final JsonObject modulesObject = new JsonObject();

        for (Module module : moduleManager.modules()) {
            final JsonObject moduleObject = new JsonObject();
            moduleObject.addProperty("enabled", module.enabled());

            final JsonObject moduleSettings = new JsonObject();
            for (Setting<?> setting : module.settings()) {
                moduleSettings.add(setting.name(), setting.write());
            }

            moduleObject.add("settings", moduleSettings);

            if (module instanceof HudModule hudModule) {
                final JsonObject hudObject = new JsonObject();
                hudObject.addProperty("x", hudModule.x());
                hudObject.addProperty("y", (hudModule.y()));
                moduleObject.add("hud", hudObject);
            }

            modulesObject.add(module.name(), moduleObject);
        }

        root.add("modules", modulesObject);
        return root;
    }

    @Override
    public Profile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject root = json.getAsJsonObject();

        if (!root.has("name")) {
            throw new JsonParseException("Profile is missing a name");
        }

        final String name = root.get("name").getAsString();

        if (!root.has("version") || root.get("version").getAsInt() != VERSION) {
            CoralMod.LOGGER.warn("Profile version does not match the expected version! Expected {}, got {}", VERSION, root.get("version").getAsInt());
        }

        final List<Module> enabledModules = new ArrayList<>();

        if (!root.has("modules")) {
            return new Profile(name, enabledModules);
        }

        final JsonObject modulesObject = root.getAsJsonObject("modules");

        for (Module module : moduleManager.modules()) {
            if (!modulesObject.has(module.name())) {
                continue;
            }

            try {
                readModule(module, modulesObject.getAsJsonObject(module.name()));
            } catch (Exception e) {
                CoralMod.LOGGER.error("Failed to read module {} in profile {}", module.name(), name, e);
            }

            if (module.enabled()) {
                enabledModules.add(module);
            }
        }

        return new Profile(name, enabledModules);
    }

    private void readModule(Module module, JsonObject moduleObject) {
        if (moduleObject.has("enabled")) {
            module.enabled(moduleObject.get("enabled").getAsBoolean());
        }

        if (moduleObject.has("settings")) {
            final JsonObject settingsObject = moduleObject.getAsJsonObject("settings");

            for (Setting<?> setting : module.settings()) {
                if (!settingsObject.has(setting.name())) {
                    continue;
                }

                try {
                    setting.read(settingsObject.get(setting.name()));
                } catch (Exception e) {
                    CoralMod.LOGGER.error("Failed to read setting {} for module {}", setting.name(), module.name(), e);
                }
            }
        }

        if (module instanceof HudModule hud && moduleObject.has("hud")) {
            final JsonObject hudObject = moduleObject.getAsJsonObject("hud");

            if (hudObject.has("x")) {
                hud.x(hudObject.get("x").getAsInt());
            }
            if (hudObject.has("y")) {
                hud.y(hudObject.get("y").getAsInt());
            }
        }
    }
}
