package gdx.kapotopia.Helpers.Builders;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import javax.swing.GroupLayout;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.AssetsManaging.FontHelper;
import gdx.kapotopia.AssetsManaging.UseFont;
import gdx.kapotopia.Kapotopia;

/**
 * A class to help build ImageTextButtons. A mandatory argument is the text displayed in the imagetextButton
 * and thus asked in the constructor. The user then can add arguments with the
 * provided methods and build it's label following these values with the build() method
 */
public class ImageTextButtonBuilder {
    private Kapotopia game;
    // Actor common attributes
    private ArrayList<EventListener> eventListeners;
    private ArrayList<EventListener> captureListeners;
    private float x, y;
    private Alignement alignement;
    private float bx, by, bw, bh;
    private float width, height;
    private float scaleXY;
    private Padding padding;
    // ImageTextButton attributes
    private String text;
    private boolean visible;
    private boolean checked;

    private ImageTextButton.ImageTextButtonStyle fontStyle;
    private Button.ButtonStyle imageStyle;


    public ImageTextButtonBuilder(Kapotopia game, String text) {
        this.game = game;
        // Actor attributes
        this.eventListeners = new ArrayList<EventListener>();
        this.captureListeners = new ArrayList<EventListener>();
        this.x = 0;
        this.y = 0;
        this.alignement = Alignement.NONE;
        this.padding = Padding.NONE;
        this.bx = -1;
        this.by = -1;
        this.bw = -1;
        this.bh = -1;
        this.width = -1;
        this.height = -1;
        this.scaleXY = 1;
        this.visible = true;
        // ImageTextButton attributes
        this.text = text;
        this.checked = false;
        this.fontStyle = new ImageTextButton.ImageTextButtonStyle(FontHelper.getStyleFont(UseFont.AESTHETIC_NORMAL_BLACK));
        this.imageStyle = null;
    }

    // Actor methods

    /**
     * Add a listener to this ImageTextButton
     * @param listener the listener
     * @return this builderObject
     */
    public ImageTextButtonBuilder withListener(EventListener listener) {
        this.eventListeners.add(listener);
        return this;
    }

    public ImageTextButtonBuilder withCaptureListener(EventListener event) {
        this.captureListeners.add(event);
        return this;
    }

    public ImageTextButtonBuilder isVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public ImageTextButtonBuilder isChecked(boolean checked) {
        this.checked = checked;
        return this;
    }

    public ImageTextButtonBuilder withPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public ImageTextButtonBuilder withY(float y) {
        this.y = y;
        return this;
    }

    public ImageTextButtonBuilder withAlignment(Alignement al) {
        this.alignement = al;
        return this;
    }

    public ImageTextButtonBuilder withPadding(Padding pa) {
        this.padding = pa;
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
    public ImageTextButtonBuilder withBounds(float boundX, float boundY, float boundWidth, float boundHeight) {
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
    public ImageTextButtonBuilder withWidth(float width) {
        this.width = width;
        return this;
    }

    /**
     * Configure the height
     * @param height, must be positive or 0
     * @return this builderObject
     */
    public ImageTextButtonBuilder withHeight(float height) {
        this.height = height;
        return this;
    }

    public ImageTextButtonBuilder withScaleXY(float scaleXY) {
        this.scaleXY = scaleXY;
        return this;
    }

    // ImageTextButton methods

    public ImageTextButtonBuilder withFontStyle(UseFont font) {
        this.fontStyle = new ImageTextButton.ImageTextButtonStyle(FontHelper.getStyleFont(font));
        return this;
    }

    public ImageTextButtonBuilder withFontStyle(TextButton.TextButtonStyle fontStyle) {
        this.fontStyle = new ImageTextButton.ImageTextButtonStyle(fontStyle);
        return this;
    }

    public ImageTextButtonBuilder withImageStyle(Button.ButtonStyle imageStyle) {
        this.imageStyle = imageStyle;
        return this;
    }

    public ImageTextButtonBuilder withImageStyle(String path) {
        final Drawable image = new TextureRegionDrawable(new TextureRegion(
                AssetsManager.getInstance().getTextureByPath(path)));
        this.imageStyle = new Button.ButtonStyle(image, image, image);
        return this;
    }

    public ImageTextButtonBuilder withImageStyle(Texture texture) {
        final Drawable image = new TextureRegionDrawable(new TextureRegion(texture));
        this.imageStyle = new Button.ButtonStyle(image, image, image);
        return this;
    }

    public ImageTextButtonBuilder withImageStyle(String pathImgUp, String pathImgDown, String pathImgChecked) {
        final Drawable imageUp = new TextureRegionDrawable(new TextureRegion(AssetsManager.getInstance().getTextureByPath(pathImgUp)));
        final Drawable imageDown = new TextureRegionDrawable(new TextureRegion(AssetsManager.getInstance().getTextureByPath(pathImgDown)));
        final Drawable imageChecked = new TextureRegionDrawable(new TextureRegion(AssetsManager.getInstance().getTextureByPath(pathImgChecked)));

        this.imageStyle = new Button.ButtonStyle(imageUp, imageDown, imageChecked);
        return this;
    }

    public ImageTextButtonBuilder withImageStyle(Texture textureImgUp, Texture textureImgDown, Texture textureImgChecked) {
        final Drawable imageUp = new TextureRegionDrawable(new TextureRegion(textureImgUp));
        final Drawable imageDown = new TextureRegionDrawable(new TextureRegion(textureImgDown));
        final Drawable imageChecked = new TextureRegionDrawable(new TextureRegion(textureImgChecked));

        this.imageStyle = new Button.ButtonStyle(imageUp, imageDown, imageChecked);
        return this;
    }



    public ImageTextButton build() {
        final ImageTextButton imgTxtBtn = new ImageTextButton(text, fontStyle);
        if (imageStyle != null) {
            final ImageTextButton.ImageTextButtonStyle style =
                    new ImageTextButton.ImageTextButtonStyle(imageStyle.up, imageStyle.down,
                            imageStyle.checked, fontStyle.font);
            imgTxtBtn.setStyle(style);
        }

        // Actor attributes

        for (EventListener listener : this.eventListeners) {
            imgTxtBtn.addListener(listener);
        }
        for (EventListener listener : this.captureListeners) {
            imgTxtBtn.addCaptureListener(listener);
        }

        if (alignement != Alignement.NONE) {
            final float MIDDLE_SCREEN = game.viewport.getScreenWidth() / 2f;
            switch (alignement) { //FIXME the alignment is not correct for all screen
                case LEFT:
                    x = MIDDLE_SCREEN / 2f - ((text.length() * Kapotopia.ONE_CHARACTER_STD_WIDTH) / 2 );
                    break;
                case CENTER:
                    x = ((MIDDLE_SCREEN / 3f) * 2f) - ((text.length() * Kapotopia.ONE_CHARACTER_STD_WIDTH) / 2 );
                    break;
                case RIGHT:
                    x = MIDDLE_SCREEN - ((text.length() * Kapotopia.ONE_CHARACTER_STD_WIDTH) / 2 );
                    break;
            }
        }
        imgTxtBtn.setPosition(x, y);

        // If padding has been mentioned, we take it. Else, we take the width/height
        if(padding != Padding.NONE) {
            switch (padding) {
                case STANDARD:
                    this.width = (Kapotopia.ONE_CHARACTER_STD_WIDTH * text.length()) + (Kapotopia.ONE_CHARACTER_STD_WIDTH * 2);
                    this.height = Kapotopia.ONE_CHARACTER_STD_HEIGHT + (Kapotopia.ONE_CHARACTER_STD_HEIGHT / 2);
                    break;
            }
        }

        // It shouldn't be possible to have a negative height or width
        if(bw >= 0 && bh >= 0) {
            imgTxtBtn.setBounds(bx, by, bw, bh);
        }
        if (width >= 0) {
            imgTxtBtn.setWidth(width);
        }
        if (height >= 0) {
            imgTxtBtn.setHeight(height);
        }

        imgTxtBtn.setVisible(visible);
        imgTxtBtn.setScale(scaleXY);

        // ImageTextButton specific attributes

        imgTxtBtn.setChecked(checked);

        return imgTxtBtn;
    }
}
