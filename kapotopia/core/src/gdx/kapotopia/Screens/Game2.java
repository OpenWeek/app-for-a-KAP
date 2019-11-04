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

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localization;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.Utils;

public class Game2 implements Screen {

    private Kapotopia game;
    private Stage stage;

    private Sound successSound;
    private Sound nextSound;

    private final String GAME_PATH = "World1/Game2/";

    private static final String TAG = "Screens-Game2";

    /**
     * Prepare images to fullScreen and hidden
     * @param img the image to prepare
     */
    private void prepareMockup(Image img) {
        img.setVisible(false);
        img.setWidth(game.viewport.getWorldWidth());
        img.setHeight(game.viewport.getWorldHeight());
    }

    public Game2(final Kapotopia game){

        Gdx.app.log(TAG,"Entering Game2 function");

        this.game = game;
        Image imgBckground = new Image(AssetsManager.getInstance().getTextureByPath("FondNiveauBlanc2.png"));
        stage = new Stage(game.viewport);

        // Sounds and Music
        this.successSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/leszek-szary_success-1.wav");
        this.nextSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/cmdrobot_videogame-jump.ogg");

        //Creation the screen images
        /*final Image intro0 = new Image(new Texture(GAME_PATH + "20_board_0.png"));
        prepareMockup(intro0);
        intro0.setVisible(true);
        final Image intro1 = new Image(new Texture(GAME_PATH + "20_board_1.png"));
        prepareMockup(intro1);*/
        final Image game0 = new Image(new Texture(GAME_PATH + "21_board_0.jpg"));
        prepareMockup(game0);
        game0.setVisible(true);
        final Image outro0 = new Image(new Texture(GAME_PATH + "22_board_0.jpg"));
        prepareMockup(outro0);

        //Creation of buttons
        TextButton.TextButtonStyle style = Utils.getStyleFont("SEASRN__.ttf");
        float x = game.viewport.getWorldWidth() / 2.5f;
        float y = game.viewport.getWorldHeight() / 10;
        float xRight = game.viewport.getWorldWidth() / 1.5f;

        //Creation of button Play
        final Button btnPlay = new TextButton(Localization.getInstance().getString("play_button"), style);
        btnPlay.setPosition(x, y);
        btnPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                successSound.play();
                //TODO
            }
        });
        btnPlay.setVisible(false);

        final Button btnBack = new TextButton(Localization.getInstance().getString("back_menu_button"),style);
        //btnBack = new TextButton("Back to Menu",style); //Unsuccessfull test to use menu after using back button
        btnBack.setPosition(xRight ,y);
        btnBack.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                nextSound.play();
                Gdx.input.vibrate(200);
                //dispose();
                game.changeScreen(ScreenType.MAINMENU);
            }
        });
        btnBack.setVisible(false);

        //Creation of button next
        /*final Button btnNext = new TextButton("Next", style);
        btnNext.setPosition(x, y);
        btnNext.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                nextSound.play();
                Gdx.input.vibrate(200);
                if (intro0.isVisible()) {
                    intro0.setVisible(false);
                    intro1.setVisible(true);
                } else if (intro1.isVisible()) {
                    intro1.setVisible(false);
                    game0.setVisible(true);
                    btnNext.setVisible(false);
                    btnPlay.setVisible(true);
                    btnBack.setVisible(true);
                }
            }
        });
        btnNext.setVisible(true);*/

        stage.addActor(imgBckground);

        //stage.addActor(intro0);
        //stage.addActor(intro1);
        stage.addActor(game0);

        //stage.addActor(btnNext);
        stage.addActor(btnPlay);
        stage.addActor(btnBack);

        AssetsManager.getInstance().addStage(stage, "game2");
    }

    @Override
    public void show() {
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
        AssetsManager.getInstance().disposeStage("game2");
    }
}
