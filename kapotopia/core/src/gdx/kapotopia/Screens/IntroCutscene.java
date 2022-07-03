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
import gdx.kapotopia.Animations.MireilleCoucouAnimation;
import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Fonts.Font;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.GameConfig;
import gdx.kapotopia.Helpers.Align;
import gdx.kapotopia.Helpers.Bounds;
import gdx.kapotopia.Helpers.Builders.ImageBuilder;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.ScreenType;

public class IntroCutscene extends CinematicScreen {
    private OrthographicCamera camera;
    private SpriteBatch mireilleBatch;

    private Animation<TextureRegion> mireilleCoucou;
    private float stateTime;
    /**
     * Initialize the basic variables using the game, the stage and the screenName.
     * ATTENTION : the method *applyBundle* MUST come after this call ! Or nothing will appear on the screen
     *
     * @param game       the Kapotopia game
     */
    public IntroCutscene(Kapotopia game) {
        super(game, new Stage(game.viewport));
        final Localisation loc = game.loc;

        final float ww = game.viewport.getWorldWidth();
        final float wh = game.viewport.getWorldHeight();

        Font font = FontHelper.CLASSIC_SANS_NORMAL_BLACK;
        Bounds dialogBubbleBounds = Align.getDialogBubbleBounds();

        Label[][] labels = new Label[][] {
                {

                },
                {
                        new LabelBuilder(game, loc.getString("dialogIntro1"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(game, loc.getString("dialogIntro2"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(game, loc.getString("dialogIntro3"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                }
        };

        final Image mireilleHappy = new Image(game.ass.get(AssetDescriptors.MI_HAPPY));
        mireilleHappy.setScale(GameConfig.SCALLING_FACTOR_INTROS);
        mireilleHappy.setPosition(ww / 4f, 0);
        final Image mireilleNormal = new Image(game.ass.get(AssetDescriptors.MI_NORMAL));
        mireilleNormal.setScale(GameConfig.SCALLING_FACTOR_INTROS);
        mireilleNormal.setPosition(ww / 4f, 0);

        final Image bubbleLeft = new ImageBuilder().withTexture(game.ass.get(AssetDescriptors.BUBBLE_MID_LEFT)).build();

        Image[][] images = new Image[][] {
                {

                },
                {
                    mireilleHappy,
                        bubbleLeft
                },
                {
                    mireilleNormal,
                        bubbleLeft
                },
                {
                    mireilleHappy,
                        bubbleLeft
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
        //TODO change MireilleBlinkAnimation to MireilleCoucouAnimation : MireilleCoucou need an atlas
        mireilleCoucou = new MireilleBlinkingAnimation(game, Animation.PlayMode.LOOP_PINGPONG).getAnimation();
        stateTime = 0f;

        applyBundle(new ParameterBundleBuilder(ScreenType.MAINMENU).withImages(images).withLabels(labels)
                .withFinishBtn(false).withNextBtnStyle(FontHelper.CLASSIC_SANS_NORMAL_WHITE).withFinishBtnStyle(FontHelper.CLASSIC_SANS_NORMAL_WHITE).withPreviousBtnStyle(FontHelper.CLASSIC_SANS_NORMAL_WHITE));
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
        mireilleBatch.setProjectionMatrix(camera.combined);

        getStage().act(delta);
        getStage().draw();

        if (getCurrentSeqIndex() == 0) {
            stateTime += delta;
            mireilleBatch.begin();
            final TextureRegion m = mireilleCoucou.getKeyFrame(stateTime, true);
            mireilleBatch.draw(m,camera.viewportWidth / 4f, 0, 0, 0,
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
