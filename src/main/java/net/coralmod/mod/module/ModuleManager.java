package net.coralmod.mod.module;

import net.coralmod.mod.module.modules.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleManager {

    private final Map<Class<? extends Module>, Module> modules = new HashMap<>();

    public ModuleManager() {
        register(new FpsModule());
        register(new NametagsModule());
        register(new FullBrightModule());
        register(new ZoomModule());
        register(new CoordinatesModule());
        register(new CPSModule());
        register(new ViewTweaksModule());
    }

    private void register(Module module) {
        modules.put(module.getClass(), module);
    }

    public <T extends Module> T getModule(Class<T> moduleClass) {
        return moduleClass.cast(modules.get(moduleClass));
    }

    public Module getModule(String name) {
        return modules.values().stream()
                .filter(module -> module.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public Collection<Module> getModules() {
        return modules.values();
    }

    public List<HudModule> getHudModules() {
        return modules.values().stream()
                .filter(module -> module instanceof HudModule)
                .map(module -> (HudModule) module)
                .toList();
    }
}
