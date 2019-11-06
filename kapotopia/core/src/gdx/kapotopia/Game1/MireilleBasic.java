package gdx.kapotopia.Game1;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;

import java.util.ArrayList;
import java.util.List;

import gdx.kapotopia.AssetsManaging.AssetsManager;

import static gdx.kapotopia.Kapotopia.SCALLING_FACTOR_ENTITY;

public class MireilleBasic extends EntityAbstract {

    /*
     *  CONSTANTES
     */
    private final static String TEXTURE_PATH = "MireilleImages/Mireille.png";
    private final static byte MAX_LIFES = 3;
    private final static int SCORE_UP = 10;

    /*
     *  CHAMPS
     */
    // Pour avertir les autres composants qu'elle a perdu une vie
    private List<MireilleListener> listeners = new ArrayList<MireilleListener>();

    private byte lifes;
    private int score;

    /*
     *  CONSTRUCTEURS
     */
    public MireilleBasic() {
        this(25,25);
    }

    public MireilleBasic(float X, float Y) {
        super();
        builderHelper(AssetsManager.getInstance().getTextureByPath(TEXTURE_PATH), X, Y);
        this.lifes = MAX_LIFES;
        this.score = 0;
    }

    /*
     *  MÉTHODES
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, this.getX(), this.getY(),
                (float) texture.getRegionWidth() / SCALLING_FACTOR_ENTITY,
                (float) texture.getRegionHeight() / SCALLING_FACTOR_ENTITY);
    }

    public void act(float delta) {
        for (Action action : this.getActions()) {
            action.act(delta);
        }
    }

    /*
     *  LISTENERS
     */
    public void addListener(MireilleListener toAdd) {
        listeners.add(toAdd);
    }

    public void increaseScore() {
        increaseScore(SCORE_UP);
    }

    public void increaseScore(int add) {
        this.score += add;
        notifyScoreChanged(this.score);
    }

    public void decreaseLife() {
        if(this.lifes > 0) {
            this.lifes--;
            System.out.println("Mireille a été touchée !");
        } else {
            hide();
            System.out.println("Mireille a perdu");
        }

        for (MireilleListener l : listeners) {
            l.lifeChanged(this.lifes);
        }

        this.score -= 15;
        notifyScoreChanged(this.score);

        this.resetPosition();
        this.updateCollision(this.getX(),this.getY());
    }

    private void notifyScoreChanged(final int score) {
        for(MireilleListener l : listeners) {
            l.scoreChanged(score);
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

