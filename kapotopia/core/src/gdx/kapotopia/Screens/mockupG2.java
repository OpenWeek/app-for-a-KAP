package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;

public class mockupG2 extends CinematicScreen {

    public mockupG2(final Kapotopia game) {
        super(game, new Stage(game.viewport), "mockupG2", ScreenType.GAME2,
                new String[]{"World1/Game2/20_board_0.png","World1/Game2/20_board_1.png"}); //,"World1/Game2/21_board_0.jpg","World1/Game2/22_board_0.jpg"});
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
