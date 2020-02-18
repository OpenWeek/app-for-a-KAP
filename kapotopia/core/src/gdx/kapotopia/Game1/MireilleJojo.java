package gdx.kapotopia.Game1;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;

import gdx.kapotopia.Animations.MireilluAnimation;
import gdx.kapotopia.Kapotopia;

public class MireilleJojo {
    private Kapotopia game;

    private final float wW;
    private final float wH;

    private float stateTime;
    private final Animation<TextureRegion> animation;
    private SpriteBatch batch;

    public MireilleJojo(Kapotopia game) {
        this.game = game;
        this.wW = game.viewport.getWorldWidth();
        this.wH = game.viewport.getWorldHeight();
        stateTime = 0;
        this.animation = new MireilluAnimation(Animation.PlayMode.NORMAL).getAnimation();
        this.batch = new SpriteBatch();
    }

    public void draw(float delta) {
        stateTime += delta;

        batch.begin();
        batch.draw(animation.getKeyFrame(stateTime, false), 0, 0,
                game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        batch.end();
    }

    public void upProjMatrBatch(Matrix4 projection) {
        this.batch.setProjectionMatrix(projection);
    }
}
