package gdx.kapotopia.Helpers;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import gdx.kapotopia.Fonts.FontSize;
import gdx.kapotopia.GameConfig;

public class Align {

    public static float getX(Alignement alignement, int length) {
        return getX(alignement, length, FontSize.NORMAL);
    }

    public static float getX(Alignement alignement, int length, FontSize size) {
        final float FontSizeFactor = getFontSizeFactorForX(size);

        float x = 0;
        final float MIDDLE_SCREEN = GameConfig.GAME_WIDTH / 2f;
        switch (alignement) {
            case LEFT:
                x = MIDDLE_SCREEN / 2f - ((length * FontSizeFactor) / 2 );
                break;
            case CENTER:
                x = MIDDLE_SCREEN - ((length * FontSizeFactor) / 2 );
                break;
            case RIGHT:
                x = ((MIDDLE_SCREEN / 2f) * 3f) - ((length * FontSizeFactor) / 2 );
                break;
        }
        return x;
    }

    private static float getFontSizeFactorForX(FontSize size) {
        switch (size) {
            case TINY:
                return GameConfig.ONE_CHAR_TINY_WIDTH;
            case SMALL:
                return GameConfig.ONE_CHAR_SMALL_WIDTH;
            case MIDDLE:
                return GameConfig.ONE_CHAR_MID_WIDTH;
            case NORMAL:
                return GameConfig.ONE_CHAR_STD_WIDTH;
            case BIG:
                return GameConfig.ONE_CHAR_BIG_WIDTH;
        }
        return GameConfig.ONE_CHAR_STD_WIDTH;
    }

    public static Label centerLabel(Label lab, Alignement alignement) {
        final float newX = getX(alignement, lab.getText().length);
        lab.setX(newX);
        return lab;
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
