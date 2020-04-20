package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;

import gdx.kapotopia.*;
import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.Fonts.UseFont;
import gdx.kapotopia.Helpers.ImageHelper;
import gdx.kapotopia.Helpers.StandardInputAdapter;

public class World2 implements Screen {

    private Kapotopia game;
    private Stage stage;

    private Sound gameStart;

    public World2(final Kapotopia game) {

        this.game = game;
        Image imgFond = ImageHelper.getBackground(game.viewport,"game3/intro/Monde2Ecran1.png");
        stage = new Stage(game.viewport);

        stage.addActor(imgFond);

        this.gameStart = game.ass.get(AssetDescriptors.SOUND_GAMESTART);

        TextButton.TextButtonStyle style = FontHelper.getStyleFont(UseFont.AESTHETIC_NORMAL_WHITE);

        final Button play = new TextButton(Localisation.getInstance().getString("play_button"), style);
        float x = game.viewport.getWorldWidth() / 2.5f;
        float y = game.viewport.getWorldHeight() / 4;
        play.setPosition(x,y);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameStart.play();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.destroyScreen(ScreenType.WORLD2);
                        game.changeScreen(ScreenType.MOCKUPG3);
                    }
                },0.1f);
            }
        });

        stage.addActor(play);

        AssetsManager.getInstance().addStage(stage, "world2");
    }

    @Override
    public void show() {
        //In case there are problems to restart the game where it was left after going to another screen and returning, it could maybe be solved by setting the Input Processor (Gdx.input.setInputProcessor(iM);) here and not when the game is first created
        InputMultiplexer iM = new InputMultiplexer();
        iM.addProcessor(new StandardInputAdapter(this,game));
        iM.addProcessor(stage);

        Gdx.input.setInputProcessor(iM);
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
        AssetsManager.getInstance().disposeStage("world2");
    }
}
