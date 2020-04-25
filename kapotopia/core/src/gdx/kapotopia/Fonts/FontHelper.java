package gdx.kapotopia.Fonts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import gdx.kapotopia.AssetsManaging.AssetsManager;

public class FontHelper {

    //TODO watch out for big fonts not doing good https://github.com/libgdx/libgdx/wiki/Distance-field-fonts
    private static final String CLASSIC_BOLD_NAME = "COMMSB.ttf";
    private static final String CLASSIC_IT_NAME = "COMMI.ttf";
    private static final String CLASSIC_REG_NAME = "COMMUNIS.ttf";
    private static final String CLASSIC_SANS_NAME = "COMMS.ttf";
    private static final String AESTHETIC_NAME = "SEASRN__.ttf";

    /* **************************************************************
     *      S T A T I C   R E F E R E N C E S   T O   F O N T S
     ************************************************************** */
    /* CLASSIC */
    public static Font CLASSIC_BOLD_NORMAL_BLACK;
    public static Font CLASSIC_BOLD_NORMAL_WHITE;
    public static Font CLASSIC_BOLD_NORMAL_YELLOW;
    public static Font CLASSIC_BOLD_BIG_BLACK;
    public static Font CLASSIC_IT_NORMAL_BLACK;
    public static Font CLASSIC_IT_NORMAL_WHITE;
    public static Font CLASSIC_REG_BIG_BLACK;
    public static Font CLASSIC_REG_BIG_WHITE;
    public static Font CLASSIC_REG_NORMAL_BLACK;
    public static Font CLASSIC_REG_NORMAL_WHITE;
    public static Font CLASSIC_SANS_MIDDLE_BLACK;
    public static Font CLASSIC_SANS_SMALL_BLACK;
    public static Font CLASSIC_SANS_SMALL_WHITE;
    public static Font CLASSIC_SANS_NORMAL_BLACK;
    public static Font CLASSIC_SANS_NORMAL_WHITE;
    public static Font CLASSIC_SANS_NORMAL_GRAY;
    public static Font CLASSIC_SANS_NORMAL_YELLOW;
    /* AESTHETIC */
    public static Font AESTHETIC_SMALL_BLACK;
    public static Font AESTHETIC_SMALL_WHITE;
    public static Font AESTHETIC_NORMAL_BLACK;
    public static Font AESTHETIC_NORMAL_WHITE;
    public static Font AESTHETIC_TINY_BLACK;

    private static boolean fontsLoaded = false;

    /**
     * SHOULD ONLY BE CALLED ONCE AT THE START OF THE APPq
     * @param ass
     */
    public static void buildAllFonts(AssetManager ass) {
        if (fontsLoaded) return;
        else fontsLoaded = true;

        final String bold = "fonts/" + CLASSIC_BOLD_NAME;
        final String italic = "fonts/" + CLASSIC_IT_NAME;
        final String sans = "fonts/" + CLASSIC_SANS_NAME;
        final String reg = "fonts/" + CLASSIC_REG_NAME;
        final String aesthetic = "fonts/" + AESTHETIC_NAME;

        final int tiny = FontSize.getRawSize(FontSize.TINY);
        final int small = FontSize.getRawSize(FontSize.SMALL);
        final int middle = FontSize.getRawSize(FontSize.MIDDLE);
        final int normal = FontSize.getRawSize(FontSize.NORMAL);
        final int big = FontSize.getRawSize(FontSize.BIG);
        final Color white = Color.WHITE;
        final Color black = Color.BLACK;
        final Color yellow = Color.YELLOW;
        final Color gray = Color.GRAY;

        // CLASSIC_BOLD_NORMAL_BLACK
        FreetypeFontLoader.FreeTypeFontLoaderParameter classicBoldNormalBlackFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = bold;
        classicBoldNormalBlackFont.fontParameters.size = normal;
        classicBoldNormalBlackFont.fontParameters.color = black;
        CLASSIC_BOLD_NORMAL_BLACK = new Font(new AssetDescriptor<BitmapFont>("CLASSIC_BOLD_NORMAL_BLACK.ttf", BitmapFont.class, classicBoldNormalBlackFont),
                "CLASSIC_BOLD_NORMAL_BLACK", FontType.CLASSIC, FontFamily.BOLD, FontSize.NORMAL, black);
        ass.load(CLASSIC_BOLD_NORMAL_BLACK.getFont());

        // CLASSIC_BOLD_NORMAL_WHITE
        FreetypeFontLoader.FreeTypeFontLoaderParameter classicBoldNormalWhiteFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = bold;
        classicBoldNormalBlackFont.fontParameters.size = normal;
        classicBoldNormalBlackFont.fontParameters.color = white;
        CLASSIC_BOLD_NORMAL_WHITE = new Font(new AssetDescriptor<BitmapFont>("CLASSIC_BOLD_NORMAL_WHITE.ttf", BitmapFont.class, classicBoldNormalWhiteFont),
                "CLASSIC_BOLD_NORMAL_WHITE", FontType.CLASSIC, FontFamily.BOLD, FontSize.NORMAL, white);
        ass.load(CLASSIC_BOLD_NORMAL_WHITE.getFont());

        // CLASSIC_BOLD_NORMAL_YELLOW
        FreetypeFontLoader.FreeTypeFontLoaderParameter classicBoldNormalYellowFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = bold;
        classicBoldNormalBlackFont.fontParameters.size = normal;
        classicBoldNormalBlackFont.fontParameters.color = yellow;
        CLASSIC_BOLD_NORMAL_YELLOW = new Font(new AssetDescriptor<BitmapFont>("CLASSIC_BOLD_NORMAL_YELLOW.ttf", BitmapFont.class, classicBoldNormalYellowFont),
                "CLASSIC_BOLD_NORMAL_YELLOW", FontType.CLASSIC, FontFamily.BOLD, FontSize.NORMAL, yellow);
        ass.load(CLASSIC_BOLD_NORMAL_YELLOW.getFont());

        // CLASSIC_BOLD_BIG_BLACK
        FreetypeFontLoader.FreeTypeFontLoaderParameter classicBoldBigBlackFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = bold;
        classicBoldNormalBlackFont.fontParameters.size = big;
        classicBoldNormalBlackFont.fontParameters.color = black;
        CLASSIC_BOLD_BIG_BLACK = new Font(new AssetDescriptor<BitmapFont>("CLASSIC_BOLD_BIG_BLACK.ttf", BitmapFont.class, classicBoldBigBlackFont),
                "CLASSIC_BOLD_BIG_BLACK", FontType.CLASSIC, FontFamily.BOLD, FontSize.BIG, black);
        ass.load(CLASSIC_BOLD_BIG_BLACK.getFont());

        // CLASSIC_IT_NORMAL_BLACK
        FreetypeFontLoader.FreeTypeFontLoaderParameter classicItalicNormalBlackFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = italic;
        classicBoldNormalBlackFont.fontParameters.size = normal;
        classicBoldNormalBlackFont.fontParameters.color = black;
        CLASSIC_IT_NORMAL_BLACK = new Font(new AssetDescriptor<BitmapFont>("CLASSIC_IT_NORMAL_BLACK.ttf", BitmapFont.class, classicItalicNormalBlackFont),
                "CLASSIC_IT_NORMAL_BLACK", FontType.CLASSIC, FontFamily.IT, FontSize.NORMAL, black);
        ass.load(CLASSIC_IT_NORMAL_BLACK.getFont());

        // CLASSIC_IT_NORMAL_WHITE
        FreetypeFontLoader.FreeTypeFontLoaderParameter classicItalicNormalWhiteFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = italic;
        classicBoldNormalBlackFont.fontParameters.size = normal;
        classicBoldNormalBlackFont.fontParameters.color = white;
        CLASSIC_IT_NORMAL_WHITE = new Font(new AssetDescriptor<BitmapFont>("CLASSIC_IT_NORMAL_WHITE.ttf", BitmapFont.class, classicItalicNormalWhiteFont),
                "CLASSIC_IT_NORMAL_WHITE", FontType.CLASSIC, FontFamily.IT, FontSize.NORMAL, white);
        ass.load(CLASSIC_IT_NORMAL_WHITE.getFont());

        // CLASSIC_REG_BIG_BLACK
        FreetypeFontLoader.FreeTypeFontLoaderParameter classicRegBigBlackFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = reg;
        classicBoldNormalBlackFont.fontParameters.size = big;
        classicBoldNormalBlackFont.fontParameters.color = black;
        CLASSIC_REG_BIG_BLACK = new Font(new AssetDescriptor<BitmapFont>("CLASSIC_REG_BIG_BLACK.ttf", BitmapFont.class, classicRegBigBlackFont),
                "CLASSIC_REG_BIG_BLACK", FontType.CLASSIC, FontFamily.REG, FontSize.BIG, black);
        ass.load(CLASSIC_REG_BIG_BLACK.getFont());

        // CLASSIC_REG_BIG_WHITE
        FreetypeFontLoader.FreeTypeFontLoaderParameter classicRegBigWhiteFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = reg;
        classicBoldNormalBlackFont.fontParameters.size = big;
        classicBoldNormalBlackFont.fontParameters.color = white;
        CLASSIC_REG_BIG_WHITE = new Font(new AssetDescriptor<BitmapFont>("CLASSIC_REG_BIG_WHITE.ttf", BitmapFont.class, classicRegBigWhiteFont),
                "CLASSIC_REG_BIG_WHITE", FontType.CLASSIC, FontFamily.REG, FontSize.BIG, white);
        ass.load(CLASSIC_REG_BIG_WHITE.getFont());

        // CLASSIC_REG_NORMAL_BLACK
        FreetypeFontLoader.FreeTypeFontLoaderParameter classicRegNormalBlackFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = reg;
        classicBoldNormalBlackFont.fontParameters.size = normal;
        classicBoldNormalBlackFont.fontParameters.color = black;
        CLASSIC_REG_NORMAL_BLACK = new Font(new AssetDescriptor<BitmapFont>("CLASSIC_REG_NORMAL_BLACK.ttf", BitmapFont.class, classicRegNormalBlackFont),
                "CLASSIC_REG_NORMAL_BLACK", FontType.CLASSIC, FontFamily.REG, FontSize.NORMAL, black);
        ass.load(CLASSIC_REG_NORMAL_BLACK.getFont());

        // CLASSIC_REG_NORMAL_WHITE
        FreetypeFontLoader.FreeTypeFontLoaderParameter classicRegNormalWhiteFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = reg;
        classicBoldNormalBlackFont.fontParameters.size = normal;
        classicBoldNormalBlackFont.fontParameters.color = white;
        CLASSIC_REG_NORMAL_WHITE = new Font(new AssetDescriptor<BitmapFont>("CLASSIC_REG_NORMAL_WHITE.ttf", BitmapFont.class, classicRegNormalWhiteFont),
                "CLASSIC_REG_NORMAL_WHITE", FontType.CLASSIC, FontFamily.REG, FontSize.NORMAL, white);
        ass.load(CLASSIC_REG_NORMAL_WHITE.getFont());

        // CLASSIC_SANS_MIDDLE_BLACK
        FreetypeFontLoader.FreeTypeFontLoaderParameter classicSansMiddleBlackFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = sans;
        classicBoldNormalBlackFont.fontParameters.size = middle;
        classicBoldNormalBlackFont.fontParameters.color = black;
        CLASSIC_SANS_MIDDLE_BLACK = new Font(new AssetDescriptor<BitmapFont>("CLASSIC_SANS_MIDDLE_BLACK.ttf", BitmapFont.class, classicSansMiddleBlackFont),
                "CLASSIC_SANS_MIDDLE_BLACK", FontType.CLASSIC, FontFamily.SANS, FontSize.MIDDLE, black);
        ass.load(CLASSIC_SANS_MIDDLE_BLACK.getFont());

        // CLASSIC_SANS_SMALL_BLACK
        FreetypeFontLoader.FreeTypeFontLoaderParameter classicSansSmallBlackFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = sans;
        classicBoldNormalBlackFont.fontParameters.size = small;
        classicBoldNormalBlackFont.fontParameters.color = black;
        CLASSIC_SANS_SMALL_BLACK = new Font(new AssetDescriptor<BitmapFont>("CLASSIC_SANS_SMALL_BLACK.ttf", BitmapFont.class, classicSansSmallBlackFont),
                "CLASSIC_SANS_SMALL_BLACK", FontType.CLASSIC, FontFamily.SANS, FontSize.SMALL, black);
        ass.load(CLASSIC_SANS_SMALL_BLACK.getFont());

        // CLASSIC_SANS_SMALL_WHITE
        FreetypeFontLoader.FreeTypeFontLoaderParameter classicSansSmallWhiteFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = sans;
        classicBoldNormalBlackFont.fontParameters.size = small;
        classicBoldNormalBlackFont.fontParameters.color = white;
        CLASSIC_SANS_SMALL_WHITE = new Font(new AssetDescriptor<BitmapFont>("CLASSIC_SANS_SMALL_WHITE.ttf", BitmapFont.class, classicSansSmallWhiteFont),
                "CLASSIC_SANS_SMALL_WHITE", FontType.CLASSIC, FontFamily.SANS, FontSize.SMALL, white);
        ass.load(CLASSIC_SANS_SMALL_WHITE.getFont());

        // CLASSIC_SANS_NORMAL_BLACK
        FreetypeFontLoader.FreeTypeFontLoaderParameter classicSansNormalBlackFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = sans;
        classicBoldNormalBlackFont.fontParameters.size = normal;
        classicBoldNormalBlackFont.fontParameters.color = black;
        CLASSIC_SANS_NORMAL_BLACK = new Font(new AssetDescriptor<BitmapFont>("CLASSIC_SANS_NORMAL_BLACK.ttf", BitmapFont.class, classicSansNormalBlackFont),
                "CLASSIC_SANS_NORMAL_BLACK", FontType.CLASSIC, FontFamily.SANS, FontSize.NORMAL, black);
        ass.load(CLASSIC_SANS_NORMAL_BLACK.getFont());

        // CLASSIC_SANS_NORMAL_WHITE
        FreetypeFontLoader.FreeTypeFontLoaderParameter classicSansNormalWhiteFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = sans;
        classicBoldNormalBlackFont.fontParameters.size = normal;
        classicBoldNormalBlackFont.fontParameters.color = white;
        CLASSIC_SANS_NORMAL_WHITE = new Font(new AssetDescriptor<BitmapFont>("CLASSIC_SANS_NORMAL_WHITE.ttf", BitmapFont.class, classicSansNormalWhiteFont),
                "CLASSIC_SANS_NORMAL_WHITE", FontType.CLASSIC, FontFamily.SANS, FontSize.NORMAL, white);
        ass.load(CLASSIC_SANS_NORMAL_WHITE.getFont());

        // CLASSIC_SANS_NORMAL_GRAY
        FreetypeFontLoader.FreeTypeFontLoaderParameter classicSansNormalGrayFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = sans;
        classicBoldNormalBlackFont.fontParameters.size = normal;
        classicBoldNormalBlackFont.fontParameters.color = gray;
        CLASSIC_SANS_NORMAL_GRAY = new Font(new AssetDescriptor<BitmapFont>("CLASSIC_SANS_NORMAL_GRAY.ttf", BitmapFont.class, classicSansNormalGrayFont),
                "CLASSIC_SANS_NORMAL_GRAY", FontType.CLASSIC, FontFamily.SANS, FontSize.NORMAL, gray);
        ass.load(CLASSIC_SANS_NORMAL_GRAY.getFont());

        // CLASSIC_SANS_NORMAL_YELLOW
        FreetypeFontLoader.FreeTypeFontLoaderParameter classicSansNormalYellowFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = sans;
        classicBoldNormalBlackFont.fontParameters.size = normal;
        classicBoldNormalBlackFont.fontParameters.color = yellow;
        CLASSIC_SANS_NORMAL_YELLOW = new Font(new AssetDescriptor<BitmapFont>("CLASSIC_SANS_NORMAL_YELLOW.ttf", BitmapFont.class, classicSansNormalYellowFont),
                "CLASSIC_SANS_NORMAL_YELLOW", FontType.CLASSIC, FontFamily.SANS, FontSize.NORMAL, yellow);
        ass.load(CLASSIC_SANS_NORMAL_YELLOW.getFont());

        // AESTHETIC_SMALL_BLACK
        FreetypeFontLoader.FreeTypeFontLoaderParameter aestheticSmallBlackFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = aesthetic;
        classicBoldNormalBlackFont.fontParameters.size = small;
        classicBoldNormalBlackFont.fontParameters.color = black;
        CLASSIC_BOLD_NORMAL_BLACK = new Font(new AssetDescriptor<BitmapFont>("AESTHETIC_SMALL_BLACK.ttf", BitmapFont.class, aestheticSmallBlackFont),
                "CLASSIC_BOLD_NORMAL_BLACK", FontType.AESTHETIC, FontFamily.REG, FontSize.SMALL, black);
        ass.load(AESTHETIC_SMALL_BLACK.getFont());

        // AESTHETIC_SMALL_WHITE
        FreetypeFontLoader.FreeTypeFontLoaderParameter aestheticSmallWhiteFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = aesthetic;
        classicBoldNormalBlackFont.fontParameters.size = small;
        classicBoldNormalBlackFont.fontParameters.color = white;
        AESTHETIC_SMALL_WHITE = new Font(new AssetDescriptor<BitmapFont>("AESTHETIC_SMALL_WHITE.ttf", BitmapFont.class, aestheticSmallWhiteFont),
                "AESTHETIC_SMALL_WHITE", FontType.AESTHETIC, FontFamily.REG, FontSize.SMALL, white);
        ass.load(AESTHETIC_SMALL_WHITE.getFont());

        // AESTHETIC_NORMAL_BLACK
        FreetypeFontLoader.FreeTypeFontLoaderParameter aestheticNormalBlackFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = aesthetic;
        classicBoldNormalBlackFont.fontParameters.size = normal;
        classicBoldNormalBlackFont.fontParameters.color = black;
        AESTHETIC_NORMAL_BLACK = new Font(new AssetDescriptor<BitmapFont>("AESTHETIC_NORMAL_BLACK.ttf", BitmapFont.class, aestheticNormalBlackFont),
                "AESTHETIC_NORMAL_BLACK", FontType.AESTHETIC, FontFamily.REG, FontSize.NORMAL, black);
        ass.load(AESTHETIC_NORMAL_BLACK.getFont());

        // AESTHETIC_NORMAL_WHITE
        FreetypeFontLoader.FreeTypeFontLoaderParameter aestheticNormalWhiteFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = aesthetic;
        classicBoldNormalBlackFont.fontParameters.size = normal;
        classicBoldNormalBlackFont.fontParameters.color = white;
        AESTHETIC_NORMAL_WHITE = new Font(new AssetDescriptor<BitmapFont>("AESTHETIC_NORMAL_WHITE.ttf", BitmapFont.class, aestheticNormalWhiteFont),
                "AESTHETIC_NORMAL_WHITE", FontType.AESTHETIC, FontFamily.REG, FontSize.NORMAL, white);
        ass.load(AESTHETIC_NORMAL_WHITE.getFont());

        // AESTHETIC_TINY_BLACK
        FreetypeFontLoader.FreeTypeFontLoaderParameter aestheticTinyBlackFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        classicBoldNormalBlackFont.fontFileName = aesthetic;
        classicBoldNormalBlackFont.fontParameters.size = tiny;
        classicBoldNormalBlackFont.fontParameters.color = black;
        AESTHETIC_TINY_BLACK = new Font(new AssetDescriptor<BitmapFont>("AESTHETIC_TINY_BLACK.ttf", BitmapFont.class, aestheticTinyBlackFont),
                "AESTHETIC_TINY_BLACK", FontType.AESTHETIC, FontFamily.REG, FontSize.TINY, black);
        ass.load(AESTHETIC_TINY_BLACK.getFont());

    }
/*
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

 */
}
