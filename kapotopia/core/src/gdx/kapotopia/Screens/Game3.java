package gdx.kapotopia.Screens;

import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;
import gdx.kapotopia.Game3.Core;
import gdx.kapotopia.Game3.EventHandlerGame3;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Utils;
import gdx.kapotopia.AssetsManager;
import gdx.kapotopia.ScreenType;

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
        Gdx.input.setCatchBackKey(true);

        TextButton.TextButtonStyle style = Utils.getStyleFont("SEASRN__.ttf");

        res = new Label("", new Label.LabelStyle(style.font, Color.BLACK));

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

        stage = new Stage(game.viewport);

        stage.addActor(res);
        Image imgFond = new Image(AssetsManager.getInstance().getTextureByPath("FondNiveauBlanc2.png"));
        stage = new Stage(game.viewport);

        this.successSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/leszek-szary_success-1.wav");

        core = new Core(this, 8,10, 3);

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
        setUpInputProcessor();
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

    private void setUpInputProcessor() {
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
