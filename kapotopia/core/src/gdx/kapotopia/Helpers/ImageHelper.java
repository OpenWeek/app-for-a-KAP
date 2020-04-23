package gdx.kapotopia.Helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

import gdx.kapotopia.AssetsManaging.AssetsManager;

public class ImageHelper {
    /**
     * Get a background image with a screen size wide
     * @param viewport the viewport used torought the game
     * @param texture
     * @return the resulting image
     */
    public static Image getBackground(FitViewport viewport, Texture texture) {
        Image background = new Image(texture);
        background.setWidth(viewport.getWorldWidth());
        background.setHeight(viewport.getWorldHeight());
        return background;
    }
}
