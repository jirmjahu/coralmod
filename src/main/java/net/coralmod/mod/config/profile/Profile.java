package net.coralmod.mod.config.profile;

import net.coralmod.mod.module.Module;

import java.util.List;

public record Profile(String name, List<Module> enabledModules) {}
