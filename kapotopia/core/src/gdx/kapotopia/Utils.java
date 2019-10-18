package gdx.kapotopia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Utils {
    public static TextButton.TextButtonStyle getStyleFont(final String path) {
        return buildTextButtonStyle(path, 60, Color.BLACK);
    }

    public static TextButton.TextButtonStyle getStyleFont(final String path, final int size) {
        return buildTextButtonStyle(path, size, Color.BLACK);
    }

    public static TextButton.TextButtonStyle getStyleFont(final String path, final int size, final Color color) {
        return buildTextButtonStyle(path, size, color);
    }

    private static TextButton.TextButtonStyle buildTextButtonStyle(final String path, final int size, final Color color) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/" + path));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = color;
        BitmapFont font = generator.generateFont(parameter); // font size 12 pixels
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        generator.dispose();
        return style;
    }
}
