package net.coralmod.mod.module.settings;

import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public abstract class Setting<T> {

    private final String name;

    @Setter
    private T value;
    private final T defaultValue;

    public void reset() {
        value = defaultValue;
    }

    public abstract JsonElement write();

    public abstract void read(JsonElement json);

}
