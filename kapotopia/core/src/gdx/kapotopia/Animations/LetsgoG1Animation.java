package gdx.kapotopia.Animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Helpers.Builders.AnimationBuilder;

public class LetsgoG1Animation {

    private final String TAG = "LetsgoAnim";
    private Animation<TextureRegion> animation;

    public LetsgoG1Animation(Animation.PlayMode playMode) {

        TextureAtlas atlas = AssetsManager.getInstance().getAtlasByPath("World1/Game1/actiontext.atlas");
        Array<TextureAtlas.AtlasRegion> r = atlas.findRegions("actiontext");
        TextureAtlas.AtlasRegion[] array = r.toArray();

        Gdx.app.log(TAG, "Texture size : " + array.length);

        this.animation = new AnimationBuilder(0.04f).withPlayMode(playMode)
                .addFrames(array).build();
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }
}
