package net.coralmod.mod.module;

import net.coralmod.mod.module.settings.Setting;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Module {

    private final String name;
    private final String description;
    private boolean enabled;
    private final List<Setting<?>> settings = new ArrayList<>();
    protected final Minecraft mc = Minecraft.getInstance();

    public Module() {
        final ModuleInfo info = getClass().getAnnotation(ModuleInfo.class);

        if (info == null) {
            throw new IllegalStateException("@ModuleInfo annotation is missing on " + getClass().getSimpleName());
        }

        this.name = info.name();
        this.description = info.description();
    }

    public void onEnable() {}

    public void onDisable() {}

    public void enabled(boolean enabled) {
        this.enabled = enabled;

        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public boolean enabled() {
        return enabled;
    }

    public List<Setting<?>> settings() {
        return settings;
    }

    public void reset() {
        for (Setting<?> setting : settings) {
            setting.reset();
        }
    }

    public void addSettings(Setting<?>... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public Setting<?> setting(String name) {
        return settings.stream()
                .filter(setting -> setting.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
