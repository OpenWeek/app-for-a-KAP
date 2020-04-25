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

import gdx.kapotopia.Animations.EvilTomAnimation;
import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Fonts.Font;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.GameConfig;
import gdx.kapotopia.Helpers.Align;
import gdx.kapotopia.Helpers.Alignement;
import gdx.kapotopia.Helpers.Bounds;
import gdx.kapotopia.Helpers.Builders.ImageBuilder;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Helpers.ImageHelper;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.ScreenType;

public class mockupG3 extends CinematicScreen {

    private final float scalling_factor = 0.6f;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Animation<TextureRegion> evilTom;
    private float stateTime;

    public mockupG3(final Kapotopia game) {
        super(game, new Stage(game.viewport), "mockupG3");

        final float ww = GameConfig.GAME_WIDTH;
        final float wh = GameConfig.GAME_HEIGHT;

        Localisation loc = Localisation.getInstance();

        Font font = FontHelper.CLASSIC_SANS_NORMAL_BLACK;
        Bounds dialogBubbleBounds = Align.getDialogBubbleBounds();
        Bounds explicativeBubbleBounds = Align.getExplicativeBubbleBounds();

        final Label[][] labels = new Label[][] {
                {
                        new LabelBuilder(game, loc.getString("game3_diag1"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(game, loc.getString("game3_diag2"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(game, loc.getString("game3_diag3"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(game, loc.getString("game3_diag4"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(game, loc.getString("game3_diag5"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(game, loc.getString("rules_title"))
                                .withStyle(FontHelper.CLASSIC_BOLD_BIG_BLACK).withAlignment(Alignement.CENTER)
                                .withY(wh - explicativeBubbleBounds.getTopPad())
                                .build(),
                        new LabelBuilder(game, loc.getString("game3_instr"))
                                .withStyle(font).withBounds(explicativeBubbleBounds)
                                .isWrapped(true)
                                .build()
                }
        };

        // Backgrounds
        final Image house = ImageHelper.getBackground(game.viewport, game.ass.get(AssetDescriptors.I3_HOUSE));
        final Image inside = ImageHelper.getBackground(game.viewport, game.ass.get(AssetDescriptors.I3_INSIDE));
        // Bubbles
        final Image bigBubble = new ImageBuilder().withTexture(game.ass.get(AssetDescriptors.BUBBLE_EXPL)).build();
        final Image bubbleLeft = new ImageBuilder().withTexture(game.ass.get(AssetDescriptors.BUBBLE_LEFT)).build();
        final Image bubbleLeft2 = new ImageBuilder().withTexture(game.ass.get(AssetDescriptors.BUBBLE_LEFT2)).build();
        final Image bubbleRight = new ImageBuilder().withTexture(game.ass.get(AssetDescriptors.BUBBLE_RIGHT)).build();
        // Characters
        final Image tom = new ImageBuilder().withTexture(game.ass.get(AssetDescriptors.GODIVA))
                .withPosition(ww * 0.075f, wh * 0.04f)
                .build();
        tom.setScale(scalling_factor);
        final Image mireille = new ImageBuilder().withTexture(game.ass.get(AssetDescriptors.MI_NORMAL))
                .withPosition(ww / 4f, 0)
                .build();
        mireille.setScale(scalling_factor);

        final Image mireilleWorried = new ImageBuilder().withTexture(game.ass.get(AssetDescriptors.MI_WORRIED))
                .withPosition(ww / 4f, 0)
                .build();
        mireilleWorried.setScale(scalling_factor);

        final Image mireilleScared = new ImageBuilder().withTexture(game.ass.get(AssetDescriptors.MI_SCARED))
                .withPosition(ww / 4f, 0)
                .build();
        mireilleScared.setScale(scalling_factor);

        final Image mireilleSurprised = new ImageBuilder().withTexture(game.ass.get(AssetDescriptors.MI_SURPRISED))
                .withPosition(ww / 4f, 0)
                .build();
        mireilleSurprised.setScale(scalling_factor);

        final Image[][] images = new Image[][] {
                {
                    house,
                        mireilleWorried,
                        bubbleLeft
                },
                {
                    house,
                        mireilleSurprised,
                        bubbleLeft
                },
                {
                    house,
                        mireille,
                        bubbleLeft
                },
                {
                    inside,
                        mireilleScared,
                        bubbleLeft2
                },
                {
                    inside,
                        //tom,
                        bubbleRight
                },
                {
                    inside,
                        tom,
                        bigBubble
                }
        };

        /* EXTRAS */
        // Camera
        this.camera = new OrthographicCamera(game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        game.viewport.setCamera(camera);
        // Making Animations
        batch = new SpriteBatch();
        evilTom = new EvilTomAnimation(game, Animation.PlayMode.NORMAL).getAnimation();
        stateTime = 0f;

        /* ENDING */

        this.applyBundle(new ParameterBundleBuilder(ScreenType.GAME3)
        .withImages(images).withLabels(labels).withFinishBtn(false).withNextBtnStyle(FontHelper.CLASSIC_BOLD_NORMAL_WHITE));
    }

    @Override
    public void show() {
        setUpInputProcessor();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);



        getStage().act(Gdx.graphics.getDeltaTime());
        getStage().draw();

        if (getCurrentSeqIndex() == 4) {
            stateTime += delta;
            batch.begin();
            final TextureRegion t = evilTom.getKeyFrame(stateTime, false);
            batch.draw(t, camera.viewportWidth * 0.075f, camera.viewportHeight * 0.04f,
                    0, 0, t.getRegionWidth(), t.getRegionHeight(), scalling_factor, scalling_factor, 0);
            batch.end();
        }
    }
}
