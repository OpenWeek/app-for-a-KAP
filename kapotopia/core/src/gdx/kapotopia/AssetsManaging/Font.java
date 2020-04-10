package gdx.kapotopia.AssetsManaging;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Font {
    private UseFont label;
    private String name;
    private FontType fontType;
    private FontFamily fontFamily;
    private int sizeRaw;
    private FontSize size;
    private Color color;
    private TextButton.TextButtonStyle style;

    public Font(UseFont label, String name, FontType fontType, FontFamily fontFamily, FontSize size, Color color, TextButton.TextButtonStyle style) {
        this.label = label;
        this.name = name;
        this.fontType = fontType;
        this.fontFamily = fontFamily;
        this.sizeRaw = FontSize.getRawSize(size);
        this.size = size;
        this.color = color;
        this.style = style;
    }

    public UseFont getLabel() {
        return label;
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

    public TextButton.TextButtonStyle getStyle() {
        return style;
    }

    public FontType getFontType() {
        return fontType;
    }

    public FontFamily getFontFamily() {
        return fontFamily;
    }
}
