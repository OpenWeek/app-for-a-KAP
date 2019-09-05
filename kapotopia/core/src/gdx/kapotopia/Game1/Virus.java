package gdx.kapotopia.Game1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import gdx.kapotopia.AssetsManager;
import gdx.kapotopia.Screens.Game1;

public class Virus extends VirusAbstract {

    private Random random;
    private boolean isIST;
    private Game1 game;

    private float acceleration;

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
        this.setSpeed(500);
        this.acceleration = 1.00f;
    }

    // Méthode draw se trouve dans VirusAbstract

    public void act(float delta) {
        final float newY = this.getY() - (this.getSpeed() * delta * acceleration) ;
        this.setY(newY);
        this.updateCollision(this.getX(),newY);
        // If the virus has reached the end of the screen
        if (this.getY() < -200) {
            if(isIST()) {
                game.addMissedIST(getName());
            }
            this.setY(screenBounds.getHeight());
            this.setX(50 + 275 * random.nextInt(3));
            changeVirusType();
            this.acceleration += 0.08f;
        }
        game.setNewEnnemiLabelPosition(this.getX(), this.getY());
    }

    /**
     * Change le type de virus (texture et label)
     */
    public void changeVirusType() {
        this.setTexture(new TextureRegion(updateNewVirus()));
        game.changeEnnemiLabel(this.getName());
        System.out.println(this.getName());
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
}
