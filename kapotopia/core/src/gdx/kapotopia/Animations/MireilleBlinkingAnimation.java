package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gdx.kapotopia.Helpers.Builders.AnimationBuilder;

public class MireilleBlinkingAnimation {

    private Animation<TextureRegion> animation;

    public MireilleBlinkingAnimation(Animation.PlayMode playMode) {
        String FRAME_1 = "MireilleImages/mireilleblink_1.png";
        String FRAME_2 = "MireilleImages/mireilleblink_2.png";
        String FRAME_3 = "MireilleImages/mireilleblink_3.png";

        this.animation = new AnimationBuilder(0.1f).withPlayMode(playMode)
                .addNewFrame(FRAME_1).addNewFrame(FRAME_2).addNewFrame(FRAME_3)
                .build();
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }
}
