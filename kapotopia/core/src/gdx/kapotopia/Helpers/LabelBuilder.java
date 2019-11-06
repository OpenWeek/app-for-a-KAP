package gdx.kapotopia.Helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import gdx.kapotopia.AssetsManaging.FontHelper;
import gdx.kapotopia.AssetsManaging.UsualFonts;
import gdx.kapotopia.Utils;

/**
 * A class to help build Labels. A mandatory argument is the text displayed in the label
 * and thus asked in the constructor. The user then can add arguments with the
 * provided methods and build it's label following these values with the build() method
 */
public class LabelBuilder {
    // Font style
    private TextButton.TextButtonStyle style;
    private String path;
    private Color color;
    private int size;
    // Label
    private String text;
    private float x, y;
    private float width, height;
    private int alignement;
    private boolean visible;
    private boolean wrap;

    /**
     * Constructor of LabelBuilder, initialize variables
     * @param text the label text (mandatory)
     */
    public LabelBuilder(String text) {
        this.style = null;
        this.path = "COMMS.ttf";
        this.color = Color.BLACK;
        this.size = 60;
        this.text = text;
        this.x = 0;
        this.y = 0;
        this.width = -1;
        this.height = -1;
        this.alignement = Align.left;
        this.visible = true;
        this.wrap = false;
    }

    public LabelBuilder withStyle(TextButton.TextButtonStyle style) {
        this.style = style;
        return this;
    }

    public LabelBuilder withStyle(UsualFonts type) {
        this.style = FontHelper.getStyleFont(type);
        return this;
    }

    public LabelBuilder withFontPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * Set up a personalized style, if this method is used, the style provided by withStyle method
     * is not taken in count
     * @param path of the font file
     * @param color of the font
     * @param size of the font
     * @return this builder
     */
    public LabelBuilder withPersonalizedStyle(String path, Color color, int size) {
        this.path = path;
        this.color = color;
        this.size = size;
        this.style = null;
        return this;
    }

    public LabelBuilder withPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public LabelBuilder withWidth(float width) {
        this.width = width;
        return this;
    }

    public LabelBuilder withHeight(float height) {
        this.height = height;
        return this;
    }

    /**
     * Set up an alignement for the label
     * @param alignement please use Align for the attributes
     * @return this builder
     */
    public LabelBuilder withAlignement(int alignement) {
        this.alignement = alignement;
        return this;
    }

    public LabelBuilder isVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public LabelBuilder isWrapped(boolean wrap) {
        this.wrap = wrap;
        return this;
    }

    /**
     * Call this function in last position to get your label
     * @return a label with all the specified options
     */
    public Label build() {
        TextButton.TextButtonStyle theStyle;
        if(style == null) {
            theStyle = Utils.getStyleFont(path, size, color);
        } else {
            theStyle = style;
        }
        Label l = new Label(text, new Label.LabelStyle(theStyle.font, theStyle.fontColor));
        l.setPosition(x, y);
        l.setAlignment(alignement);
        l.setVisible(visible);
        l.setWrap(wrap);
        if(width >= 0) l.setWidth(width);
        if(height >= 0) l.setHeight(height);
        return l;
    }
}
