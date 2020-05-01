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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.Helpers.Builders.TextButtonBuilder;
import gdx.kapotopia.Helpers.ImageHelper;
import gdx.kapotopia.Helpers.StandardInputAdapter;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.ScreenType;

public class World2 implements Screen {

    private Kapotopia game;
    private Stage stage;

    private Sound gameStart;

    public World2(final Kapotopia game) {

        this.game = game;
        Image imgFond = ImageHelper.getBackground(game.viewport,game.ass.get(AssetDescriptors.MM_W2));
        stage = new Stage(game.viewport);

        stage.addActor(imgFond);

        this.gameStart = game.ass.get(AssetDescriptors.SOUND_GAMESTART);

        float x = game.viewport.getWorldWidth() / 2.5f;
        float y = game.viewport.getWorldHeight() / 4;
        final Button play = new TextButtonBuilder(game, game.loc.getString("play_button"))
                .withStyle(FontHelper.AESTHETIC_NORMAL_WHITE).withPosition(x,y).withListener(new ChangeListener() {
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
                }).build();

        stage.addActor(play);
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
        stage.dispose();
    }
}
