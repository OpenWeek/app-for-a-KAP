package gdx.kapotopia.Helpers.Builders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Kapotopia;

public class ImageBuilder {

    private Texture texture;
    private boolean isVisible;
    private float x, y;
    private float width, height;
    private Color color;

    public ImageBuilder() {
        texture = null;
        isVisible = true;
        x = 0; y = 0;
        width = -1; height = -1;
        color = null;
    }

    public ImageBuilder isVisible(boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public ImageBuilder withPosition(float x, float y) {
        this.x = x; this.y = y;
        return this;
    }

    public ImageBuilder withWidth(float width) {
        this.width = width;
        return this;
    }

    public ImageBuilder withHeight(float height) {
        this.height = height;
        return this;
    }

    public ImageBuilder withSize(float width, float height) {
        this.height = height;
        this.width = width;
        return this;
    }

    public ImageBuilder withColor(Color color) {
        this.color = color;
        return this;
    }

    public ImageBuilder withTexture(Texture texture) {
        this.texture = texture;
        return this;
    }

    public Image build() {
        final Image image;
        if (texture != null)
            image = new Image(texture);
        else image = new Image();
        image.setPosition(x,y);
        if ( width >= 0 ) image.setWidth(width);
        if ( height >= 0 ) image.setHeight(height);
        image.setVisible(isVisible);
        if(color != null) image.setColor(color);
        return image;
    }

    /* STATIC METHODS */

    /**
     * Convert a Image[] array into a Image[][] matrix
     * @throws NullPointerException if the given parameter is null
     * @param images must be != null
     * @return a new matrix of size Image[images.length][1]
     */
    public static Image[][] convert(Image[] images) {
        if (images == null) throw new NullPointerException("ImageBuilder - convert(Image[])\nimages is null");
        Image[][] imgMatrix = new Image[images.length][1];
        for (int i=0; i<images.length; i++)
            imgMatrix[i][0] = images[i];
        return imgMatrix;
    }

    /**
     * Convert an array of Textures paths into a Image[][] matrix, by loading the textures via AssetsManager
     * @throws NullPointerException if the given parameter is null
     * @param imagesPaths must be != null
     * @return a new matrix of size Image[imagesPaths.length][1]
     */
    public static Image[][] convert(Kapotopia game, String[] imagesPaths) {
        if (imagesPaths == null) throw new NullPointerException("ImageBuilder - convert(String[])\nimagesPaths is null");
        Image[][] imgMatrix = new Image[imagesPaths.length][1];
        for (int i=0; i<imagesPaths.length; i++)
            imgMatrix[i][0] = new Image(game.ass.get(new AssetDescriptor<Texture>(imagesPaths[i], Texture.class)));
        return imgMatrix;
    }
}
