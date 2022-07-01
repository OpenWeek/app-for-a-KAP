package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.Helpers.Alignement;
import gdx.kapotopia.Helpers.Builders.ImageButtonBuilder;
import gdx.kapotopia.Helpers.Builders.TextButtonBuilder;
import gdx.kapotopia.Helpers.ChangeScreenListener;
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
        Texture imgFondGame1 = game.ass.get(AssetDescriptors.MM1_W2);
        Texture imgFondGame2 = game.ass.get(AssetDescriptors.COVER_GAME4);

        final Localisation loc = game.loc;

        this.gameStart = game.ass.get(AssetDescriptors.SOUND_GAMESTART);

        float x = game.viewport.getWorldWidth();
        float y = game.viewport.getWorldHeight();

        final ImageButton imgButtonGame1 = new ImageButtonBuilder()
                .withImageUp(imgFondGame1)
                .withBounds(0, game.viewport.getWorldHeight()/2, game.viewport.getWorldWidth(), game.viewport.getWorldHeight()/2)
                .withListener(new ChangeScreenListener(game, ScreenType.MOCKUPG3) {}).build();
        final Button btnGame1 = new TextButtonBuilder(game, loc.getString("game3_button"))
                .withStyle(FontHelper.AESTHETIC_NORMAL_WHITE)
                .withY(y*0.57f).withAlignment(Alignement.CENTER)
                .withListener(new ChangeScreenListener(game,ScreenType.MOCKUPG3) {}).build();

        final ImageButton imgButtonGame2 = new ImageButtonBuilder()
                .withImageUp(imgFondGame2)
                .withBounds(0,0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight()/2)
                .withListener(new ChangeScreenListener(game, ScreenType.MOCKUPG4) {}).build();
        final Button btnGame2 = new TextButtonBuilder(game, loc.getString("game4_button"))
                .withStyle(FontHelper.AESTHETIC_NORMAL_WHITE)
                .withY(y*0.1f).withAlignment(Alignement.CENTER)
                .withListener(new ChangeScreenListener(game, ScreenType.MOCKUPG4) {}).build();

        stage = new Stage(game.viewport);

        stage.addActor(imgButtonGame1);
        stage.addActor(btnGame1);
        stage.addActor(imgButtonGame2);
        stage.addActor(btnGame2);
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
