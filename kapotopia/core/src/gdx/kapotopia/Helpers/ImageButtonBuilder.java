package gdx.kapotopia.Helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import gdx.kapotopia.AssetsManaging.AssetsManager;

public class ImageButtonBuilder {
    private boolean visible;
    private float x, y;
    private float bx, by, bw, bh;
    private ImageButton.ImageButtonStyle style;
    private TextureRegionDrawable imageUp;
    private TextureRegionDrawable imageDown;
    private TextureRegionDrawable imageChecked;
    private EventListener listener;

    public ImageButtonBuilder() {
        this.visible = false;
        this.x = 0;
        this.y = 0;
        this.bx = -1;
        this.by = -1;
        this.bw = -1;
        this.bh = -1;
        this.style = null;
        this.imageUp = new TextureRegionDrawable(new TextureRegion(
                AssetsManager.getInstance().getTextureByPath("badlogic.jpg")));
        this.imageDown = null;
        this.imageChecked = null;
        this.listener = null;
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

    public ImageButtonBuilder withStyle(ImageButton.ImageButtonStyle style) {
        this.style = style;
        return this;
    }

    public ImageButtonBuilder isVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public ImageButtonBuilder withPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public ImageButtonBuilder withBounds(float boundX, float boundY, float boundWidth, float boundHeight) {
        this.bx = boundX;
        this.by = boundY;
        this.bw = boundWidth;
        this.bh = boundHeight;
        return this;
    }

    public ImageButtonBuilder withListener(EventListener listener) {
        this.listener = listener;
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

        imgbtn.setVisible(visible);
        imgbtn.setPosition(x, y);
        // It shouldn't be possible to have a negative height or weight
        if(bw >= 0 && bh >= 0) {
            imgbtn.setBounds(bx, by, bw, bh);
        }
        if(listener != null) {
            imgbtn.addListener(listener);
        }
        return imgbtn;
    }

}
