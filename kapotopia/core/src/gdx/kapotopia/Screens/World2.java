package gdx.kapotopia.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Utils;

public class World2 implements Screen {

    private Kapotopia game;
    private Texture fond;
    private Stage stage;

    public World2(final Kapotopia game) {

        this.game = game;
        fond = new Texture("FondNiveauBlanc2.png");
        Image imgFond = new Image(fond);
        stage = new Stage(game.viewport);

        stage.addActor(imgFond);


        TextButton.TextButtonStyle style = Utils.getStyleFont("SEASRN__.ttf");

        Button play = new TextButton("Play", style);
        float x = game.viewport.getWorldWidth() / 2.5f;
        float y = game.viewport.getWorldHeight() / 2;
        play.setPosition(x,y);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Game3(game));
                dispose();
            }
        });

        stage.addActor(play);
        InputMultiplexer iM = new InputMultiplexer();
        iM.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    dispose();
                    game.setScreen(new MainMenu(game));
                    return true;
                }
                return false;
            }
        });
        iM.addProcessor(stage);

        Gdx.input.setInputProcessor(iM);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        fond.dispose();
        stage.dispose();
    }
}
