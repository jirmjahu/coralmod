package net.coralmod.mod.module;

import lombok.Getter;
import net.coralmod.mod.module.settings.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Module {

    private final ModuleInfo info;
    private final String name;
    private final String description;
    private boolean enabled;
    private final List<Setting<?>> settings = new ArrayList<>();

    public Module() {
        info = getClass().getAnnotation(ModuleInfo.class);
        if (info == null) {
            throw new RuntimeException("ModuleInfo annotation is missing on class " + getClass().getSimpleName());
        }
        name = info.name();
        description = info.description();
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void addSettings(Setting<?>... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public Setting<?> getSetting(String name) {
        return settings.stream()
                .filter(setting -> setting.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
