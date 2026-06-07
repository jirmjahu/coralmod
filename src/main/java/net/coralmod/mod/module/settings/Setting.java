package net.coralmod.mod.module.settings;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class Setting<T> {

    private final String name;
    private T value;
    private final T defaultValue;
    private final List<BiConsumer<T, T>> changeListeners = new ArrayList<>();

    public Setting(String name, T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public String name() {
        return name;
    }

    public T value() {
        return value;
    }

    public T defaultValue() {
        return defaultValue;
    }

    public void value(T newValue) {
        final T old = this.value;

        if (old.equals(newValue)) {
            return;
        }

        this.value = newValue;

        for (BiConsumer<T, T> listener : changeListeners) {
            listener.accept(old, newValue);
        }
    }

    public void onChange(BiConsumer<T, T> listener) {
        changeListeners.add(listener);
    }

    public void reset() {
        value = defaultValue;
    }

    public abstract JsonElement write();

    public abstract void read(JsonElement json);
}
