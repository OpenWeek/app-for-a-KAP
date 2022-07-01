package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.Fonts.Font;
import gdx.kapotopia.Game4.GameState;
import gdx.kapotopia.GameConfig;
import gdx.kapotopia.Helpers.Alignement;
import gdx.kapotopia.Helpers.Builders.ImageButtonBuilder;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Helpers.Builders.ImageTextButtonBuilder;
import gdx.kapotopia.Helpers.Padding;
import gdx.kapotopia.Kapotopia;

import com.badlogic.gdx.scenes.scene2d.Stage;

import gdx.kapotopia.Languages;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.ScreenType;


public class Game4 implements Screen {

    private Kapotopia game;
    private Stage stage;
    private Localisation loc;

    private float screenWidth;
    private float screenHeight;
    private ImageButton pauseIcon;
    private ImageTextButton quitBtn;
    private ImageTextButton pauseBtn;
    private ImageTextButton legendBtn;
    private ImageTextButton returnBtn;
    private Image legendImg;
    private final float BTN_SPACING = 90f;
    private Font normalFont;

    private Texture arrow1, arrow2, arrow3, arrow4;

    private Label scoreLabel;

    private final String TAG = this.getClass().getSimpleName();
    private GameState gameState;

    private OrthographicCamera camera;

    public Game4(final Kapotopia game) {
        this.game = game;
        this.loc = game.loc;

        screenWidth = game.viewport.getWorldWidth();
        screenHeight = game.viewport.getWorldHeight();
        this.normalFont = FontHelper.CLASSIC_SANS_NORMAL_WHITE;

        this.camera = new OrthographicCamera(screenWidth, screenHeight);
        game.viewport.setCamera(camera);

        game.getSettings().setIntro_4_skip(true);

        loadAssets();

        this.arrow1 = game.ass.get(AssetDescriptors.RIGHT_ARROW4);
        this.arrow2 = game.ass.get(AssetDescriptors.DOWN_ARROW4);
        this.arrow3 = game.ass.get(AssetDescriptors.LEFT_ARROW4);
        this.arrow4 = game.ass.get(AssetDescriptors.UP_ARROW4);


        ImageButton rightArrow = new ImageButtonBuilder()
                .withImageUp(arrow1)
                .withPosition(screenWidth/2 + 125, screenHeight/11)
                .withListener(new InputListener() {
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        setDirection(1);
                        return true;
                    }
                })
                .build();

        ImageButton downArrow = new ImageButtonBuilder()
                .withImageUp(arrow2)
                .withPosition(screenWidth/2 -31, screenHeight/20 - 20)
                .withListener(new InputListener() {
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        setDirection(2);
                        return true;
                    }
                })
                .build();

        ImageButton leftArrow = new ImageButtonBuilder()
                .withImageUp(arrow3)
                .withPosition(screenWidth/3 - 50, screenHeight/11)
                .withListener(new InputListener() {
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        setDirection(3);
                        return true;
                    }
                })
                .build();

        ImageButton upArrow = new ImageButtonBuilder()
                .withImageUp(arrow4)
                .withPosition(screenWidth/2 - 31, screenHeight/7 + 10)
                .withListener(new InputListener() {
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        setDirection(0);
                        return true;
                    }
                })
                .build();


        Image background = new Image(game.ass.get(AssetDescriptors.BACKGROUND_GAME4));

        if (game.loc.getChosenLanguage() == Languages.FRENCH){
            this.legendImg = new Image(game.ass.get(AssetDescriptors.LEGEND_FR));
        }
        else{
            this.legendImg = new Image(game.ass.get(AssetDescriptors.LEGEND_EN));
        }
        this.legendImg.setVisible(false);

        this.stage = new Stage(game.viewport);
        this.stage.addActor(background);
        this.stage.addActor(rightArrow);
        this.stage.addActor(downArrow);
        this.stage.addActor(leftArrow);
        this.stage.addActor(upArrow);



        gameState = new GameState(game, this);

        // Buttons
        final Game4 dis = this;
        this.pauseIcon = new ImageButtonBuilder().withImageUp(game.ass.get(AssetDescriptors.PAUSE_LOGO))
                .withImageChecked(game.ass.get(AssetDescriptors.PLAY_LOGO))
                .withImageDown(game.ass.get(AssetDescriptors.PAUSE_LOGO))
                .withBounds(gameState.getBounds().width - (screenWidth / 5f),
                        gameState.getBounds().height - (screenHeight / 10f), screenWidth / 7.2f, screenHeight / 25f)
                .withListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(gameState.isPaused()) {
                            dis.pauseIcon.setChecked(false);
                            resumeFromPause();
                        }else{
                            dis.pauseIcon.setChecked(true);
                            pause();
                        }
                    }
                }).build();
        stage.addActor(pauseIcon);

        EventListener quitEvent = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getMusicControl().stopMusic();
                game.destroyScreen(ScreenType.GAME4);
                game.destroyScreen(ScreenType.WORLD2);
                game.changeScreen(ScreenType.WORLD2);
            }
        };

        EventListener pauseEvent = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(gameState.isPaused()) {
                    pauseIcon.setChecked(false);
                    resumeFromPause();
                } else {
                    pauseIcon.setChecked(true);
                    pause();
                }
                Gdx.app.debug(TAG, "pauseLabel clicked - isPaused is " + gameState.isPaused());
            }
        };

        EventListener legendEvent = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(gameState.isPaused()) {
                    legendImg.setVisible(true);
                    returnBtn.setVisible(true);
                    pauseBtn.setVisible(false);
                }
                Gdx.app.debug(TAG, "pauseLabel clicked - isPaused is " + gameState.isPaused());
            }
        };

        EventListener returnEvent = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(gameState.isPaused()) {
                    legendImg.setVisible(false);
                    returnBtn.setVisible(false);
                    pauseBtn.setVisible(true);
                }
                Gdx.app.debug(TAG, "pauseLabel clicked - isPaused is " + gameState.isPaused());
            }
        };

        quitBtn = new ImageTextButtonBuilder(game, loc.getString("quit_button_text"))
                .withFontStyle(FontHelper.CLASSIC_SANS_NORMAL_WHITE)
                .withAlignment(Alignement.CENTER)
                .withY((gameState.getBounds().getHeight() / 2) - 2*BTN_SPACING)
                .withPadding(Padding.STANDARD)
                .withListener(quitEvent)
                .withImageStyle(game.ass.get(AssetDescriptors.BTN_ROCK))
                .isVisible(false)
                .build();

        pauseBtn = new ImageTextButtonBuilder(game, loc.getString("continue_button"))
                .withFontStyle(normalFont).withAlignment(Alignement.CENTER) // faut rajouter le x
                .withY(gameState.getBounds().height / 2)
                .isVisible(false)
                .withImageStyle(game.ass.get(AssetDescriptors.BTN_ROCK))
                .withPadding(Padding.STANDARD)
                .withListener(pauseEvent)
                .build();

        legendBtn = new ImageTextButtonBuilder(game, loc.getString("legend_button"))
                .withFontStyle(normalFont).withAlignment(Alignement.CENTER)
                .withY((gameState.getBounds().getHeight() / 2) - BTN_SPACING)
                .isVisible(false)
                .withImageStyle(game.ass.get(AssetDescriptors.BTN_ROCK))
                .withPadding(Padding.STANDARD)
                .withListener(legendEvent)
                .build();

        returnBtn = new ImageTextButtonBuilder(game, loc.getString("previous_button"))
                .withFontStyle(normalFont)
                .withPosition(screenWidth - 230, (gameState.getBounds().getHeight()) - BTN_SPACING)
                .isVisible(false)
                .withImageStyle(game.ass.get(AssetDescriptors.BTN_ROCK))
                .withPadding(Padding.STANDARD)
                .withListener(returnEvent)
                .build();

        stage.addActor(quitBtn);
        stage.addActor(pauseBtn);
        stage.addActor(legendBtn);

        scoreLabel = new LabelBuilder(game, game.loc.getString("score2_label")  + gameState.getTotalScore()).withStyle(FontHelper.CLASSIC_SANS_NORMAL_WHITE)
                .withPosition(50, screenHeight - 120).build();
        this.stage.addActor(scoreLabel);
        this.stage.addActor(legendImg);
        stage.addActor(returnBtn);
    }

    private void setDirection(int nextDirection){
        this.gameState.setDirection(nextDirection);
    }

    private void loadAssets() {
        long startTime = TimeUtils.millis();

        game.ass.finishLoading();
        Gdx.app.log(TAG, game.ass.getDiagnostics());
        Gdx.app.log(TAG, "Elapsed time for loading assets : " + TimeUtils.timeSinceMillis(startTime) + " ms");
    }

    public void updateWhenResumeFromPause() {
        pauseBtn.setVisible(false);
        quitBtn.setVisible(false);
        legendBtn.setVisible(false);
    }

    public void updateAtPause() {
        pauseBtn.setVisible(true);
        quitBtn.setVisible(true);
        legendBtn.setVisible(true);
    }

    @Override
    public void show() {
        InputMultiplexer iM = new InputMultiplexer();
        iM.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    game.destroyScreen(ScreenType.GAME4);
                    game.changeScreen(ScreenType.WORLD2);
                    return true;
                }
                return false;
            }
        });
        iM.addProcessor(stage);
        Gdx.input.setInputProcessor(iM);
        game.getMusicControl().playMusic();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        scoreLabel.setText(game.loc.getString("score2_label")  + gameState.getTotalScore());

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        gameState.update(delta, game.viewport);
        gameState.draw(screenWidth, screenHeight, camera);

    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
    }

    @Override
    public void pause() {
        updateAtPause();
        gameState.updateOnPause();
        game.getMusicControl().pauseMusic();
        Gdx.app.debug(TAG, "game paused - isPaused is true");
    }
    @Override
    public void resume() {
        gameState.updateOnResume();
        game.getMusicControl().playMusic();
        Gdx.app.debug(TAG, "game resumed - isPaused is false");
    }
    public void resumeFromPause() {
        updateWhenResumeFromPause();
        gameState.resumeFromPause();
        game.getMusicControl().playMusic();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }
}
