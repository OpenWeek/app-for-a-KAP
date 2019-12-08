package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gdx.kapotopia.Helpers.Builders.AnimationBuilder;

public class LeavesBackgroundAnimation {

    private Animation<TextureRegion> animation;

    public LeavesBackgroundAnimation(Animation.PlayMode playMode) {
        final String [] FRAMES = new String[]{"World1/Game1/Feuilles.png",
                "World1/Game1/Feuilles2.png", "World1/Game1/Feuilles3.png",
                "World1/Game1/Feuilles4.png", "World1/Game1/Feuilles5.png"};

        this.animation = new AnimationBuilder(0.75f).withPlayMode(playMode)
                .addFrames(FRAMES).build();
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }
}
