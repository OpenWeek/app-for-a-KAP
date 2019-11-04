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

    /**
     * Get one of the preconfigured fonts
     * @param type the preconfigured font
     * @return the desired font or null if the type wasn't taken in charge
     */
    public static TextButton.TextButtonStyle getStyleFont(UsualFonts type) {
        switch (type) {
            case CLASSIC_BOLD_NORMAL_BLACK:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_BOLD_NORMAL_BLACK", CLASSIC_BOLD_NAME, 60, Color.BLACK);
            case CLASSIC_BOLD_NORMAL_WHITE:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_BOLD_NORMAL_WHITE", CLASSIC_BOLD_NAME, 60, Color.WHITE);
            case CLASSIC_BOLD_NORMAL_YELLOW:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_BOLD_NORMAL_YELLOW", CLASSIC_BOLD_NAME, 60, Color.YELLOW);
            case CLASSIC_IT_NORMAL_BLACK:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_IT_NORMAL_BLACK", CLASSIC_IT_NAME, 60, Color.BLACK);
            case CLASSIC_IT_NORMAL_WHITE:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_IT_NORMAL_WHITE", CLASSIC_IT_NAME, 60, Color.WHITE);
            case CLASSIC_REG_NORMAL_BLACK:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_REG_NORMAL_BLACK", CLASSIC_REG_NAME, 60, Color.BLACK);
            case CLASSIC_REG_NORMAL_WHITE:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_REG_NORMAL_WHITE", CLASSIC_REG_NAME, 60, Color.WHITE);
            case CLASSIC_SANS_SMALL_BLACK:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_SANS_SMALL_BLACK", CLASSIC_SANS_NAME, 38, Color.BLACK);
            case CLASSIC_SANS_SMALL_WHITE:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_SANS_SMALL_WHITE", CLASSIC_SANS_NAME, 38, Color.WHITE);
            case CLASSIC_SANS_NORMAL_BLACK:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_SANS_NORMAL_BLACK", CLASSIC_SANS_NAME, 60, Color.BLACK);
            case CLASSIC_SANS_NORMAL_WHITE:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_SANS_NORMAL_WHITE", CLASSIC_SANS_NAME, 60, Color.WHITE);
            case CLASSIC_SANS_NORMAL_GRAY:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_SANS_NORMAL_GRAY", CLASSIC_SANS_NAME, 60, Color.GRAY);
            case CLASSIC_SANS_NORMAL_YELLOW:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_SANS_NORMAL_YELLOW", CLASSIC_SANS_NAME, 60, Color.YELLOW);
            case AESTHETIC_SMALL_BLACK:
                return AssetsManager.getInstance().addStyleFont("AESTHETIC_SMALL_BLACK", AESTHETIC_NAME, 38, Color.BLACK);
            case AESTHETIC_SMALL_WHITE:
                return AssetsManager.getInstance().addStyleFont("AESTHETIC_SMALL_WHITE", AESTHETIC_NAME, 38, Color.WHITE);
            case AESTHETIC_NORMAL_BLACK:
                return AssetsManager.getInstance().addStyleFont("AESTHETIC_NORMAL_BLACK", AESTHETIC_NAME, 60, Color.BLACK);
            case AESTHETIC_NORMAL_WHITE:
                return AssetsManager.getInstance().addStyleFont("AESTHETIC_NORMAL_WHITE", AESTHETIC_NAME, 60, Color.WHITE);
            case AESTHETIC_TINY_BLACK:
                return AssetsManager.getInstance().addStyleFont("AESTHETIC_TINY_BLACK", AESTHETIC_NAME, 20, Color.BLACK);
                default:
                    return  getStyleFont(UsualFonts.CLASSIC_SANS_NORMAL_BLACK);
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
