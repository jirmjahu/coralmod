package net.coralmod.mod.module.modules;

import net.coralmod.mod.module.HudModule;
import net.coralmod.mod.module.ModuleInfo;
import net.coralmod.mod.module.settings.BooleanSetting;
import net.coralmod.mod.module.settings.NumberSetting;
import net.coralmod.mod.utils.RenderUtils;
import net.coralmod.mod.utils.ServerUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

import java.awt.*;

@ModuleInfo(name = "Server Address", description = "Displays the server address")
public class ServerAddressModule extends HudModule {

    private Identifier currentServerIcon;

    private final BooleanSetting showServerIcon = new BooleanSetting("Show Server Icon", true);
    private final NumberSetting serverIconSize = new NumberSetting("Server Icon Size", 12, 5, 32, 1);

    public ServerAddressModule() {
        super(20, 20);
        addSettings(showServerIcon, serverIconSize);

        brackets.value(false);

        ClientPlayConnectionEvents.JOIN.register((_, _, _) -> {
            // update server icon after joining a new server
            currentServerIcon = ServerUtils.getServerIcon();
        });
    }

    @Override
    public void onEnable() {
        currentServerIcon = ServerUtils.getServerIcon();
    }

    @Override
    public void render(GuiGraphicsExtractor graphics, Font font) {
        final String text = brackets.value() ? "[" + getText() + "]" : getText();

        final int textWidth = font.width(text);
        final int textHeight = font.lineHeight;

        final int padding = background.value() ? 2 : 0;
        final int iconSize = showServerIcon.value() && currentServerIcon != null ? this.serverIconSize.value().intValue() : 0;

        width(iconSize + (showServerIcon.value() ? 4 : 0) + textWidth + padding * 2);
        height(Math.max(iconSize, textHeight) + padding * 2);

        if (background.value()) {
            graphics.fill(
                    x(),
                    y(),
                    x() + width(),
                    y() + height(),
                    new Color(0, 0, 0, 140).getRGB()
            );
        }

        if (showServerIcon.value() && currentServerIcon != null) {
            final int iconX = x() + padding;
            final int iconY = y() + padding + (height() - padding * 2 - iconSize) / 2;

            RenderUtils.drawTexture(graphics, currentServerIcon, iconX, iconY, iconSize);
        }

        final int textX = x() + padding + iconSize + (showServerIcon.value() ? 4 : 0);
        final int textY = y() + padding + (height() - padding * 2 - textHeight) / 2;

        graphics.text(font, text, textX, textY + 1, -1, textShadow.value());
    }

    @Override
    public String getText() {
        if (mc.hasSingleplayerServer()) {
            return "Singleplayer";
        }

        if (mc.getCurrentServer() == null) {
            return "Unknown";
        }

        return mc.getCurrentServer().ip;
    }
}
