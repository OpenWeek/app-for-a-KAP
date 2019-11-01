package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;

import gdx.kapotopia.AssetsManager.AssetsManager;
import gdx.kapotopia.GameDifficulty;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.Helpers.StandardInputAdapter;
import gdx.kapotopia.Utils;

public class ChoosingDifficultyScreen implements Screen {
    // Basic variables
    private Kapotopia game;
    private Stage stage;

    private Sound clic;
    private Sound clicBlockedSound;
    private Sound pauseSound;

    private static final String TAG = "difficultyScreen";

    public ChoosingDifficultyScreen(final Kapotopia game) {
        this.game = game;
        Image fond = new Image(AssetsManager.getInstance().getTextureByPath("FondNiveauBlanc2.png"));
        this.stage = new Stage(game.viewport);

        this.clic = AssetsManager.getInstance().getSoundByPath("sound/bruitage/kickhat_open-button-2.wav");
        this.clicBlockedSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/dland_hint.wav");
        this.pauseSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/crisstanza_pause.mp3");

        ScreenType nextScreen = (ScreenType) game.getTheValueGateway().removeFromTheStore("nextscreen");
        if(nextScreen == null)
            nextScreen = ScreenType.MAINMENU;
        final ScreenType finalNextScreen = nextScreen;
        UnlockedLevel ul_tmp = (UnlockedLevel) game.getTheValueGateway().removeFromTheStore("unlockedLevel");
        final UnlockedLevel unlockedLevel;
        if(ul_tmp == null)
            unlockedLevel = UnlockedLevel.HARD_UNLOCKED;
        else
            unlockedLevel = ul_tmp;

        // Buttons configuration
        TextButton.TextButtonStyle styleNormal = Utils.getStyleFont("COMMS.ttf");
        TextButton.TextButtonStyle styleGrey = Utils.getStyleFont("COMMS.ttf", 60, Color.GRAY);

        TextButton easyBtn = new TextButton("Facile", styleNormal);
        TextButton mediumBtn;
        TextButton hardBtn;
        TextButton infiniteBtn;
        switch (unlockedLevel) {
            case EASY_UNLOCKED:
                mediumBtn = new TextButton("Moyen", styleGrey);;
                hardBtn = new TextButton("Difficile", styleGrey);;
                infiniteBtn = new TextButton("Infini", styleGrey);;
                break;
            case MEDI_UNLOCKED:
                mediumBtn = new TextButton("Moyen", styleNormal);;
                hardBtn = new TextButton("Difficile", styleGrey);;
                infiniteBtn = new TextButton("Infini", styleNormal);;
                break;
            case HARD_UNLOCKED:
                mediumBtn = new TextButton("Moyen", styleNormal);;
                hardBtn = new TextButton("Difficile", styleNormal);;
                infiniteBtn = new TextButton("Infini", styleNormal);;
                break;
            default:
                mediumBtn = new TextButton("Moyen", styleNormal);;
                hardBtn = new TextButton("Difficile", styleNormal);;
                infiniteBtn = new TextButton("Infini", styleNormal);;
                break;
        }


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
                Gdx.input.vibrate(50);
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
                Gdx.input.vibrate(50);
                if(unlockedLevel == UnlockedLevel.EASY_UNLOCKED) {
                    clicBlockedSound.play();
                }else{
                    clic.play();
                    game.getTheValueGateway().addToTheStore("difficulty", GameDifficulty.MEDIUM);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            game.changeScreen(finalNextScreen);
                        }
                    },2f);
                }

            }
        });
        hardBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.vibrate(50);
                if(unlockedLevel == UnlockedLevel.EASY_UNLOCKED || unlockedLevel == UnlockedLevel.MEDI_UNLOCKED) {
                    clicBlockedSound.play();
                }else{
                    clic.play();
                    game.getTheValueGateway().addToTheStore("difficulty", GameDifficulty.HARD);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            game.changeScreen(finalNextScreen);
                        }
                    },2f);
                }

            }
        });
        infiniteBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.vibrate(50);
                if(unlockedLevel == UnlockedLevel.EASY_UNLOCKED) {
                    clicBlockedSound.play();
                }else {
                    clic.play();
                    game.getTheValueGateway().addToTheStore("difficulty", GameDifficulty.INFINITE);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            game.changeScreen(finalNextScreen);
                        }
                    },2f);
                }

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
