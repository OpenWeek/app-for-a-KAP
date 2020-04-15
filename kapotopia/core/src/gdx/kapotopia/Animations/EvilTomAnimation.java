package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Helpers.Builders.AnimationBuilder;

public class EvilTomAnimation extends AnimationAbstract {

    public EvilTomAnimation(Animation.PlayMode playMode) {

        TextureAtlas atlas = AssetsManager.getInstance().getAtlasByPath("game3/intro/tom.atlas");
        Array<TextureAtlas.AtlasRegion> r = atlas.findRegions("tom");
        TextureAtlas.AtlasRegion[] array = r.toArray();

        setAnimation(new AnimationBuilder(0.03f).withPlayMode(playMode)
                .addFrames(array).build());
    }
}
