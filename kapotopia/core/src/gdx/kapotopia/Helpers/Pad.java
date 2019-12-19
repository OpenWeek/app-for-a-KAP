package gdx.kapotopia.Helpers;

import gdx.kapotopia.Kapotopia;

public class Pad {
    public static float getWidth(Padding pad, int textLength) {
        float width = 0;
        switch (pad) {
            case STANDARD:
                width = (Kapotopia.ONE_CHARACTER_STD_WIDTH * textLength) + (Kapotopia.ONE_CHARACTER_STD_WIDTH * 2);
        }
        return width;
    }

    public static float getHeight(Padding pad) {
        float height = 1;
        switch (pad) {
            case STANDARD:
                height = Kapotopia.ONE_CHARACTER_STD_HEIGHT + (Kapotopia.ONE_CHARACTER_STD_HEIGHT / 2);
        }
        return height;
    }
}
