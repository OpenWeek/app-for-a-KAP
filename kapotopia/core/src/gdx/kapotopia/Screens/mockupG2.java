package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;

//import static gdx.kapotopia.ScreenType.GAME2;

public class mockupG2 extends CinematicScreen {

    public mockupG2(final Kapotopia game) {
        super(game, new Stage(game.viewport), "mockupG2");
        this.applyBundle(new ParameterBundleBuilder(ScreenType.GAME2)
                .withTextures(new String[]{"World1/Game2/20_board_0.png", "World1/Game2/20_board_1.png"}));
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
