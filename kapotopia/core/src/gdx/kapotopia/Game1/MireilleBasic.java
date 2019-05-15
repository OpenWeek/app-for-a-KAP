package gdx.kapotopia.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static gdx.kapotopia.Kapotopia.SCALLING_FACTOR_ENTITY;

public class MireilleBasic extends Actor {

    private final static String TEXTURE_PATH = "MireilleImages/Mireille.png";

    private TextureRegion texture;

    public MireilleBasic() {
        texture = new TextureRegion(new Texture(Gdx.files.internal(TEXTURE_PATH)));
        this.setHeight(texture.getRegionHeight() >> SCALLING_FACTOR_ENTITY);
        this.setWidth(texture.getRegionWidth() >> SCALLING_FACTOR_ENTITY);
        this.setX(25);
        this.setY(25);
    }

    public MireilleBasic(float X, float Y) {
        texture = new TextureRegion(new Texture(Gdx.files.internal(TEXTURE_PATH)));
        this.setHeight(texture.getRegionHeight());
        this.setWidth(texture.getRegionWidth());
        this.setX(X);
        this.setY(Y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, this.getX(), this.getY(),
                texture.getRegionWidth() >> SCALLING_FACTOR_ENTITY,
                texture.getRegionHeight() >> SCALLING_FACTOR_ENTITY);
    }

    public void act(float delta) {

    }
}

