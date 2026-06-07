package net.coralmod.mod.command.sub;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.Module;
import net.coralmod.mod.module.ModuleManager;
import net.coralmod.mod.module.settings.BooleanSetting;
import net.coralmod.mod.module.settings.ModeSetting;
import net.coralmod.mod.module.settings.NumberSetting;
import net.coralmod.mod.module.settings.Setting;
import net.coralmod.mod.utils.ChatUtils;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class ModuleCommand {

    private static final ModuleManager moduleManager = CoralMod.instance().moduleManager();

    public static LiteralArgumentBuilder<FabricClientCommandSource> build() {
        return ClientCommands.literal("module")
                .then(ClientCommands.argument("moduleName", StringArgumentType.word())
                        .suggests((ctx, builder) -> {
                            moduleManager.modules().forEach(module -> builder.suggest(module.name()));
                            return builder.buildFuture();
                        })
                        .then(ClientCommands.literal("enable").executes(ctx ->
                                enableModule(StringArgumentType.getString(ctx, "moduleName"))))
                        .then(ClientCommands.literal("disable").executes(ctx ->
                                disableModule(StringArgumentType.getString(ctx, "moduleName"))))
                        .then(ClientCommands.literal("setting")
                                .then(ClientCommands.argument("settingName", StringArgumentType.word())
                                        .suggests((ctx, builder) -> {
                                            final String moduleName = StringArgumentType.getString(ctx, "moduleName");
                                            final Module module = moduleManager.module(moduleName);
                                            if (module == null) {
                                                return builder.buildFuture();
                                            }
                                            module.settings().forEach(setting -> builder.suggest(setting.name()));
                                            return builder.buildFuture();
                                        })
                                        .then(ClientCommands.argument("value", StringArgumentType.word())
                                                .executes(ctx -> setModuleSetting(
                                                        StringArgumentType.getString(ctx, "moduleName"),
                                                        StringArgumentType.getString(ctx, "settingName"),
                                                        StringArgumentType.getString(ctx, "value")
                                                ))
                                        )
                                )
                        )
                );
    }

    private static int enableModule(String name) {
        final Module module = moduleManager.module(name);
        if (module == null) {
            ChatUtils.sendToPlayer("Module " + name + " not found");
            return 0;
        }

        module.enabled(true);
        ChatUtils.sendToPlayer("Module " + name + " enabled");
        return 1;
    }

    private static int disableModule(String name) {
        final Module module = moduleManager.module(name);
        if (module == null) {
            ChatUtils.sendToPlayer("Module " + name + " not found");
            return 0;
        }

        module.enabled(false);
        ChatUtils.sendToPlayer("Module " + name + " disabled");
        return 1;
    }

    private static int setModuleSetting(String moduleName, String settingName, String value) {
        final Module module = moduleManager.module(moduleName);
        if (module == null) {
            ChatUtils.sendToPlayer("Module " + moduleName + " not found");
            return 0;
        }

        final Setting<?> setting = module.setting(settingName);
        if (setting == null) {
            ChatUtils.sendToPlayer("Setting " + settingName + " not found");
            return 0;
        }

        try {
            if (setting instanceof BooleanSetting booleanSetting) {
                booleanSetting.value(Boolean.parseBoolean(value));
                ChatUtils.sendToPlayer("Setting " + settingName + " set to " + value);
                return 1;
            }

            if (setting instanceof NumberSetting numberSetting) {
                numberSetting.value(Double.parseDouble(value));
                ChatUtils.sendToPlayer("Setting " + settingName + " set to " + value);
                return 1;
            }

            if (setting instanceof ModeSetting modeSetting) {
                if (!modeSetting.modes().contains(value)) {
                    return 0;
                }
                modeSetting.value(value);
                ChatUtils.sendToPlayer("Setting " + settingName + " set to " + value);
                return 1;
            }
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }
}
