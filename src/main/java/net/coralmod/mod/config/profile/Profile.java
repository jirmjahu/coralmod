package net.coralmod.mod.config.profile;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@EqualsAndHashCode(of = "name")
@RequiredArgsConstructor
public class Profile {

    private final String name;
    private final Map<String, Boolean> modules;
    private final Map<String, Map<String, Object>> moduleSettings;
    private final Map<String, Map<String, Integer>> hudPositions;

}
