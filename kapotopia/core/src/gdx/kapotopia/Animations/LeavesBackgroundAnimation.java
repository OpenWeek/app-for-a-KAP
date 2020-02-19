package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;

import gdx.kapotopia.Helpers.Builders.AnimationBuilder;

public class LeavesBackgroundAnimation extends AnimationAbstract{

    public LeavesBackgroundAnimation(Animation.PlayMode playMode) {
        final String [] FRAMES = new String[]{"World1/Game1/Feuilles.png"
        };

        setAnimation(new AnimationBuilder(0.75f).withPlayMode(playMode)
                .addFrames(FRAMES).build());
    }
}
