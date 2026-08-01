package net.coralmod.mod.module.modules;

import net.coralmod.mod.module.ModuleCategory;
import net.coralmod.mod.module.HudModule;
import net.coralmod.mod.module.ModuleInfo;
import net.coralmod.mod.module.settings.BooleanSetting;
import net.coralmod.mod.render.DrawContext;
import net.coralmod.mod.render.FontRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@ModuleInfo(name = "ArmorHud", description = "Displays armor with durability", category = ModuleCategory.HUD)
public final class ArmorHudModule extends HudModule {

    private static final int ITEM_SIZE = 16;
    private static final int ITEM_PADDING = 2;
    private static final int TEXT_GAP = 3;
    private static final int BACKGROUND_PADDING = 4;

    private final BooleanSetting showDurability = new BooleanSetting("Show Durability", true);
    private final BooleanSetting showDurabilityPercent = new BooleanSetting("Durability as Percent", false);

    public ArmorHudModule() {
        super(20, 20);
        settings().remove(brackets);
        addSettings(showDurability, showDurabilityPercent);
    }

    @Override
    public void render(DrawContext context) {
        if (mc.player == null) {
            return;
        }

        final FontRenderer font = context.fonts().minecraft();

        final List<ItemStack> armor = Arrays.stream(EquipmentSlot.values())
                .filter(slot -> slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR)
                .map(mc.player::getItemBySlot)
                .filter(stack -> !stack.isEmpty())
                .toList();

        final int padding = background.value() ? BACKGROUND_PADDING : 0;

        final int maxTextWidth = armor.stream()
                .filter(_ -> showDurability.value())
                .mapToInt(itemStack -> font.width(durabilityText(itemStack)))
                .max()
                .orElse(0);

        width(ITEM_SIZE + maxTextWidth + padding * 2 + (showDurability.value() ? TEXT_GAP : 0));
        height(armor.size() * (ITEM_SIZE + ITEM_PADDING) - ITEM_PADDING + padding * 2);

        if (background.value()) {
            context.shapes().rect(x(), y(), x() + width(), y() + height(), new Color(0, 0, 0, 150));
        }

        int offset = padding;
        for (ItemStack stack : armor.reversed()) {
            context.textures().item(stack.getItem(),  x() + padding + ITEM_SIZE / 2,  y() + offset + ITEM_SIZE / 2 - 1, 1.0F);

            if (showDurability.value()) {
                final int textX = x() + padding + ITEM_SIZE + TEXT_GAP;
                final int textY = y() + offset + (ITEM_SIZE - font.height()) / 2;
                font.draw(durabilityText(stack), textX, textY, Color.WHITE, textShadow.value());
            }

            offset += ITEM_SIZE + ITEM_PADDING;
        }
    }

    private String durabilityText(ItemStack item) {
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
