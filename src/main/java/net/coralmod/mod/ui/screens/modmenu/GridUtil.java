package net.coralmod.mod.ui.screens.modmenu;

import net.coralmod.mod.ui.Position;

import java.util.function.BiConsumer;

public final class GridUtil {

    private GridUtil() {
    }

    public static <T> void layoutGrid(Iterable<T> elements, int x, int y, BiConsumer<T, Position> consumer) {
        final int startX = x + ModMenuScreen.PADDING;
        final int usableWidth = ModMenuScreen.MENU_WIDTH - 2 * ModMenuScreen.PADDING - (ModMenuScreen.BUTTONS_PER_ROW - 1) * ModMenuScreen.PADDING;
        final int buttonWidth = usableWidth / ModMenuScreen.BUTTONS_PER_ROW;
        final int leftoverPixels = usableWidth % ModMenuScreen.BUTTONS_PER_ROW;

        int currentX = startX;
        int currentY = y + ModMenuScreen.PADDING;
        int column = 0;

        for (T element : elements) {
            int width = buttonWidth;

            if (column < leftoverPixels) {
                width++;
            }

            consumer.accept(element, new Position(currentX, currentY, width, ModMenuScreen.BUTTON_HEIGHT));

            currentX += width + ModMenuScreen.PADDING;
            column++;

            if (column == ModMenuScreen.BUTTONS_PER_ROW) {
                column = 0;
                currentX = startX;
                currentY += ModMenuScreen.BUTTON_HEIGHT + ModMenuScreen.PADDING;
            }
        }
    }
}