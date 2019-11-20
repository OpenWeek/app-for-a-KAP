package gdx.kapotopia;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import gdx.kapotopia.AssetsManaging.FontHelper;

public class Utils {
    public static TextButton.TextButtonStyle getStyleFont(final String path) {
        return FontHelper.buildTextButtonStyle(path, 60, Color.BLACK);
    }

    public static TextButton.TextButtonStyle getStyleFont(final String path, final int size) {
        return FontHelper.buildTextButtonStyle(path, size, Color.BLACK);
    }

    public static TextButton.TextButtonStyle getStyleFont(final String path, final int size, final Color color) {
        return FontHelper.buildTextButtonStyle(path, size, color);
    }

    @org.jetbrains.annotations.Contract(pure = true)
    public static int floorOfAMultipleOf250(int nbr) {
        for (int i=2000; i > 0; i = i - 250) {
            if(nbr > i) return i;
        }
        return 0;
    }
}
