package gdx.kapotopia.Game1;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Kapotopia;

import static gdx.kapotopia.GameConfig.SCALLING_FACTOR_ENTITY;

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

    /*
     *  CONSTRUCTORS
     */

    public MireilleBasic(Kapotopia game, float X, float Y) {
        super();
        /*
         *  CONSTANTES
         */
        /*
         *  INITIALIZATION
         */
        builderHelper(game.ass.get(AssetDescriptors.MI_NORMAL), X, Y);
        this.lifes = (byte) 3;
        this.score = 0;
        this.random = new Random();
        this.jojoActivated = false;

        // Differents textures
        normalTexture = new TextureRegion(game.ass.get(AssetDescriptors.MI_NORMAL));
        sadTexture = new TextureRegion(game.ass.get(AssetDescriptors.MI_CRY));
        madTexture = new TextureRegion(game.ass.get(AssetDescriptors.MI_TIRED));
        happyTexture = new TextureRegion(game.ass.get(AssetDescriptors.MI_HAPPY));
        jojoFaceTexture = new TextureRegion(game.ass.get(AssetDescriptors.MI_JOJO_FACE));
        jojoPoseTexture = new TextureRegion(game.ass.get(AssetDescriptors.MI_JOJO_POSE));
        jojoKanjiTexture = new TextureRegion(game.ass.get(AssetDescriptors.MI_JOJO_KANJI));
    }

    /*
     *  METHODS
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        //TODO jojoscalling factor is there because the file dimensions for these textures are smaller than the other ones for mireille. When these will be reduced, we will be able to remove this factor
        batch.draw(texture, this.getX(), this.getY(),
                (float) texture.getRegionWidth() / (SCALLING_FACTOR_ENTITY),
                (float) texture.getRegionHeight() / (SCALLING_FACTOR_ENTITY));
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

