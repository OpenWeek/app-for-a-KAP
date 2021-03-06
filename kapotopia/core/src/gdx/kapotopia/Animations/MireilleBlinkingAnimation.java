package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Helpers.Builders.AnimationBuilder;
import gdx.kapotopia.Kapotopia;

public class MireilleBlinkingAnimation extends AnimationAbstract {

    public MireilleBlinkingAnimation(Kapotopia game, Animation.PlayMode playMode) {
        if(!game.ass.containsAsset(AssetDescriptors.ANIM_MIREILLE_BLINK)) {
            game.ass.load(AssetDescriptors.ANIM_MIREILLE_BLINK);
            game.ass.finishLoadingAsset(AssetDescriptors.ANIM_MIREILLE_BLINK);
        }
        TextureAtlas atlas = game.ass.get(AssetDescriptors.ANIM_MIREILLE_BLINK);
        Array<TextureAtlas.AtlasRegion> r = atlas.findRegions("mireilleblink");
        TextureAtlas.AtlasRegion[] array = r.toArray();

        setAnimation(new AnimationBuilder(0.1f).withPlayMode(playMode)
                .addFrames(array).build());
    }
}
