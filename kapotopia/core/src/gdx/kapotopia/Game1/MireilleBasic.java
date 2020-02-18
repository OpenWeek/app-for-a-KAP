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
     *  FIELDS
     */
    // Pour avertir les autres composants qu'elle a perdu une vie
    private List<MireilleListener> listeners = new ArrayList<MireilleListener>();
    //TODO see if we cannot refractor the way we use textures
    private TextureRegion normalTexture;
    private TextureRegion sadTexture;
    private TextureRegion madTexture;
    private TextureRegion happyTexture;
    private TextureRegion jojoFaceTexture;
    private TextureRegion jojoPoseTexture;
    private TextureRegion jojoKanjiTexture;

    private Random random;
    private byte lifes;
    private int score;
    private boolean jojoActivated;
    private float jojoScallingFactor;

    /*
     *  CONSTRUCTORS
     */
    public MireilleBasic() {
        this(25,25);
    }

    public MireilleBasic(float X, float Y) {
        super();
        /*
         *  CONSTANTES
         */
        String NORMAL_TEXTURE_PATH = "MireilleImages/Mireille.png";
        String SAD_TEXTURE_PATH = "MireilleImages/MireillePleure.png";
        String MAD_TEXTURRE_PATH = "MireilleImages/MireilleAChaud.png";
        String HAPPY_TEXTURE_PATH = "MireilleImages/MireilleBoucheOuverte.png";
        String JOJO_FACE_TEXTURE_PATH = "MireilleImages/MireilleJojo.png";
        String JOJO_POSE_TEXTURE_PATH = "MireilleImages/MireilleJojoPose.png";
        String JOJO_KANJI_TEXTURE_PATH = "MireilleImages/MireilleJojoPoseKanji.png";
        final AssetsManager man = AssetsManager.getInstance();
        /*
         *  INITIALIZATION
         */
        builderHelper(man.getTextureByPath(NORMAL_TEXTURE_PATH), X, Y);
        this.lifes = (byte) 3;
        this.score = 0;
        this.random = new Random();
        this.jojoActivated = false;
        jojoScallingFactor = 0f;

        // Differents textures
        normalTexture = new TextureRegion(man.getTextureByPath(NORMAL_TEXTURE_PATH));
        sadTexture = new TextureRegion(man.getTextureByPath(SAD_TEXTURE_PATH));
        madTexture = new TextureRegion(man.getTextureByPath(MAD_TEXTURRE_PATH));
        happyTexture = new TextureRegion(man.getTextureByPath(HAPPY_TEXTURE_PATH));
        jojoFaceTexture = new TextureRegion(man.getTextureByPath(JOJO_FACE_TEXTURE_PATH));
        jojoPoseTexture = new TextureRegion(man.getTextureByPath(JOJO_POSE_TEXTURE_PATH));
        jojoKanjiTexture = new TextureRegion(man.getTextureByPath(JOJO_KANJI_TEXTURE_PATH));
    }

    /*
     *  METHODS
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        //TODO jojoscalling factor is there because the file dimensions for these textures are smaller than the other ones for mireille. When these will be reduced, we will be able to remove this factor
        batch.draw(texture, this.getX(), this.getY(),
                (float) texture.getRegionWidth() / (SCALLING_FACTOR_ENTITY - jojoScallingFactor),
                (float) texture.getRegionHeight() / (SCALLING_FACTOR_ENTITY - jojoScallingFactor));
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
        int SCORE_UP = 10;
        increaseScore(SCORE_UP);
    }

    public void increaseScore(int add) {
        this.score += add;
        notifyScoreChanged(this.score);
        // We change temporally the texture
        if (jojoActivated)  this.texture = jojoKanjiTexture;
        else                this.texture = happyTexture;
        Timer.schedule(new BackToNormalTask(), 1.5f);
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
            if (jojoActivated)   this.texture = jojoFaceTexture;
            else                this.texture = madTexture;
        }else{
            if (jojoActivated)  this.texture = jojoFaceTexture;
            else                this.texture = sadTexture;
        }
        Timer.schedule(new BackToNormalTask(), 1.5f);
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

    public void toggleJojo() {
        this.jojoActivated = true;
        jojoScallingFactor = 2f;
        this.texture = jojoPoseTexture;
    }

    private class BackToNormalTask extends Timer.Task {
        @Override
        public void run() {
            if (jojoActivated) texture = jojoPoseTexture;
            else texture = normalTexture;
        }
    }
}

