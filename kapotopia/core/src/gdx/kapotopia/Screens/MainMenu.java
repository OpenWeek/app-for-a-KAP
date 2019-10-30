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

import gdx.kapotopia.AssetsManager.AssetsManager;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.Utils;

public class MainMenu implements Screen {

    private Kapotopia game;
    private Stage stage;

    private Sound clic;

    private static final String TAG = "Screen-MainMenu";

    public MainMenu(final Kapotopia game) {

        Gdx.app.log(TAG,"Entering MainMenu function");

        this.game = game;
        //Image imgFond = new Image(AssetsManager.getInstance().getTextureByPath("FondNiveauBlanc2.png"));
        final Image imgFond = new Image(AssetsManager.getInstance().getTextureByPath("FondNiveauBlanc2.png")); //Test for backbutton
        stage = new Stage(game.viewport);

        this.clic = AssetsManager.getInstance().getSoundByPath("sound/bruitage/kickhat_open-button-2.wav");
        //Import font
        TextButton.TextButtonStyle style = Utils.getStyleFont("SEASRN__.ttf");
        TextButton.TextButtonStyle styleTiny = Utils.getStyleFont("SEASRN__.ttf", 20);
        //setup Button


        Button world1 = new TextButton("World 1", style);
        Button world2 = new TextButton("World 2", style);
        Button world3 = new TextButton("World 3", style);
        Button world4 = new TextButton("World 4", style);

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

        Label version = new Label("v:" + Kapotopia.VERSION_NAME + " | code:" + Kapotopia.VERSION_CODE, new Label.LabelStyle(styleTiny.font, Color.BLACK));
        version.setPosition(15, 0);

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
