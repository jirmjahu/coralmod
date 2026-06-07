package net.coralmod.mod.utils;

public final class MouseUtils {

    private MouseUtils() {
    }

    public static boolean isMouseOver(double mouseX, double mouseY, float x, float y, float width, float height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
