package net.coralmod.mod.module;

import net.coralmod.mod.module.modules.*;

import java.util.*;

public class ModuleManager {

    private final Map<Class<? extends Module>, Module> modules = new LinkedHashMap<>();

    public ModuleManager() {
        register(new ArmorHudModule());
        register(new AspectModule());
        register(new AutoGGModule());
        register(new ClockModule());
        register(new CoordinatesModule());
        register(new CPSModule());
        register(new ScoreboardModule());
        register(new FpsModule());
        register(new FullBrightModule());
        register(new NametagsModule());
        register(new NoBackgroundModule());
        register(new PingModule());
        register(new ServerAddressModule());
        register(new ViewTweaksModule());
        register(new ZoomModule());
    }

    private void register(Module module) {
        modules.put(module.getClass(), module);
    }

    public <T extends Module> T module(Class<T> moduleClass) {
        return moduleClass.cast(modules.get(moduleClass));
    }

    public Module module(String name) {
        return modules.values().stream()
                .filter(module -> module.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public Collection<Module> modules() {
        return modules.values();
    }

    public List<HudModule> hudModules() {
        return modules.values().stream()
                .filter(HudModule.class::isInstance)
                .map(HudModule.class::cast)
                .toList();
    }

    public List<Module> enabledModules() {
        return modules.values().stream()
                .filter(Module::enabled)
                .toList();
    }
}
