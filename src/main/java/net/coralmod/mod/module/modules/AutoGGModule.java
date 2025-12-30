package net.coralmod.mod.module.modules;

import net.coralmod.mod.event.AddChatMessageEvent;
import net.coralmod.mod.module.Module;
import net.coralmod.mod.module.ModuleInfo;
import net.coralmod.mod.utils.ChatUtils;
import net.coralmod.mod.utils.TimeDelay;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import java.util.List;

@ModuleInfo(name = "AutoGG", description = "Automatically sends GG at the end of a round")
public class AutoGGModule extends Module {

    private static final long DELAY_TIME = 1000;

    private final TimeDelay delay;
    private boolean shouldSend = false;

    private static final List<String> GOMME_MESSAGES = List.of(
            "-= Statistiken dieser Runde =-",
            "-= Statistics of this game =-"
    );

    private static final List<String> CYTOOXIEN_MESSAGES = List.of(
            "Statistiken dieser Runde",
            "Statistics of the game"
    );

    private static final List<String> HYPIXEL_MESSAGES = List.of(
            "1st Killer -",
            "1st Place -",
            "Winner:",
            " - Damage Dealt -",
            "Winning Team -",
            "1st -",
            "Winners:",
            "Winning Team:",
            " won the game!",
            "Top Seeker:",
            "Last team standing!",
            "Winner #1 (",
            "Top Survivors",
            "Winners -",
            "Sumo Duel -",
            "Most Wool Placed -",
            "Your Overall Winstreak:"
    );

    public AutoGGModule() {
        this.delay = new TimeDelay();

        AddChatMessageEvent.ADD_CHAT_MESSAGE_EVENT.register(message -> {
            if (!isEnabled()) {
                return;
            }

            if (shouldSendGG(message)) {
                shouldSend = true;
                delay.reset();
            }
        });

        ClientTickEvents.START_CLIENT_TICK.register(mc -> {
            if (!isEnabled()) {
                return;
            }

            if (shouldSend && delay.hasPassed(DELAY_TIME)) {
                ChatUtils.sendAsPlayer("GG");
                shouldSend = false;
            }
        });
    }

    private boolean shouldSendGG(String message) {
        return GOMME_MESSAGES.stream().anyMatch(message::contains)
                || CYTOOXIEN_MESSAGES.stream().anyMatch(message::contains)
                || HYPIXEL_MESSAGES.stream().anyMatch(message::contains);
    }
}
