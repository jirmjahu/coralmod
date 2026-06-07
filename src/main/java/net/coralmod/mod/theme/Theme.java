package net.coralmod.mod.theme;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.awt.*;

public enum Theme {

    TUBE("Tube", Items.TUBE_CORAL, new Color(47, 82, 194)),
    BRAIN("Brain", Items.BRAIN_CORAL, new Color(195, 83, 150)),
    BUBBLE("Bubble", Items.BUBBLE_CORAL, new Color(160, 24, 158)),
    FIRE("Fire", Items.FIRE_CORAL, new Color(165, 37, 46)),
    HORN("Horn", Items.HORN_CORAL, new Color(207, 184, 62));

    private final String displayName;
    private final Item displayItem;
    private final Color primaryColor;
    private final Color secondaryColor;

    Theme(String displayName, Item displayItem, Color primaryColor) {
        this.displayName = displayName;
        this.displayItem = displayItem;
        this.primaryColor = primaryColor;
        this.secondaryColor = primaryColor.darker();
    }

    public String displayName() {
        return displayName;
    }

    public Item displayItem() {
        return displayItem;
    }

    public Color primaryColor() {
        return primaryColor;
    }

    public Color secondaryColor() {
        return secondaryColor;
    }
}
