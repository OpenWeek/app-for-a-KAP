package gdx.kapotopia.Game1;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static gdx.kapotopia.Kapotopia.SCALLING_FACTOR_ENTITY;

public abstract class VirusAbstract extends EntityAbstract {

    /* ENCAPSULED FIELDS */

    protected final float LOCAL_SCALLING_FACTOR = 1.7f;
    protected float speed;
    protected Rectangle screenBounds;
    /**
     * Real Width of the Virus, which is scalled by two differents factors given by a local factor and a general factor
     * Should be used instead of width
     */
    protected float realWidth;
    /**
     * Real Height of the Virus, which is scalled by two differents factors given by a local factor and a general factor
     * Should be used instead of height
     */
    protected float realHeight;

    /* ACCESSORS */

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

    public float getRealWidth() {
        return realWidth;
    }

    public float getRealHeight() {
        return realHeight;
    }

    /* COMMON LIBGDX CALLS */

    @Override
    public void draw(Batch batch, float parentAlpha) {
        final float width = ((float) texture.getRegionWidth()) / (SCALLING_FACTOR_ENTITY + LOCAL_SCALLING_FACTOR);
        final float height = ((float) texture.getRegionHeight()) / (SCALLING_FACTOR_ENTITY + LOCAL_SCALLING_FACTOR);
        this.realWidth = width;
        this.realHeight = height;
        batch.draw(texture, this.getX(), this.getY(), width, height);
    }
}

