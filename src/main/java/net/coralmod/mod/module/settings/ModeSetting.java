package net.coralmod.mod.module.settings;

import lombok.Getter;

import java.util.List;

@Getter
public class ModeSetting extends Setting<String> {

    private final List<String> modes;

    public ModeSetting(String name, String defaultMode, List<String> modes) {
        super(name, defaultMode, defaultMode);
        this.modes = modes;
    }
}
