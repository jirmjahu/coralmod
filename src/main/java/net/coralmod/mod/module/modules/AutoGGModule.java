package net.coralmod.mod.module.modules;

import net.coralmod.mod.event.AddChatMessageEvent;
import net.coralmod.mod.module.ModuleCategory;
import net.coralmod.mod.module.Module;
import net.coralmod.mod.module.ModuleInfo;
import net.coralmod.mod.module.settings.BooleanSetting;
import net.coralmod.mod.utils.ChatUtils;
import net.coralmod.mod.utils.ServerUtils;
import net.coralmod.mod.utils.TimeDelay;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import java.util.List;

@ModuleInfo(name = "AutoGG", description = "Automatically sends GG at the end of a round", category = ModuleCategory.MISC)
public class AutoGGModule extends Module {

    private static final long DELAY_MS = 1000;

    private final BooleanSetting gomme = new BooleanSetting("GommeHD", true);
    private final BooleanSetting hypixel = new BooleanSetting("Hypixel", true);
    private final BooleanSetting cytooxien = new BooleanSetting("Cytooxien", true);

    private final List<ServerConfig> servers = List.of(
            new ServerConfig(gomme, List.of("gommehd.net"), List.of(
                    "-= Statistiken dieser Runde =-",
                    "-= Statistics of this game =-"
            )),
            new ServerConfig(hypixel, List.of("hypixel.net"), List.of(
                    "1st Killer -",
                    "Winner:",
                    "Winning Team",
                    "won the game!",
                    "Top Survivors",
                    "Your Overall Winstreak:"
            )),
            new ServerConfig(cytooxien, List.of("cytooxien.de", "cytooxien.net"), List.of(
                    "Statistiken dieser Runde",
                    "Statistics of the game"
            ))
    );

    private final TimeDelay delay = new TimeDelay();
    private boolean shouldSend = false;

    public AutoGGModule() {
        addSettings(gomme, hypixel, cytooxien);

        AddChatMessageEvent.ADD_CHAT_MESSAGE_EVENT.register(message -> {
            if (!enabled() || ServerUtils.getCurrentServerIp() == null) {
                return;
            }

            if (shouldSendGG(message)) {
                shouldSend = true;
                delay.reset();
            }
        });

        ClientTickEvents.START_CLIENT_TICK.register(mc -> {
            if (!enabled()) {
                return;
            }

            if (shouldSend && delay.hasPassed(DELAY_MS)) {
                ChatUtils.sendAsPlayer("GG");
                shouldSend = false;
            }
        });
    }

    private boolean shouldSendGG(String message) {
        final String currentIp = ServerUtils.getCurrentServerIp();
        if (currentIp == null) {
            return false;
        }

        return servers.stream()
                .filter(ServerConfig::isEnabled)
                .anyMatch(s -> s.matchesServer(currentIp) && s.matchesMessage(message));
    }

    private record ServerConfig(BooleanSetting setting, List<String> ips, List<String> triggers) {

        boolean isEnabled() {
            return setting.value();
        }

        boolean matchesServer(String ip) {
            return ips.stream().anyMatch(ip::contains);
        }

        boolean matchesMessage(String message) {
            return triggers.stream().anyMatch(message::contains);
        }
    }
}
