package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Helpers.Builders.AnimationBuilder;

public class SkyBackgroundAnimation extends AnimationAbstract {

    public SkyBackgroundAnimation(Animation.PlayMode playMode) {

        TextureAtlas atlas = AssetsManager.getInstance().getAtlasByPath("World1/Game1/nightsky.atlas");
        Array<TextureAtlas.AtlasRegion> r = atlas.findRegions("Ciel");
        TextureAtlas.AtlasRegion[] array = r.toArray();

        setAnimation(new AnimationBuilder(0.05f).withPlayMode(playMode)
                .addFrames(array).build());
    }
}
