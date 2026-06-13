package net.coralmod.mod.module.modules;

import net.coralmod.mod.module.ModuleCategory;
import net.coralmod.mod.module.Module;
import net.coralmod.mod.module.ModuleInfo;
import net.coralmod.mod.module.settings.BooleanSetting;

@ModuleInfo(name = "Scoreboard", description = "Customize your scoreboard", category = ModuleCategory.RENDER)
public class ScoreboardModule extends Module {

    private final BooleanSetting enableScoreboard = new BooleanSetting("Enable Scoreboard", true);
    private final BooleanSetting numbers = new BooleanSetting("Numbers", false);
    private final BooleanSetting background = new BooleanSetting("Background", true);
    private final BooleanSetting titleBackground = new BooleanSetting("Title Background", true);
    private final BooleanSetting textShadow = new BooleanSetting("Text Shadow", false);

    public ScoreboardModule() {
        addSettings(enableScoreboard, numbers, background, titleBackground, textShadow);
    }

    public BooleanSetting enableScoreboard() {
        return enableScoreboard;
    }

    public BooleanSetting numbers() {
        return numbers;
    }

    public BooleanSetting background() {
        return background;
    }

    public BooleanSetting titleBackground() {
        return titleBackground;
    }

    public BooleanSetting textShadow() {
        return textShadow;
    }
}
