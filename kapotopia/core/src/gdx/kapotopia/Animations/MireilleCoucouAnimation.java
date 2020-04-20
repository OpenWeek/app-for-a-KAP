package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;

import gdx.kapotopia.Helpers.Builders.AnimationBuilder;
import gdx.kapotopia.Kapotopia;

public class MireilleCoucouAnimation extends AnimationAbstract {

    public MireilleCoucouAnimation(Kapotopia game, Animation.PlayMode playMode) {
        String [] FRAMES = {
                "MireilleImages/MireilleCoucou_1.png",
                "MireilleImages/MireilleCoucou_2.png",
                "MireilleImages/MireilleCoucou_3.png",
                "MireilleImages/MireilleCoucou_4.png",
                "MireilleImages/MireilleCoucou_5.png",
                "MireilleImages/MireilleCoucou_6.png",
                "MireilleImages/MireilleCoucou_7.png"
        };

        setAnimation(new AnimationBuilder(0.05f).addFrames(FRAMES)
                .withPlayMode(playMode).build());
    }
}
