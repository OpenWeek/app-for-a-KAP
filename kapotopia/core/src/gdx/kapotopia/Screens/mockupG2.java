package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Fonts.Font;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.GameConfig;
import gdx.kapotopia.Helpers.Align;
import gdx.kapotopia.Helpers.Alignement;
import gdx.kapotopia.Helpers.Bounds;
import gdx.kapotopia.Helpers.Builders.ImageTextButtonBuilder;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Helpers.ImageHelper;
import gdx.kapotopia.Helpers.Padding;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.ScreenType;

//import static gdx.kapotopia.ScreenType.GAME2;

public class mockupG2 extends CinematicScreen {

    private ImageTextButton skipBtn;

    public mockupG2(final Kapotopia game) {
        super(game, new Stage(game.viewport), "mockupG2");
        final float ww = game.viewport.getWorldWidth();
        final float wh = game.viewport.getWorldHeight();
        final Localisation loc = game.loc;
        Font font = FontHelper.CLASSIC_SANS_NORMAL_BLACK;
        Bounds dialogBubbleBounds = Align.getDialogBubbleBounds();
        Bounds explicativeBubbleBounds = Align.getExplicativeBubbleBounds();
        Label[][] labels = new Label[][] {
                {
                        new LabelBuilder(game, loc.getString("game2_diag1"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(game, loc.getString("game2_diag2"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(game, loc.getString("game2_diag3"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(game, loc.getString("rules_title"))
                                .withStyle(FontHelper.CLASSIC_BOLD_BIG_BLACK).withAlignment(Alignement.CENTER)
                                .withY(wh - explicativeBubbleBounds.getTopPad())
                                .build(),
                        new LabelBuilder(game, loc.getString("game2_rules"))
                                .withStyle(font).withBounds(explicativeBubbleBounds)
                                .isWrapped(true)
                                .build()
                }
        };
        final Image sky = new Image(game.ass.get(AssetDescriptors.SKY));
        final Image sea = new Image(game.ass.get(AssetDescriptors.SEA));
        final Image sand = new Image(game.ass.get(AssetDescriptors.SABLE));
        final Image mireilleHappy = new Image(game.ass.get(AssetDescriptors.MI_HAPPY));
        mireilleHappy.setScale(GameConfig.SCALLING_FACTOR_INTROS);
        mireilleHappy.setPosition(ww / 4f, 0);
        final Image mireilleSurprise = new Image(game.ass.get(AssetDescriptors.MI_SURPRISED));
        mireilleSurprise.setScale(GameConfig.SCALLING_FACTOR_INTROS);
        mireilleSurprise.setPosition(ww / 4f, 0);
        final Image alyxOpen = new Image(game.ass.get(AssetDescriptors.ALYX_OPEN));
        alyxOpen.setPosition(ww / 5f, 0);
        final Image alyxNorm = new Image(game.ass.get(AssetDescriptors.ALYX_NORMAL));
        alyxNorm.setPosition(ww / 5f, 0);

        final Image[][] images = {
                {
                    sand,
                    sky,
                    sea,
                    mireilleHappy,
                    new Image(game.ass.get(AssetDescriptors.BUBBLE_RIGHT))
                },
                {
                    sand,
                    sky,
                    sea,
                    mireilleSurprise,
                    new Image(game.ass.get(AssetDescriptors.BUBBLE_RIGHT))
                },
                {
                    sand,
                    sky,
                    sea,
                    sand,
                    alyxOpen,
                    new Image(game.ass.get(AssetDescriptors.BUBBLE_LEFT))
                },
                {
                    sand,
                    sky,
                    sea,
                    alyxNorm,
                    new Image(game.ass.get(AssetDescriptors.BUBBLE_EXPL))
                }
        };
        // Skip button
        skipBtn = new ImageTextButtonBuilder(game, game.loc.getString("skip_button"))
                .withFontStyle(FontHelper.AESTHETIC_NORMAL_WHITE)
                .withPosition(game.viewport.getWorldWidth() * 0.75f, this.game.viewport.getWorldHeight() / 30f)
                .withImageStyle(game.ass.get(AssetDescriptors.BTN_SAND)).isVisible(true)
                .withPadding(Padding.STANDARD)
                .withListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        resetScreen();
                        game.changeScreen(ScreenType.GAME2);
                    }
                })
                .build();


        this.applyBundle(new ParameterBundleBuilder(ScreenType.GAME2)
                .withImages(images)
                .withNextBtnStyle(FontHelper.CLASSIC_SANS_NORMAL_BLACK)
                .withTimerScheduleTime(0)
                .withLabels(labels)
                .withFinishBtn(false));

        getStage().addActor(skipBtn);
    }

    @Override
    public void show() {
        setUpInputProcessor();

        skipBtn.setVisible(game.getSettings().isIntro_2_skip());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getStage().act(Gdx.graphics.getDeltaTime());
        getStage().draw();
    }
}
