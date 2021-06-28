package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Helpers.Builders.AnimationBuilder;
import gdx.kapotopia.Kapotopia;

public class DifficultyScreenHellAnimation extends AnimationAbstract {

    public DifficultyScreenHellAnimation(Kapotopia game, Animation.PlayMode playMode) {
        if (!game.ass.containsAsset(AssetDescriptors.ANIM_DIF_HELL)) {
            game.ass.load(AssetDescriptors.ANIM_DIF_HELL);
            game.ass.finishLoadingAsset(AssetDescriptors.ANIM_DIF_HELL);
        }
        TextureAtlas atlas = game.ass.get(AssetDescriptors.ANIM_DIF_HELL);
        Array<TextureAtlas.AtlasRegion> r = atlas.findRegions("difScreenFire");
        TextureAtlas.AtlasRegion[] array = r.toArray();

        setAnimation(new AnimationBuilder(0.04f).withPlayMode(playMode)
                .addFrames(array).build());
    }
}
