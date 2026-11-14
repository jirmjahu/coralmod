package net.coralmod.mod.utils;

import com.mojang.blaze3d.platform.InputConstants;

public final class KeyUtils {

    private KeyUtils() {
    }

    public static boolean isKeyPressed(int key) {
        return InputConstants.isKeyDown(key);
    }
}
