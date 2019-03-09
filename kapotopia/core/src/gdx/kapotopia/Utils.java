package gdx.kapotopia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Utils {
    public static TextButton.TextButtonStyle getStyleFont(String path) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SEASRN__.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        parameter.color = Color.BLACK;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font12;
        generator.dispose();
        return style;
    }
}
