package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Helpers.Builders.AnimationBuilder;
import gdx.kapotopia.Kapotopia;

public class EyesBackgroundAnimation extends AnimationAbstract {

    public EyesBackgroundAnimation(Kapotopia game, Animation.PlayMode playMode) {
        if(!game.ass.containsAsset(AssetDescriptors.ANIM_EYES))
            game.ass.load(AssetDescriptors.ANIM_EYES);
        TextureAtlas atlas = game.ass.get(AssetDescriptors.ANIM_EYES);
        Array<TextureAtlas.AtlasRegion> r = atlas.findRegions("eyes");
        TextureAtlas.AtlasRegion[] array = r.toArray();

        setAnimation(new AnimationBuilder(0.04f).withPlayMode(playMode)
                .addFrames(array).build());
    }
}
