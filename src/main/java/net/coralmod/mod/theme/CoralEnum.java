package net.coralmod.mod.theme;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.awt.*;

// Color enum for Themes :)
// There are 5 corals
// Colors for each of them calculated with: https://avg-colour.netlify.app/

// i call .brighter().brighter().brighter().brighter() because the black/transparent background of the coral images is also used for the calculation

@AllArgsConstructor
@Getter
public enum CoralEnum {
    TUBE("Tube", Items.TUBE_CORAL, new Color(11, 20, 49).brighter().brighter().brighter().brighter().getRGB()),
    BRAIN("Brain", Items.BRAIN_CORAL, new Color(42, 18, 32).brighter().brighter().brighter().brighter().getRGB()),
    BUBBLE("Bubble", Items.BUBBLE_CORAL, new Color(41, 5, 40).brighter().brighter().brighter().brighter().getRGB()),
    FIRE("Fire", Items.FIRE_CORAL, new Color(43, 9, 11).brighter().brighter().brighter().brighter().getRGB()),
    HORN("Horn", Items.HORN_CORAL, new Color(46, 41, 13).brighter().brighter().brighter().brighter().getRGB()),

    // more themes not based on coral
    WHITE("Gray", Items.LIGHT_GRAY_WOOL, Color.GRAY.getRGB()),
    ORCHID("Orchid", Items.BLUE_ORCHID, new Color(100, 192, 255).getRGB())

    ;

    private final String name;
    private final Item item; // for gui draw
    private final int color;
}
