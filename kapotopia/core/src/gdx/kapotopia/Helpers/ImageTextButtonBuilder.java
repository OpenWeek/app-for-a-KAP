package gdx.kapotopia.Helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.AssetsManaging.FontHelper;
import gdx.kapotopia.AssetsManaging.UsualFonts;

public class ImageTextButtonBuilder {
    private String text;
    private boolean visible;
    private boolean checked;
    private float x, y;
    private float bx, by, bw, bh;
    private float scaleXY;
    private ImageTextButton.ImageTextButtonStyle fontStyle;
    private Button.ButtonStyle imageStyle;
    private EventListener listener;

    public ImageTextButtonBuilder(String text) {
        this.text = text;
        this.visible = true;
        this.checked = false;
        this.x = 0;
        this.y = 0;
        this.bx = -1;
        this.by = -1;
        this.bw = -1;
        this.bh = -1;
        this.scaleXY = 1;
        this.fontStyle = new ImageTextButton.ImageTextButtonStyle(FontHelper.getStyleFont(UsualFonts.AESTHETIC_NORMAL_BLACK));
        this.imageStyle = null;
        this.listener = null;
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

    public ImageTextButtonBuilder withBounds(float boundX, float boundY, float boundWidth, float boundHeight) {
        this.bx = boundX;
        this.by = boundY;
        this.bw = boundWidth;
        this.bh = boundHeight;
        return this;
    }

    public ImageTextButtonBuilder withFontStyle(UsualFonts font) {
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

    public ImageTextButtonBuilder withListener(EventListener listener) {
        this.listener = listener;
        return this;
    }

    public ImageTextButtonBuilder withScaleXY(float scaleXY) {
        this.scaleXY = scaleXY;
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
        imgTxtBtn.setPosition(x, y);
        // It shouldn't be possible to have a negative height or weight
        if(bw >= 0 && bh >= 0) {
            imgTxtBtn.setBounds(bx, by, bw, bh);
        }

        if (listener != null) {
            imgTxtBtn.addListener(listener);
        }

        imgTxtBtn.setVisible(visible);
        imgTxtBtn.setChecked(checked);

        imgTxtBtn.setScale(scaleXY);

        return imgTxtBtn;
    }
}
