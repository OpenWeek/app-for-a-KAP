package gdx.kapotopia.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class AnimationAbstract {
    private Animation<TextureRegion> animation;

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    protected void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }
}
