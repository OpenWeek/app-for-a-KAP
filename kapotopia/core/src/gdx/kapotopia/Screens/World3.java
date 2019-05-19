package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import gdx.kapotopia.AssetsManager;
import gdx.kapotopia.Game1.MireilleBasic;
import gdx.kapotopia.Game1.Virus;
import gdx.kapotopia.Kapotopia;

public class World3 implements Screen {

    private Kapotopia game;
    private Texture fond;
    private Stage stage;
    private MireilleBasic mireille;
    private Virus ennemi;

    public World3(final Kapotopia game) {

        this.game = game;
        fond = AssetsManager.getInstance().getTextureByPath("FondNiveauBlanc2.png");
        Image imgFond = new Image(fond);
        stage = new Stage(game.viewport);
        stage.addActor(imgFond);
        ennemi = new Virus(new Rectangle(0,0,game.viewport.getScreenWidth(),game.viewport.getScreenHeight()));
        stage.addActor(ennemi);
        mireille = new MireilleBasic();
        mireille.addListener(new ActorGestureListener() {
            public void fling (InputEvent event, float velocityX, float velocityY, int button) {
                if (velocityX > 0)
                {
                    mireille.setX(Math.min((mireille.getX()+275),600));
                }
                else {
                    mireille.setX(Math.max((mireille.getX()-275),50));
                }
                System.out.println("swipe!! " + velocityX + ", " + velocityY);
            }
        });
        mireille.addListener(new ChangeListener() {
                                 @Override
                                 public void changed(ChangeEvent event, Actor actor) {
                                     System.out.println("change");

                                 }
                             });
        stage.addActor(mireille);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
