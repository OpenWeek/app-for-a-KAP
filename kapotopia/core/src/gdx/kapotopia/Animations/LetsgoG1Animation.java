package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gdx.kapotopia.Helpers.Builders.AnimationBuilder;

public class LetsgoG1Animation {

    private Animation<TextureRegion> animation;

    public LetsgoG1Animation(Animation.PlayMode playMode) {
        String FRAME_1 = "World1/Game1/actiontext_1.png";
        String FRAME_2 = "World1/Game1/actiontext_2.png";
        String FRAME_3 = "World1/Game1/actiontext_3.png";
        String FRAME_4 = "World1/Game1/actiontext_4.png";

        this.animation = new AnimationBuilder(0.75f).withPlayMode(playMode)
                .addNewFrame(FRAME_1).addNewFrame(FRAME_2).addNewFrame(FRAME_3).addNewFrame(FRAME_4)
                .build();
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }
}
