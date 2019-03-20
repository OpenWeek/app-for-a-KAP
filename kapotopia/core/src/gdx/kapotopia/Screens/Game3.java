package gdx.kapotopia.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;
import gdx.kapotopia.Game3.Core;
import gdx.kapotopia.Game3.EventHandlerGame3;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Utils;

public class Game3 implements Screen {

    private Kapotopia game;
    private Texture fond;
    private Stage stage;
    private boolean inGame;
    private Label res;

    private Core core;

    public Game3(final Kapotopia game) {

        this.game = game;
        inGame = true;

        core = new Core(this, 8,10, 3);

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

        fond = new Texture("FondNiveauBlanc2.png");
        Image imgFond = new Image(fond);
        stage = new Stage(game.viewport);

        stage.addActor(imgFond);
    }

    public void back(){
        dispose();
        game.setScreen(new World2(game));
    }

    public void result(boolean won){
        I18NBundle languageStrings = I18NBundle.createBundle(Gdx.files.internal("strings/strings"));
        String result;
        inGame = false;
        if(won){
            result = languageStrings.get("game3_win");
        }
        else {
            result = languageStrings.get("game3_loose");
        }

        res.setText(result);
        float x = game.viewport.getWorldWidth() / 2.5f;
        float y = game.viewport.getWorldHeight() / 2;
        res.setPosition(x,y);
    }

    @Override
    public void show() {

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
        fond.dispose();
        stage.dispose();
    }
}
