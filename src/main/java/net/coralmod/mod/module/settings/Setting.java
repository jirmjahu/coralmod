package net.coralmod.mod.module.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public abstract class Setting<T> {

    private final String name;

    @Setter
    private T value;

}
