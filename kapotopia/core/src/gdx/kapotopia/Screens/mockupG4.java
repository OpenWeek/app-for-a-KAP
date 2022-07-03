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
import gdx.kapotopia.Helpers.Builders.ImageBuilder;
import gdx.kapotopia.Helpers.Builders.ImageTextButtonBuilder;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Helpers.ImageHelper;
import gdx.kapotopia.Helpers.Padding;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.ScreenType;

//import static gdx.kapotopia.ScreenType.GAME2;

public class mockupG4 extends CinematicScreen {

    private ImageTextButton skipBtn;

    public mockupG4(final Kapotopia game) {
        super(game, new Stage(game.viewport));
        final float ww = game.viewport.getWorldWidth();
        final float wh = game.viewport.getWorldHeight();
        final Localisation loc = game.loc;
        Font font = FontHelper.CLASSIC_SANS_NORMAL_BLACK;
        Bounds dialogBubbleBounds = Align.getDialogBubbleBounds();
        Bounds explicativeBubbleBounds = Align.getExplicativeBubbleBounds();
        Label[][] labels = new Label[][] {
                {
                        new LabelBuilder(game, loc.getString("game4_diag1"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(game, loc.getString("game4_diag2"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(game, loc.getString("game4_diag3"))
                                .withStyle(font).withBounds(dialogBubbleBounds)
                                .isWrapped(true)
                                .build()
                },
                {
                        new LabelBuilder(game, loc.getString("rules_title"))
                                .withStyle(FontHelper.CLASSIC_BOLD_BIG_BLACK).withAlignment(Alignement.CENTER)
                                .withY(wh - explicativeBubbleBounds.getTopPad())
                                .build(),
                        new LabelBuilder(game, loc.getString("game4_rules"))
                                .withStyle(font).withBounds(explicativeBubbleBounds)
                                .isWrapped(true)
                                .build()
                }
        };
        final Image back = new Image(game.ass.get(AssetDescriptors.BACKGROUND_DISC));
        final Image mireilleHappy = new Image(game.ass.get(AssetDescriptors.MI_HAPPY));
        mireilleHappy.setScale(GameConfig.SCALLING_FACTOR_INTROS);
        mireilleHappy.setPosition(ww / 4f, 0);
        final Image mireilleSurprise = new Image(game.ass.get(AssetDescriptors.MI_SURPRISED));
        mireilleSurprise.setScale(GameConfig.SCALLING_FACTOR_INTROS);
        mireilleSurprise.setPosition(ww / 4f, 0);

        final Image tom = new ImageBuilder().withTexture(game.ass.get(AssetDescriptors.GODIVA))
                .withPosition(ww * 0.075f, wh * 0.04f)
                .build();
        tom.setScale(GameConfig.SCALLING_FACTOR_INTROS);

        final Image[][] images = {
                {
                        back,
                        mireilleHappy,
                        new Image(game.ass.get(AssetDescriptors.BUBBLE_RIGHT))
                },
                {
                        back,
                        mireilleSurprise,
                        new Image(game.ass.get(AssetDescriptors.BUBBLE_RIGHT))
                },
                {
                        back,
                        tom,
                        new Image(game.ass.get(AssetDescriptors.BUBBLE_LEFT))
                },
                {
                        back,
                        tom,
                        new Image(game.ass.get(AssetDescriptors.BUBBLE_EXPL))
                }
        };
        // Skip button
        skipBtn = new ImageTextButtonBuilder(game, game.loc.getString("skip_button"))
                .withFontStyle(FontHelper.AESTHETIC_NORMAL_WHITE)
                .withPosition(game.viewport.getWorldWidth() * 0.75f, this.game.viewport.getWorldHeight() / 30f)
                .withImageStyle(game.ass.get(AssetDescriptors.BTN_ROCK)).isVisible(true)
                .withPadding(Padding.STANDARD)
                .withListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        resetScreen();
                        game.changeScreen(ScreenType.STDGAME4);
                    }
                })
                .build();


        this.applyBundle(new ParameterBundleBuilder(ScreenType.STDGAME4) // changed from GAME4
                .withImages(images)
                .withNextBtnStyle(FontHelper.CLASSIC_SANS_NORMAL_WHITE)
                .withFinishBtnStyle(FontHelper.CLASSIC_SANS_NORMAL_WHITE)
                .withPreviousBtnStyle(FontHelper.CLASSIC_SANS_NORMAL_WHITE)
                .withTimerScheduleTime(0)
                .withLabels(labels)
                .withFinishBtn(false)
        );

        getStage().addActor(skipBtn);

        // Setting up the music
        game.getMusicControl().changeMusic(game.ass.get(AssetDescriptors.MUSIC_GAME3), 0, true);
        game.getMusicControl().playMusic();
    }

    @Override
    public void show() {
        setUpInputProcessor();

        skipBtn.setVisible(game.getSettings().isIntro_4_skip());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getStage().act(Gdx.graphics.getDeltaTime());
        getStage().draw();
    }
}
