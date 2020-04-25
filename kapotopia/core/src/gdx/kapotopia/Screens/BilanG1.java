package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.HashSet;
import java.util.LinkedList;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Bilan1.RenderController;
import gdx.kapotopia.Bilan1.BilanController;
import gdx.kapotopia.Game1.VirusContainer;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Helpers.Builders.TextButtonBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.Helpers.StandardInputAdapter;

public class BilanG1 implements Screen {
    // Basic variables
    private Kapotopia game;
    private Stage stage;

    private HashSet<VirusContainer> missedIsts;

    private Button next;

    private BilanController controller;
    private RenderController renderController;

    // Labels
    private LinkedList<Label> istsToShow;
    private LinkedList<Label> descrToShow;
    private Label intro;
    private int pointeur;

    // Sounds
    private Sound fail;
    private Sound pauseSound;
    private Sound openSound;

    // Constants
    private final String TAG = "BilangG1";

    public BilanG1(final Kapotopia game) {
        this.game = game;
        this.stage = new Stage(game.viewport);

        this.controller = new BilanController(game, this);
        this.renderController = new RenderController(game, this);

        final float wWidth = game.viewport.getWorldWidth();
        final float wHeight = game.viewport.getWorldHeight();

        /* IMAGES */
        // Images

        this.missedIsts = game.getGame1().getMissedIST();
        if(missedIsts == null) {
            comeBackToG1();
            return;
        }
        if(missedIsts.isEmpty()) {
            comeBackToG1();
            return;
        }

        // ists to show
        this.istsToShow = new LinkedList<Label>();
        this.descrToShow = new LinkedList<Label>();

        controller.init(stage, istsToShow, descrToShow, missedIsts);

        pointeur = 0;

        // Intro text
        intro = new LabelBuilder(Localisation.getInstance().getString("intro_text"))
                .withStyle(UseFont.CLASSIC_SANS_NORMAL_BLACK).withPosition(wWidth / 9f,wHeight / 1.2f).isWrapped(true).withWidth(wWidth / 1.3f).build();
        stage.addActor(intro);

        // Button
        final float xNext = wWidth * 0.4f;
        final float yNext = wHeight * 0.125f;
        next = new TextButtonBuilder(Localisation.getInstance().getString("next_button"))
                .withStyle(UseFont.CLASSIC_SANS_NORMAL_BLACK).withPosition(xNext, yNext)
                .withListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        Gdx.input.vibrate(50);
                        if(istsToShow.size() <= pointeur) {
                            comeBackToG1();
                        }else{
                            if(pointeur == 0) {
                                final Label ln = istsToShow.getFirst();
                                ln.setVisible(true);
                                final Label ld = descrToShow.getFirst();
                                ld.setVisible(true);
                                intro.setVisible(false);
                                renderController.setShowStiSprites();
                            } else {
                                istsToShow.get(pointeur-1).setVisible(false);
                                final Label ln = istsToShow.get(pointeur);
                                ln.setVisible(true);
                                descrToShow.get(pointeur-1).setVisible(false);
                                final Label ld = descrToShow.get(pointeur);
                                ld.setVisible(true);
                                renderController.dequeueStiSprite();
                            }
                            openSound.play();
                            pointeur++;
                        }
                    }
                })
                .build();
        stage.addActor(next);

        // Sounds

        this.fail = game.ass.get(AssetDescriptors.SOUND_FAIL);
        this.pauseSound = game.ass.get(AssetDescriptors.SOUND_PAUSE);
        this.openSound = game.ass.get(AssetDescriptors.SOUND_JUMP_V1);

        AssetsManager.getInstance().addStage(stage, TAG);
    }

    private void comeBackToG1() {
        game.destroyScreen(ScreenType.BILANG1);
        game.changeScreen(ScreenType.GAME1);
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
        renderController.update();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height,true);
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

    public RenderController getRenderController() {
        return renderController;
    }
}
