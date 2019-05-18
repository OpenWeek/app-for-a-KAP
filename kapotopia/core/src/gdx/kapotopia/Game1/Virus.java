package gdx.kapotopia.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Virus extends VirusAbstract {

    private Random random;

    public Virus(Rectangle bounds) {
        this.screenBounds = bounds;
        random = new Random();

        builderHelper(getRandomTexture(),50,bounds.getHeight());
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
            this.setTexture(new TextureRegion(new Texture(Gdx.files.internal(getRandomTexture()))));
        }
    }

    private String getRandomTexture() {
        int randomTexture = Math.abs(random.nextInt() % TEXTURES_PATHS_IST.length);
        System.out.println("RandomTextureNumber :" + randomTexture + " generated from " + TEXTURES_PATHS_IST.length + " Textures");
        return TEXTURES_PATHS_IST[randomTexture];
    }
}

