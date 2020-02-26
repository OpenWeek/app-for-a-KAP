package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Helpers.Builders.TextButtonBuilder;
import gdx.kapotopia.Helpers.ChangeScreenListener;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.STIData;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.Utils;

public class World4 implements Screen {

    private Kapotopia game;
    private Stage stage;
    // Test Animation
    private Animation<TextureRegion> animTest;
    private Animation<TextureRegion> animTest2;
    private Image displayedIst;

    public World4(final Kapotopia game) {

        preload();

        this.game = game;
        Texture fond = AssetsManager.getInstance().getTextureByPath("Pokedex.png");
        Image imgFond = new Image(fond);
        stage = new Stage(game.viewport);
        stage.addActor(imgFond);

        TextButton.TextButtonStyle style = Utils.getStyleFont("COMMS.ttf", 60);
        Label soon = new Label(Localisation.getInstance().getString("soon_label"), new Label.LabelStyle(style.font, style.fontColor));
        soon.setPosition(50, game.viewport.getWorldHeight() * 0.8f);
        soon.setWrap(true);
        soon.setWidth(game.viewport.getWorldWidth() - 200);
        soon.setHeight(300);
        soon.setVisible(true);
        stage.addActor(soon);

        TextButton back = new TextButtonBuilder(Localisation.getInstance().getString("back_button")).withStyle(style)
                .withPosition(game.viewport.getWorldWidth() / 2, 50).isVisible(true)
                .withListener(new ChangeScreenListener(game, ScreenType.MAINMENU, ScreenType.WORLD4)).build();
        stage.addActor(back);

        // Animation test



        AssetsManager.getInstance().addStage(stage, "world4");
    }

    private void preload(){
        Gdx.app.log("W4", "Preloading stuff... Size of the names array: "+STIData.getIstNames().length);
        for(Object name : STIData.getIstNames()){
            Gdx.app.log("W4", (String)name);
            Gdx.app.log("W4", STIData.getIstType((String)name));
            Gdx.app.log("W4", STIData.getIstSpritePath((String)name));
        }
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();


    }


    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);

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
        AssetsManager.getInstance().disposeStage("world4");
    }

}
