package net.coralmod.mod.config;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Config {

    private final Set<String> profiles = new HashSet<>();
    private String currentProfile;
    private String selectedTheme;

}
