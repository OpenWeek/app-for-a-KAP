package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Helpers.Builders.AnimationBuilder;

public class NeonDoorAnimation extends AnimationAbstract {

    public NeonDoorAnimation(Animation.PlayMode playMode) {

        TextureAtlas atlas = AssetsManager.getInstance().getAtlasByPath("EcranMenu/mainmenu_w2.atlas");
        Array<TextureAtlas.AtlasRegion> r = atlas.findRegions("mainmenu_w2");
        TextureAtlas.AtlasRegion[] array = r.toArray();

        setAnimation(new AnimationBuilder(0.07f).withPlayMode(playMode)
                .addFrames(array).build());
    }
}
