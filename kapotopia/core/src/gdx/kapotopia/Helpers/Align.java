package gdx.kapotopia.Helpers;

import gdx.kapotopia.Kapotopia;

public class Align {

    public static float getX(Alignement alignement, int length) {
        float x = 0;
        final float MIDDLE_SCREEN = Kapotopia.GAME_WIDTH / 2f;
        switch (alignement) {
            case LEFT:
                x = MIDDLE_SCREEN / 2f - ((length * Kapotopia.ONE_CHARACTER_STD_WIDTH) / 2 );
                break;
            case CENTER:
                x = MIDDLE_SCREEN - ((length * Kapotopia.ONE_CHARACTER_STD_WIDTH) / 2 );
                break;
            case RIGHT:
                x = ((MIDDLE_SCREEN / 2f) * 3f) - ((length * Kapotopia.ONE_CHARACTER_STD_WIDTH) / 2 );
                break;
        }
        return x;
    }
}
