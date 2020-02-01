package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import gdx.kapotopia.AssetsManaging.UseFont;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localization;
import gdx.kapotopia.ScreenType;

//import static gdx.kapotopia.ScreenType.GAME2;

public class mockupG2 extends CinematicScreen {

    public mockupG2(final Kapotopia game) {
        super(game, new Stage(game.viewport), "mockupG2");
        final Localization loc = Localization.getInstance();
        UseFont font = UseFont.CLASSIC_SANS_NORMAL_BLACK;
        Label[] labels = new Label[] {
                new LabelBuilder(loc.getString("game2_diag1"))
                        .withStyle(font)
                        .withBounds(60, 1030, 995,315)
                        .isWrapped(true).build(),
                new LabelBuilder(loc.getString("game2_rules"))
                        .withStyle(font)
                        .withBounds(80,800,920,500)
                        .isWrapped(true).build(),
        };
        this.applyBundle(new ParameterBundleBuilder(ScreenType.GAME2)
                .withTextures(new String[]{"World1/Game2/20_board_0.png", "World1/Game2/20_board_1.png"})
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
