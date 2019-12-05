package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;

public class mockupG3 extends CinematicScreen {

    public mockupG3(final Kapotopia game) {
        super(game, new Stage(game.viewport), "mockupG3");
        this.applyBundle(new ParameterBundleBuilder(ScreenType.GAME3)
        .withTextures(new String[]{"game3/Monde2Ecran2.png",
        "game3/Monde2Ecran3.png"}).withFinishBtn(false));
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
