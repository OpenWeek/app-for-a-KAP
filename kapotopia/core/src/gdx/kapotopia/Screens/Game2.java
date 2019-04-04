package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;

import java.util.LinkedList;

import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Utils;


public class Game2 implements Screen {

    private Kapotopia game;
    private Texture fond;
    private Stage stage;
    private final String GAME_PATH = "World1/Game2/";

    /**
     * Prepare images to fullScreen and hidden
     * @param img the image to prepare
     */
    private void prepareMockup(Image img) {
        img.setVisible(false);
        img.setWidth(game.viewport.getScreenWidth());
        img.setHeight(game.viewport.getScreenHeight());
    }

    public Game2(final Kapotopia game){

        this.game = game;
        fond = new Texture("FondNiveauBlanc2.png");
        Image imgFond = new Image(fond);
        stage = new Stage(game.viewport);

        //Creation the screen images
        final Image intro0 = new Image(new Texture(GAME_PATH + "20_board_0.png"));
        prepareMockup(intro0);
        intro0.setVisible(true);
        final Image intro1 = new Image(new Texture(GAME_PATH + "20_board_1.png"));
        prepareMockup(intro1);
        final Image game0 = new Image(new Texture(GAME_PATH + "21_board_0.png"));
        prepareMockup(game0);
        final Image outro0 = new Image(new Texture(GAME_PATH + "22_board_0.png"));
        prepareMockup(outro0);

        //Creation of buttons
        TextButton.TextButtonStyle style = Utils.getStyleFont("SEASRN__.ttf");
        float x = game.viewport.getWorldWidth() / 2.5f;
        float y = game.viewport.getWorldHeight() / 10;

        //Creation of button Play
        final Button btnPlay = new TextButton("Play", style);
        btnPlay.setPosition(x, y);
        btnPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //TODO
            }
        });
        btnPlay.setVisible(false);

        //Creation of button next
        final Button btnNext = new TextButton("Next", style);
        btnNext.setPosition(x, y);
        btnNext.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.vibrate(200);
                if (intro0.isVisible()) {
                    intro0.setVisible(false);
                    intro1.setVisible(true);
                } else if (intro1.isVisible()) {
                    intro1.setVisible(false);
                    game0.setVisible(true);
                    btnNext.setVisible(false);
                    //TODO est-ce que ça enlève le listener?
                    btnPlay.setVisible(true);
                }
            }
        });
        btnNext.setVisible(true);

        stage.addActor(imgFond);

        stage.addActor(intro0);
        stage.addActor(intro1);
        stage.addActor(game0);

        stage.addActor(btnNext);
        stage.addActor(btnPlay);

        Gdx.input.setInputProcessor(stage);
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

    }
}
