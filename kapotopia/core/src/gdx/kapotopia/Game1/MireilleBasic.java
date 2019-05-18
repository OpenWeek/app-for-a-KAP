package gdx.kapotopia.Game1;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

import java.util.ArrayList;
import java.util.List;

import static gdx.kapotopia.Kapotopia.SCALLING_FACTOR_ENTITY;

public class MireilleBasic extends EntityAbstract {

    /*
     *  CONSTANTES
     */
    private final static String TEXTURE_PATH = "MireilleImages/Mireille.png";
    private final static byte MAX_LIFES = 3;

    /*
     *  CHAMPS
     */
    // Pour avertir les autres composants qu'elle a perdu une vie
    private List<LifeListener> listeners = new ArrayList<LifeListener>();

    private byte lifes;

    /*
     *  CONSTRUCTEURS
     */
    public MireilleBasic() {
        super();
        builderHelper(TEXTURE_PATH,25,25);
        this.lifes = MAX_LIFES;
    }

    public MireilleBasic(float X, float Y) {
        super();
        builderHelper(TEXTURE_PATH,X,Y);
        this.lifes = MAX_LIFES;
    }

    /*
     *  MÉTHODES
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, this.getX(), this.getY(),
                texture.getRegionWidth() >> SCALLING_FACTOR_ENTITY,
                texture.getRegionHeight() >> SCALLING_FACTOR_ENTITY);
    }

    public void act(float delta) {

    }

    /*
     *  LISTENERS
     */
    public void addListener(LifeListener toAdd) {
        listeners.add(toAdd);
    }

    public void decreaseLife() {
        if(this.lifes > 0) {
            this.lifes--;
            System.out.println("Mireille a été touchée !");
        } else {
            hide();
            System.out.println("Mireille a perdu");
        }

        for (LifeListener l : listeners) {
            l.lifeChanged(this.lifes);
        }
    }

    /*
     * GETTERS ET SETTERS
     */

    public byte getLifes() {
        return lifes;
    }

    public void setLifes(byte lifes) {
        this.lifes = lifes;
    }
}

