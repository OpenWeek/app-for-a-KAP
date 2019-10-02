package gdx.kapotopia.Game1;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static gdx.kapotopia.Kapotopia.SCALLING_FACTOR_ENTITY;

public abstract class VirusAbstract extends EntityAbstract {

    /* ENCAPSULED FIELDS */

    protected float speed;
    protected Rectangle screenBounds;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Rectangle getScreenBounds() {
        return screenBounds;
    }

    public void setScreenBounds(Rectangle screenBounds) {
        this.screenBounds = screenBounds;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

    /* COMMON LIBGDX CALLS */

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, this.getX(), this.getY(),
                (float) texture.getRegionWidth() / SCALLING_FACTOR_ENTITY,
                (float) texture.getRegionHeight() / SCALLING_FACTOR_ENTITY);
    }
}
