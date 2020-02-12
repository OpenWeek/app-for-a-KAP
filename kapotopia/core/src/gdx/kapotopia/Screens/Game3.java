package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import gdx.kapotopia.*;
import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.AssetsManaging.FontHelper;
import gdx.kapotopia.AssetsManaging.UseFont;
import gdx.kapotopia.Game3.Core;
import gdx.kapotopia.Game3.EventHandlerGame3;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Helpers.Builders.PopUpBuilder;
import gdx.kapotopia.Helpers.Builders.TextButtonBuilder;

public class Game3 implements Screen {

    private Kapotopia game;
    private Stage stage;
    private Stage popStage;
    private boolean inGame;
    private boolean musicOn;

    private Sound successSound;
    private Music music;

    private Core core;

    public Game3(final Kapotopia game) {

        this.game = game;
        inGame = true;
        popStage = new Stage(game.viewport);

        core = new Core(this, 9,10, 2);

        EventHandlerGame3 eventHandlerGame3 = new EventHandlerGame3(core);

        InputMultiplexer iM = new InputMultiplexer();
        iM.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    game.destroyScreen(ScreenType.GAME3);
                    game.changeScreen(ScreenType.WORLD2);
                    return true;
                }
                return false;
            }
        });
        iM.addProcessor(popStage);
        iM.addProcessor(eventHandlerGame3);

        Gdx.input.setInputProcessor(iM);

        stage = new Stage(game.viewport);

        //stage.addActor(res);
        Image imgFond = new Image(AssetsManager.getInstance().getTextureByPath("game3/Porte.png"));
        Image imgFond2 = new Image(AssetsManager.getInstance().getTextureByPath("game3/VerrouFerme.png"));
        Image imgFond3 = new Image(AssetsManager.getInstance().getTextureByPath("game3/NeonsRoses.png"));

        // Sounds and musics
        this.musicOn = game.getSettings().isMusicOn();
        this.successSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/leszek-szary_success-1.wav");
        this.music = AssetsManager.getInstance().getMusicByPath("sound/one_eyed_maestro.mp3");
        this.music.setLooping(true);

        stage.addActor(imgFond);
        stage.addActor(imgFond2);
        stage.addActor(imgFond3);
        AssetsManager.getInstance().addStage(stage, "game3");

    }

    public Core getCore(){return core;}

    public void back(){
        if(core.playerSucceeded()) {
            this.successSound.play();
        }
        //game.changeScreen(ScreenType.WORLD2);
        quitGameConfirm();
    }
    public final Kapotopia getGame(){
        return game;
    }
    public Stage getPopStage(){
        return popStage;
    }

    @Override
    public void show() {
        //TODO Maybe Gdx.input.setInputProcessor(iM); needs to be here -> To check
        InputMultiplexer iM = new InputMultiplexer();
        iM.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    game.destroyScreen(ScreenType.GAME3);
                    game.changeScreen(ScreenType.WORLD2);
                    return true;
                }
                return false;
            }
        });
        iM.addProcessor(popStage);
        iM.addProcessor(new EventHandlerGame3(core));
        Gdx.input.setInputProcessor(iM);
        if (musicOn)
            music.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        if(inGame) {
            core.draw();
        }
        popStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        if (musicOn)
            this.music.pause();
    }

    @Override
    public void resume() {
        if (musicOn)
            this.music.play();
    }

    @Override
    public void hide() {
        if (musicOn)
            this.music.stop();
    }

    @Override
    public void dispose() {
        AssetsManager.getInstance().disposeStage("game3");
    }

    public void quitGameConfirm() {

        final PopUpBuilder popup = new  PopUpBuilder(game, popStage);

        popup.setTitle("Are you sure that you want to exit?");

        TextButton btnYes = new TextButtonBuilder("Exit").withStyle(UseFont.AESTHETIC_NORMAL_BLACK).build();
        btnYes.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {

                // Do whatever here for exit button
                popup.close();

                game.changeScreen(ScreenType.WORLD2);
                game.destroyScreen(ScreenType.GAME3);

                return true;
            }

        });

        popup.addButton(btnYes);
        popup.setPosition(0,50);
        popup.show();
    }
    public void LockPopUp(String description) {

        final PopUpBuilder popup = new  PopUpBuilder(game, popStage);

        popup.setTitle(description);

        TextButton btnYes = new TextButtonBuilder("back").withStyle(UseFont.AESTHETIC_NORMAL_BLACK).build();
        btnYes.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {

                // Do whatever here for exit button
                popup.close();
                return true;
            }

        });

        popup.addButton(btnYes);
        popup.setPosition(0,50);
        popup.show();
    }

}
