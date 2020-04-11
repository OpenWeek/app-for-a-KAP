package gdx.kapotopia.Helpers.Builders;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;

import gdx.kapotopia.Fonts.Font;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.Fonts.UseFont;
import gdx.kapotopia.Helpers.Align;
import gdx.kapotopia.Helpers.Alignement;

/**
 * A class to help build TextButton. A mandatory argument is the text displayed in the TextButton
 * and thus asked in the constructor. The user then can add arguments with the
 * provided methods and build it's label following these values with the build() method
 */
public class TextButtonBuilder {
    // Actor common attributes
    private ArrayList<EventListener> eventListeners;
    private ArrayList<EventListener> captureListeners;
    private float x, y;
    private float bx, by, bw, bh;
    private float width, height;
    private Alignement alignement;
    private boolean visible;
    // TextButton attributes
    private TextButton.TextButtonStyle style;
    private Font font;
    private Skin skin;
    private String text;
    private boolean disable;

    /**
     * Constructor of TextButtonBuilder, initialize variables
     * @param text the TextButton text (mandatory)
     */
    public TextButtonBuilder(String text) {
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
        this.alignement = Alignement.NONE;
        this.visible = true;

        // TextButton attributes
        this.style = null;
        this.font = null;
        this.skin = null;
        this.text = text;
        this.disable = false;
    }

    // Common Actor attributes

    /**
     * Add a listener to this TextButton
     * @param listener the listener
     * @return this builderObject
     */
    public TextButtonBuilder withListener(EventListener listener) {
        this.eventListeners.add(listener);
        return this;
    }

    public TextButtonBuilder withCaptureListener(EventListener event) {
        this.captureListeners.add(event);
        return this;
    }

    public TextButtonBuilder withX(float x) {
        this.x = x;
        return this;
    }

    public TextButtonBuilder withY(float y) {
        this.y = y;
        return this;
    }

    public TextButtonBuilder withPosition(float x, float y) {
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
    public TextButtonBuilder withBounds(float boundX, float boundY, float boundWidth, float boundHeight) {
        this.bx = boundX;
        this.by = boundY;
        this.bw = boundWidth;
        this.bh = boundHeight;
        return this;
    }

    /**
     * Configure the width
     * @param width, must be positive or 0
     * @return this builderObject
     */
    public TextButtonBuilder withWidth(float width) {
        this.width = width;
        return this;
    }

    /**
     * Configure the height
     * @param height, must be positive or 0
     * @return this builderObject
     */
    public TextButtonBuilder withHeight(float height) {
        this.height = height;
        return this;
    }

    public TextButtonBuilder withAlignment(Alignement alignment) {
        this.alignement = alignment;
        return this;
    }

    public TextButtonBuilder isVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    // TextButton attributes

    public TextButtonBuilder withStyle(TextButton.TextButtonStyle style) {
        this.style = style;
        return this;
    }

    public TextButtonBuilder withStyle(UseFont type) {
        this.font = FontHelper.getFont(type);
        return this;
    }

    public TextButtonBuilder withStyle(Font font) {
        this.font = font;
        return this;
    }

    public TextButtonBuilder withSkin(Skin skin) {
        this.skin = skin;
        return this;
    }

    public TextButtonBuilder isDisable(boolean disable) {
        this.disable = disable;
        return this;
    }

    /**
     * Build the TextButton using the parameters given before.
     *
     * Note: If both a style and a skin are set, the style
     *
     * @return the textButton given the TextButtonBuilder arguments
     * @throws IllegalArgumentException if no skin or no style are set
     */
    public TextButton build() throws IllegalArgumentException{
        final TextButton tb;
        if (font == null) {
            if (style != null) {
                tb = new TextButton(text, style);
            } else {
                if (skin != null) {
                    tb = new TextButton(text, skin);
                } else {
                    throw new IllegalArgumentException("No style or Skin set");
                }
            }
        } else {
            tb = new TextButton(text, font.getStyle());
        }


        // Actor attributes

        if (alignement != Alignement.NONE) {
            if (font == null) {
                x = Align.getX(alignement, text.length());
            } else {
                x = Align.getX(alignement, text.length(), font.getSize());
            }
        }
        tb.setPosition(x, y);
        // It shouldn't be possible to have a negative height or weight
        if(bw >= 0 && bh >= 0) {
            tb.setBounds(bx, by, bw, by);
        }
        if (width >= 0) {
            tb.setWidth(width);
        }
        if (height >= 0) {
            tb.setHeight(height);
        }
        tb.setVisible(visible);

        for (EventListener listener : this.eventListeners) {
            tb.addListener(listener);
        }
        for (EventListener listener : this.captureListeners) {
            tb.addCaptureListener(listener);
        }

        // TextButton specific attributes

        tb.setDisabled(disable);

        return tb;
    }
}
