package gdx.kapotopia.Helpers;

import gdx.kapotopia.GameConfig;

public class Pad {
    public static float getWidth(Padding pad, int textLength) {
        float width = 0;
        switch (pad) {
            case STANDARD:
                width = (GameConfig.ONE_CHAR_STD_WIDTH * textLength) + (GameConfig.ONE_CHAR_STD_WIDTH * 2);
        }
        return width;
    }

    public static float getHeight(Padding pad) {
        float height = 1;
        switch (pad) {
            case STANDARD:
                height = GameConfig.ONE_CHAR_STD_HEIGHT + (GameConfig.ONE_CHAR_STD_HEIGHT / 2);
        }
        return height;
    }

}
