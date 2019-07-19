package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
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
import gdx.kapotopia.Utils;

public class mockupG1 implements Screen {

    private static final String MOCKUP_FOLDER = "World1/Game1/";

    private Kapotopia game;
    private Texture fond;
    private Stage stage;

    private Sound gameStart;
    private Sound changeScreenSound;
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
        final Image imgFond = new Image(fond);
        imgFond.setVisible(false);
        stage = new Stage(game.viewport);
        //Mock-up
        final Image mock1 = new Image(AssetsManager.getInstance().getTextureByPath(MOCKUP_FOLDER +"w1_board_1.jpg"));
        prepareMockup(mock1);
        mock1.setVisible(true);
        final Image mock2 = new Image(AssetsManager.getInstance().getTextureByPath(MOCKUP_FOLDER +"w1_board_2.jpg"));
        prepareMockup(mock2);
        final Image mock3 = new Image(AssetsManager.getInstance().getTextureByPath(MOCKUP_FOLDER +"w1_board_3.jpg"));
        prepareMockup(mock3);
        final Image mock4 = new Image(AssetsManager.getInstance().getTextureByPath(MOCKUP_FOLDER +"w1_board_4.jpg"));
        prepareMockup(mock4);
        final Image mock5 = new Image(AssetsManager.getInstance().getTextureByPath(MOCKUP_FOLDER +"w11_board_0.jpg"));
        prepareMockup(mock5);
        final Image mock6 = new Image(AssetsManager.getInstance().getTextureByPath(MOCKUP_FOLDER +"w12_board_0.jpg"));
        prepareMockup(mock6);
        //Import font
        TextButton.TextButtonStyle style = Utils.getStyleFont("SEASRN__.ttf");
        //Setup button

        this.gameStart = AssetsManager.getInstance().getSoundByPath("sound/bruitage/plasterbrain__game-start.ogg");
        this.changeScreenSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/cmdrobot__text-message-or-videogame-jump.ogg");

        final Button play = new TextButton("Play", style);
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
                        play.setVisible(false);
                        game.changeScreen(ScreenType.GAME1);
                    }
                }, 2f);
            }
        });
        play.setVisible(false);

        final Button next = new TextButton("Next", style);
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
        Gdx.input.setInputProcessor(stage);
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

    @Override
    public void show() {

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
