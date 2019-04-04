package gdx.kapotopia.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Mireille2 extends Actor {

    private TextureRegion texture;

    public Mireille2() {

        texture = new TextureRegion(new Texture(Gdx.files.internal("Mireille.png")));
        this.setHeight(texture.getRegionHeight());
        this.setWidth(texture.getRegionWidth());
        this.setX(50);
        this.setY(50);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, this.getX(), this.getY());
    }

    public void act(float delta) {

    }
}

