package gdx.kapotopia.Game1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;

import java.util.Random;

import gdx.kapotopia.AssetsManager;
import gdx.kapotopia.Screens.Game1;

import static gdx.kapotopia.Kapotopia.SCALLING_FACTOR_ENTITY;

public class Virus extends VirusAbstract {

    private final String TAG = "VIRUS";

    private Random random;
    private boolean isIST;
    private Game1 game;

    private float acceleration;
    private float accAddFactor;
    private float nameLabX;

    public Virus(Rectangle bounds, Game1 game) {
        this.screenBounds = bounds;
        this.game = game;
        this.random = new Random();
        this.setName("Tom");
        if(game == null) {
            builderHelper(null,50,bounds.getHeight());
        }else{
            builderHelper(updateNewVirus(),50,bounds.getHeight());
        }
        this.speed = 500;
        this.acceleration = 1.00f;
        this.accAddFactor = 0.08f;
        this.realWidth = ((float) texture.getRegionWidth()) / (SCALLING_FACTOR_ENTITY + LOCAL_SCALLING_FACTOR);
        this.realHeight = ((float) texture.getRegionHeight()) / (SCALLING_FACTOR_ENTITY + LOCAL_SCALLING_FACTOR);

        // We don't know yet the size of the label, so we take an arbitrary middle position
        this.nameLabX = computeNameLabX();
    }

    // Méthode draw se trouve dans VirusAbstract

    public void act(float delta) {

        for (Action action : this.getActions()) {
            action.act(delta);
        }

        final float newY = this.getY() - (this.getSpeed() * delta * acceleration) ;
        this.setY(newY);
        this.updateCollision(this.getX(),newY);
        // If the virus has reached the end of the screen
        boolean hasToChange = false;
        if (this.getY() < -200) {
            if(isIST()) {
                game.addMissedIST(getName());
            }
            acceleration += accAddFactor;
            hasToChange = true;
        }

        game.setNewEnnemiLabelPosition(this.nameLabX, this.getY() - 25);
        if(hasToChange) {
            changeVirusType();
        }
    }

    private void setNewRandPosition() {
        this.setY(screenBounds.getHeight());
        this.setX(50 + 250 * random.nextInt(4));
    }

    /**
     * Compute the X value for the label that indicate the name
     * @return the new Value of X
     */
    private float computeNameLabX() {
        /* Note: I know this is very ugly code, but believe me, i spent already to much time trying
         *       to find a better way to do this. I found the values by trials.
         *       This works, don't break it.
         */
        final float factoredNameLength;
        if (this.getName().length() > 11) {
            factoredNameLength = this.getName().length() * this.getName().length();
        } else if (this.getName().length() > 9) {
            factoredNameLength = this.getName().length() * this.getName().length() * (this.getName().length() / 7.5f);
        } else if (this.getName().length() > 7) {
            factoredNameLength = this.getName().length() * this.getName().length() * (this.getName().length() / 6f);
        } else if (this.getName().length() > 5) {
            factoredNameLength = this.getName().length() * this.getName().length() * (this.getName().length() / 1.5f);
        } else if (this.getName().length() > 3) {
            factoredNameLength = this.getName().length() * this.getName().length() * this.getName().length() * (this.getName().length() / 2f);
        } else {
            factoredNameLength = this.getName().length() * this.getName().length() * this.getName().length() * this.getName().length() * (this.getName().length() / 2f);
        }

        final float a = this.getX() + (this.getRealWidth() - factoredNameLength) / 2f;
        return Math.max(a,this.getX());
    }

    /**
     * Change le type de virus (texture et label)
     */
    public void changeVirusType() {
        this.setTexture(new TextureRegion(updateNewVirus()));
        game.changeEnnemiLabel(this.getName());
        setNewRandPosition();
        // We update the X position of the name label to make it fit right
        this.nameLabX = computeNameLabX();
//        Gdx.app.log(TAG, "GetX() : " + getX() + " | Name.length : " +
//                this.getName().length() + "\nRealWidth : " + getRealWidth() + " | Width : " +
//                getWidth() + "\nnameLabX : " + this.nameLabX + " for " + this.getName());
    }

    /**
     * Change le virus pour un autre, met à jour le nom, isIST et renvoie une nouvelle texture
     * @return une nouvelle texture
     */
    private Texture updateNewVirus() {
        final VirusContainer v = game.getRdmVirusTexture(VIRUS_TYPE.getRandomType());
        this.setName(v.getName());
        this.isIST = v.isIst();
        return AssetsManager.getInstance().getTextureByPath(v.getTexturePath());
    }

    public boolean isIST() {
        return isIST;
    }

    public void setIST(boolean IST) {
        isIST = IST;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getAccAddFactor() {
        return accAddFactor;
    }

    public void setAccAddFactor(float accAddFactor) {
        this.accAddFactor = accAddFactor;
    }
}
