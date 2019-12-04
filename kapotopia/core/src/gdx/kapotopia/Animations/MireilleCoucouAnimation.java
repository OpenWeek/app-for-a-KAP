package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gdx.kapotopia.Helpers.Builders.AnimationBuilder;

public class MireilleCoucouAnimation {

    private Animation<TextureRegion> animation;

    public MireilleCoucouAnimation(Animation.PlayMode playMode) {
        String [] FRAMES = {
                "MireilleImages/MireilleCoucou_1.png",
                "MireilleImages/MireilleCoucou_2.png",
                "MireilleImages/MireilleCoucou_3.png",
                "MireilleImages/MireilleCoucou_4.png",
                "MireilleImages/MireilleCoucou_5.png",
                "MireilleImages/MireilleCoucou_6.png",
                "MireilleImages/MireilleCoucou_7.png"
        };

        this.animation = new AnimationBuilder(0.05f).addFrames(FRAMES)
                .withPlayMode(playMode).build();
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }
}
