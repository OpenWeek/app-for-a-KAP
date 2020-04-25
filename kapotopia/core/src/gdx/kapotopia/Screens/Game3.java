package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.AssetsManaging.AssetPaths;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.Game3.Core;
import gdx.kapotopia.Game3.EventHandlerGame3;
import gdx.kapotopia.Helpers.Builders.PopUpBuilder;
import gdx.kapotopia.Helpers.Builders.TextButtonBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;

public class Game3 implements Screen {

    private final String TAG = this.getClass().getSimpleName();

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

        loadAssets();

        inGame = true;
        popStage = new Stage(game.viewport);

        core = new Core(this, 10,15, 3);

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
        final Image door = new Image(game.ass.get(AssetDescriptors.DOOR));
        final Image lock = new Image(game.ass.get(AssetDescriptors.DOOR_LOCK));
        final Image neon = new Image(game.ass.get(chooseNeon()));

        // Sounds and musics
        this.musicOn = game.getSettings().isMusicOn();
        this.successSound = game.ass.get(AssetDescriptors.SOUND_SUCCESS);
        this.music = game.ass.get(AssetDescriptors.MUSIC_GAME3);
        this.music.setLooping(true);

        stage.addActor(door);
        stage.addActor(lock);
        stage.addActor(neon);
    }

    private AssetDescriptor<Texture> chooseNeon() {
        Random r = new Random();
        String[] neons = {AssetPaths.NEON_ROSE, AssetPaths.NEON_RED, AssetPaths.NEON_TURQUOISE,
                AssetPaths.NEON_GREEN, AssetPaths.NEON_VIOLET};
        return new AssetDescriptor<Texture>(neons[r.nextInt(neons.length)], Texture.class);
    }

    private void loadAssets() {
        long startTime = TimeUtils.millis();
        // Graphics
        game.ass.load(AssetDescriptors.NEON_ROSE);
        game.ass.load(AssetDescriptors.NEON_RED);
        game.ass.load(AssetDescriptors.NEON_TURQUOISE);
        game.ass.load(AssetDescriptors.NEON_GREEN);
        game.ass.load(AssetDescriptors.NEON_VIOLET);
        game.ass.load(AssetDescriptors.DOOR);
        game.ass.load(AssetDescriptors.DOOR_LOCK);
        game.ass.load(AssetDescriptors.BATTERY);
        game.ass.load(AssetDescriptors.CLOSED_LOCK1);
        game.ass.load(AssetDescriptors.CLOSED_LOCK2);
        game.ass.load(AssetDescriptors.OPENED_LOCK1);
        game.ass.load(AssetDescriptors.OPENED_LOCK2);
        game.ass.load(AssetDescriptors.CROSS_T);
        game.ass.load(AssetDescriptors.TCROSS_T);
        game.ass.load(AssetDescriptors.LINE_T);
        game.ass.load(AssetDescriptors.HALF_LINE_T);
        game.ass.load(AssetDescriptors.TURN_T);
        // Sounds
        game.ass.load(AssetDescriptors.MUSIC_GAME3);

        game.ass.finishLoading();
        Gdx.app.log(TAG, game.ass.getDiagnostics());
        Gdx.app.log(TAG, "Elapsed time for loading assets : " + TimeUtils.timeSinceMillis(startTime) + " ms");
    }

    public Core getCore(){return core;}

    public void back(){
        if(core.playerSucceeded()) {
            this.successSound.play();
        }
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
        stage.dispose();
    }

    public void quitGameConfirm() {

        final PopUpBuilder popup = new  PopUpBuilder(game, popStage);

        popup.setTitle("Congratulations!");

        TextButton btnYes = new TextButtonBuilder(game, "Exit").withStyle(FontHelper.AESTHETIC_NORMAL_BLACK).build();
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

        TextButton btnYes = new TextButtonBuilder(game, "back").withStyle(FontHelper.AESTHETIC_NORMAL_BLACK).build();
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
