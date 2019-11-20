package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
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
import gdx.kapotopia.AssetsManager.AssetsManager;
import gdx.kapotopia.Game3.Core;
import gdx.kapotopia.Game3.EventHandlerGame3;

public class Game3 implements Screen {

    private Kapotopia game;
    private Stage stage;
    private boolean inGame;

    private Sound successSound;

    private Core core;

    public Game3(final Kapotopia game) {

        this.game = game;
        inGame = true;

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
        iM.addProcessor(eventHandlerGame3);

        Gdx.input.setInputProcessor(iM);

        stage = new Stage(game.viewport);

        //stage.addActor(res);
        Image imgFond = new Image(AssetsManager.getInstance().getTextureByPath("game3/PorteNéonsBleus.png"));

        this.successSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/leszek-szary_success-1.wav");

        stage.addActor(imgFond);
        AssetsManager.getInstance().addStage(stage, "game3");

    }

    public void back(){
        if(core.playerSucceeded()) {
            this.successSound.play();
        }
        game.changeScreen(ScreenType.WORLD2);
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
        iM.addProcessor(new EventHandlerGame3(core));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        if(inGame) {
            core.draw();
        }
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
        AssetsManager.getInstance().disposeStage("game3");
    }

    public void quitGameConfirm() {

        Label.LabelStyle style = new Label.LabelStyle(FontHelper, Color.WHITE);
        Label label1 = new Label("Are you sure that you want to exit?", style);
        label1.setAlignment(Align.center);
        //style.font.setScale(1, -1);
        style.fontColor = Color.WHITE;

        Skin tileSkin = new Skin();
        Texture tex = new Texture(myButtontexture);
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tileSkin.add("white", tex);
        tileSkin.add("default", new BitmapFont());

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = tileSkin.newDrawable("white");
        textButtonStyle.down = tileSkin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = tileSkin.newDrawable("white",
                Color.LIGHT_GRAY);
        textButtonStyle.over = tileSkin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = _myTextBitmapFont;
        textButtonStyle.font.setScale(1, -1);
        textButtonStyle.fontColor = Color.WHITE;
        tileSkin.add("default", textButtonStyle);

        TextButton btnYes = new TextButton("Exit", tileSkin);
        TextButton btnNo = new TextButton("Cancel", tileSkin);

        // /////////////////
        Skin skinDialog = new Skin(Gdx.files.internal("data/uiskin.json"));
        final Dialog dialog = new Dialog("", skinDialog) {
            @Override
            public float getPrefWidth() {
                // force dialog width
                // return Gdx.graphics.getWidth() / 2;
                return 700f;
            }

            @Override
            public float getPrefHeight() {
                // force dialog height
                // return Gdx.graphics.getWidth() / 2;
                return 400f;
            }
        };
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(false);

        btnYes.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {

                // Do whatever here for exit button
                dialog.hide();
                dialog.cancel();
                dialog.remove();

                return true;
            }

        });

        btnNo.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {

                //Do whatever here for cancel

                dialog.cancel();
                dialog.hide();

                return true;
            }

        });

        /*TextureRegion myTex = new TextureRegion(_dialogBackgroundTextureRegion);
        myTex.flip(false, true);
        myTex.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Drawable drawable = new TextureRegionDrawable(myTex);
        dialog.setBackground(drawable);*/

        float btnSize = 80f;
        Table t = new Table();
        // t.debug();

        dialog.getContentTable().add(label1).padTop(40f);

        t.add(btnYes).width(btnSize).height(btnSize);
        t.add(btnNo).width(btnSize).height(btnSize);

        dialog.getButtonTable().add(t).center().padBottom(80f);
        dialog.show(stage).setPosition(
                (1720 / 2) - (720 / 2),
                (1080) - (1080 - 40));

        dialog.setName("quitDialog");
        stage.addActor(dialog);

    }

}
