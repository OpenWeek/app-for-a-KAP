package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Helpers.Builders.AnimationBuilder;
import gdx.kapotopia.Kapotopia;

public class SkyBackgroundAnimation extends AnimationAbstract {

    public SkyBackgroundAnimation(Kapotopia game, Animation.PlayMode playMode) {
        if(!game.ass.containsAsset(AssetDescriptors.ANIM_SKY))
            game.ass.load(AssetDescriptors.ANIM_SKY);
        TextureAtlas atlas = game.ass.get(AssetDescriptors.ANIM_SKY);
        Array<TextureAtlas.AtlasRegion> r = atlas.findRegions("Ciel");
        TextureAtlas.AtlasRegion[] array = r.toArray();

        setAnimation(new AnimationBuilder(0.05f).withPlayMode(playMode)
                .addFrames(array).build());
    }
}
