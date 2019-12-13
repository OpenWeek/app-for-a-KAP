package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gdx.kapotopia.Helpers.Builders.AnimationBuilder;

public class DifficultyScreenInfinityAnimation {

    private Animation<TextureRegion> animation;

    public DifficultyScreenInfinityAnimation(Animation.PlayMode playMode) {
        final String [] FRAMES = new String[]{"EcranMenu/EcranJeu1Infini1.png",
                "EcranMenu/EcranJeu1Infini2.png", "EcranMenu/EcranJeu1Infini3.png",
                "EcranMenu/EcranJeu1Infini4.png"};

        this.animation = new AnimationBuilder(0.0625f).withPlayMode(playMode)
                .addFrames(FRAMES).build();
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }
}
