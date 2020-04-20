package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import gdx.kapotopia.Animations.MireilleBlinkingAnimation;
import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Fonts.UseFont;
import gdx.kapotopia.Helpers.Align;
import gdx.kapotopia.Helpers.Alignement;
import gdx.kapotopia.Helpers.Bounds;
import gdx.kapotopia.Helpers.Builders.ImageBuilder;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Helpers.ImageHelper;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.UnlockedLevel;

public class mockupG1 extends CinematicScreen {

    private final String TAG = this.getClass().getSimpleName();

    private final float scalling_factor = 0.6f;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Animation<TextureRegion> mireilleBlink;
    private float stateTime;

    public mockupG1(final Kapotopia game) {
        super(game, new Stage(game.viewport), "mockupG1");
        final Localisation loc = Localisation.getInstance();
        final float ww = game.viewport.getWorldWidth();
        final float wh = game.viewport.getWorldHeight();
        UseFont font = UseFont.CLASSIC_SANS_NORMAL_BLACK;
        Bounds dialogBubbleBounds = Align.getDialogBubbleBounds();
        Bounds explicativeBubbleBounds = Align.getExplicativeBubbleBounds();
        Label[][] labels = new Label[][] {
                {
                        new LabelBuilder(loc.getString("dialogG1_1"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(loc.getString("dialogG1_2"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(loc.getString("dialogG1_3"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(loc.getString("dialogG1_4"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(loc.getString("dialogG1_5"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(loc.getString("dialogG1_6"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(loc.getString("rules_title"))
                                .withStyle(UseFont.CLASSIC_BOLD_BIG_BLACK).withAlignment(Alignement.CENTER)
                                .withY(wh - explicativeBubbleBounds.getTopPad())
                                .build(),
                        new LabelBuilder(loc.getString("dialogG1_rules_1"))
                                .withStyle(font).withBounds(explicativeBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(loc.getString("dialogG1_7"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                }
        };
        final Image jungle = ImageHelper.getBackground(game.viewport, "World1/Game1/Jungle.png");
        final Image sky = ImageHelper.getBackground(game.viewport, "World1/Game1/Ciel.png");
        final Image leaves = ImageHelper.getBackground(game.viewport, "World1/Game1/Feuilles.png");

        final Image mireilleCrying = new ImageBuilder().withTexture("MireilleImages/MireillePleure.png").build();
        mireilleCrying.setScale(scalling_factor);
        mireilleCrying.setPosition(ww / 4f, 0);

        final Image mireilleTired = new ImageBuilder().withTexture("MireilleImages/MireilleAChaud.png").build();
        mireilleTired.setScale(scalling_factor);
        mireilleTired.setPosition(ww / 4f, 0);

        final Image dildo1 = new ImageBuilder().withTexture("World1/Game1/SergendDildo.png").build();
        dildo1.setScale(scalling_factor);
        dildo1.setPosition(ww / 8f, 0);

        final Image dildo2 = new ImageBuilder().withTexture("World1/Game1/SergentDildo2.png").build();
        dildo2.setScale(scalling_factor);
        dildo2.setPosition(ww / 3, 0);

        final Image croquis = new ImageBuilder().withTexture("World1/Game1/Croquis.png").build();
        //croquis.setScale(scalling_factor / 1.5f);
        croquis.setY(explicativeBubbleBounds.getTopPad() / 4);
        Gdx.app.log(TAG, "ww / 2 :" + (ww / 2) + " | croquis.getWidth() / 2 : " + ((croquis.getWidth() / 3) / 2));
        croquis.setX((ww / 2) + (croquis.getWidth() / 2));

        final Image bigBubble = new ImageBuilder().withTexture("ImagesGadgets/BulleExplicative.png").build();
        final Image bubbleLeft = new ImageBuilder().withTexture("ImagesGadgets/Bulle1.png").build();
        final Image bubbleRight = new ImageBuilder().withTexture("ImagesGadgets/Bulle3.png").build();
        /* WE DEFINE THE IMAGES THAT WILL APPEAR HERE */
        final Image[][] images = new Image[][] {
                {
                    sky,
                    jungle,
                        leaves,
                        bubbleLeft
                },
                {
                    sky,
                    jungle,
                        leaves,
                        bubbleLeft
                },
                {
                    sky,
                    jungle,
                        leaves,
                        mireilleCrying,
                        bubbleLeft
                },
                {
                    sky,
                    jungle,
                        leaves,
                        mireilleTired,
                        bubbleLeft
                },
                {
                    sky,
                    jungle,
                        leaves,
                        dildo1,
                        bubbleRight
                },
                {
                    sky,
                    jungle,
                        leaves,
                        dildo2,
                        bubbleLeft
                },
                {
                    sky,
                    jungle,
                        leaves,
                        bigBubble,
                        croquis
                },
                {
                    sky,
                    jungle,
                        leaves,
                        dildo1,
                        bubbleRight
                }
        };



        /*
                Additional stuff to the cutscene
        */
        // Camera
        this.camera = new OrthographicCamera(game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        game.viewport.setCamera(camera);
        // Making Animations
        batch = new SpriteBatch();
        mireilleBlink = new MireilleBlinkingAnimation(game, Animation.PlayMode.LOOP_PINGPONG).getAnimation();
        stateTime = 0f;

        /* ENDING */
        applyBundle(new ParameterBundleBuilder(ScreenType.DIF)
                .withImages(images).withFinishBtn(false)
                .withNextBtnStyle(UseFont.CLASSIC_SANS_NORMAL_WHITE).withTimerScheduleTime(0).withLabels(labels));
        // Preload this sound for the BilanG1 screen
        AssetsManager.getInstance().getSoundByPath("sound/bruitage/littlerainyseasons_fail.mp3");
    }

    @Override
    public void show() {
        setUpInputProcessor();
        game.getTheValueGateway().addToTheStore("nextscreen", ScreenType.GAME1);
        final UnlockedLevel level = game.getSettings().getG1UnlockedLvl();
        game.getTheValueGateway().addToTheStore("unlockedLevel", level);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        if (getCurrentSeqIndex() == 0 || getCurrentSeqIndex() == 1) {
            stateTime += delta;
            batch.begin();
            final TextureRegion m = mireilleBlink.getKeyFrame(stateTime, true);
            batch.draw(m, camera.viewportWidth / 4f, 0, 0, 0,
                    m.getRegionWidth(), m.getRegionHeight(), scalling_factor, scalling_factor, 0);
            batch.end();
        }
    }
}