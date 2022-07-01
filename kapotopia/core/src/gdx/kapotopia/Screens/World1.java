package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.Helpers.Alignement;
import gdx.kapotopia.Helpers.Builders.ImageButtonBuilder;
import gdx.kapotopia.Helpers.Builders.TextButtonBuilder;
import gdx.kapotopia.Helpers.ChangeScreenListener;
import gdx.kapotopia.Helpers.StandardInputAdapter;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.ScreenType;

public class World1 implements Screen {

    private Kapotopia game;
    private Stage stage;

    private Sound pauseSound;

    public World1(final Kapotopia game) {
        this.game = game;
        Texture imgFondGame1 = game.ass.get(AssetDescriptors.WORLD1_GAME1);
        Texture imgFondGame2 = game.ass.get(AssetDescriptors.WORLD1_GAME2);
        ImageButton imgGame1 = new ImageButtonBuilder()
                .withImageUp(imgFondGame1)
                .withListener(new ChangeScreenListener(game, ScreenType.MOCKUPG1))
                .withBounds(0, game.viewport.getWorldHeight()/2, game.viewport.getWorldWidth(), game.viewport.getWorldHeight()/2)
                .build();
        ImageButton imgGame2 = new ImageButtonBuilder()
                .withImageUp(imgFondGame2)
                .withListener(new ChangeScreenListener(game, ScreenType.MOCKUPG2))
                .withBounds(0,0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight()/2)
                .build();
        stage = new Stage(game.viewport);

        // Import sounds
        this.pauseSound = game.ass.get(AssetDescriptors.SOUND_PAUSE);

        final Localisation loc = game.loc;

        final float y = game.viewport.getWorldHeight();
        TextButton game1 = new TextButtonBuilder(game, loc.getString("game1_button")).withStyle(FontHelper.AESTHETIC_NORMAL_WHITE)
                .withY(y * 0.57f).withAlignment(Alignement.CENTER)
                .withListener(new ChangeScreenListener(game, ScreenType.MOCKUPG1)).build();
        TextButton game2 = new TextButtonBuilder(game, loc.getString("game2_button")).withStyle(FontHelper.AESTHETIC_NORMAL_BLACK)
                .withY(y * 0.1f).withAlignment(Alignement.CENTER)
                .withListener(new ChangeScreenListener(game, ScreenType.MOCKUPG2)).build();

        stage.addActor(imgGame1);
        stage.addActor(imgGame2);
        stage.addActor(game1);
        stage.addActor(game2);
    }

    @Override
    public void show() {
        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(new StandardInputAdapter(this, game));
        im.addProcessor(stage);
        Gdx.input.setInputProcessor(im);
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
        pauseSound.play();
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
