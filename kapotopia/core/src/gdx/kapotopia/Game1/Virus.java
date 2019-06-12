package gdx.kapotopia.Game1;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import gdx.kapotopia.Screens.Game1;

public class Virus extends VirusAbstract {

    private Random random;
    private String name;
    private boolean isIST;
    private Game1 game;

    public Virus(Rectangle bounds, Game1 game) {
        this.screenBounds = bounds;
        this.game = game;
        random = new Random();
        if(game == null) {
            builderHelper(null,50,bounds.getHeight());
        }else{
            builderHelper(game.getRdmVirusTexture(VIRUS_TYPE.getRandomType()),50,bounds.getHeight());
        }
        this.setSpeed(500);
    }

    // Méthode draw se trouve dans VirusAbstract

    public void act(float delta) {
        final float newY = this.getY() - this.getSpeed() * delta;
        this.setY(newY);
        this.updateCollision(this.getX(),newY);
        if (this.getY() < -200) {
            this.setY(screenBounds.getHeight());
            this.setX(50 + 275 * random.nextInt(3) + 1);

            this.setTexture(new TextureRegion(game.getRdmVirusTexture(VIRUS_TYPE.getRandomType())));
        }
    }
}

