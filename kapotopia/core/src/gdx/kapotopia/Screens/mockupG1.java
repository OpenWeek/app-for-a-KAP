package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.AssetsManaging.UseFont;
import gdx.kapotopia.Helpers.Builders.ImageBuilder;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.UnlockedLevel;

public class mockupG1 extends CinematicScreen {

    public mockupG1(final Kapotopia game) {
        super(game, new Stage(game.viewport), "mockupG1");
        final Localisation loc = Localisation.getInstance();
        UseFont font = UseFont.CLASSIC_SANS_NORMAL_BLACK;
        Label[][] labels = new Label[][] {
                {
                        new LabelBuilder(loc.getString("dialogG1_1"))
                                .withStyle(font).withBounds(125, 1000, 750 ,315)
                                .isWrapped(true).build()
                },
                {
                        new LabelBuilder(loc.getString("dialogG1_2"))
                                .withStyle(font).withBounds(125,1050,800,315).isWrapped(true).build()
                },
                {
                        new LabelBuilder(loc.getString("dialogG1_3"))
                                .withStyle(font).withBounds(125,1050,800,315).isWrapped(true).build()
                },
                {
                        new LabelBuilder(loc.getString("dialogG1_rules"))
                                .withStyle(font).withBounds(100,800,900,1500).isWrapped(true).build()
                },
                {
                        new LabelBuilder(loc.getString("dialogG1_4"))
                                .withStyle(font).withBounds(150,1100,750,315).isWrapped(true).build()
                }
        };
        final Image jungle = new ImageBuilder().withTexture("World1/Game1/Jungle.png").build();
        final Image sky = new ImageBuilder().withTexture("World1/Game1/Ciel.png").build();
        final Image leaves = new ImageBuilder().withTexture("World1/Game1/Feuilles.png").build();
        final Image mireille = new ImageBuilder().withTexture("MireilleImages/MireilleAChaud.png").build();
        final float scalling_factor = 0.6f;
        mireille.setScale(scalling_factor);
        mireille.setPosition(250, -150);
        final Image dildo1 = new ImageBuilder().withTexture("World1/Game1/SergendDildo.png").build();
        dildo1.setScale(scalling_factor);
        dildo1.setPosition(125, -50);
        final Image dildo2 = new ImageBuilder().withTexture("World1/Game1/SergentDildo2.png").build();
        dildo2.setScale(scalling_factor);
        dildo2.setPosition(game.viewport.getWorldWidth() / 3, -50);
        dildo2.setX(game.viewport.getWorldWidth() / 3);
        final Image bigBubble = new ImageBuilder().withTexture("ImagesGadgets/BulleExplicative.png").build();
        final Image bubbleLeft = new ImageBuilder().withTexture("ImagesGadgets/Bulle1.png").build();
        final Image bubbleRight = new ImageBuilder().withTexture("ImagesGadgets/Bulle3.png").build();
        final Image[][] images = new Image[][] {
                {
                    sky,
                    jungle,
                        leaves,
                        mireille,
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
                        bigBubble
                },
                {
                    sky,
                    jungle,
                        leaves,
                        dildo1,
                        bubbleRight
                }
        };

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

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}