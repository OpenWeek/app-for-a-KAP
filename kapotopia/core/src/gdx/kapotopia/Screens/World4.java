package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

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
    private int istIndex;
    private String[] istNames;

    public World4(final Kapotopia game) {

        preload();

        this.game = game;
        Texture fond = AssetsManager.getInstance().getTextureByPath("Pokedex.png");
        Image imgFond = new Image(fond);
        stage = new Stage(game.viewport);
        stage.addActor(imgFond);

        TextButton.TextButtonStyle style = Utils.getStyleFont("COMMS.ttf", 60);


        final TextButton back = new TextButtonBuilder(Localisation.getInstance().getString("back_button")).withStyle(style)
                .withListener(new ChangeScreenListener(game, ScreenType.MAINMENU, ScreenType.WORLD4)).build();
        back.setPosition((game.viewport.getWorldWidth() / 2) - back.getWidth() / 2, 50);
        back.setVisible(true);
        stage.addActor(back);

        //computing top left corner of the game boy's upperscreen
        int upperWidth = (int)((430f/720f)*imgFond.getWidth());
        int upperHeight = (int)((370f/1280f)*imgFond.getHeight());

        int upperDownLeftCornerX = (int)((144f/720f)*imgFond.getWidth());
        int upperDownLeftCornerY = (int)((90f/1280f)*imgFond.getHeight());
        upperDownLeftCornerY = (int)game.viewport.getWorldHeight() - upperDownLeftCornerY - upperHeight - 90;
        upperDownLeftCornerY = upperDownLeftCornerY + (int)((1/20f)*game.viewport.getScreenHeight());



        //computing top left corner of the game boy's lowerscreen
        int lowerUpLeftCornerX = (int)((144f/720f)*imgFond.getWidth());
        int lowerUpLeftCornerY = (int)(((730f)/1280f)*imgFond.getHeight());

        int lowerWidth = (int)((630f/720f)*imgFond.getWidth());
        int lowerHeight = (int)((542f/1280f)*imgFond.getHeight());

        //this is used to make some sort of giant button
        displayedIst = new Image(AssetsManager.getInstance().getTextureByPath(STIData.getIstSpritePath(istNames[istIndex])));
        displayedIst.setBounds(upperDownLeftCornerX, upperDownLeftCornerY, upperWidth, upperHeight);
        Gdx.app.log("WR", displayedIst.getX()+" "+displayedIst.getY());
        displayedIst.setVisible(true);
        displayedIst.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                istIndex = (istIndex + 1) % istNames.length;
                displayedIst.setDrawable(
                        new TextureRegionDrawable(
                                new TextureRegion(AssetsManager.getInstance().getTextureByPath(
                                        STIData.getIstSpritePath(istNames[istIndex]))
                                )
                        )
                );
                // must return true for touchUp event to occur
                return true;
            }
        });
        displayedIst.setTouchable(Touchable.enabled);
        stage.addActor(displayedIst);
        AssetsManager.getInstance().addStage(stage, "world4");
    }

    private void preload(){

        Gdx.app.log("W4", "Preloading stuff... ");
        istNames = new String[STIData.getIstNames().length];
        for(Object name : STIData.getIstNames()){
            istNames[istIndex] = (String)name;
            istIndex = (istIndex + 1) % istNames.length;
            AssetsManager.getInstance().getTextureByPath(STIData.getIstSpritePath((String)name));
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
