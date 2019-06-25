package gdx.kapotopia.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import gdx.kapotopia.AssetsManager;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.Utils;

public class MainMenu implements Screen {

    private Kapotopia game;
    private Stage stage;

    private Sound clic;

    public MainMenu(final Kapotopia game) {

        this.game = game;
        Image imgFond = new Image(AssetsManager.getInstance().getTextureByPath("FondNiveauBlanc2.png"));
        stage = new Stage(game.viewport);

        this.clic = AssetsManager.getInstance().getSoundByPath("sound/bruitage/kickhat__open-button-2.wav");
        //Import font
        TextButton.TextButtonStyle style = Utils.getStyleFont("SEASRN__.ttf");
        //setup Button

        Button world1 = new TextButton("World 1", style);
        Button world2 = new TextButton("World 2", style);
        Button world3 = new TextButton("World 3", style);
        Button world4 = new TextButton("World 4", style);
        final float x = game.viewport.getWorldWidth() / 2.5f;
        float y = game.viewport.getWorldHeight() *0.2f;
        world4.setPosition(x, y);
        y = game.viewport.getWorldHeight() *0.4f;
        world3.setPosition(x, y);
        y = game.viewport.getWorldHeight()*0.6f;
        world2.setPosition(x, y);
        y = game.viewport.getWorldHeight()*0.8f;
        world1.setPosition(x, y);
        world1.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                clic.play();
                game.changeScreen(ScreenType.WORLD1);
            }
        });
        world2.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                clic.play();
                game.changeScreen(ScreenType.WORLD2);
            }
        });
        world3.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                clic.play();
                game.changeScreen(ScreenType.WORLD3);
            }
        });
        world4.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                clic.play();
                game.changeScreen(ScreenType.WORLD4);
            }
        });

        stage.addActor(imgFond);
        //add button to the scene
        stage.addActor(world1);
        stage.addActor(world2);
        stage.addActor(world3);
        stage.addActor(world4);
        Gdx.input.setInputProcessor(stage);
        System.out.println("coucou");

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
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
        stage.dispose();
    }
}
