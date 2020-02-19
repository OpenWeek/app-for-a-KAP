package gdx.kapotopia.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static gdx.kapotopia.GameConfig.SCALLING_FACTOR_ENTITY;

public abstract class VirusAbstract extends EntityAbstract {

    final String TAG = this.getClass().getSimpleName();
    /* ENCAPSULED FIELDS */

    protected final float LOCAL_SCALLING_FACTOR = 1.5f;
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

    /**
     * Update realWidth and realHeight with the two scalling factor (glocal SCALLING_FACTOR_ENTITY and
     * LOCAL_SCALLING_FACTOR) and the current texture width and height
     */
    public void updateRealUnits() {
//        Gdx.app.debug(TAG, "(before) realHeight : " + realHeight + " | realWidth : " + realWidth);
        final float width = this.texture.getRegionWidth();
        final float height = this.texture.getRegionHeight();
//        Gdx.app.debug(TAG, "Texture Width : " + width + " | Texture height : " + height);
        this.realWidth = width / SCALLING_FACTOR_ENTITY;
        this.realWidth *= LOCAL_SCALLING_FACTOR;
        this.realHeight = height / SCALLING_FACTOR_ENTITY;
        this.realHeight *= LOCAL_SCALLING_FACTOR;
//        Gdx.app.debug(TAG, "(after) realHeight : " + realHeight + " | realWidth : " + realWidth);
    }

    /* COMMON LIBGDX CALLS */

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, this.getX(), this.getY(), realWidth, realHeight);
    }
}

