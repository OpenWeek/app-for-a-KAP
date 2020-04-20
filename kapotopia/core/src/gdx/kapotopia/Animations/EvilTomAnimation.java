package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Helpers.Builders.AnimationBuilder;
import gdx.kapotopia.Kapotopia;

public class EvilTomAnimation extends AnimationAbstract {

    public EvilTomAnimation(Kapotopia game, Animation.PlayMode playMode) {
        if(!game.ass.containsAsset(AssetDescriptors.ANIM_EVIL_TOM))
            game.ass.load(AssetDescriptors.ANIM_EVIL_TOM);
        TextureAtlas atlas = game.ass.get(AssetDescriptors.ANIM_EVIL_TOM);
        Array<TextureAtlas.AtlasRegion> r = atlas.findRegions("tom");
        TextureAtlas.AtlasRegion[] array = r.toArray();

        setAnimation(new AnimationBuilder(0.03f).withPlayMode(playMode)
                .addFrames(array).build());
    }
}
