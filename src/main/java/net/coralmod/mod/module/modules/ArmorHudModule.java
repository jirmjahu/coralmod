package net.coralmod.mod.module.modules;

import net.coralmod.mod.module.HudModule;
import net.coralmod.mod.module.ModuleInfo;
import net.coralmod.mod.module.settings.BooleanSetting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@ModuleInfo(name = "ArmorHud", description = "Displays armor with durability")
public class ArmorHudModule extends HudModule {

    private static final int ITEM_SIZE = 16;
    private static final int ITEM_PADDING = 2;
    private static final int BACKGROUND_PADDING = 4;

    private final BooleanSetting showDurability = new BooleanSetting("Show Durability", true);
    private final BooleanSetting showDurabilityPercent = new BooleanSetting("Durability as Percent", false);

    public ArmorHudModule() {
        super(20, 20);
        settings().remove(brackets);
        addSettings(showDurability, showDurabilityPercent);
    }

    @Override
    public void render(GuiGraphicsExtractor graphics, Font font) {
        if (mc.player == null) {
            return;
        }

        final List<ItemStack> armor = Arrays.stream(EquipmentSlot.values())
                .filter(slot -> slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR)
                .map(mc.player::getItemBySlot)
                .filter(stack -> !stack.isEmpty())
                .toList();

        final int padding = background.value() ? BACKGROUND_PADDING : 0;

        final int maxTextWidth = armor.stream()
                .filter(itemStack -> showDurability.value())
                .mapToInt(itemStack -> font.width(getDurabilityText(itemStack)))
                .max()
                .orElse(0);

        // add a little extra padding when durability text is visible
        width(ITEM_SIZE + maxTextWidth + padding * 2 + (showDurability.value() ? 4 : 0));
        height(armor.size() * (ITEM_SIZE + ITEM_PADDING) - ITEM_PADDING + padding * 2);

        if (background.value()) {
            graphics.fill(x(), y(), x() + width(), y() + height(), new Color(0, 0, 0, 150).getRGB());
        }

        int offset = padding;
        for (ItemStack stack : armor.reversed()) {
            graphics.item(stack, x() + padding, y() + offset);

            if (showDurability.value()) {
                graphics.text(font, getDurabilityText(stack), x() + ITEM_SIZE + padding + 2, y() + offset + 5, -1, textShadow.value());
            }

            offset += ITEM_SIZE + ITEM_PADDING;
        }
    }

    private String getDurabilityText(ItemStack item) {
        final int maxDamage = item.getMaxDamage();
        final int damage = item.getDamageValue();

        if (showDurabilityPercent.value()) {
            return (int) (((float) (maxDamage - damage) / maxDamage) * 100) + "%";
        }
        return (maxDamage - damage) + "/" + maxDamage;
    }

    @Override
    public String getText() {
        return "";
    }
}
