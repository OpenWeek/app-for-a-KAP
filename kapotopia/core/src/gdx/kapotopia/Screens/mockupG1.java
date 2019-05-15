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
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Utils;

public class mockupG1 implements Screen {

    private static final String MOCKUP_FOLDER = "World1/Game1/";

    private Kapotopia game;
    private Texture fond;
    private Stage stage;
    /**
     * Prepare images to fullScreen and hidden
     * @param img the image to prepare
     */
    private void prepareMockup(Image img) {
        img.setVisible(false);
        img.setWidth(game.viewport.getScreenWidth());
        img.setHeight(game.viewport.getScreenHeight());
    }

    public mockupG1(final Kapotopia game) {
        this.game = game;
        fond = new Texture("FondNiveauBlanc2.png");
        final Image imgFond = new Image(fond);
        imgFond.setVisible(false);
        stage = new Stage(game.viewport);
        //Mock-up
        final Image mock1 = new Image(new Texture(MOCKUP_FOLDER +"w1_board_1.jpg"));
        prepareMockup(mock1);
        mock1.setVisible(true);
        final Image mock2 = new Image(new Texture(MOCKUP_FOLDER + "w1_board_2.jpg"));
        prepareMockup(mock2);
        final Image mock3 = new Image(new Texture(MOCKUP_FOLDER + "w1_board_3.jpg"));
        prepareMockup(mock3);
        final Image mock4 = new Image(new Texture(MOCKUP_FOLDER + "w1_board_4.jpg"));
        prepareMockup(mock4);
        final Image mock5 = new Image(new Texture(MOCKUP_FOLDER + "w11_board_0.jpg"));
        prepareMockup(mock5);
        final Image mock6 = new Image(new Texture(MOCKUP_FOLDER + "w12_board_0.jpg"));
        prepareMockup(mock6);
        //Import font
        TextButton.TextButtonStyle style = Utils.getStyleFont("SEASRN__.ttf");
        //Setup button

        final Button play = new TextButton("Play", style);
        float xPlay = game.viewport.getWorldWidth() / 2.5f;
        float yPlay = game.viewport.getWorldHeight() / 2;
        play.setPosition(xPlay,yPlay);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.vibrate(200);
                play.setVisible(false);
                game.setScreen(new Game1(game));
                dispose();
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
                mock1.setVisible(false);
                mock2.setVisible(true);
                dispose();
            }else if(mock2.isVisible()) {
                mock2.setVisible(false);
                mock3.setVisible(true);
                dispose();
            }else if(mock3.isVisible()) {
                mock3.setVisible(false);
                mock4.setVisible(true);
                dispose();
            }else if(mock4.isVisible()) {
                mock4.setVisible(false);
                mock5.setVisible(true);
                dispose();
            }else if(mock5.isVisible()) {
                mock5.setVisible(false);
                mock6.setVisible(true);
                dispose();
            }else if(mock6.isVisible()) {
                mock6.setVisible(false);
                imgFond.setVisible(true);
                play.setVisible(true);
                next.setVisible(false);
                dispose();
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
        //fond.dispose();
        //stage.dispose();
    }
}
