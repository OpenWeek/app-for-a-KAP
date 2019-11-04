package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import java.util.HashSet;
import java.util.LinkedList;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.AssetsManaging.FontHelper;
import gdx.kapotopia.AssetsManaging.UsualFonts;
import gdx.kapotopia.Game1.VirusContainer;
import gdx.kapotopia.Helpers.LabelBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localization;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.Helpers.StandardInputAdapter;
import gdx.kapotopia.Utils;

public class BilanG1 implements Screen {
    // Basic variables
    private Kapotopia game;
    private Stage stage;
    TextButton.TextButtonStyle style;

    private HashSet<VirusContainer> missedIsts;

    private Button next;

    // Labels
    private LinkedList<Label> istsToShow;
    private LinkedList<Label> descrToShow;
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
        this.stage = new Stage(game.viewport);
        this.style = FontHelper.getStyleFont(UsualFonts.CLASSIC_SANS_NORMAL_BLACK);

        final float wWidth = game.viewport.getWorldWidth();
        final float wHeight = game.viewport.getWorldHeight();

        this.imgFond = new Image(AssetsManager.getInstance().getTextureByPath("FondNiveauBlanc2.png"));
        imgFond.setVisible(true);
        stage.addActor(imgFond);

        this.missedIsts = (HashSet<VirusContainer>) game.getTheValueGateway().removeFromTheStore("G1-missedIST");
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
        this.descrToShow = new LinkedList<Label>();

        for (VirusContainer ist : missedIsts) {
            // Name
            final float xNext = wWidth / 3f;
            final float yNext = wHeight / 1.5f;
            final Label ln = new LabelBuilder(ist.getName()).withStyle(style).isVisible(false).withPosition(xNext, yNext).build();

            // Description
            final float xDescr = wWidth / 30f;
            final float yDescr = yNext - (wHeight / 3f);
            final float wDescr = wWidth - 2 * (wWidth / 30f);
            final float hDescr = wHeight / 3f;
            final Label ld = new LabelBuilder(ist.getDescription()).withStyle(style).isVisible(false)
                    .withPosition(xDescr, yDescr).withAlignement(Align.left).withWidth(wDescr)
                    .withHeight(hDescr).isWrapped(true).build();

            // General
            istsToShow.add(ln);
            descrToShow.add(ld);
            stage.addActor(ln);
            stage.addActor(ld);
        }

        pointeur = 0;

        // Intro text
        intro = new LabelBuilder(Localization.getInstance().getString("intro_text"))
                .withStyle(style).withPosition(wWidth / 9f,wHeight / 1.2f).isWrapped(true).withWidth(wWidth / 1.3f).build();
        stage.addActor(intro);

        // Button
        next = new TextButton(Localization.getInstance().getString("next_button"), style);
        final float xNext = wWidth / 2.5f;
        final float yNext = wHeight / 8f;
        next.setPosition(xNext, yNext);
        next.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.vibrate(50);
                if(istsToShow.size() <= pointeur) {
                    changeToMainMenu();
                }else{
                    if(pointeur == 0) {
                        final Label ln = istsToShow.getFirst();
                        ln.setVisible(true);
                        final Label ld = descrToShow.getFirst();
                        ld.setVisible(true);
                        intro.setVisible(false);
                    } else {
                        istsToShow.get(pointeur-1).setVisible(false);
                        final Label ln = istsToShow.get(pointeur);
                        ln.setVisible(true);
                        descrToShow.get(pointeur-1).setVisible(false);
                        final Label ld = descrToShow.get(pointeur);
                        ld.setVisible(true);
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
        Gdx.input.vibrate(new long[] { 0, 750, 400}, -1);
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
