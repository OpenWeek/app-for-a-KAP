package gdx.kapotopia.Fonts;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Wrapper class for a font object. Contains a TextButtonStyle, and the different characteristics of the font
 */
public class Font {
    private AssetDescriptor<BitmapFont> font;
    private String name;
    private FontType fontType;
    private FontFamily fontFamily;
    private int sizeRaw;
    private FontSize size;
    private Color color;

    public Font(AssetDescriptor<BitmapFont> font, String name, FontType fontType, FontFamily fontFamily, FontSize size, Color color) {
        this.font = font;
        this.name = name;
        this.fontType = fontType;
        this.fontFamily = fontFamily;
        this.sizeRaw = FontSize.getRawSize(size);
        this.size = size;
        this.color = color;
    }

    public AssetDescriptor<BitmapFont> getFont() {
        return font;
    }

    public String getName() {
        return name;
    }

    public int getSizeRaw() {
        return sizeRaw;
    }

    public FontSize getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    public FontType getFontType() {
        return fontType;
    }

    public FontFamily getFontFamily() {
        return fontFamily;
    }
}
