package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Helpers.Builders.AnimationBuilder;
import gdx.kapotopia.Kapotopia;

public class LetsgoG1Animation extends AnimationAbstract {

    public LetsgoG1Animation(Kapotopia game, Animation.PlayMode playMode) {
        if(!game.ass.containsAsset(AssetDescriptors.ANIM_ACTIONTEXT))
            game.ass.load(AssetDescriptors.ANIM_ACTIONTEXT);
        TextureAtlas atlas = game.ass.get(AssetDescriptors.ANIM_ACTIONTEXT);
        Array<TextureAtlas.AtlasRegion> r = atlas.findRegions("actiontext");
        TextureAtlas.AtlasRegion[] array = r.toArray();

        setAnimation(new AnimationBuilder(0.04f).withPlayMode(playMode)
                .addFrames(array).build());
    }
}
