package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;

public class LoadingScreen implements Screen {
    private float mireilleAngle;
    private Image mireille;
    private Kapotopia game;
    private Label text;
    private Stage stage;
    private ScreenType[] nextScreenType;
    public LoadingScreen(Kapotopia game, ScreenType[] sType) {
        this.game = game;
        this.nextScreenType = sType;
        this.mireilleAngle = 0f;
        this.stage = new Stage(this.game.viewport);


//        this.text = new LabelBuilder(this.game, "Loading...").withStyle(FontHelper.AESTHETIC_NORMAL_WHITE).build();
//        this.text.setPosition(
//                (this.game.viewport.getWorldWidth() / 2) -  this.text.getWidth() / 2,
//                this.game.viewport.getWorldHeight() / 4
//        );
//        this.stage.addActor(this.text);


        this.mireille = new Image(this.game.ass.get(AssetDescriptors.MI_LOADING)) {
            @Override
            public void act(float delta) {
                this.rotateBy(100 * delta);
            }
        };
        this.mireille.setPosition(
                this.game.viewport.getWorldWidth() / 2,
                this.game.viewport.getWorldHeight() /2
        );
        this.stage.addActor(mireille);
        Gdx.app.error("LOADINGSCREEN", "finished building the loading screen.");

    }
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.app.error("LOADINGSCREEN", "rendering loading screen.");
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(this.game.ass.isFinished()) {
            this.game.changeScreen(this.nextScreenType[0]);
            this.dispose();
            return;
        } else {
            this.game.ass.update(17);
        }
        this.stage.act(delta);
        this.stage.draw();


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.stage.dispose();

    }
}
