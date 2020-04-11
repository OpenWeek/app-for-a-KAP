package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import gdx.kapotopia.Fonts.UseFont;
import gdx.kapotopia.GameConfig;
import gdx.kapotopia.Helpers.Builders.ImageBuilder;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.ScreenType;

public class mockupG3 extends CinematicScreen {

    public mockupG3(final Kapotopia game) {
        super(game, new Stage(game.viewport), "mockupG3");

        Localisation loc = Localisation.getInstance();

        final Label[][] labels = new Label[][] {
                {

                },
                {
                    new LabelBuilder(loc.getString("game3_instr"))
                            .withStyle(UseFont.CLASSIC_SANS_NORMAL_BLACK)
                            .withPosition(GameConfig.ONE_CHAR_STD_WIDTH, (game.viewport.getWorldHeight() / 2))
                            .withWidth(game.viewport.getWorldWidth() - (2 * GameConfig.ONE_CHAR_STD_WIDTH))
                            .withHeight(GameConfig.ONE_CHAR_STD_HEIGHT * 10).isWrapped(true)
                            .build()
                }
        };

        final Image tom = new ImageBuilder().withTexture("game3/Thomas Godiva.png").withPosition(50, 50)
                .build();
        final float scale_factor = 0.75f;
        tom.setScale(scale_factor);

        final Image[][] images = new Image[][] {
                {
                    new ImageBuilder().withTexture("game3/Monde2Ecran2.png").build(),
                        tom
                },
                {
                    new ImageBuilder().withTexture("game3/Monde2Ecran3.png").build()
                }
        };

        this.applyBundle(new ParameterBundleBuilder(ScreenType.GAME3)
        .withImages(images).withLabels(labels).withFinishBtn(false).withNextBtnStyle(UseFont.CLASSIC_BOLD_NORMAL_WHITE));
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
