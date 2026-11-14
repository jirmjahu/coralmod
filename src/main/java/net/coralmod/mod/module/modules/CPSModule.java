package net.coralmod.mod.module.modules;

import net.coralmod.mod.module.ModuleCategory;
import net.coralmod.mod.module.HudModule;
import net.coralmod.mod.module.ModuleInfo;
import net.coralmod.mod.module.settings.BooleanSetting;
import net.coralmod.mod.utils.ClickCounter;

@ModuleInfo(name = "CPS", description = "Shows your CPS", category = ModuleCategory.HUD)
public class CPSModule extends HudModule {

    private final BooleanSetting showCpsText = new BooleanSetting("Show CPS Text", true);
    private final BooleanSetting showLeftButton = new BooleanSetting("Show Left CPS", true);
    private final BooleanSetting showRightButton = new BooleanSetting("Show Right CPS", true);

    public CPSModule() {
        super(20, 20);
        addSettings(showCpsText, showLeftButton, showRightButton);
    }

    @Override
    public String getText() {
        final StringBuilder builder = new StringBuilder();

        if (showCpsText.value()) {
            builder.append("CPS: ");
        }
        if (showLeftButton.value()) {
            builder.append(ClickCounter.leftCps());
        }
        if (showLeftButton.value() && showRightButton.value()) {
            builder.append(" | ");
        }
        if (showRightButton.value()) {
            builder.append(ClickCounter.rightCps());
        }

        return builder.toString();
    }
}
