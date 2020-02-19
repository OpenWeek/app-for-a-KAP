package gdx.kapotopia.Helpers.Builders;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Helpers.Align;
import gdx.kapotopia.Helpers.Alignement;

/**
 * A class to help build ImageButton. A mandatory argument is the text displayed in the ImageButton
 * and thus asked in the constructor. The user then can add arguments with the
 * provided methods and build it's label following these values with the build() method
 */
public class ImageButtonBuilder {
    // Actor common attributes
    private ArrayList<EventListener> eventListeners;
    private ArrayList<EventListener> captureListeners;
    private float x, y;
    private float bx, by, bw, bh;
    private float width, height;
    private Alignement alignement;
    private boolean visible;
    // TextButton attributes
    private ImageButton.ImageButtonStyle style;
    private TextureRegionDrawable imageUp;
    private TextureRegionDrawable imageDown;
    private TextureRegionDrawable imageChecked;

    public ImageButtonBuilder() {
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

        // ImageButton attributes
        this.style = null;
        this.imageUp = new TextureRegionDrawable(new TextureRegion(
                AssetsManager.getInstance().getTextureByPath("pause_logo.png")));
        this.imageDown = null;
        this.imageChecked = null;
    }

    // Common Actor methods

    /**
     * Add a listener to this ImageButton
     * @param listener the listener
     * @return this builderObject
     */
    public ImageButtonBuilder withListener(EventListener listener) {
        this.eventListeners.add(listener);
        return this;
    }

    public ImageButtonBuilder withCaptureListener(EventListener event) {
        this.captureListeners.add(event);
        return this;
    }

    public ImageButtonBuilder withY(float y) {
        this.y = y;
        return this;
    }

    public ImageButtonBuilder withPosition(float x, float y) {
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
    public ImageButtonBuilder withBounds(float boundX, float boundY, float boundWidth, float boundHeight) {
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
    public ImageButtonBuilder withWidth(float width) {
        this.width = width;
        return this;
    }

    /**
     * Configure the height
     * @param height, must be positive or 0
     * @return this builderObject
     */
    public ImageButtonBuilder withHeight(float height) {
        this.height = height;
        return this;
    }

    public ImageButtonBuilder withAlignment(Alignement alignment) {
        this.alignement = alignment;
        return this;
    }

    public ImageButtonBuilder isVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    // ImageButton methods

    public ImageButtonBuilder withStyle(ImageButton.ImageButtonStyle style) {
        this.style = style;
        return this;
    }

    public ImageButtonBuilder withImageUp(String path) {
        this.imageUp = new TextureRegionDrawable(new TextureRegion(
                AssetsManager.getInstance().getTextureByPath(path)));
        return this;
    }

    public ImageButtonBuilder withImageUp(Texture texture) {
        this.imageUp = new TextureRegionDrawable(new TextureRegion(texture));
        return this;
    }

    public ImageButtonBuilder withImageDown(String path) {
        this.imageDown = new TextureRegionDrawable(new TextureRegion(
                AssetsManager.getInstance().getTextureByPath(path)));
        return this;
    }

    public ImageButtonBuilder withImageDown(Texture texture) {
        this.imageDown = new TextureRegionDrawable(new TextureRegion(texture));
        return this;
    }

    public ImageButtonBuilder withImageChecked(String path) {
        this.imageChecked = new TextureRegionDrawable(new TextureRegion(
                AssetsManager.getInstance().getTextureByPath(path)));
        return this;
    }

    public ImageButtonBuilder withImageChecked(Texture texture) {
        this.imageChecked = new TextureRegionDrawable(new TextureRegion(texture));
        return this;
    }

    public ImageButton build() {
        final ImageButton imgbtn;
        if (style == null) {
            if (imageDown == null) {
                imgbtn = new ImageButton(imageUp);
            }else{
                if(imageChecked == null) {
                    imgbtn = new ImageButton(imageUp, imageDown);
                }else{
                    imgbtn = new ImageButton(imageUp, imageDown, imageChecked);
                }
            }
        }else{
            imgbtn = new ImageButton(style);
        }

        // Actor attributes

        for (EventListener listener : this.eventListeners) {
            imgbtn.addListener(listener);
        }

        for (EventListener listener : this.captureListeners) {
            imgbtn.addCaptureListener(listener);
        }

        if (alignement != Alignement.NONE) {
            this.x = Align.getX(alignement, imageUp.getRegion().getRegionWidth());
        }
        imgbtn.setPosition(x, y);
        // It shouldn't be possible to have a negative height or weight
        if(bw >= 0 && bh >= 0) {
            imgbtn.setBounds(bx, by, bw, bh);
        }
        if (width >= 0) {
            imgbtn.setWidth(width);
        }
        if (height >= 0) {
            imgbtn.setHeight(height);
        }
        imgbtn.setVisible(visible);


        // ImageButton specific attributes

        return imgbtn;
    }

}
