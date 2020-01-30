package gdx.kapotopia.Helpers.Builders;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import gdx.kapotopia.AssetsManaging.AssetsManager;

public class ImageBuilder {

    private Texture texture;
    private boolean isVisible;
    private int x, y;
    private Color color;

    public ImageBuilder() {
        texture = null;
        isVisible = true;
        x = 0; y = 0;
        color = null;
    }

    public ImageBuilder isVisible(boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public ImageBuilder withPosition(int x, int y) {
        this.x = x; this.y = y;
        return this;
    }

    public ImageBuilder withColor(Color color) {
        this.color = color;
        return this;
    }

    public ImageBuilder withTexture(String path) {
        texture = AssetsManager.getInstance().getTextureByPath(path);
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
    public static Image[][] convert(String[] imagesPaths) {
        if (imagesPaths == null) throw new NullPointerException("ImageBuilder - convert(String[])\nimagesPaths is null");
        Image[][] imgMatrix = new Image[imagesPaths.length][1];
        for (int i=0; i<imagesPaths.length; i++)
            imgMatrix[i][0] = new Image(AssetsManager.getInstance().getTextureByPath(imagesPaths[i]));
        return imgMatrix;
    }
}
