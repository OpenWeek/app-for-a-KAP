package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Helpers.Builders.AnimationBuilder;

public class MireilleBlinkingAnimation {

    private Animation<TextureRegion> animation;

    public MireilleBlinkingAnimation(Animation.PlayMode playMode) {

        TextureAtlas atlas = AssetsManager.getInstance().getAtlasByPath("MireilleImages/mireilleblinking.atlas");
        Array<TextureAtlas.AtlasRegion> r = atlas.findRegions("mireilleblink");
        TextureAtlas.AtlasRegion[] array = r.toArray();

        this.animation = new AnimationBuilder(0.0625f).withPlayMode(playMode)
                .addFrames(array).build();
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }
}
