package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Fonts.UseFont;
import gdx.kapotopia.Helpers.Alignement;
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
        Image imgFond = new Image(AssetsManager.getInstance().getTextureByPath("EcranMenu/World1EcranMenu.png"));
        stage = new Stage(game.viewport);
        stage.addActor(imgFond);

        // Import sounds
        this.pauseSound = game.ass.get(AssetDescriptors.SOUND_PAUSE);

        final Localisation loc = Localisation.getInstance();

        final float y = game.viewport.getWorldHeight();
        TextButton game1 = new TextButtonBuilder(loc.getString("game1_button")).withStyle(UseFont.AESTHETIC_NORMAL_WHITE)
                .withY(y * 0.57f).withAlignment(Alignement.CENTER)
                .withListener(new ChangeScreenListener(game, ScreenType.MOCKUPG1)).build();
        TextButton game2 = new TextButtonBuilder(loc.getString("game2_button")).withStyle(UseFont.AESTHETIC_NORMAL_WHITE)
                .withY(y * 0.1f).withAlignment(Alignement.CENTER)
                .withListener(new ChangeScreenListener(game, ScreenType.MOCKUPG2)).build();

        stage.addActor(imgFond);
        stage.addActor(game1);
        stage.addActor(game2);

        AssetsManager.getInstance().addStage(stage, "world1");
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
        AssetsManager.getInstance().disposeStage("world1");
    }
}
