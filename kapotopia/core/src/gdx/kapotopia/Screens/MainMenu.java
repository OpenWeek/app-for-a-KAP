package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.AssetsManaging.FontHelper;
import gdx.kapotopia.AssetsManaging.SoundHelper;
import gdx.kapotopia.AssetsManaging.UseFont;
import gdx.kapotopia.AssetsManaging.UseSound;
import gdx.kapotopia.Helpers.ChangeScreenListener;
import gdx.kapotopia.Helpers.LabelBuilder;
import gdx.kapotopia.Helpers.TextButtonBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localization;
import gdx.kapotopia.ScreenType;

public class MainMenu implements Screen {

    private Kapotopia game;
    private Stage stage;

    private Sound pauseSound;

    private static final String TAG = "Screen-MainMenu";

    public MainMenu(final Kapotopia game) {

        Gdx.app.log(TAG,"Entering MainMenu function");

        this.game = game;
        final Image imgFond = new Image(AssetsManager.getInstance().getTextureByPath("DessinMenuPrincipal.png")); //Test for backbutton
        stage = new Stage(game.viewport);

        // Import sounds
        this.pauseSound = SoundHelper.getSound(UseSound.PAUSE);

        //Import fonts
        TextButton.TextButtonStyle style = FontHelper.getStyleFont(UseFont.AESTHETIC_NORMAL_BLACK);

        //setup Button
        final float x = game.viewport.getWorldWidth() / 2.6f;
        final float y = game.viewport.getWorldHeight();
        final TextButton world1 = new TextButtonBuilder(Localization.getInstance().getString("text_world1"))
                .withStyle(UseFont.AESTHETIC_NORMAL_WHITE).withPosition(x, y * 0.8f)
                .withListener(new ChangeScreenListener(game, ScreenType.WORLD1)).build();
        final TextButton world2 = new TextButtonBuilder(Localization.getInstance().getString("text_world2"))
                .withStyle(style).withPosition(x, y * 0.6f)
                .withListener(new ChangeScreenListener(game, ScreenType.WORLD2)).build();
        final TextButton world3 = new TextButtonBuilder(Localization.getInstance().getString("text_world3"))
                .withStyle(style).withPosition(x, y * 0.4f)
                .withListener(new ChangeScreenListener(game, ScreenType.WORLD3)).build();
        final TextButton world4 = new TextButtonBuilder(Localization.getInstance().getString("text_world4"))
                .withStyle(style).withPosition(x, y * 0.2f)
                .withListener(new ChangeScreenListener(game, ScreenType.WORLD4)).build();

        Label version = new LabelBuilder("v:" + Kapotopia.VERSION_NAME + " | code:" + Kapotopia.VERSION_CODE)
                .withStyle(UseFont.AESTHETIC_TINY_BLACK).withPosition(15, 0).build();

        stage.addActor(imgFond);
        //add button to the scene
        stage.addActor(world1);
        stage.addActor(world2);
        stage.addActor(world3);
        stage.addActor(world4);
        stage.addActor(version);

        AssetsManager.getInstance().addStage(stage, "mainmenu");
    }
    @Override
    public void show() {
        Gdx.app.log(TAG,"Entering show function");
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        this.pauseSound.play();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        AssetsManager.getInstance().disposeStage("mainmenu");
    }
}
