package gdx.kapotopia.Game1;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.utils.Timer;

import gdx.kapotopia.AssetsManaging.AssetsManager;

import static gdx.kapotopia.Kapotopia.SCALLING_FACTOR_ENTITY;

public class MireilleBasic extends EntityAbstract {

    /*
     *  CONSTANTES
     */
    private final String NORMAL_TEXTURE_PATH = "MireilleImages/Mireille.png";
    private final String SAD_TEXTURE_PATH = "MireilleImages/MireillePleure.png";
    private final String MAD_TEXTURRE_PATH = "MireilleImages/MireilleAChaud.png";
    private final String HAPPY_TEXTURE_PATH = "MireilleImages/MireilleBoucheOuverte.png";
    private final byte MAX_LIFES = 3;
    private final int SCORE_UP = 10;
    private final Timer.Task backToNormalTask;

    /*
     *  FIELDS
     */
    // Pour avertir les autres composants qu'elle a perdu une vie
    private List<MireilleListener> listeners = new ArrayList<MireilleListener>();
    private TextureRegion normalTexture;
    private TextureRegion sadTexture;
    private TextureRegion madTexture;
    private TextureRegion happyTexture;

    private Random random;
    private byte lifes;
    private int score;

    /*
     *  CONSTRUCTORS
     */
    public MireilleBasic() {
        this(25,25);
    }

    public MireilleBasic(float X, float Y) {
        super();
        final AssetsManager man = AssetsManager.getInstance();
        builderHelper(man.getTextureByPath(NORMAL_TEXTURE_PATH), X, Y);
        this.lifes = MAX_LIFES;
        this.score = 0;
        this.random = new Random();
        backToNormalTask = new Timer.Task() {
            @Override
            public void run() {
                texture = normalTexture;
            }
        };

        // Differents textures
        normalTexture = new TextureRegion(man.getTextureByPath(NORMAL_TEXTURE_PATH));
        sadTexture = new TextureRegion(man.getTextureByPath(SAD_TEXTURE_PATH));
        madTexture = new TextureRegion(man.getTextureByPath(MAD_TEXTURRE_PATH));
        happyTexture = new TextureRegion(man.getTextureByPath(HAPPY_TEXTURE_PATH));
    }

    /*
     *  METHODS
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
        // We change temporally the texture
        this.texture = happyTexture;
        Timer.schedule(backToNormalTask, 1.5f);
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

        this.updateCollision(this.getX(),this.getY());

        // We change temporally the texture
        boolean madOrSad = random.nextBoolean();
        if(madOrSad) {
            this.texture = madTexture;
        }else{
            this.texture = sadTexture;
        }
        Timer.schedule(backToNormalTask, 1.5f);
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

