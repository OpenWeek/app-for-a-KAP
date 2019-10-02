package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.HashSet;
import java.util.LinkedList;

import gdx.kapotopia.AssetsManager;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.StandardInputAdapter;
import gdx.kapotopia.Utils;

public class BilanG1 implements Screen {
    // Basic variables
    private Kapotopia game;
    private Texture fond;
    private Stage stage;
    TextButton.TextButtonStyle style;

    private HashSet<String> missedIsts;

    private Button next;

    // Labels
    private LinkedList<Label> istsToShow;
    private Label intro;
    private int pointeur;

    // Images
    private Image imgFond;

    // Sounds
    private Sound fail;
    private Sound pauseSound;
    private Sound openSound;

    // Constantes
    private static final String TAG = "BilangG1";

    public BilanG1(final Kapotopia game) {
        this.game = game;
        this.fond = AssetsManager.getInstance().getTextureByPath("FondNiveauBlanc2.png");
        this.stage = new Stage(game.viewport);
        this.style = Utils.getStyleFont("SEASRN__.ttf");

        this.imgFond = new Image(fond);
        imgFond.setVisible(true);
        stage.addActor(imgFond);

        this.missedIsts = (HashSet) game.getTheValueGateway().removeFromTheStore("G1-missedIST");
        if(missedIsts == null) {
            changeToMainMenu();
            return;
        }
        if(missedIsts.isEmpty()) {
            changeToMainMenu();
            return;
        }

        // ists to show
        this.istsToShow = new LinkedList<Label>();
        for (String ist : missedIsts) {
            final Label l = new Label(ist, new Label.LabelStyle(style.font, Color.BLACK));
            istsToShow.add(l);
            l.setVisible(false);
            final float xNext = game.viewport.getWorldWidth() / 3f;
            final float yNext = game.viewport.getWorldHeight() / 2f;
            l.setPosition(xNext, yNext);
            stage.addActor(l);
        }
        pointeur = 0;

        // Intro text
        this.intro = new Label("Bien joué ! Mais vous avez oublié d'attraper les ists suivant",
                new Label.LabelStyle(style.font, Color.BLACK));
        intro.setPosition(game.viewport.getWorldWidth() / 9f,
                game.viewport.getWorldHeight() / 1.2f);
        intro.setWidth(game.viewport.getWorldWidth() / 1.3f);
        intro.setWrap(true);
        stage.addActor(intro);

        // Button
        next = new TextButton("Next", style);
        final float xNext = game.viewport.getWorldWidth() / 2.5f;
        final float yNext = game.viewport.getWorldHeight() / 8f;
        next.setPosition(xNext, yNext);
        next.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.vibrate(50);
                if(istsToShow.size() <= pointeur) {
                    changeToMainMenu();
                }else{
                    if(pointeur == 0) {
                        final Label l = istsToShow.getFirst();
                        l.setVisible(true);
                        intro.setVisible(false);
                    } else {
                        istsToShow.get(pointeur-1).setVisible(false);
                        final Label l = istsToShow.get(pointeur);
                        l.setVisible(true);
                    }
                    openSound.play();
                    pointeur++;
                }
            }
        });
        stage.addActor(next);

        // Sounds
        this.fail = AssetsManager.getInstance().getSoundByPath("sound/bruitage/littlerainyseasons_fail.mp3");
        this.pauseSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/crisstanza_pause.mp3");
        this.openSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/cmdrobot_videogame-jump.ogg");

        AssetsManager.getInstance().addStage(stage, TAG);
    }

    private void changeToMainMenu() {
        game.destroyScreen(ScreenType.GAME1);
        game.destroyScreen(ScreenType.BILANG1);
        game.changeScreen(ScreenType.MAINMENU);
    }

    @Override
    public void show() {
        fail.play();
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

    }

    @Override
    public void dispose() {
        AssetsManager.getInstance().disposeStage(TAG);
    }
}
