package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Helpers.Builders.AnimationBuilder;

public class DifficultyScreenInfinityAnimation extends AnimationAbstract {

    public DifficultyScreenInfinityAnimation(Animation.PlayMode playMode) {

        final String ATLAS_PATH = "EcranMenu/blackhole.atlas";
        TextureAtlas atlas = AssetsManager.getInstance().getAtlasByPath(ATLAS_PATH);
        Array<TextureAtlas.AtlasRegion> r = atlas.findRegions("blackhole");
        TextureAtlas.AtlasRegion[] array = r.toArray();

        setAnimation(new AnimationBuilder(0.04f).withPlayMode(playMode)
                .addFrames(array).build());
    }
}
