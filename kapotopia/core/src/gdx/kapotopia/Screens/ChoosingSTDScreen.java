package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;

import org.graalvm.compiler.hotspot.IsGraalPredicate;

import java.util.ArrayList;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.GameDifficulty;
import gdx.kapotopia.Helpers.Builders.ImageBuilder;
import gdx.kapotopia.Helpers.Builders.ImageButtonBuilder;
import gdx.kapotopia.Helpers.Builders.PopUpBuilder;
import gdx.kapotopia.Helpers.Builders.TextButtonBuilder;
import gdx.kapotopia.Helpers.ChangeScreenListener;
import gdx.kapotopia.Helpers.StandardInputAdapter;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;

public class ChoosingSTDScreen implements Screen {

    private Kapotopia game;
    private Stage stage;
    private OrthographicCamera camera;

    private Sound clic;
    private Sound clicBlockedSound;
    private Sound pauseSound;

    private boolean popupHere = false;

    private Stage popStage;

    public ChoosingSTDScreen(final Kapotopia game) {
        this.game = game;
        this.stage = new Stage(game.viewport);
        this.camera = new OrthographicCamera(game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        this.camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f,0);
        game.viewport.setCamera(camera);
        this.camera.update();
        popStage = new Stage(game.viewport);


        this.clic = game.ass.get(AssetDescriptors.SOUND_CLICKED_BTN);
        this.clicBlockedSound = game.ass.get(AssetDescriptors.SOUND_HINT);
        this.pauseSound = game.ass.get(AssetDescriptors.SOUND_PAUSE);

        float screenHeight = game.viewport.getWorldHeight();
        float screenWidth = game.viewport.getWorldWidth();


        Image fond = new ImageBuilder()
                .withTexture(game.ass.get(AssetDescriptors.ISTCHOICE))
                .build();

        ImageButton VIH= new ImageButtonBuilder()
                .withBounds(0,4*screenHeight/5f, screenWidth/2f, screenHeight/5f)
                .withImageUp(game.ass.get(AssetDescriptors.VIH))
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupHere) {
                            game.vars.setChosenSTD(0);
                            game.destroyScreen(ScreenType.STDGAME4);
                            game.changeScreen(ScreenType.GAME4);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();

        TextButton VIH_Text = new TextButtonBuilder(game, game.loc.getString("hiv_name"))
                .withStyle(FontHelper.AESTHETIC_NORMAL_WHITE)
                .withPosition(screenWidth/5.5f, 4*screenHeight/5)
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupHere) {
                            game.vars.setChosenSTD(0);
                            game.destroyScreen(ScreenType.STDGAME4);
                            game.changeScreen(ScreenType.GAME4);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();
        ImageButton HEPA = new ImageButtonBuilder()
                .withBounds(screenWidth/2f,4*screenHeight/5f, screenWidth/2f, screenHeight/5f)
                .withImageUp(game.ass.get(AssetDescriptors.HEPA))
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupHere) {
                            game.vars.setChosenSTD(1);
                            game.destroyScreen(ScreenType.STDGAME4);
                            game.changeScreen(ScreenType.GAME4);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();
        TextButton Hepa_Text = new TextButtonBuilder(game, game.loc.getString("a_hepatitis_name"))
                .withStyle(FontHelper.AESTHETIC_NORMAL_WHITE)
                .withPosition(screenWidth/1.8f, 4*screenHeight/5)
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupHere) {
                            game.vars.setChosenSTD(1);
                            game.destroyScreen(ScreenType.STDGAME4);
                            game.changeScreen(ScreenType.GAME4);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();
        ImageButton HEPB = new ImageButtonBuilder()
                .withBounds(0,3*screenHeight/5f, screenWidth/2f, screenHeight/5f)
                .withImageUp(game.ass.get(AssetDescriptors.HEPB))
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupHere) {
                            game.vars.setChosenSTD(2);
                            game.destroyScreen(ScreenType.STDGAME4);
                            game.changeScreen(ScreenType.GAME4);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();
        TextButton Hepb_Text = new TextButtonBuilder(game, game.loc.getString("b_hepatitis_name"))
                .withStyle(FontHelper.AESTHETIC_NORMAL_WHITE)
                .withPosition(screenWidth/8, 3*screenHeight/5)
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupHere) {
                            game.vars.setChosenSTD(2);
                            game.destroyScreen(ScreenType.STDGAME4);
                            game.changeScreen(ScreenType.GAME4);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();
        ImageButton HEPC = new ImageButtonBuilder()
                .withBounds(screenWidth/2f,3*screenHeight/5f, screenWidth/2f, screenHeight/5f)
                .withImageUp(game.ass.get(AssetDescriptors.HEPC))
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupHere) {
                            game.vars.setChosenSTD(3);
                            game.destroyScreen(ScreenType.STDGAME4);
                            game.changeScreen(ScreenType.GAME4);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();
        TextButton Hepc_Text = new TextButtonBuilder(game, game.loc.getString("c_hepatitis_name"))
                .withStyle(FontHelper.AESTHETIC_NORMAL_WHITE)
                .withPosition(screenWidth/1.8f, 3*screenHeight/5)
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupHere) {
                            game.vars.setChosenSTD(3);
                            game.destroyScreen(ScreenType.STDGAME4);
                            game.changeScreen(ScreenType.GAME4);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();
        ImageButton SYPH = new ImageButtonBuilder()
                .withBounds(0,2*screenHeight/5f, screenWidth/2f, screenHeight/5f)
                .withImageUp(game.ass.get(AssetDescriptors.SYPHILIS))
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupHere) {
                            game.vars.setChosenSTD(4);
                            game.destroyScreen(ScreenType.STDGAME4);
                            game.changeScreen(ScreenType.GAME4);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();
        TextButton Syph_Text = new TextButtonBuilder(game, game.loc.getString("syphilis_name"))
                .withStyle(FontHelper.AESTHETIC_NORMAL_WHITE)
                .withPosition(screenWidth/8, 2*screenHeight/5)
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupHere) {
                            game.vars.setChosenSTD(4);
                            game.destroyScreen(ScreenType.STDGAME4);
                            game.changeScreen(ScreenType.GAME4);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();
        ImageButton HERP = new ImageButtonBuilder()
                .withBounds(screenWidth/2f,2*screenHeight/5f, screenWidth/2f, screenHeight/5f)
                .withImageUp(game.ass.get(AssetDescriptors.HERPES))
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupHere) {
                            game.vars.setChosenSTD(5);
                            game.destroyScreen(ScreenType.STDGAME4);
                            game.changeScreen(ScreenType.GAME4);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();
        TextButton Herp_Text = new TextButtonBuilder(game, game.loc.getString("herpes_name"))
                .withStyle(FontHelper.AESTHETIC_NORMAL_WHITE)
                .withPosition(screenWidth/2, 2*screenHeight/5)
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupHere) {
                            game.vars.setChosenSTD(5);
                            game.destroyScreen(ScreenType.STDGAME4);
                            game.changeScreen(ScreenType.GAME4);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();
        ImageButton PAPIL = new ImageButtonBuilder()
                .withBounds(0,screenHeight/5f, screenWidth/2f, screenHeight/5f)
                .withImageUp(game.ass.get(AssetDescriptors.PAPILLO))
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupHere) {
                            game.vars.setChosenSTD(6);
                            game.destroyScreen(ScreenType.STDGAME4);
                            game.changeScreen(ScreenType.GAME4);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();
        TextButton Papil_Text = new TextButtonBuilder(game, game.loc.getString("hpv_name"))
                .withStyle(FontHelper.AESTHETIC_NORMAL_WHITE)
                .withPosition(0, screenHeight/5)
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupHere) {
                            game.vars.setChosenSTD(6);
                            game.destroyScreen(ScreenType.STDGAME4);
                            game.changeScreen(ScreenType.GAME4);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();
        ImageButton CHLA = new ImageButtonBuilder()
                .withBounds(screenWidth/2f,screenHeight/5f, screenWidth/2f, screenHeight/5f)
                .withImageUp(game.ass.get(AssetDescriptors.CHLAMYDIA))
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupHere) {
                            game.vars.setChosenSTD(7);
                            game.destroyScreen(ScreenType.STDGAME4);
                            game.changeScreen(ScreenType.GAME4);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();
        TextButton Chla_Text = new TextButtonBuilder(game, game.loc.getString("chlamydia_name"))
                .withStyle(FontHelper.AESTHETIC_NORMAL_WHITE)
                .withPosition(screenWidth/1.8f, screenHeight/5)
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupHere) {
                            game.vars.setChosenSTD(7);
                            game.destroyScreen(ScreenType.STDGAME4);
                            game.changeScreen(ScreenType.GAME4);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();
        ImageButton GONO = new ImageButtonBuilder()
                .withBounds(0,0, screenWidth/2f, screenHeight/5f)
                .withImageUp(game.ass.get(AssetDescriptors.GONORRHEE))
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupHere) {
                            game.vars.setChosenSTD(8);
                            game.destroyScreen(ScreenType.STDGAME4);
                            game.changeScreen(ScreenType.GAME4);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();
        TextButton Gono_Text = new TextButtonBuilder(game, game.loc.getString("gonorrhea_name"))
                .withStyle(FontHelper.AESTHETIC_NORMAL_BLACK)
                .withPosition(screenWidth/10, 0)
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupHere) {
                            game.vars.setChosenSTD(8);
                            game.destroyScreen(ScreenType.STDGAME4);
                            game.changeScreen(ScreenType.GAME4);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();
        ImageButton TRICH = new ImageButtonBuilder()
                .withBounds(screenWidth/2f,0, screenWidth/2f, screenHeight/5f)
                .withImageUp(game.ass.get(AssetDescriptors.TRICHOMONAS))
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupHere) {
                            game.vars.setChosenSTD(9);
                            game.destroyScreen(ScreenType.STDGAME4);
                            game.changeScreen(ScreenType.GAME4);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();
        TextButton Trich_Text = new TextButtonBuilder(game, game.loc.getString("trichomonas_name"))
                .withStyle(FontHelper.AESTHETIC_NORMAL_WHITE)
                .withPosition(screenWidth/2, 0)
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupHere) {
                            game.vars.setChosenSTD(9);
                            game.destroyScreen(ScreenType.STDGAME4);
                            game.changeScreen(ScreenType.GAME4);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();

        stage.addActor(fond);
        stage.addActor(VIH);
        stage.addActor(HEPA);
        stage.addActor(HEPB);
        stage.addActor(HEPC);
        stage.addActor(SYPH);
        stage.addActor(HERP);
        stage.addActor(PAPIL);
        stage.addActor(CHLA);
        stage.addActor(GONO);
        stage.addActor(TRICH);
        stage.addActor(VIH_Text);
        stage.addActor(Hepa_Text);
        stage.addActor(Hepb_Text);
        stage.addActor(Hepc_Text);
        stage.addActor(Syph_Text);
        stage.addActor(Herp_Text);
        stage.addActor(Papil_Text);
        stage.addActor(Chla_Text);
        stage.addActor(Gono_Text);
        stage.addActor(Trich_Text);
        chooseSTDPopup();
    }

    @Override
    public void show() {
        setUpInputProcessor();
    }

    /**
     * Set up the input processor with the StandardInputAdapter
     */
    protected void setUpInputProcessor() {
        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(new StandardInputAdapter(this, game));
        im.addProcessor(popStage);
        im.addProcessor(stage);
        Gdx.input.setInputProcessor(im);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        popStage.draw();
        popStage.act(Gdx.graphics.getDeltaTime());

    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        pauseSound.play();
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
        popStage.dispose();
    }

    public void chooseSTDPopup() {

        final PopUpBuilder popup = new  PopUpBuilder(game, popStage);
        popupHere = true;

        String title = game.loc.getString("game4_choose");

        popup.setTitle(title);

        TextButton btnYes = new TextButtonBuilder(game, game.loc.getString("next_button"))
                .withStyle(FontHelper.AESTHETIC_NORMAL_WHITE)
                .withListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        System.out.println("closed");
                        popup.close();
                        popupHere = false;
                        return super.touchDown(event, x, y, pointer, button);
                    }
                })
                .build();

        popup.addButton(btnYes);
        popup.setPosition(0,(int)game.viewport.getWorldHeight()/3);
        popup.show();
    }

}
