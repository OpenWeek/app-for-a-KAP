package gdx.kapotopia.Fonts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import gdx.kapotopia.AssetsManaging.AssetsManager;

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
    public static TextButton.TextButtonStyle getStyleFont(UseFont type) {
        switch (type) {
            case CLASSIC_BOLD_NORMAL_BLACK:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_BOLD_NORMAL_BLACK", CLASSIC_BOLD_NAME, FontSize.getRawSize(FontSize.NORMAL), Color.BLACK);
            case CLASSIC_BOLD_NORMAL_WHITE:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_BOLD_NORMAL_WHITE", CLASSIC_BOLD_NAME, FontSize.getRawSize(FontSize.NORMAL), Color.WHITE);
            case CLASSIC_BOLD_NORMAL_YELLOW:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_BOLD_NORMAL_YELLOW", CLASSIC_BOLD_NAME, FontSize.getRawSize(FontSize.NORMAL), Color.YELLOW);
            case CLASSIC_BOLD_BIG_BLACK:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_BOLD_BIG_BLACK", CLASSIC_BOLD_NAME, FontSize.getRawSize(FontSize.BIG), Color.BLACK);
            case CLASSIC_IT_NORMAL_BLACK:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_IT_NORMAL_BLACK", CLASSIC_IT_NAME, FontSize.getRawSize(FontSize.NORMAL), Color.BLACK);
            case CLASSIC_IT_NORMAL_WHITE:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_IT_NORMAL_WHITE", CLASSIC_IT_NAME, FontSize.getRawSize(FontSize.NORMAL), Color.WHITE);
            case CLASSIC_REG_BIG_BLACK:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_REG_NORMAL_BLACK", CLASSIC_REG_NAME, FontSize.getRawSize(FontSize.BIG), Color.BLACK);
            case CLASSIC_REG_BIG_WHITE:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_REG_NORMAL_WHITE", CLASSIC_REG_NAME, FontSize.getRawSize(FontSize.BIG), Color.WHITE);
            case CLASSIC_REG_NORMAL_BLACK:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_REG_NORMAL_BLACK", CLASSIC_REG_NAME, FontSize.getRawSize(FontSize.NORMAL), Color.BLACK);
            case CLASSIC_REG_NORMAL_WHITE:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_REG_NORMAL_WHITE", CLASSIC_REG_NAME, FontSize.getRawSize(FontSize.NORMAL), Color.WHITE);
            case CLASSIC_SANS_MIDDLE_BLACK:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_SANS_MIDDLE_BLACK", CLASSIC_SANS_NAME, FontSize.getRawSize(FontSize.MIDDLE), Color.BLACK);
            case CLASSIC_SANS_SMALL_BLACK:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_SANS_SMALL_BLACK", CLASSIC_SANS_NAME, FontSize.getRawSize(FontSize.SMALL), Color.BLACK);
            case CLASSIC_SANS_SMALL_WHITE:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_SANS_SMALL_WHITE", CLASSIC_SANS_NAME, FontSize.getRawSize(FontSize.SMALL), Color.WHITE);
            case CLASSIC_SANS_NORMAL_BLACK:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_SANS_NORMAL_BLACK", CLASSIC_SANS_NAME, FontSize.getRawSize(FontSize.NORMAL), Color.BLACK);
            case CLASSIC_SANS_NORMAL_WHITE:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_SANS_NORMAL_WHITE", CLASSIC_SANS_NAME, FontSize.getRawSize(FontSize.NORMAL), Color.WHITE);
            case CLASSIC_SANS_NORMAL_GRAY:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_SANS_NORMAL_GRAY", CLASSIC_SANS_NAME, FontSize.getRawSize(FontSize.NORMAL), Color.GRAY);
            case CLASSIC_SANS_NORMAL_YELLOW:
                return AssetsManager.getInstance().addStyleFont("CLASSIC_SANS_NORMAL_YELLOW", CLASSIC_SANS_NAME, FontSize.getRawSize(FontSize.NORMAL), Color.YELLOW);
            case AESTHETIC_SMALL_BLACK:
                return AssetsManager.getInstance().addStyleFont("AESTHETIC_SMALL_BLACK", AESTHETIC_NAME, FontSize.getRawSize(FontSize.SMALL), Color.BLACK);
            case AESTHETIC_SMALL_WHITE:
                return AssetsManager.getInstance().addStyleFont("AESTHETIC_SMALL_WHITE", AESTHETIC_NAME, FontSize.getRawSize(FontSize.SMALL), Color.WHITE);
            case AESTHETIC_NORMAL_BLACK:
                return AssetsManager.getInstance().addStyleFont("AESTHETIC_NORMAL_BLACK", AESTHETIC_NAME, FontSize.getRawSize(FontSize.NORMAL), Color.BLACK);
            case AESTHETIC_NORMAL_WHITE:
                return AssetsManager.getInstance().addStyleFont("AESTHETIC_NORMAL_WHITE", AESTHETIC_NAME, FontSize.getRawSize(FontSize.NORMAL), Color.WHITE);
            case AESTHETIC_TINY_BLACK:
                return AssetsManager.getInstance().addStyleFont("AESTHETIC_TINY_BLACK", AESTHETIC_NAME, FontSize.getRawSize(FontSize.TINY), Color.BLACK);
                default:
                    return getStyleFont(UseFont.CLASSIC_SANS_NORMAL_BLACK);
        }
    }

    public static gdx.kapotopia.Fonts.Font getFont(UseFont type) {
        TextButton.TextButtonStyle style = getStyleFont(type);
        switch (type) {
            case CLASSIC_BOLD_NORMAL_BLACK:
                return new gdx.kapotopia.Fonts.Font(type, "CLASSIC_BOLD_NORMAL_WHITE", FontType.CLASSIC, gdx.kapotopia.Fonts.FontFamily.BOLD, FontSize.NORMAL, Color.BLACK, style);
            case CLASSIC_BOLD_NORMAL_WHITE:
                return new gdx.kapotopia.Fonts.Font(type, "CLASSIC_BOLD_NORMAL_WHITE", FontType.CLASSIC, gdx.kapotopia.Fonts.FontFamily.BOLD, FontSize.NORMAL, Color.WHITE, style);
            case CLASSIC_BOLD_NORMAL_YELLOW:
                return new gdx.kapotopia.Fonts.Font(type, "CLASSIC_BOLD_NORMAL_YELLOW", FontType.CLASSIC, gdx.kapotopia.Fonts.FontFamily.BOLD, FontSize.NORMAL, Color.YELLOW, style);
            case CLASSIC_BOLD_BIG_BLACK:
                return new gdx.kapotopia.Fonts.Font(type, "CLASSIC_BOLD_NORMAL_WHITE", FontType.CLASSIC, gdx.kapotopia.Fonts.FontFamily.BOLD, FontSize.BIG, Color.BLACK, style);
            case CLASSIC_IT_NORMAL_BLACK:
                return new gdx.kapotopia.Fonts.Font(type, "CLASSIC_IT_NORMAL_BLACK", FontType.CLASSIC, gdx.kapotopia.Fonts.FontFamily.IT, FontSize.NORMAL, Color.BLACK, style);
            case CLASSIC_IT_NORMAL_WHITE:
                return new gdx.kapotopia.Fonts.Font(type, "CLASSIC_IT_NORMAL_WHITE", FontType.CLASSIC, gdx.kapotopia.Fonts.FontFamily.IT, FontSize.NORMAL, Color.WHITE, style);
            case CLASSIC_REG_BIG_BLACK:
                return new gdx.kapotopia.Fonts.Font(type, "CLASSIC_REG_NORMAL_BLACK", FontType.CLASSIC, gdx.kapotopia.Fonts.FontFamily.REG, FontSize.BIG, Color.BLACK, style);
            case CLASSIC_REG_BIG_WHITE:
                return new gdx.kapotopia.Fonts.Font(type, "CLASSIC_REG_NORMAL_WHITE", FontType.CLASSIC, gdx.kapotopia.Fonts.FontFamily.REG, FontSize.BIG, Color.WHITE, style);
            case CLASSIC_REG_NORMAL_BLACK:
                return new gdx.kapotopia.Fonts.Font(type, "CLASSIC_REG_NORMAL_BLACK", FontType.CLASSIC, gdx.kapotopia.Fonts.FontFamily.REG, FontSize.NORMAL, Color.BLACK, style);
            case CLASSIC_REG_NORMAL_WHITE:
                return new gdx.kapotopia.Fonts.Font(type, "CLASSIC_REG_NORMAL_WHITE", FontType.CLASSIC, gdx.kapotopia.Fonts.FontFamily.REG, FontSize.NORMAL, Color.WHITE, style);
            case CLASSIC_SANS_MIDDLE_BLACK:
                return new gdx.kapotopia.Fonts.Font(type, "CLASSIC_BOLD_NORMAL_WHITE", FontType.CLASSIC, gdx.kapotopia.Fonts.FontFamily.REG, FontSize.MIDDLE, Color.BLACK, style);
            case CLASSIC_SANS_SMALL_BLACK:
                return new gdx.kapotopia.Fonts.Font(type, "CLASSIC_SANS_SMALL_BLACK", FontType.CLASSIC, gdx.kapotopia.Fonts.FontFamily.SANS, FontSize.SMALL, Color.BLACK, style);
            case CLASSIC_SANS_SMALL_WHITE:
                return new gdx.kapotopia.Fonts.Font(type, "CLASSIC_SANS_SMALL_WHITE", FontType.CLASSIC, gdx.kapotopia.Fonts.FontFamily.SANS, FontSize.SMALL, Color.WHITE, style);
            case CLASSIC_SANS_NORMAL_BLACK:
                return new gdx.kapotopia.Fonts.Font(type, "CLASSIC_SANS_NORMAL_BLACK", FontType.CLASSIC, gdx.kapotopia.Fonts.FontFamily.SANS, FontSize.NORMAL, Color.BLACK, style);
            case CLASSIC_SANS_NORMAL_WHITE:
                return new gdx.kapotopia.Fonts.Font(type, "CLASSIC_SANS_NORMAL_WHITE", FontType.CLASSIC, gdx.kapotopia.Fonts.FontFamily.SANS, FontSize.NORMAL, Color.WHITE, style);
            case CLASSIC_SANS_NORMAL_GRAY:
                return new gdx.kapotopia.Fonts.Font(type, "CLASSIC_SANS_NORMAL_GRAY", FontType.CLASSIC, gdx.kapotopia.Fonts.FontFamily.SANS, FontSize.NORMAL, Color.GRAY, style);
            case CLASSIC_SANS_NORMAL_YELLOW:
                return new gdx.kapotopia.Fonts.Font(type, "CLASSIC_SANS_NORMAL_YELLOW", FontType.CLASSIC, gdx.kapotopia.Fonts.FontFamily.SANS, FontSize.NORMAL, Color.YELLOW, style);
            case AESTHETIC_SMALL_BLACK:
                return new gdx.kapotopia.Fonts.Font(type, "AESTHETIC_SMALL_BLACK", FontType.AESTHETIC, gdx.kapotopia.Fonts.FontFamily.REG, FontSize.SMALL, Color.BLACK, style);
            case AESTHETIC_SMALL_WHITE:
                return new gdx.kapotopia.Fonts.Font(type, "CLASSIC_BOLD_NORMAL_WHITE", FontType.AESTHETIC, gdx.kapotopia.Fonts.FontFamily.REG, FontSize.SMALL, Color.WHITE, style);
            case AESTHETIC_NORMAL_BLACK:
                return new gdx.kapotopia.Fonts.Font(type, "AESTHETIC_NORMAL_BLACK", FontType.AESTHETIC, gdx.kapotopia.Fonts.FontFamily.REG, FontSize.NORMAL, Color.BLACK, style);
            case AESTHETIC_NORMAL_WHITE:
                return new gdx.kapotopia.Fonts.Font(type, "AESTHETIC_NORMAL_WHITE", FontType.AESTHETIC, gdx.kapotopia.Fonts.FontFamily.REG, FontSize.NORMAL, Color.WHITE, style);
            case AESTHETIC_TINY_BLACK:
                return new gdx.kapotopia.Fonts.Font(type, "AESTHETIC_TINY_BLACK", FontType.AESTHETIC, gdx.kapotopia.Fonts.FontFamily.REG, FontSize.TINY, Color.BLACK, style);
            default:
                return new Font(type, "CLASSIC_SANS_NORMAL_BLACK", FontType.CLASSIC, FontFamily.SANS, FontSize.NORMAL, Color.BLACK, style);
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
