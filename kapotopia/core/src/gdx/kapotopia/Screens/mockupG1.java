package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import gdx.kapotopia.AssetsManager.AssetsManager;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;

public class mockupG1 extends CinematicScreen {

    public mockupG1(final Kapotopia game) {
        super(game, new Stage(game.viewport), "mockupG1", ScreenType.DIF,
                new String[]{"World1/Game1/World1Ecran1.png", "World1/Game1/World1Ecran2.png",
                "World1/Game1/World1Ecran3.png", "World1/Game1/World1Ecran4.png",
                        "World1/Game1/World1Ecran5.png"}, Color.WHITE, 0);
        game.getTheValueGateway().addToTheStore("nextscreen", ScreenType.GAME1);
        game.getTheValueGateway().addToTheStore("unlockedLevel", UnlockedLevel.HARD_UNLOCKED);
        AssetsManager.getInstance().getSoundByPath("sound/bruitage/littlerainyseasons_fail.mp3");
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
