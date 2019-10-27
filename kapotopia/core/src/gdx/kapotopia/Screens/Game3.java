package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import gdx.kapotopia.*;
import gdx.kapotopia.AssetsManager.AssetsManager;
import gdx.kapotopia.Game3.Core;
import gdx.kapotopia.Game3.EventHandlerGame3;

public class Game3 implements Screen {

    private Kapotopia game;
    private Stage stage;
    private boolean inGame;
    private Label res;

    private Sound successSound;

    private Core core;

    public Game3(final Kapotopia game) {

        this.game = game;
        inGame = true;

        core = new Core(this, 8,10, 2);

        EventHandlerGame3 eventHandlerGame3 = new EventHandlerGame3(core);

        InputMultiplexer iM = new InputMultiplexer();
        iM.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    game.changeScreen(ScreenType.WORLD2);
                    return true;
                }
                return false;
            }
        });
        iM.addProcessor(eventHandlerGame3);

        Gdx.input.setInputProcessor(iM);

        stage = new Stage(game.viewport);

        //stage.addActor(res);
        Image imgFond = new Image(AssetsManager.getInstance().getTextureByPath("game3/PorteNéonsBleus.png"));
        stage = new Stage(game.viewport);

        this.successSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/leszek-szary_success-1.wav");

        stage.addActor(imgFond);
        AssetsManager.getInstance().addStage(stage, "game3");
    }

    public void back(){
        if(core.playerSucceeded()) {
            this.successSound.play();
        }
        game.changeScreen(ScreenType.WORLD2);
    }

    @Override
    public void show() {
        //TODO Maybe Gdx.input.setInputProcessor(iM); needs to be here -> To check
        InputMultiplexer iM = new InputMultiplexer();
        iM.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    game.changeScreen(ScreenType.WORLD2);
                    return true;
                }
                return false;
            }
        });
        iM.addProcessor(new EventHandlerGame3(core));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        if(inGame) {
            core.draw();
        }
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
        AssetsManager.getInstance().disposeStage("game3");
    }

    void setUpInputProcessor() {
        EventHandlerGame3 eventHandlerGame3 = new EventHandlerGame3(core);
        Gdx.input.setCatchBackKey(true);
        InputMultiplexer iM = new InputMultiplexer();
        iM.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    game.changeScreen(ScreenType.WORLD2);
                    return true;
                }
                return false;
            }
        });
        iM.addProcessor(eventHandlerGame3);
        Gdx.input.setInputProcessor(iM);
    }
}
