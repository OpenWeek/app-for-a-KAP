package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import gdx.kapotopia.Animations.MireilleBlinkingAnimation;
import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Fonts.Font;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.GameConfig;
import gdx.kapotopia.Helpers.Align;
import gdx.kapotopia.Helpers.Alignement;
import gdx.kapotopia.Helpers.Bounds;
import gdx.kapotopia.Helpers.Builders.ImageBuilder;
import gdx.kapotopia.Helpers.Builders.ImageTextButtonBuilder;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Helpers.ImageHelper;
import gdx.kapotopia.Helpers.Padding;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.UnlockedLevel;

public class mockupG1 extends CinematicScreen {

    private final String TAG = this.getClass().getSimpleName();

    private OrthographicCamera camera;
    private SpriteBatch mireilleBatch;

    private Animation<TextureRegion> mireilleBlink;
    private float stateTime;
    private ImageTextButton skipBtn;

    public mockupG1(final Kapotopia game) {
        super(game, new Stage(game.viewport));
        final Localisation loc = game.loc;

        final float ww = game.viewport.getWorldWidth();
        final float wh = game.viewport.getWorldHeight();

        Font font = FontHelper.CLASSIC_SANS_NORMAL_BLACK;
        Bounds dialogBubbleBounds = Align.getDialogBubbleBounds();
        Bounds explicativeBubbleBounds = Align.getExplicativeBubbleBounds();

        Label[][] labels = new Label[][] {
                {
                        new LabelBuilder(game, loc.getString("dialogG1_1"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(game, loc.getString("dialogG1_2"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(game, loc.getString("dialogG1_3"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(game, loc.getString("dialogG1_4"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(game, loc.getString("dialogG1_5"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(game, loc.getString("dialogG1_6"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(game, loc.getString("rules_title"))
                                .withStyle(FontHelper.CLASSIC_BOLD_BIG_BLACK).withAlignment(Alignement.CENTER)
                                .withY(wh - explicativeBubbleBounds.getTopPad())
                                .build(),
                        new LabelBuilder(game, loc.getString("dialogG1_rules_1"))
                                .withStyle(font).withBounds(explicativeBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(game, loc.getString("dialogG1_7"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                }
        };
        final Image jungle = ImageHelper.getBackground(game.viewport, game.ass.get(AssetDescriptors.JUNGLE));
        final Image sky = ImageHelper.getBackground(game.viewport, game.ass.get(AssetDescriptors.NIGHT_SKY));
        final Image leaves = ImageHelper.getBackground(game.viewport, game.ass.get(AssetDescriptors.LEAVES));

        final Image mireilleCrying = new ImageBuilder().withTexture(game.ass.get(AssetDescriptors.MI_CRY)).build();
        mireilleCrying.setScale(GameConfig.SCALLING_FACTOR_INTROS);
        mireilleCrying.setPosition(ww / 4f, 0);

        final Image mireilleTired = new ImageBuilder().withTexture(game.ass.get(AssetDescriptors.MI_TIRED)).build();
        mireilleTired.setScale(GameConfig.SCALLING_FACTOR_INTROS);
        mireilleTired.setPosition(ww / 4f, 0);

        final Image dildo1 = new ImageBuilder().withTexture(game.ass.get(AssetDescriptors.SERGENT1)).build();
        dildo1.setScale(GameConfig.SCALLING_FACTOR_INTROS);
        dildo1.setPosition(ww / 8f, 0);

        final Image dildo2 = new ImageBuilder().withTexture(game.ass.get(AssetDescriptors.SERGENT2)).build();
        dildo2.setScale(GameConfig.SCALLING_FACTOR_INTROS);
        dildo2.setPosition(ww / 3, 0);

        final Image croquis = new ImageBuilder().withTexture(game.ass.get(AssetDescriptors.CROQUIS)).build();
        //croquis.setScale(scalling_factor / 1.5f);
        croquis.setY(explicativeBubbleBounds.getTopPad() / 4);
        Gdx.app.log(TAG, "ww / 2 :" + (ww / 2) + " | croquis.getWidth() / 2 : " + ((croquis.getWidth() / 3) / 2));
        croquis.setX((ww / 2) + (croquis.getWidth() / 2));

        final Image bigBubble = new ImageBuilder().withTexture(game.ass.get(AssetDescriptors.BUBBLE_EXPL)).build();
        final Image bubbleLeft = new ImageBuilder().withTexture(game.ass.get(AssetDescriptors.BUBBLE_MID_LEFT)).build();
        final Image bubbleRight = new ImageBuilder().withTexture(game.ass.get(AssetDescriptors.BUBBLE_MID_RIGHT)).build();
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
        mireilleBatch = new SpriteBatch();
        mireilleBlink = new MireilleBlinkingAnimation(game, Animation.PlayMode.LOOP_PINGPONG).getAnimation();
        stateTime = 0f;
        // Skip Button

        skipBtn = new ImageTextButtonBuilder(game, game.loc.getString("skip_button"))
                .withFontStyle(FontHelper.AESTHETIC_NORMAL_WHITE)
                .withPosition(game.viewport.getWorldWidth() * 0.75f, this.game.viewport.getWorldHeight() / 30f)
                .withImageStyle(game.ass.get(AssetDescriptors.BTN_WOOD))
                .isVisible(true)
                .withPadding(Padding.STANDARD)
                .withListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        resetScreen();
                        game.changeScreen(ScreenType.DIFGAME1);
                    }
                })
                .build();


        /* ENDING */
        applyBundle(new ParameterBundleBuilder(ScreenType.DIFGAME1)
                .withImages(images)
                .withFinishBtn(false)
                .withPreviousBtnStyle(FontHelper.CLASSIC_SANS_NORMAL_WHITE)
                .withFinishBtnStyle(FontHelper.CLASSIC_SANS_NORMAL_WHITE)
                .withNextBtnStyle(FontHelper.CLASSIC_SANS_NORMAL_WHITE)
                .withTimerScheduleTime(0)
                .withLabels(labels));
        getStage().addActor(skipBtn);

        // Setting up the music
        game.getMusicControl().changeMusic(game.ass.get(AssetDescriptors.MUSIC_GAME1), 0, true);
        game.getMusicControl().playMusic();
    }

    @Override
    public void show() {
        setUpInputProcessor();

        game.vars.setNextScreenOfChoosingDifScreen(ScreenType.GAME1);
        final UnlockedLevel level = game.getSettings().getG1UnlockedLvl();
        game.vars.setGame1UnlockedLevels(level);

        skipBtn.setVisible(game.getSettings().isIntro_1_skip());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        mireilleBatch.setProjectionMatrix(camera.combined);

        getStage().act(delta);
        getStage().draw();

        if (getCurrentSeqIndex() == 0 || getCurrentSeqIndex() == 1) {
            stateTime += delta;
            mireilleBatch.begin();
            final TextureRegion m = mireilleBlink.getKeyFrame(stateTime, true);
            mireilleBatch.draw(m, camera.viewportWidth / 4f, 0, 0, 0,
                    m.getRegionWidth(), m.getRegionHeight(), GameConfig.SCALLING_FACTOR_INTROS, GameConfig.SCALLING_FACTOR_INTROS, 0);
            mireilleBatch.end();
        }
    }

    @Override
    public void dispose() {
        getStage().dispose();
        mireilleBatch.dispose();
    }
}