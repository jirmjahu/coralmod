package net.coralmod.mod.module.modules;

import net.coralmod.mod.module.HudModule;
import net.coralmod.mod.module.ModuleInfo;
import net.coralmod.mod.module.settings.BooleanSetting;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@ModuleInfo(name = "Clock", description = "Displays the current time")
public class ClockModule extends HudModule {

    private final BooleanSetting use24HourFormat = new BooleanSetting("24-Hour Format", true);
    private final BooleanSetting showSeconds = new BooleanSetting("Show Seconds", true);

    public ClockModule() {
        super(20, 20);
        addSettings(use24HourFormat, showSeconds);
    }

    @Override
    public String getText() {
        final String pattern;
        if (use24HourFormat.value()) {
            pattern = showSeconds.value() ? "HH:mm:ss" : "HH:mm";
        } else {
            pattern = showSeconds.value() ? "hh:mm:ss a" : "hh:mm a";
        }
        return LocalDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(pattern));
    }
}
