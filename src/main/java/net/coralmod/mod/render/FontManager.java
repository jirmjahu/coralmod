package net.coralmod.mod.render;

public interface FontManager {

    FontRenderer get(String id);

    FontRenderer minecraft();

    FontRenderer inter();

    FontRenderer interBold();

    FontRenderer interExtraBold();

    FontRenderer cascadiaCode();

}
