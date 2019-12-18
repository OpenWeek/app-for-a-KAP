package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;

import gdx.kapotopia.Helpers.Builders.AnimationBuilder;

public class DifficultyScreenInfinityAnimation extends AnimationAbstract {

    public DifficultyScreenInfinityAnimation(Animation.PlayMode playMode) {
        final String [] FRAMES = new String[]{"EcranMenu/EcranJeu1Infini1.png",
                "EcranMenu/EcranJeu1Infini2.png", "EcranMenu/EcranJeu1Infini3.png",
                "EcranMenu/EcranJeu1Infini4.png","EcranMenu/EcranJeu1Infini5.png",
                "EcranMenu/EcranJeu1Infini6.png", "EcranMenu/EcranJeu1Infini7.png",
                "EcranMenu/EcranJeu1Infini8.png","EcranMenu/EcranJeu1Infini9.png",
                "EcranMenu/EcranJeu1Infini10.png", "EcranMenu/EcranJeu1Infini11.png",
                "EcranMenu/EcranJeu1Infini12.png"};

        setAnimation(new AnimationBuilder(0.05f).withPlayMode(playMode)
                .addFrames(FRAMES).build());
    }
}
