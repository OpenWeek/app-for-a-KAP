package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Helpers.Builders.AnimationBuilder;

public class SkyBackgroundAnimation extends AnimationAbstract {

    public SkyBackgroundAnimation(Animation.PlayMode playMode) {

        TextureAtlas atlas = AssetsManager.getInstance().getAtlasByPath("World1/Game1/sky2.atlas");
        Array<TextureAtlas.AtlasRegion> r = atlas.findRegions("sky");
        TextureAtlas.AtlasRegion[] array = r.toArray();

        setAnimation(new AnimationBuilder(0.5f).withPlayMode(playMode)
                .addFrames(array).build());
    }
}
