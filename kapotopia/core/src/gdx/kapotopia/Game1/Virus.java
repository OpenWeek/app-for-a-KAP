package gdx.kapotopia.Game1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;

import java.util.Random;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Helpers.Align;
import gdx.kapotopia.Screens.Game1;

public class Virus extends VirusAbstract {

    private final String TAG = this.getClass().getSimpleName();

    private Random random;
    private boolean isIST;
    private boolean isMaybeIST;
    private Game1 game;

    private float acceleration;
    private float accAddFactor;
    private float accMaxLim;
    private float nameLabX;

    public Virus(Rectangle bounds, Game1 game) {
        this.screenBounds = bounds;
        this.game = game;
        this.random = new Random();
        this.setName("Tom");
        builderHelper(updateNewVirus(),50,bounds.getHeight());
        this.speed = 500;
        this.acceleration = 1.00f;
        this.accAddFactor = 0.08f;
        this.accMaxLim = 3f;
        updateRealUnits();

        // We don't know yet the size of the label, so we take an arbitrary middle position
        this.nameLabX = Align.getXCenteredWithElement(this.getX(), this.getRealWidth(), this.getName().length());
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
                game.getGameController().addMissedIST(isMaybeIST, getName());
            }
            // We cap the acceleration to keep it under an acceptable level
            if (acceleration <= accMaxLim) acceleration += accAddFactor;
            hasToChange = true;
        }

        game.setNewEnnemiLabelPosition(this.nameLabX, this.getY() - 25);
        if(hasToChange) {
            changeVirusType();
        }
    }

    private void setNewRandPosition() {
        this.setY(screenBounds.getHeight());
        this.setX(game.getGameController().getMIN_X() + game.getGameController().getMOVE_VALUE_X() * random.nextInt(4));
    }

    /**
     * Change the type of the virus (texture and label)
     * Update with a new texture, a new label, new realWidth and realHeight
     */
    public void changeVirusType() {
        this.setTexture(new TextureRegion(updateNewVirus()));
        updateRealUnits();
        game.changeEnnemiLabel(this.getName());
        setNewRandPosition();
        // We update the X position of the name label to make it fit right
        this.nameLabX = Align.getXCenteredWithElement(this.getX(), this.getRealWidth(), this.getName().length());
    }

    /**
     * Change le virus pour un autre, met à jour le nom, isIST et renvoie une nouvelle texture
     * @return une nouvelle texture
     */
    private Texture updateNewVirus() {
        final VirusContainer v = game.getGameController().getRdmVirusTexture(VIRUS_TYPE.getRandomType());
        this.setName(v.getName());
        this.isIST = v.isIst();
        this.isMaybeIST = v.isMaybeIst();
        return game.getGame().ass.get(v.getTexture());
    }

    public boolean isIST() {
        return isIST;
    }

    public void setIST(boolean IST) {
        isIST = IST;
    }

    public boolean isMaybeIST() {
        return isMaybeIST;
    }

    public void setMaybeIST(boolean maybeIST) {
        isMaybeIST = maybeIST;
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

    public float getAccMaxLim() {
        return accMaxLim;
    }

    public void setAccMaxLim(float accMaxLim) {
        this.accMaxLim = accMaxLim;
    }
}
