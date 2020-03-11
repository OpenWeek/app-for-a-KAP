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

    public static float getXCenteredWithElement(float x, float elementWidth, int textLength) {
        final float center = x + (elementWidth / 2);
        final float labelLength = textLength * GameConfig.ONE_CHAR_SMALL_WIDTH;
        final float labelX = center - (labelLength / 2);
        return labelX;
    }

    public static Bounds getDialogBubbleBounds() {
        final float ww = GameConfig.GAME_WIDTH;
        final float wh = GameConfig.GAME_HEIGHT;

        final float width = ww * 0.9f;
        final float height = wh * 0.42f;
        final float top_pad = wh / 128;
        final float hor_pad = ww / 48;
        final float x = ww - width - hor_pad;
        final float y = wh - height - top_pad;

        return new Bounds(x, y, width, height);
    }

    public static Bounds getExplicativeBubbleBounds() {
        final float ww = GameConfig.GAME_WIDTH;
        final float wh = GameConfig.GAME_HEIGHT;

        final float width = ww * 0.95f;
        final float height = wh * 0.5f;
        final float top_pad = wh / 42.667f;
        final float hor_pad = ww / 98.5f;
        final float x = ww - width - hor_pad;
        final float y = wh - height - top_pad;

        return new Bounds(x, y, width, height);
    }
}
