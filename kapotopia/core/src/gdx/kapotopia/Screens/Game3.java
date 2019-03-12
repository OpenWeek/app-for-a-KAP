package gdx.kapotopia.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import gdx.kapotopia.Game3.Core;
import gdx.kapotopia.Game3.EventHandlerGame3;
import gdx.kapotopia.Kapotopia;

public class Game3 implements Screen {

    private Kapotopia game;
    private Texture fond;
    private Stage stage;

    private Core core;

    public Game3(final Kapotopia game) {

        this.game = game;
        fond = new Texture("FondNiveauBlanc2.png");
        Image imgFond = new Image(fond);
        stage = new Stage(game.viewport);

        core = new Core(this, 8,10, 3);


        stage.addActor(imgFond);

        EventHandlerGame3 eventHandlerGame3 = new EventHandlerGame3(core);
        Gdx.input.setCatchBackKey(true);

        InputMultiplexer iM = new InputMultiplexer();
        iM.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    dispose();
                    game.setScreen(new World2(game));
                    return true;
                }
                return false;
            }
        });
        iM.addProcessor(eventHandlerGame3);

        Gdx.input.setInputProcessor(iM);


    }

    public void back(){
        dispose();
        game.setScreen(new World2(game));
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        core.draw();
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