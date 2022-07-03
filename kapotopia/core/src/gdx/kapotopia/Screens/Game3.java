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

import java.util.ArrayList;
import java.util.Random;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.AssetsManaging.AssetPaths;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.Game3.Core;
import gdx.kapotopia.Game3.EventHandlerGame3;
import gdx.kapotopia.Helpers.Builders.PopUpBuilder;
import gdx.kapotopia.Helpers.Builders.TextButtonBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Languages;
import gdx.kapotopia.ScreenType;

public class Game3 implements Screen {

    private final String TAG = this.getClass().getSimpleName();

    private Kapotopia game;
    private Stage stage;
    private Stage popStage;
    private boolean inGame;

    private Sound successSound;

    private Core core;

    public Game3(final Kapotopia game) {

        this.game = game;

        // Allowing that the game intro can be skipped
        game.getSettings().setIntro_3_skip(true);

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

        // Sounds
        this.successSound = game.ass.get(AssetDescriptors.SOUND_SUCCESS);

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

        Gdx.app.log(TAG, game.ass.getDiagnostics());
        Gdx.app.log(TAG, "Elapsed time for loading assets : " + TimeUtils.timeSinceMillis(startTime) + " ms");
    }

    public Core getCore(){return core;}

    public void back(ArrayList<String> list){
        if(core.playerSucceeded()) {
            this.successSound.play();
        }
        quitGameConfirm(list);
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
        game.getMusicControl().playMusic();
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
        game.getMusicControl().pauseMusic();
    }

    @Override
    public void resume() {
        game.getMusicControl().playMusic();
    }

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void quitGameConfirm(ArrayList<String> list) {

        final PopUpBuilder popup = new  PopUpBuilder(game, popStage);

        String title = game.loc.getString("congrats_msg");
        if (!list.isEmpty()){
            title += "\n" + game.loc.getString("mistake_msg");
            for (String e : list){
                title += "\n"+ game.loc.getString(e);
            }
        }

        popup.setTitle(title);



        TextButton btnReplay = new TextButtonBuilder(game, game.loc.getString("restart_button")).withStyle(FontHelper.AESTHETIC_NORMAL_WHITE).build();
        btnReplay.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {

                // Do whatever here for exit button
                popup.close();

                game.destroyScreen(ScreenType.GAME3);
                game.changeScreen(ScreenType.GAME3);

                return true;
            }

        });

        TextButton btnYes = new TextButtonBuilder(game, game.loc.getString("exit_button")).withStyle(FontHelper.AESTHETIC_NORMAL_WHITE).build();
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
        
        popup.addButton(btnReplay);
        popup.addButton(btnYes);
        popup.setPosition(0,50);
        popup.show();
    }
    public void LockPopUp(String description) {

        final PopUpBuilder popup = new  PopUpBuilder(game, popStage);

        popup.setTitle(description);

        TextButton btnYes = new TextButtonBuilder(game, game.loc.getString("back_button")).withStyle(FontHelper.AESTHETIC_NORMAL_WHITE).build();
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
