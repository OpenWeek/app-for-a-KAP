package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;

import gdx.kapotopia.Helpers.Builders.AnimationBuilder;

public class DifficultyScreenHellAnimation extends AnimationAbstract {

    public DifficultyScreenHellAnimation(Animation.PlayMode playMode) {
        final String [] FRAMES = new String[]{"EcranMenu/EcranJeu1Enfer.png",
                "EcranMenu/EcranJeu1Enfer2.png", "EcranMenu/EcranJeu1Enfer3.png"};

        setAnimation(new AnimationBuilder(0.04f).withPlayMode(playMode)
                .addFrames(FRAMES).build());
    }
}
