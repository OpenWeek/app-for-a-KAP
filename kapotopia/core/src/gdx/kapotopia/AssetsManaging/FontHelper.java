package gdx.kapotopia.AssetsManaging;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class FontHelper {
    private static final String CLASSIC_BOLD_NAME = "COMMSB.ttf";
    private static final String CLASSIC_IT_NAME = "COMMI.ttf";
    private static final String CLASSIC_REG_NAME = "COMMUNIS.ttf";
    private static final String CLASSIC_SANS_NAME = "COMMS.ttf";
    private static final String AESTHETIC_NAME = "SEASRN__.ttf";

    private static final int TINY_SIZE = 20;
    private static final int SMALL_SIZE = 38;
    private static final int MIDDLE_SIZE = 50;
    private static final int NORMAL_SIZE = 60;
    private static final int BIG_SIZE = 90;

    /**
     * Get one of the preconfigured fonts
     * @param type the preconfigured font
     * @return the desired font or null if the type wasn't taken in charge
     */
    public static TextButton.TextButtonStyle getStyleFont(UseFont type) {
        switch (type) {
            case CLASSIC_BOLD_NORMAL_BLACK:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_BOLD_NORMAL_BLACK", CLASSIC_BOLD_NAME, NORMAL_SIZE, Color.BLACK);
            case CLASSIC_BOLD_NORMAL_WHITE:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_BOLD_NORMAL_WHITE", CLASSIC_BOLD_NAME, NORMAL_SIZE, Color.WHITE);
            case CLASSIC_BOLD_NORMAL_YELLOW:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_BOLD_NORMAL_YELLOW", CLASSIC_BOLD_NAME, NORMAL_SIZE, Color.YELLOW);
            case CLASSIC_IT_NORMAL_BLACK:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_IT_NORMAL_BLACK", CLASSIC_IT_NAME, NORMAL_SIZE, Color.BLACK);
            case CLASSIC_IT_NORMAL_WHITE:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_IT_NORMAL_WHITE", CLASSIC_IT_NAME, NORMAL_SIZE, Color.WHITE);
            case CLASSIC_REG_BIG_BLACK:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_REG_NORMAL_BLACK", CLASSIC_REG_NAME, BIG_SIZE, Color.BLACK);
            case CLASSIC_REG_BIG_WHITE:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_REG_NORMAL_WHITE", CLASSIC_REG_NAME, BIG_SIZE, Color.WHITE);
            case CLASSIC_REG_NORMAL_BLACK:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_REG_NORMAL_BLACK", CLASSIC_REG_NAME, NORMAL_SIZE, Color.BLACK);
            case CLASSIC_REG_NORMAL_WHITE:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_REG_NORMAL_WHITE", CLASSIC_REG_NAME, NORMAL_SIZE, Color.WHITE);
            case CLASSIC_SANS_MIDDLE_BLACK:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_SANS_MIDDLE_BLACK", CLASSIC_SANS_NAME, MIDDLE_SIZE, Color.BLACK);
            case CLASSIC_SANS_SMALL_BLACK:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_SANS_SMALL_BLACK", CLASSIC_SANS_NAME, SMALL_SIZE, Color.BLACK);
            case CLASSIC_SANS_SMALL_WHITE:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_SANS_SMALL_WHITE", CLASSIC_SANS_NAME, SMALL_SIZE, Color.WHITE);
            case CLASSIC_SANS_NORMAL_BLACK:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_SANS_NORMAL_BLACK", CLASSIC_SANS_NAME, NORMAL_SIZE, Color.BLACK);
            case CLASSIC_SANS_NORMAL_WHITE:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_SANS_NORMAL_WHITE", CLASSIC_SANS_NAME, NORMAL_SIZE, Color.WHITE);
            case CLASSIC_SANS_NORMAL_GRAY:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_SANS_NORMAL_GRAY", CLASSIC_SANS_NAME, NORMAL_SIZE, Color.GRAY);
            case CLASSIC_SANS_NORMAL_YELLOW:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_SANS_NORMAL_YELLOW", CLASSIC_SANS_NAME, NORMAL_SIZE, Color.YELLOW);
            case AESTHETIC_SMALL_BLACK:
                return AssetsManager.getInstance().addStyleFont("AESTHETIC_SMALL_BLACK", AESTHETIC_NAME, SMALL_SIZE, Color.BLACK);
            case AESTHETIC_SMALL_WHITE:
                return AssetsManager.getInstance().addStyleFont("AESTHETIC_SMALL_WHITE", AESTHETIC_NAME, SMALL_SIZE, Color.WHITE);
            case AESTHETIC_NORMAL_BLACK:
                return AssetsManager.getInstance().addStyleFont("AESTHETIC_NORMAL_BLACK", AESTHETIC_NAME, NORMAL_SIZE, Color.BLACK);
            case AESTHETIC_NORMAL_WHITE:
                return AssetsManager.getInstance().addStyleFont("AESTHETIC_NORMAL_WHITE", AESTHETIC_NAME, NORMAL_SIZE, Color.WHITE);
            case AESTHETIC_TINY_BLACK:
                return AssetsManager.getInstance().addStyleFont("AESTHETIC_TINY_BLACK", AESTHETIC_NAME, TINY_SIZE, Color.BLACK);
                default:
                    return  getStyleFont(UseFont.CLASSIC_SANS_NORMAL_BLACK);
        }
    }

    public static TextButton.TextButtonStyle buildTextButtonStyle(final String path, final int size, final Color color) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/" + path));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = color;
        BitmapFont font = generator.generateFont(parameter);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        generator.dispose();
        return style;
    }
}
