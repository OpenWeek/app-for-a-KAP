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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;

import gdx.kapotopia.AssetsManager;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.StandardInputAdapter;
import gdx.kapotopia.Utils;

public class mockupG1 implements Screen {

    private static final String MOCKUP_FOLDER = "World1/Game1/";

    private Kapotopia game;
    private Texture fond;
    private Stage stage;

    private Button play;
    private Button next;

    private Image mock1;
    private Image mock2;
    private Image mock3;
    private Image mock4;
    private Image mock5;
    private Image mock6;
    private Image imgFond;

    private Sound gameStart;
    private Sound changeScreenSound;
    private Sound pauseSound;
    /**
     * Prepare images to fullScreen and hidden
     * @param img the image to prepare
     */
    private void prepareMockup(Image img) {
        img.setVisible(false);
        img.setWidth(game.viewport.getWorldWidth());
        img.setHeight(game.viewport.getWorldHeight());
    }

    public mockupG1(final Kapotopia game) {
        this.game = game;
        this.fond = AssetsManager.getInstance().getTextureByPath("FondNiveauBlanc2.png");
        imgFond = new Image(fond);
        imgFond.setVisible(false);
        stage = new Stage(game.viewport);
        //Mock-up
        mock1 = new Image(AssetsManager.getInstance().getTextureByPath(MOCKUP_FOLDER +"w1_board_1.jpg"));
        prepareMockup(mock1);
        mock1.setVisible(true);
        mock2 = new Image(AssetsManager.getInstance().getTextureByPath(MOCKUP_FOLDER +"w1_board_2.jpg"));
        prepareMockup(mock2);
        mock3 = new Image(AssetsManager.getInstance().getTextureByPath(MOCKUP_FOLDER +"w1_board_3.jpg"));
        prepareMockup(mock3);
        mock4 = new Image(AssetsManager.getInstance().getTextureByPath(MOCKUP_FOLDER +"w1_board_4.jpg"));
        prepareMockup(mock4);
        mock5 = new Image(AssetsManager.getInstance().getTextureByPath(MOCKUP_FOLDER +"w11_board_0.jpg"));
        prepareMockup(mock5);
        mock6 = new Image(AssetsManager.getInstance().getTextureByPath(MOCKUP_FOLDER +"w12_board_0.jpg"));
        prepareMockup(mock6);
        //Import font
        TextButton.TextButtonStyle style = Utils.getStyleFont("SEASRN__.ttf");
        //Setup button

        // Sounds
        this.gameStart = AssetsManager.getInstance().getSoundByPath("sound/bruitage/plasterbrain_game-start.ogg");
        this.changeScreenSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/cmdrobot_videogame-jump.ogg");
        this.pauseSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/crisstanza_pause.mp3");

        // Buttons
        play = new TextButton("Play", style);
        next = new TextButton("Next", style);
        //// Play
        float xPlay = game.viewport.getWorldWidth() / 2.5f;
        float yPlay = game.viewport.getWorldHeight() / 2;
        play.setPosition(xPlay,yPlay);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.vibrate(200);
                gameStart.play();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        resetScreen();
                        game.changeScreen(ScreenType.GAME1);
                    }
                }, 2f);
            }
        });
        play.setVisible(false);
        //// Next
        float xNext = game.viewport.getWorldWidth() / 2.5f;
        float yNext = game.viewport.getWorldHeight() / 10;
        next.setPosition(xNext, yNext);
        next.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            Gdx.input.vibrate(50);
            if(mock1.isVisible()) {
                changeMockup(mock1,mock2);
            }else if(mock2.isVisible()) {
                changeMockup(mock2,mock3);
            }else if(mock3.isVisible()) {
                changeMockup(mock3,mock4);
            }else if(mock4.isVisible()) {
                changeMockup(mock4,mock5);
            }else if(mock5.isVisible()) {
                changeMockup(mock5,mock6);
            }else if(mock6.isVisible()) {
                changeMockup(mock6,imgFond);
                play.setVisible(true);
                next.setVisible(false);
            }
            }
        });

        stage.addActor(imgFond);

        stage.addActor(mock1);
        stage.addActor(mock2);
        stage.addActor(mock3);
        stage.addActor(mock4);
        stage.addActor(mock5);
        stage.addActor(mock6);

        //buttons
        stage.addActor(play);
        stage.addActor(next);

        AssetsManager.getInstance().addStage(stage, "mockupG1");
    }

    /**
     * Change from mockup m1 to mockup m2 and play a sound effect
     * @param m1 an Image file
     * @param m2 an Image file
     */
    private void changeMockup(Image m1, Image m2) {
        m1.setVisible(false);
        m2.setVisible(true);
        changeScreenSound.play();
    }

    private void resetScreen() {
        mock1.setVisible(true);
        next.setVisible(true);

        play.setVisible(false);
        mock2.setVisible(false);
        mock3.setVisible(false);
        mock4.setVisible(false);
        mock5.setVisible(false);
        mock6.setVisible(false);
        imgFond.setVisible(false);
    }

    @Override
    public void show() {
        setUpInputProcessor();
    }

    private void setUpInputProcessor() {
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
        resetScreen();
    }

    @Override
    public void dispose() {
        AssetsManager.getInstance().disposeStage("mockupG1");
    }
}
