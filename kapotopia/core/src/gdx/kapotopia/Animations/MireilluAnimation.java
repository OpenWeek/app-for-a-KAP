package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Helpers.Builders.AnimationBuilder;
import gdx.kapotopia.Kapotopia;

public class MireilluAnimation extends AnimationAbstract {

    public MireilluAnimation(Kapotopia game, Animation.PlayMode playMode) {
        if(!game.ass.containsAsset(AssetDescriptors.ANIM_MIREILLU))
            game.ass.load(AssetDescriptors.ANIM_MIREILLU);
        TextureAtlas atlas = game.ass.get(AssetDescriptors.ANIM_MIREILLU);
        Array<TextureAtlas.AtlasRegion> r = atlas.findRegions("jojo");
        TextureAtlas.AtlasRegion[] array = r.toArray();

        setAnimation(new AnimationBuilder(0.1f).withPlayMode(playMode)
                .addFrames(array).build());
    }
}
