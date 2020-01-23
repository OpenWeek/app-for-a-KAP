package gdx.kapotopia.Helpers.Builders;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import gdx.kapotopia.AssetsManaging.AssetsManager;

public class ImageBuilder {
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
