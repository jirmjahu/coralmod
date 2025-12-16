package net.coralmod.mod.module.settings;

import lombok.Getter;

@Getter
public class NumberSetting extends Setting<Double> {

    private final double min;
    private final double max;
    private final double increment;

    public NumberSetting(String name, double defaultValue, double min, double max, double increment) {
        super(name, defaultValue, defaultValue);
        this.min = min;
        this.max = max;
        this.increment = increment;
    }
}
