package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.AssetsManaging.UseFont;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localization;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.UnlockedLevel;

public class mockupG1 extends CinematicScreen {

    public mockupG1(final Kapotopia game) {
        super(game, new Stage(game.viewport), "mockupG1");
        final Localization loc = Localization.getInstance();
        UseFont font = UseFont.CLASSIC_SANS_NORMAL_BLACK;
        Label[] labels = new Label[] {
                new LabelBuilder(loc.getString("dialogG1_1"))
                        .withStyle(font).withBounds(125, 1000, 750 ,315)
                        .isWrapped(true).build(),
                new LabelBuilder(loc.getString("dialogG1_2"))
                        .withStyle(font).withBounds(125,1050,800,315).isWrapped(true).build(),
                new LabelBuilder(loc.getString("dialogG1_3"))
                        .withStyle(font).withBounds(125,1050,800,315).isWrapped(true).build(),
                new LabelBuilder(loc.getString("dialogG1_rules"))
                        .withStyle(font).withBounds(100,800,900,1500).isWrapped(true).build(),
                new LabelBuilder(loc.getString("dialogG1_4"))
                        .withStyle(font).withBounds(150,1100,750,315).isWrapped(true).build()
        };
        applyBundle(new ParameterBundleBuilder(ScreenType.DIF)
                .withTextures(new String[]{"World1/Game1/World1Ecran1.png", "World1/Game1/World1Ecran2.png",
                        "World1/Game1/World1Ecran5.png", "World1/Game1/World1Ecran3.png",
                        "World1/Game1/World1Ecran4.png"}).withFinishBtn(false)
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