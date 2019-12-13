package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Helpers.Builders.TextButtonBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localization;
import gdx.kapotopia.ScreenType;

public class MainMenu implements Screen {

    private Kapotopia game;
    private Stage stage;

    private Sound pauseSound;

    // Background
    private OrthographicCamera camera;
    private SpriteBatch backgroundBatch;
    private float stateTime;
    private Texture part_1, part_2, part_3, part_4;

    private static final String TAG = "Screen-MainMenu";

    public MainMenu(final Kapotopia game) {

        Gdx.app.log(TAG,"Entering MainMenu function");

        this.game = game;
        //final Image imgFond = new Image(AssetsManager.getInstance().getTextureByPath("EcranMenu/DessinMenuPrincipal.png")); //Test for backbutton
        stage = new Stage(game.viewport);

        // Background
        this.camera = new OrthographicCamera(game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        this.camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f,0); // I dont understand why, but this works. If someone knows plz explain me. F.D.
        this.camera.update();
        this.backgroundBatch = new SpriteBatch();
        stateTime = 0f;

        this.part_1 = AssetsManager.getInstance().getTextureByPath("EcranMenu/MenuPrincipalCadre1.png");
        this.part_2 = AssetsManager.getInstance().getTextureByPath("EcranMenu/MenuPrincipalCadre2.png");
        this.part_3 = AssetsManager.getInstance().getTextureByPath("EcranMenu/MenuPrincipalCadre3.png");
        this.part_4 = AssetsManager.getInstance().getTextureByPath("EcranMenu/MenuPrincipalBoutons.png");

        // Import sounds
        this.pauseSound = SoundHelper.getSound(UseSound.PAUSE);

        //Import fonts
        TextButton.TextButtonStyle style = FontHelper.getStyleFont(UseFont.AESTHETIC_NORMAL_WHITE);

        //setup Button
        final float x = game.viewport.getWorldWidth() / 2.6f;
        final float y = game.viewport.getWorldHeight();
        final TextButton world1 = new TextButtonBuilder(Localization.getInstance().getString("text_world1"))
                .withStyle(style).withPosition(x, y * 0.83f)
                .withListener(new ChangeScreenListener(game, ScreenType.WORLD1)).build();
        final TextButton world2 = new TextButtonBuilder(Localization.getInstance().getString("text_world2"))
                .withStyle(style).withPosition(x, y * 0.5f)
                .withListener(new ChangeScreenListener(game, ScreenType.WORLD2)).build();
        final TextButton world4 = new TextButtonBuilder(Localization.getInstance().getString("text_world4"))
                .withStyle(style).withPosition(x, y * 0.2f)
                .withListener(new ChangeScreenListener(game, ScreenType.WORLD4)).build();

        Label version = new LabelBuilder("v:" + Kapotopia.VERSION_NAME + " | code:" + Kapotopia.VERSION_CODE)
                .withStyle(UseFont.AESTHETIC_TINY_BLACK).withPosition(15, 0).build();

        //stage.addActor(imgFond);
        //add button to the scene
        stage.addActor(world1);
        stage.addActor(world2);
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

        camera.update();
        backgroundBatch.setProjectionMatrix(camera.combined);

        stateTime += delta;

        backgroundBatch.begin();
        backgroundBatch.draw(part_4, 0, 0);
        backgroundBatch.draw(part_3, 0, 0);
        backgroundBatch.draw(part_2, 0, 0);
        backgroundBatch.draw(part_1, 0, 0);
        backgroundBatch.end();

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
