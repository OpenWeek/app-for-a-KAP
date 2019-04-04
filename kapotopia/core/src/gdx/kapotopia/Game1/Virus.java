package gdx.kapotopia.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;

public class Virus extends Actor {
    private TextureRegion texture;
    private float speed = 500;
    private Rectangle bounds;

    public Virus() {

        texture = new TextureRegion(new Texture(Gdx.files.internal("virus1.png")));
        this.setHeight(texture.getRegionHeight());
        this.setWidth(texture.getRegionWidth());
        this.setX(50);
        this.setY(1920);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, this.getX(), this.getY());
    }

    public void act(float delta) {
        this.setY(this.getY()-speed*delta);
        if (this.getY() < 0)
        {
            Random random = new Random();
            this.setY(1920);
            this.setX(50+275*random.nextInt(3) + 1);
        }


    }
}

