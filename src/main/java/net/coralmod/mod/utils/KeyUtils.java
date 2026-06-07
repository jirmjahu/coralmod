package net.coralmod.mod.utils;

import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public final class KeyUtils {

    private KeyUtils() {
    }

    public static boolean isKeyPressed(int key) {
        return GLFW.glfwGetKey(Minecraft.getInstance().getWindow().handle(), key) == GLFW.GLFW_PRESS;
    }
}
