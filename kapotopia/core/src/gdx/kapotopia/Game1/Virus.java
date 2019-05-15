package gdx.kapotopia.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import static gdx.kapotopia.Kapotopia.SCALLING_FACTOR_ENTITY;

public class Virus extends VirusAbstract {

    private Random random;

    public Virus(Rectangle bounds) {
        this.bounds = bounds;
        random = new Random();
        int randomTexture = random.nextInt() % TEXTURES_PATHS_IST.length;
        System.out.println("RandomTextureNumber :" + randomTexture + " generated from " + TEXTURES_PATHS_IST.length + " Textures");
        if (randomTexture < 0)
            randomTexture = 0;
        this.setTexture(new TextureRegion(new Texture(Gdx.files.internal(TEXTURES_PATHS_IST[randomTexture]))));
        this.setHeight(getTexture().getRegionHeight() >> SCALLING_FACTOR_ENTITY);
        this.setWidth(getTexture().getRegionWidth() >> SCALLING_FACTOR_ENTITY);
        this.setX(50);
        this.setY(bounds.getHeight());

        this.speed = 500;
    }

    // Méthode draw se trouve dans VirusAbstract

    public void act(float delta) {
        this.setY(this.getY() - speed * delta);
        if (this.getY() < -200) {
            this.setY(bounds.getHeight());
            this.setX(50 + 275 * random.nextInt(3) + 1);
        }
    }
}

