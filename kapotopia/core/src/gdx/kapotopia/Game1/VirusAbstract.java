package gdx.kapotopia.Game1;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static gdx.kapotopia.Kapotopia.SCALLING_FACTOR_ENTITY;

public abstract class VirusAbstract extends EntityAbstract {

    /* CONSTANTES */

    protected final static String TEXTURE_FOLDER = "IST/";
    protected final static String[] TEXTURES_PATHS_IST = {TEXTURE_FOLDER + "Blennorragie.png",
            TEXTURE_FOLDER + "Chlamydia.png", TEXTURE_FOLDER + "HepatiteB.png",
            TEXTURE_FOLDER + "MissHerpes.png", TEXTURE_FOLDER + "MisterMycose.png",
            TEXTURE_FOLDER + "MrSida.png", TEXTURE_FOLDER + "papillomavirus.png",
            TEXTURE_FOLDER + "Syphillis.png", TEXTURE_FOLDER + "Trichonomas.png"};
    protected final static String[] TEXTURES_PATHS_FAKE_IST = {TEXTURE_FOLDER + "FausseIST1.png",
            TEXTURE_FOLDER + "FausseIST2.png", TEXTURE_FOLDER + "FausseIST4 - Lycanthropie.png",
            TEXTURE_FOLDER + "FausseIST4 - LycanthropieV2.png", TEXTURE_FOLDER + "Flemme.png"};
    protected final static String[] TEXTURES_PATHS_BOSSES = {TEXTURE_FOLDER + "MegaMST.png",
            TEXTURE_FOLDER + "MegaMST2.png", TEXTURE_FOLDER + "MegaMST3.png"};

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
                texture.getRegionWidth() >> SCALLING_FACTOR_ENTITY,
                texture.getRegionHeight() >> SCALLING_FACTOR_ENTITY);
    }
}
