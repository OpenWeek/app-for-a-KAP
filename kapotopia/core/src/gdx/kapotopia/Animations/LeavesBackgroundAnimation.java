package gdx.kapotopia.Animations;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Helpers.Builders.AnimationBuilder;
import gdx.kapotopia.Kapotopia;

public class LeavesBackgroundAnimation extends AnimationAbstract{

    public LeavesBackgroundAnimation(Kapotopia game, Animation.PlayMode playMode) {

        setAnimation(new AnimationBuilder(0.75f).withPlayMode(playMode)
                .addNewFrame(new TextureRegion(game.ass.get(AssetDescriptors.LEAVES))).build());
    }
}
