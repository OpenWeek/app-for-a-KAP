package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Fonts.UseFont;
import gdx.kapotopia.GameConfig;
import gdx.kapotopia.Helpers.Builders.ImageBuilder;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Helpers.ImageHelper;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.ScreenType;

//import static gdx.kapotopia.ScreenType.GAME2;

public class mockupG2 extends CinematicScreen {

    public mockupG2(final Kapotopia game) {
        super(game, new Stage(game.viewport), "mockupG2");
        final Localisation loc = Localisation.getInstance();
        UseFont font = UseFont.CLASSIC_SANS_NORMAL_BLACK;
        Label[] labels = new Label[] {
                new LabelBuilder(loc.getString("game2_diag1"))
                        .withStyle(font)
                        //.withBounds(60, 1030, 995,315)
                        .withWidth(game.viewport.getWorldWidth() - (2 * GameConfig.ONE_CHAR_STD_WIDTH))
                        //.withHeight(GameConfig.ONE_CHAR_STD_HEIGHT * 10)
                        .withPosition(GameConfig.ONE_CHAR_STD_WIDTH, (game.viewport.getWorldHeight() /1.27f))
                        .isWrapped(true)
                        .build(),
                new LabelBuilder(loc.getString("game2_rules"))
                        .withStyle(font)
                        //.withBounds(80,800,920,500)
                        .withWidth(game.viewport.getWorldWidth() - (4 * GameConfig.ONE_CHAR_STD_WIDTH))
                        .withPosition(2* GameConfig.ONE_CHAR_STD_WIDTH, (game.viewport.getWorldHeight() /1.55f))
                        .isWrapped(true)
                        .build(),
        };
        final Image back1 = ImageHelper.getBackground(game.viewport, "World1/Game2/20_board_0.png");
        final Image back2 = ImageHelper.getBackground(game.viewport, "World1/Game2/20_board_1.png");
        final Image[] images = {
                back1,
                back2
        };
        this.applyBundle(new ParameterBundleBuilder(ScreenType.GAME2)
                .withImages(images)
                .withNextBtnStyle(UseFont.CLASSIC_SANS_NORMAL_BLACK)
                .withTimerScheduleTime(0)
                .withLabels(labels)
                .withFinishBtn(false));
        game.getTheValueGateway().addToTheStore("nextscreen",ScreenType.GAME2);
    }

    @Override
    public void show() {
        setUpInputProcessor();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
