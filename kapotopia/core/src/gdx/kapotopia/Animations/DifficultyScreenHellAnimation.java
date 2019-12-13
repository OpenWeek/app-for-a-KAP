package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gdx.kapotopia.Helpers.Builders.AnimationBuilder;

public class DifficultyScreenHellAnimation {

    private Animation<TextureRegion> animation;

    public DifficultyScreenHellAnimation(Animation.PlayMode playMode) {
        final String [] FRAMES = new String[]{"EcranMenu/EcranJeu1Enfer.png",
                "EcranMenu/EcranJeu1Enfer2.png", "EcranMenu/EcranJeu1Enfer3.png"};

        this.animation = new AnimationBuilder(0.0625f).withPlayMode(playMode)
                .addFrames(FRAMES).build();
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }
}
