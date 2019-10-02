package gdx.kapotopia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Utils {
<<<<<<< HEAD
    private static TextButton.TextButtonStyle style = null;

    public static TextButton.TextButtonStyle getStyleFont(String path) {
        if (style == null) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SEASRN__.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 60;
            parameter.color = Color.BLACK;
            BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
            style = new TextButton.TextButtonStyle();
            style.font = font12;
            generator.dispose();
        }
=======
    public static TextButton.TextButtonStyle getStyleFont(final String path) {
        return buildTextButtonStyle(path, 60);
    }

    public static TextButton.TextButtonStyle getStyleFont(final String path, final int size) {
        return buildTextButtonStyle(path, size);
    }

    private static TextButton.TextButtonStyle buildTextButtonStyle(final String path, final int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(path));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = Color.BLACK;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font12;
        generator.dispose();
>>>>>>> 1ed356c98204d9fcf0d14c3cd101e091134b4294
        return style;
    }
}
