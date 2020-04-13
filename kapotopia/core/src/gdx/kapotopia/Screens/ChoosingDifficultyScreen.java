package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;

import gdx.kapotopia.Animations.DifficultyScreenHellAnimation;
import gdx.kapotopia.Animations.DifficultyScreenInfinityAnimation;
import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Fonts.Font;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.Fonts.UseFont;
import gdx.kapotopia.GameDifficulty;
import gdx.kapotopia.Helpers.Alignement;
import gdx.kapotopia.Helpers.Builders.ImageTextButtonBuilder;
import gdx.kapotopia.Helpers.Padding;
import gdx.kapotopia.Helpers.StandardInputAdapter;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.Sound.SoundHelper;
import gdx.kapotopia.Sound.UseSound;
import gdx.kapotopia.UnlockedLevel;

public class ChoosingDifficultyScreen implements Screen {
    // Basic variables
    private Kapotopia game;
    private Stage stage;
    private OrthographicCamera camera;

    private Sound clic;
    private Sound clicBlockedSound;
    private Sound pauseSound;

    private final ScreenType nextScreen;

    private final String BTN_PATH = "ImagesGadgets/Bouton.png";

    private GameDifficulty choosenDifficulty;

    // Animation
    private Texture top;
    private Animation<TextureRegion> backgroundHell;
    private Animation<TextureRegion> backgroundInfinity;
    private SpriteBatch spriteBatch;
    private float stateTime;

    public ChoosingDifficultyScreen(final Kapotopia game) {
        this.game = game;
        this.stage = new Stage(game.viewport);
        this.camera = new OrthographicCamera(game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        this.camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f,0);
        this.camera.update();

        // Background animation
        this.top = AssetsManager.getInstance().getTextureByPath("EcranMenu/EcranJeu1Haut.png");
        this.backgroundHell = new DifficultyScreenHellAnimation(Animation.PlayMode.LOOP).getAnimation();
        this.backgroundInfinity = new DifficultyScreenInfinityAnimation(Animation.PlayMode.LOOP).getAnimation();
        this.spriteBatch = new SpriteBatch();
        stateTime = 0.1f;

        // Sounds

        this.clic = AssetsManager.getInstance().getSoundByPath("sound/bruitage/kickhat_open-button-2.wav");
        this.clicBlockedSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/dland_hint.wav");
        this.pauseSound = SoundHelper.getSound(UseSound.PAUSE);

        ScreenType nextScreen = (ScreenType) game.getTheValueGateway().removeFromTheStore("nextscreen");
        if(nextScreen == null)
            nextScreen = ScreenType.MAINMENU;
        this.nextScreen = nextScreen;
        UnlockedLevel ul_tmp = (UnlockedLevel) game.getTheValueGateway().removeFromTheStore("unlockedLevel");
        final UnlockedLevel unlockedLevel;
        if(ul_tmp == null)
            unlockedLevel = UnlockedLevel.HARD_UNLOCKED;
        else
            unlockedLevel = ul_tmp;

        // Buttons configuration
        Font fontNormal = FontHelper.getFont(UseFont.CLASSIC_SANS_NORMAL_WHITE);
        Font fontGrey = FontHelper.getFont(UseFont.CLASSIC_SANS_NORMAL_GRAY);
        final Font mediumBtnFont;
        final Font hardBtnFont;
        final Font infiniteBtnFont;

        switch (unlockedLevel) {
            case EASY_UNLOCKED:
                mediumBtnFont = fontGrey;
                hardBtnFont = fontGrey;
                infiniteBtnFont = fontGrey;
                break;
            case MEDI_UNLOCKED:
                mediumBtnFont = fontNormal;
                hardBtnFont = fontGrey;
                infiniteBtnFont = fontNormal;
                break;
            case HARD_UNLOCKED:
            default:
                mediumBtnFont = fontNormal;
                hardBtnFont = fontNormal;
                infiniteBtnFont = fontNormal;
                break;
        }

        final float WH = game.viewport.getWorldHeight();
        final Localisation loc = Localisation.getInstance();

        ImageTextButton infiniteBtn = new ImageTextButtonBuilder(game, loc.getString("infinite_button"))
                .withY(WH * 0.1f).withAlignment(Alignement.CENTER).withPadding(Padding.STANDARD)
                .withListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        Gdx.input.vibrate(50);
                        if(unlockedLevel == UnlockedLevel.EASY_UNLOCKED) {
                            clicBlockedSound.play();
                        }else {
                            goToNextScreen(GameDifficulty.INFINITE);
                        }
                    }
                }).withFontStyle(infiniteBtnFont).withImageStyle(BTN_PATH).build();

        ImageTextButton hardBtn = new ImageTextButtonBuilder(game, loc.getString("hard_button"))
                .withY(WH * 0.3f).withAlignment(Alignement.CENTER).withPadding(Padding.STANDARD)
                .withListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        Gdx.input.vibrate(50);
                        if(unlockedLevel == UnlockedLevel.EASY_UNLOCKED || unlockedLevel == UnlockedLevel.MEDI_UNLOCKED) {
                            clicBlockedSound.play();
                        }else{
                            goToNextScreen(GameDifficulty.HARD);
                        }
                    }
                }).withFontStyle(hardBtnFont).withImageStyle(BTN_PATH).build();

        ImageTextButton mediumBtn = new ImageTextButtonBuilder(game, loc.getString("medium_button"))
                .withY(WH * 0.5f).withAlignment(Alignement.CENTER).withPadding(Padding.STANDARD)
                .withListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        Gdx.input.vibrate(50);
                        if(unlockedLevel == UnlockedLevel.EASY_UNLOCKED) {
                            clicBlockedSound.play();
                        }else{
                            goToNextScreen(GameDifficulty.MEDIUM);
                        }
                    }
                }).withFontStyle(mediumBtnFont).withImageStyle(BTN_PATH).build();

        ImageTextButton easyBtn = new ImageTextButtonBuilder(game, loc.getString("easy_button"))
                .withY(WH * 0.7f).withAlignment(Alignement.CENTER).withPadding(Padding.STANDARD)
                .withListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        Gdx.input.vibrate(50);
                        goToNextScreen(GameDifficulty.EASY);
                    }
                }).withFontStyle(fontNormal).withImageStyle(BTN_PATH).build();

        stage.addActor(easyBtn);
        stage.addActor(mediumBtn);
        stage.addActor(hardBtn);
        stage.addActor(infiniteBtn);

        // Other variables
        choosenDifficulty = GameDifficulty.EASY;

        AssetsManager.getInstance().addStage(stage, this.getClass().getName());
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
        im.addProcessor(stage);
        Gdx.input.setInputProcessor(im);
    }

    private void goToNextScreen(GameDifficulty difficulty) {
        clic.play();
        game.vars.setChoosenDifficultyG1(difficulty);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.destroyScreen(ScreenType.DIF);
                game.changeScreen(nextScreen);
            }
        },0.5f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        stateTime += delta;
        spriteBatch.begin();
        spriteBatch.draw(backgroundInfinity.getKeyFrame(stateTime, true), 0f, 0f);
        spriteBatch.draw(backgroundHell.getKeyFrame(stateTime, true), 0f, 0f);
        spriteBatch.draw(top, 0f, 0f);
        spriteBatch.end();

        stage.act(delta);
        stage.draw();
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
        spriteBatch.dispose();
    }
}
