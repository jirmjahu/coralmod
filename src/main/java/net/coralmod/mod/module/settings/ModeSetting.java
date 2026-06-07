package net.coralmod.mod.module.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.List;

public class ModeSetting extends Setting<String> {

    private final List<String> modes;

    public ModeSetting(String name, String defaultMode, List<String> modes) {
        super(name, defaultMode);
        this.modes = modes;
    }

    public List<String> modes() {
        return modes;
    }

    @Override
    public JsonElement write() {
        return new JsonPrimitive(value());
    }

    @Override
    public void read(JsonElement json) {
        value(json.getAsString());
    }
}
