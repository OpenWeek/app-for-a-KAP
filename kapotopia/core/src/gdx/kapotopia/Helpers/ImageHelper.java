package gdx.kapotopia.Helpers;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

import gdx.kapotopia.AssetsManaging.AssetsManager;

public class ImageHelper {
    /**
     * Get a background image with a screen size wide
     * @param viewport the viewport used torought the game
     * @param texturePath
     * @return the resulting image
     */
    public static Image getBackground(FitViewport viewport, String texturePath) {
        Image background = new Image(AssetsManager.getInstance().getTextureByPath(texturePath));
        background.setWidth(viewport.getWorldWidth());
        background.setHeight(viewport.getWorldHeight());
        return background;
    }
}
