package gdx.kapotopia.Helpers.Builders;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import gdx.kapotopia.Fonts.Font;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.Fonts.UseFont;
import gdx.kapotopia.Helpers.Alignement;
import gdx.kapotopia.Helpers.Bounds;

/**
 * A class to help build Labels. A mandatory argument is the text displayed in the label
 * and thus asked in the constructor. The user then can add arguments with the
 * provided methods and build it's label following these values with the build() method
 */
public class LabelBuilder {
    // Actor common attributes
    private ArrayList<EventListener> eventListeners;
    private ArrayList<EventListener> captureListeners;
    private float x, y;
    private float bx, by, bw, bh;
    private float width, height;
    private Alignement alignment;
    private boolean visible;
    // Font style
    private TextButton.TextButtonStyle style;
    private Font font;
    // Label
    private String text;
    private int textAlignement;
    private boolean wrap;

    /**
     * Constructor of LabelBuilder, initialize variables
     * @param text the label text (mandatory)
     */
    public LabelBuilder(String text) {
        // Actor attributes
        this.eventListeners = new ArrayList<EventListener>();
        this.captureListeners = new ArrayList<EventListener>();
        this.x = 0;
        this.y = 0;
        this.bx = -1;
        this.by = -1;
        this.bw = -1;
        this.bh = -1;
        this.width = -1;
        this.height = -1;
        this.alignment = Alignement.NONE;
        this.visible = true;
        // Label attributes
        this.style = null;
        this.font = null;
        this.text = text;
        this.textAlignement = Align.left;
        this.wrap = false;
    }

    // Common Actor attributes

    /**
     * Add a listener to this Label
     * @param listener the listener
     * @return this builderObject
     */
    public LabelBuilder withListener(EventListener listener) {
        this.eventListeners.add(listener);
        return this;
    }

    public LabelBuilder withCaptureListener(EventListener event) {
        this.captureListeners.add(event);
        return this;
    }

    public LabelBuilder withX(float x) {
        this.x = x;
        return this;
    }

    public LabelBuilder withY(float y) {
        this.y = y;
        return this;
    }

    public LabelBuilder withPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Configure the bounds
     * @param boundX the boundary origin position x
     * @param boundY the boundary position y
     * @param boundWidth the width of the boundary, must be positive or 0
     * @param boundHeight the height of the boundary, must be positive or 0
     * @return this builderObject
     */
    public LabelBuilder withBounds(float boundX, float boundY, float boundWidth, float boundHeight) {
        this.bx = boundX;
        this.by = boundY;
        this.bw = boundWidth;
        this.bh = boundHeight;
        return this;
    }

    public LabelBuilder withBounds(Bounds bounds) {
        this.bx = bounds.getX();
        this.by = bounds.getY();
        this.bw = bounds.getWidth();
        this.bh = bounds.getHeight();
        return this;
    }

    /**
     * Configure the width
     * @param width, must be positive or 0
     * @return this builderObject
     */
    public LabelBuilder withWidth(float width) {
        this.width = width;
        return this;
    }

    /**
     * Configure the height
     * @param height, must be positive or 0
     * @return this builderObject
     */
    public LabelBuilder withHeight(float height) {
        this.height = height;
        return this;
    }

    public LabelBuilder withAlignment(Alignement alignement) {
        this.alignment = alignement;
        return this;
    }

    public LabelBuilder isVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    // Font style

    public LabelBuilder withStyle(TextButton.TextButtonStyle style) {
        this.style = style;
        return this;
    }

    public LabelBuilder withStyle(Font font) {
        this.font = font;
        return this;
    }

    public LabelBuilder withStyle(UseFont type) {
        this.font = FontHelper.getFont(type);
        return this;
    }

    // Label

    /**
     * Set up an textAlignement for the label
     * @param textAlignement please use Align for the attributes
     * @return this builder
     */
    public LabelBuilder withTextAlignement(int textAlignement) {
        this.textAlignement = textAlignement;
        return this;
    }

    public LabelBuilder isWrapped(boolean wrap) {
        this.wrap = wrap;
        return this;
    }

    // Main build method

    /**
     * Call this function in last position to get your label
     * @return a label with all the specified options
     */
    public Label build() {
        //final Label label = (Label) super.buildActor();

        TextButton.TextButtonStyle theStyle;
        Color theColor;
        if (font == null) {
            theStyle = style;
            theColor = style.fontColor;
        } else {
            theStyle = font.getStyle();
            theColor = font.getColor();
        }

        Label l = new Label(text, new Label.LabelStyle(theStyle.font, theColor));

        // Actor Attributes

        if (alignment != Alignement.NONE) {
            if (font == null) {
                this.x = gdx.kapotopia.Helpers.Align.getX(alignment, text.length());
            } else {
                this.x = gdx.kapotopia.Helpers.Align.getX(alignment, text.length(), font.getSize());
            }
        }
        l.setPosition(x, y);
        // It shouldn't be possible to have a negative height or weight
        if (bw >= 0 && bh >= 0) {
            l.setBounds(bx, by, bw, by);
        }
        if (width >= 0) {
            l.setWidth(width);
        }
        if (height >= 0) {
            l.setHeight(height);
        }

        l.setVisible(visible);
        for (EventListener listener : this.eventListeners) {
            l.addListener(listener);
        }
        for (EventListener listener : this.captureListeners) {
            l.addCaptureListener(listener);
        }

        // Label Attributes

        l.setAlignment(textAlignement);
        l.setWrap(wrap);

        return l;
    }

    /* STATIC METHODS */

    /**
     * Convert a Label[] array into a Label[][] matrix
     * @throws NullPointerException if the given parameter is null
     * @param labs must be != null
     * @return a new matrix of size Label[labs.length][1]
     */
    public static Label[][] convert(Label[] labs) {
        if (labs == null) throw new NullPointerException("LabelBuilder - convert(Label[])\nimages is null");
        Label[][] labelMatrix = new Label[labs.length][1];
        for (int i=0; i<labs.length; i++)
            labelMatrix[i][0] = labs[i];
        return labelMatrix;
    }

    /**
     * Create a new empty Label matrix with Labels whose texts are ""
     * @throws IllegalArgumentException if the given size is equals or lower than 0
     * @param size the size of the new matrix
     * @return a new matrix of size Label[size][1]
     */
    public static Label[][] createEmptyMatrix(int size) {
        if (size <= 0) throw new IllegalArgumentException("LabelBuilder - createEmptyMatrix(int)\nsize is equal or smaller than 0");
        Label[][] labelMatrix = new Label[size][1];
        for (int i=0; i<size; i++)
            labelMatrix[i][0] = new LabelBuilder("").withStyle(UseFont.CLASSIC_SANS_NORMAL_BLACK).build();
        return labelMatrix;
    }
}
