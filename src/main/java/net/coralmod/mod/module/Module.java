package net.coralmod.mod.module;

import lombok.Getter;

@Getter
public class Module {

    private final ModuleInfo info;
    private final String name;
    private final String description;
    private boolean enabled;

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
}
