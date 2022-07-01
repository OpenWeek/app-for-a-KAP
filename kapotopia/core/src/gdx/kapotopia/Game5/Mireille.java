package gdx.kapotopia.Game5;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Mireille extends Actor {

    private TextureRegion frame1;
    private TextureRegion frame2;
    private TextureRegion frame3;
    private TextureRegion currentFrame;
    private TextureRegion[] allFrame;
    Animation<TextureRegion> mireilleAnimation;
    private float duration;
    private float stateTime;

    public Mireille() {

        frame1 = new TextureRegion(new Texture(Gdx.files.internal("MireilleImages/Mireille.png")));
        frame2 = new TextureRegion(new Texture(Gdx.files.internal("MireilleImages/Mireille.png")));
        frame3 = new TextureRegion(new Texture(Gdx.files.internal("MireilleImages/Mireille.png")));
        allFrame = new TextureRegion[] {frame1, frame2, frame3};
        mireilleAnimation = new Animation<TextureRegion>(0.2f, allFrame);
        stateTime=0;
        this.setHeight(200);
        this.setWidth(200);
        this.setX(100);
        this.setY(900);
        currentFrame = frame1;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentFrame, this.getX(), this.getY());
    }

    public void act(float delta) {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = mireilleAnimation.getKeyFrame(stateTime, true);
    }
}
