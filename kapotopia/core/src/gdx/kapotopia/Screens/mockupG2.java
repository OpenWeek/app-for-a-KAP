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
        final Localisation loc = game.loc;
        Font font = FontHelper.CLASSIC_SANS_NORMAL_BLACK;
        Label[] labels = new Label[] {
                new LabelBuilder(game, loc.getString("game2_diag1"))
                        .withStyle(font)
                        //.withBounds(60, 1030, 995,315)
                        .withWidth(game.viewport.getWorldWidth() - (2 * GameConfig.ONE_CHAR_STD_WIDTH))
                        //.withHeight(GameConfig.ONE_CHAR_STD_HEIGHT * 10)
                        .withPosition(GameConfig.ONE_CHAR_STD_WIDTH, (game.viewport.getWorldHeight() /1.27f))
                        .isWrapped(true)
                        .build(),
                new LabelBuilder(game, loc.getString("game2_rules"))
                        .withStyle(font)
                        //.withBounds(80,800,920,500)
                        .withWidth(game.viewport.getWorldWidth() - (4 * GameConfig.ONE_CHAR_STD_WIDTH))
                        .withPosition(2* GameConfig.ONE_CHAR_STD_WIDTH, (game.viewport.getWorldHeight() /1.55f))
                        .isWrapped(true)
                        .build(),
        };
        final Image back1 = ImageHelper.getBackground(game.viewport, game.ass.get(AssetDescriptors.I2_BACK1));
        final Image back2 = ImageHelper.getBackground(game.viewport, game.ass.get(AssetDescriptors.I2_BACK2));
        final Image[] images = {
                back1,
                back2
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
