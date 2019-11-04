package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.AssetsManaging.FontHelper;
import gdx.kapotopia.AssetsManaging.UsualFonts;
import gdx.kapotopia.Helpers.LabelBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localization;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.Utils;

public class MainMenu implements Screen {

    private Kapotopia game;
    private Stage stage;

    private Sound clic;
    private Sound pauseSound;

    private static final String TAG = "Screen-MainMenu";

    public MainMenu(final Kapotopia game) {

        Gdx.app.log(TAG,"Entering MainMenu function");

        this.game = game;
        //Image imgFond = new Image(AssetsManager.getInstance().getTextureByPath("FondNiveauBlanc2.png"));
        final Image imgFond = new Image(AssetsManager.getInstance().getTextureByPath("FondNiveauBlanc2.png")); //Test for backbutton
        stage = new Stage(game.viewport);

        // Import sounds
        this.clic = AssetsManager.getInstance().getSoundByPath("sound/bruitage/kickhat_open-button-2.wav");
        this.pauseSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/crisstanza_pause.mp3");

        //Import fonts
        TextButton.TextButtonStyle style = FontHelper.getStyleFont(UsualFonts.AESTHETIC_NORMAL_BLACK);

        //setup Button
        Button world1 = new TextButton(Localization.getInstance().getString("text_world1"), style);
        Button world2 = new TextButton(Localization.getInstance().getString("text_world2"), style);
        Button world3 = new TextButton(Localization.getInstance().getString("text_world3"), style);
        Button world4 = new TextButton(Localization.getInstance().getString("text_world4"), style);

        final float x = game.viewport.getWorldWidth() / 2.6f;
        float y = game.viewport.getWorldHeight() * 0.2f;
        world4.setPosition(x, y);
        y = game.viewport.getWorldHeight() * 0.4f;
        world3.setPosition(x, y);
        y = game.viewport.getWorldHeight() * 0.6f;
        world2.setPosition(x, y);
        y = game.viewport.getWorldHeight() * 0.8f;
        world1.setPosition(x, y);
        world1.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                clic.play();
                game.changeScreen(ScreenType.WORLD1);
            }
        });
        world2.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                clic.play();
                game.changeScreen(ScreenType.WORLD2);
            }
        });
        world3.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                clic.play();
                game.changeScreen(ScreenType.WORLD3);
            }
        });
        world4.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                clic.play();
                game.changeScreen(ScreenType.WORLD4);
            }
        });

        Label version = new LabelBuilder("v:" + Kapotopia.VERSION_NAME + " | code:" + Kapotopia.VERSION_CODE)
                .withStyle(UsualFonts.AESTHETIC_TINY_BLACK).withPosition(15, 0).build();

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
