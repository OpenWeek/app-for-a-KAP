package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Helpers.Builders.AnimationBuilder;
import gdx.kapotopia.Kapotopia;

public class NeonDoorAnimation extends AnimationAbstract {

    public NeonDoorAnimation(Kapotopia game, Animation.PlayMode playMode) {
        if(!game.ass.containsAsset(AssetDescriptors.ANIM_NEON_DOOR))
            game.ass.load(AssetDescriptors.ANIM_NEON_DOOR);
        TextureAtlas atlas = game.ass.get(AssetDescriptors.ANIM_NEON_DOOR);
        Array<TextureAtlas.AtlasRegion> r = atlas.findRegions("mainmenu_w2");
        TextureAtlas.AtlasRegion[] array = r.toArray();

        setAnimation(new AnimationBuilder(0.07f).withPlayMode(playMode)
                .addFrames(array).build());
    }
}
