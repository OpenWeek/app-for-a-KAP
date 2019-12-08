package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gdx.kapotopia.Helpers.Builders.AnimationBuilder;

public class NeonDoorAnimation {

    private Animation<TextureRegion> animation;

    public NeonDoorAnimation(Animation.PlayMode playMode) {
        final String [] FRAMES = new String[]{"game3/PorteNeonsBleus.png", "game3/PorteNeonsJaunes.png",
                "game3/PorteNeonsRoses.png", "game3/PorteNeonsRouges.png", "game3/PorteNeonsVerts.png",
                "game3/PorteNeonsViolets.png"};

        this.animation = new AnimationBuilder(3f).withPlayMode(playMode)
                .addFrames(FRAMES).build();
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }
}
