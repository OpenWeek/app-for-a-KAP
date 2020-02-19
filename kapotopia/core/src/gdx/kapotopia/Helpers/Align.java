package gdx.kapotopia.Helpers;

import gdx.kapotopia.GameConfig;

public class Align {

    public static float getX(Alignement alignement, int length) {
        float x = 0;
        final float MIDDLE_SCREEN = GameConfig.GAME_WIDTH / 2f;
        switch (alignement) {
            case LEFT:
                x = MIDDLE_SCREEN / 2f - ((length * GameConfig.ONE_CHAR_STD_WIDTH) / 2 );
                break;
            case CENTER:
                x = MIDDLE_SCREEN - ((length * GameConfig.ONE_CHAR_STD_WIDTH) / 2 );
                break;
            case RIGHT:
                x = ((MIDDLE_SCREEN / 2f) * 3f) - ((length * GameConfig.ONE_CHAR_STD_WIDTH) / 2 );
                break;
        }
        return x;
    }
}
