package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;

import javax.rmi.CORBA.Util;

import gdx.kapotopia.AssetsManager;
import gdx.kapotopia.GameDifficulty;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.StandardInputAdapter;
import gdx.kapotopia.Utils;
import gdx.kapotopia.ValueGateway;

public class ChoosingDifficultyScreen implements Screen {
    // Basic variables
    private Kapotopia game;
    private Image fond;
    private Stage stage;

    private Sound clic;
    private Sound pauseSound;
    // Buttons
    private TextButton easyBtn;
    private TextButton mediumBtn;
    private TextButton hardBtn;
    private TextButton infiniteBtn;

    private static final String TAG = "difficultyScreen";

    public ChoosingDifficultyScreen(final Kapotopia game) {
        this.game = game;
        this.fond = new Image(AssetsManager.getInstance().getTextureByPath("FondNiveauBlanc2.png"));
        this.stage = new Stage(game.viewport);

        this.clic = AssetsManager.getInstance().getSoundByPath("sound/bruitage/kickhat_open-button-2.wav");
        this.pauseSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/crisstanza_pause.mp3");

        ScreenType nextScreen = (ScreenType) game.getTheValueGateway().removeFromTheStore("nextscreen");
        if(nextScreen == null)
            nextScreen = ScreenType.MAINMENU;
        final ScreenType finalNextScreen = nextScreen;

        // Buttons configuration
        TextButton.TextButtonStyle style = Utils.getStyleFont("COMMS.ttf");
        easyBtn = new TextButton("Facile", style);
        mediumBtn = new TextButton("Moyen", style);
        hardBtn = new TextButton("Difficile", style);
        infiniteBtn = new TextButton("Infini", style);

        final float x = game.viewport.getWorldWidth() / 2.6f;
        float y = game.viewport.getWorldHeight() * 0.2f;
        infiniteBtn.setPosition(x,y);
        y = game.viewport.getWorldHeight() * 0.4f;
        hardBtn.setPosition(x,y);
        y = game.viewport.getWorldHeight() * 0.6f;
        mediumBtn.setPosition(x,y);
        y = game.viewport.getWorldHeight() * 0.8f;
        easyBtn.setPosition(x,y);

        easyBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clic.play();
                game.getTheValueGateway().addToTheStore("difficulty", GameDifficulty.EASY);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.changeScreen(finalNextScreen);
                    }
                },2f);
            }
        });
        mediumBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clic.play();
                game.getTheValueGateway().addToTheStore("difficulty", GameDifficulty.MEDIUM);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.changeScreen(finalNextScreen);
                    }
                },2f);
            }
        });
        hardBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clic.play();
                game.getTheValueGateway().addToTheStore("difficulty", GameDifficulty.HARD);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.changeScreen(finalNextScreen);
                    }
                },2f);
            }
        });
        infiniteBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clic.play();
                game.getTheValueGateway().addToTheStore("difficulty", GameDifficulty.INFINITE);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.changeScreen(finalNextScreen);
                    }
                },2f);
            }
        });

        stage.addActor(fond);
        stage.addActor(easyBtn);
        stage.addActor(mediumBtn);
        stage.addActor(hardBtn);
        stage.addActor(infiniteBtn);

        AssetsManager.getInstance().addStage(stage, TAG);
    }

    @Override
    public void show() {
        setUpInputProcessor();
    }

    /**
     * Set up the input processor with the StandardInputAdapter
     */
    protected void setUpInputProcessor() {
        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(new StandardInputAdapter(this, game));
        im.addProcessor(stage);
        Gdx.input.setInputProcessor(im);
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

    }
}
