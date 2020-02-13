package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import gdx.kapotopia.Animations.EyesBackgroundAnimation;
import gdx.kapotopia.Animations.LeavesBackgroundAnimation;
import gdx.kapotopia.Animations.LetsgoG1Animation;
import gdx.kapotopia.Animations.SkyBackgroundAnimation;
import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.AssetsManaging.FontHelper;
import gdx.kapotopia.AssetsManaging.SoundHelper;
import gdx.kapotopia.AssetsManaging.UseFont;
import gdx.kapotopia.AssetsManaging.UseSound;
import gdx.kapotopia.Game1.CollisionManager;
import gdx.kapotopia.Game1.MireilleBasic;
import gdx.kapotopia.Game1.MireilleJojo;
import gdx.kapotopia.Game1.MireilleListener;
import gdx.kapotopia.Game1.VIRUS_TYPE;
import gdx.kapotopia.Game1.Virus;
import gdx.kapotopia.Game1.VirusContainer;
import gdx.kapotopia.GameDifficulty;
import gdx.kapotopia.Helpers.Alignement;
import gdx.kapotopia.Helpers.Builders.ImageButtonBuilder;
import gdx.kapotopia.Helpers.Builders.ImageTextButtonBuilder;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Helpers.Builders.TextButtonBuilder;
import gdx.kapotopia.Helpers.Padding;
import gdx.kapotopia.Helpers.SimpleDirectionGestureDetector;
import gdx.kapotopia.Helpers.StandardInputAdapter;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.UnlockedLevel;
import gdx.kapotopia.Utils;

public class Game1 implements Screen, MireilleListener {

    // General Variables
    private Kapotopia game;
    private Stage stage;
    private OrthographicCamera camera;
    private Random random;
    // Graphisms and animations
    private final Animation<TextureRegion> sky;
    private final Texture trees;
    private final Animation<TextureRegion> eyes;
    private final Animation<TextureRegion> leaves;
    private final Animation<TextureRegion> letsGoAnimation;
    private float stateTime;
    private boolean letsGoAppeared;
    private SpriteBatch animationSpriteBatch;
    private SpriteBatch backgroundBatch;
    //JOJO
    private byte Jcount;
    private boolean jojoAppears;
    private boolean jojoHasAppeared;
    private boolean jojoTimerLaunched;
    private MireilleJojo jojo;
    // Style of text
    private TextButton.TextButtonStyle style;
    private TextButton.TextButtonStyle styleSmall;
    // Sounds and musics
    private Sound touchedSound;
    private Sound failSound;
    private Sound successSound;
    private Sound jumpSound;
    private Sound pauseSound;
    private Sound istTouchedSound;
    private Music music;
    private Music musicJ;
    // Useful Variables
    private boolean isFinish;
    private boolean didGameOverScreenAppeared;
    private boolean isPaused;   // To check if the game is paused or not
    private boolean musicOn;    // If this is true, the music will play
    private boolean musicJOn;
    private boolean victory;
    private byte mireilleLife;
    private int totalScore;
    private int istsCatched;
    private int istsToCatch;
    private int upperLimitScore;
    // Labels and Buttons
    private Label lifeLabel;
    private Label scoreLabel;
    private Label istCatchedLabel;
    private Label ennemiNameLabel;
    private Label pauseLabel;
    private Label missedLabel;
    private ImageButton pauseIcon;
    private ImageTextButton quitBtn;

    // Constants

    private final int MIN_X;
    private final int MAX_X;
    private final int MIN_Y;
    private final int MOVE_VALUE_X;
    private final Rectangle bounds;
    private final float BTN_SPACING = 90f;

    private final String TAG = this.getClass().getSimpleName();
    private final String LIFE_TXT = "Vies: ";
    private final String SCORE_TXT = "Score: ";
    private final String HIGHSCORE_TXT = "Highscore: ";
    private final String IST_CATCHED_TXT = "Ists attrapées: ";
    private final String MUSICPATH = "sound/Musique_fast_chiptune.ogg";
    private final String BTN_PATH = "ImagesGadgets/Bouton.png";

    private GameDifficulty difficulty;

    // Actors
    private final Virus ennemi;
    private final MireilleBasic mireille;

    private List<VirusContainer> ist;  // <Nom, VIRUS_TYPE>
    private List<VirusContainer> fake; // <Nom, VIRUS_TYPE>
    private List<VirusContainer> maybeIst;

    private HashSet<VirusContainer> missedIsts;


    /* *******************************************************
     *                      M E T H O D S                   *
     ******************************************************* */

    //TODO add Jojos references

    /**
     * Constructeur
     * @param game
     */
    public Game1(final Kapotopia game) {
        // Initialize global variables
        this.game = game;
        this.stage = new Stage(game.viewport);
        this.camera = new OrthographicCamera(game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        this.camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f,0); // I dont understand why, but this works. If someone knows plz explain me. F.D.
        this.camera.update();
        this.random = new Random();
        final Localisation loc = Localisation.getInstance();

        this.style = FontHelper.getStyleFont(UseFont.CLASSIC_SANS_NORMAL_WHITE);
        this.styleSmall = FontHelper.getStyleFont(UseFont.CLASSIC_SANS_SMALL_WHITE);

        this.bounds = new Rectangle(0,0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        this.MIN_X = 15;
        this.MAX_X = Utils.floorOfAMultipleOf250( ( ((int) game.viewport.getWorldWidth()) / 2) + 250);
        this.MIN_Y = 25;
        this.MOVE_VALUE_X = 250;

        this.isFinish = false;
        this.didGameOverScreenAppeared = false;
        this.isPaused = true;
        this.musicOn = game.getSettings().isMusicOn();
        this.victory = false;
        this.totalScore = 0;
        this.istsCatched = 0;

        this.missedIsts = new HashSet<VirusContainer>();

        /* Setting up the stage */

        // Graphisms and animations
        initVirusTextures();
        this.sky = new SkyBackgroundAnimation(Animation.PlayMode.LOOP_RANDOM).getAnimation();
        this.trees = AssetsManager.getInstance().getTextureByPath("World1/Game1/Jungle.png");
        this.eyes = new EyesBackgroundAnimation(Animation.PlayMode.LOOP).getAnimation();
        this.leaves = new LeavesBackgroundAnimation(Animation.PlayMode.LOOP_RANDOM).getAnimation();

        this.letsGoAnimation = new LetsgoG1Animation(Animation.PlayMode.NORMAL).getAnimation();
        this.animationSpriteBatch = new SpriteBatch();
        this.backgroundBatch = new SpriteBatch();
        stateTime = 0f;
        letsGoAppeared = false;

        ////////////////////////////////////////////////////////////////////////////////////////////
        Jcount = 0;
        jojoAppears = false;
        jojoHasAppeared = false;
        jojoTimerLaunched = false;
        musicJOn = false;
        this.jojo = new MireilleJojo(game);
        this.musicJ = AssetsManager.getInstance().getMusicByPath("sound/bgm.ogg");
        this.musicJ.setLooping(true);
        this.musicJ.setPosition(0);
        ////////////////////////////////////////////////////////////////////////////////////////////

        // Major actors
        this.mireille = prepareMireille();
        this.mireille.addListener(this);
        this.ennemi = new Virus(this.bounds, this);

        // Buttons
        this.pauseIcon = new ImageButtonBuilder().withImageUp("pause_logo_2.png")
                .withBounds(bounds.width - 230, bounds.height - 200, 140, 80)
                .withListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(isPaused) {
                            resumeFromPause();
                        }else{
                            pause();
                        }
                    }
                }).build();
        stage.addActor(pauseIcon);

        EventListener quitEvent = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                music.stop();
                musicJ.stop();
                game.destroyScreen(ScreenType.GAME1);
                game.destroyScreen(ScreenType.MAINMENU);
                game.changeScreen(ScreenType.MAINMENU);
            }
        };

        quitBtn = new ImageTextButtonBuilder(game, loc.getString("quit_button_text"))
                .withFontStyle(UseFont.CLASSIC_SANS_NORMAL_WHITE).withAlignment(Alignement.CENTER)
                .withY((bounds.getHeight() / 2) - BTN_SPACING).withPadding(Padding.STANDARD)
                .withListener(quitEvent).withImageStyle(BTN_PATH).isVisible(false).build();

        stage.addActor(quitBtn);

        // Labels
        lifeLabel = new LabelBuilder(LIFE_TXT + mireilleLife).withStyle(style)
                .withPosition(bounds.width - 240, bounds.height - 100).build();
        istCatchedLabel = new LabelBuilder(IST_CATCHED_TXT  + istsCatched).withStyle(style)
                .withPosition(25, bounds.height - 100).build();
        scoreLabel = new LabelBuilder(SCORE_TXT  + totalScore).withStyle(style)
                .withPosition(25, bounds.height - 200).build();
        pauseLabel = new LabelBuilder(loc.getString("pause_label_text")).withStyle(style).withTextAlignement(Align.center)
                .withPosition(((bounds.width / 5) * 2) + 20, bounds.height / 2).isVisible(false).build();
        missedLabel = new LabelBuilder(loc.getString("missed_label_text")).withStyle(styleSmall).isVisible(false).build();
        ennemiNameLabel = new LabelBuilder(ennemi.getName()).withStyle(styleSmall).withTextAlignement(Align.center)
                .withPosition(ennemi.getX() + (ennemi.getRealWidth() - ennemi.getName().length()) /2,ennemi.getY() - 50).build();

        pauseLabel.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               if(isPaused) {
                   resumeFromPause();
               } else {
                   pause();
               }
               Gdx.app.debug(TAG, "pauseLabel clicked - isPaused is " + isPaused);
           }
        });

        stage.addActor(lifeLabel);
        stage.addActor(istCatchedLabel);
        stage.addActor(scoreLabel);
        stage.addActor(pauseLabel);
        stage.addActor(missedLabel);
        stage.addActor(ennemiNameLabel);

        // Music and Sounds
        this.music = prepareMusic();
        this.touchedSound = SoundHelper.getSound(UseSound.PUNCH);
        this.failSound = SoundHelper.getSound(UseSound.FAIL);
        this.jumpSound = SoundHelper.getSound(UseSound.JUMP_V2);
        this.pauseSound = SoundHelper.getSound(UseSound.PAUSE);
        this.istTouchedSound = SoundHelper.getSound(UseSound.COIN);
        this.successSound = SoundHelper.getSound(UseSound.SUCCESS);

        // Last configurations

        this.difficulty = (GameDifficulty) game.getTheValueGateway().removeFromTheStore("difficulty");
        if(this.difficulty == null)
            this.difficulty = GameDifficulty.MEDIUM;
        configureGame(this.difficulty);
        AssetsManager.getInstance().addStage(stage, TAG);
    }

    @Override
    public void show() {
        // in the case when the player come back after changed preferences by using back button
        this.musicOn = game.getSettings().isMusicOn();
        if(musicOn) {
            if (musicJOn) {
                musicJ.play();
            } else {
                music.play();
            }
        }

        setUpInputProcessor();
        //In case there are problems to restart the game where it was left after going to another screen and returning, it could maybe be solved by setting the Input Processor (Gdx.input.setInputProcessor(im);) here and not when the game is first created
        // We ensure that after the animation has played, the game really start
        if(!letsGoAppeared) {
            final float delay = this.letsGoAnimation.getFrameDuration() * this.letsGoAnimation.getKeyFrames().length;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    letsGoAppeared = true;
                    isPaused = false;
                    Gdx.app.debug(TAG, "LetsGoAppeared - isPaused is false");
                }
            }, delay);
        }
        Gdx.app.log(TAG, "coucou c'est oui-oui");
    }

    private void renderBackground() {

        backgroundBatch.begin();

        backgroundBatch.draw(sky.getKeyFrame(stateTime, true),0, 0);
        backgroundBatch.draw(trees, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight(),
                0, 0,1080,1920, false, false);
        backgroundBatch.draw(eyes.getKeyFrame(stateTime, true),0, 0);
        if(!isFinish) // We hide mireille  when the game is finished
            mireille.draw(backgroundBatch, 0);
        ennemi.draw(backgroundBatch, 0);
        backgroundBatch.draw(leaves.getKeyFrame(stateTime, true), 0, 0);

        backgroundBatch.end();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        backgroundBatch.setProjectionMatrix(camera.combined);
        animationSpriteBatch.setProjectionMatrix(camera.combined);
        jojo.upProjMatrBatch(camera.combined);

        stateTime += delta;
        renderBackground();

        lifeLabel.setText(LIFE_TXT + mireilleLife);
        scoreLabel.setText(SCORE_TXT + totalScore);
        istCatchedLabel.setText(IST_CATCHED_TXT + istsCatched);
        if(isFinish) {
            // GAME OVER
            if(!didGameOverScreenAppeared) {
                gameOver();
                didGameOverScreenAppeared = true;
            }
        }else{
            if(!isPaused) {

                stage.act(delta);
                mireille.act(delta);
                ennemi.act(delta);

                if(CollisionManager.getInstance().checkCollision(mireille,ennemi)) {
                    // Collision
                }
            }
        }
        stage.draw();

        if(!letsGoAppeared) {

            TextureRegion currentFrame = letsGoAnimation.getKeyFrame(stateTime, false);
            animationSpriteBatch.begin();
            animationSpriteBatch.draw(currentFrame, (bounds.width / 5) * 2, bounds.height / 2);
            animationSpriteBatch.end();
        }

        if (jojoAppears && !jojoHasAppeared) {
            jojo.draw(delta);
            if (!jojoTimerLaunched) {
                music.stop();
                musicJ.play();
                musicJOn = true;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        jojoHasAppeared = true;
                        isPaused = false;
                        Gdx.app.debug(TAG, "Jojo has appeared - isPaused is false");
                    }
                }, 10f);
                jojoTimerLaunched = true;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        if(musicOn) {
            if (musicJOn) {
                musicJ.pause();
            }else{
                music.pause();
            }
        }
        pauseSound.play();
        pauseLabel.setVisible(true);
        quitBtn.setVisible(true);
        isPaused = true;
        Gdx.app.debug(TAG, "game paused - isPaused is true");
    }

    @Override
    public void resume() {
        isPaused = true;
        Gdx.app.debug(TAG, "game resumed - isPaused is false");
    }

    private void resumeFromPause() {
        pauseLabel.setVisible(false);
        quitBtn.setVisible(false);
        if(musicOn) {
            if (musicJOn) {
                musicJ.play();
            } else {
                music.play();
            }
        }

        isPaused = false;
    }

    @Override
    public void hide() {
        isPaused = true;
        Gdx.app.debug(TAG, "game hidden - isPaused is true");
        if(musicOn) {
            if (musicJOn) {
                musicJ.pause();
            } else {
                music.pause();
            }
        }
    }

    @Override
    public void dispose() {
        AssetsManager.getInstance().disposeStage(TAG);
        animationSpriteBatch.dispose();
        backgroundBatch.dispose();
    }

    // Textures

    /**
     * Initialize virus textures (true and fake ones) selected by reading sprite.xml
     * and by saving them into ArrayLists
     */
    private void initVirusTextures() {
        XmlReader xml = new XmlReader();
        Element root = xml.parse(Gdx.files.internal("sprite.xml"));
        Element ist_xml = root.getChildByName("ist-l");
        Element fake_xml = root.getChildByName("fakeist-l");
        Element maybe_xml = root.getChildByName("maybeist-l");

        List<VirusContainer> ist = new ArrayList<VirusContainer>();
        List<VirusContainer> fake = new ArrayList<VirusContainer>();
        List<VirusContainer> maybe = new ArrayList<VirusContainer>();

        for (Element el : ist_xml.getChildrenByName("ist")) {
            final String description = el.getChildByName("explanation").getText();
            ist.add(new VirusContainer(el.get("texture"),el.get("name"), true, false, description));
        }

        for (Element el : fake_xml.getChildrenByName("fakeist")) {
            fake.add(new VirusContainer(el.get("texture"),el.get("name"), false, false, ""));
        }

        for (Element el : maybe_xml.getChildrenByName("maybeist")) {
            final String description = el.getChildByName("explanation").getText();
            maybe.add(new VirusContainer(el.get("texture"), el.get("name"),true, true, description));
        }

        this.ist = ist;
        this.fake = fake;
        this.maybeIst = maybe;
    }
    public VirusContainer getRdmVirusTexture(VIRUS_TYPE type) {
        final int r;
        switch (type) {
            case IST:
                r = Math.abs(random.nextInt() % ist.size());
                return ist.get(r);
            case FAKEIST:
                r = Math.abs(random.nextInt() % fake.size());
                return fake.get(r);
            case MAYBEIST:
                if (difficulty != GameDifficulty.HARD) {
                    r = Math.abs(random.nextInt() % maybeIst.size());
                    return maybeIst.get(r);
                } else {
                    r = Math.abs(random.nextInt() % ist.size());
                    return ist.get(r);
                }
        }
        return null;
    }

    // Music

    private Music prepareMusic() {
        Music music = AssetsManager.getInstance().getMusicByPath(MUSICPATH);
        music.setPosition(0f);
        music.setLooping(false);
        music.setVolume(0.66f);
        music.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                music.setPosition(11.5f);
                music.play();
                Gdx.app.log(TAG, "Music stopped");
            }
        });
        return music;
    }

    private void resetMusic(Music music) {
        music.pause();
        music.setPosition(0f);
    }

    // Labels

    public void setNewEnnemiLabelPosition(float x, float y){
        this.ennemiNameLabel.setPosition(x, y);
    }
    public void changeEnnemiLabel(String newName) {
        this.ennemiNameLabel.setText(newName);
    }
    private void playMissedLabelAnim() {
        missedLabel.setVisible(true);
        final float x = mireille.getX(), y = mireille.getY() + mireille.getHeight();
        missedLabel.setPosition(x,y);
        missedLabel.addAction(Actions.moveTo(x,y + 50f, 1f));
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                missedLabel.setVisible(false);
            }
        },1f);
    }

    // Game mechanics

    private void configureGame(GameDifficulty dif) {
        switch (dif) {
            case EASY:
                mireille.setLifes((byte) 3);
                this.mireilleLife = mireille.getLifes();
                istsToCatch = 10;
                upperLimitScore = -1;
                break;
            case MEDIUM:
                mireille.setLifes((byte) 3);
                this.mireilleLife = mireille.getLifes();
                istsToCatch = 35;
                upperLimitScore = 200;
                //this.ennemi.setAccAddFactor(0.09f);
                break;
            case HARD:
                mireille.setLifes((byte) 1);
                this.mireilleLife = mireille.getLifes();
                istsToCatch = 50;
                upperLimitScore = 500;
                //this.ennemi.setAccAddFactor(0.10f);
                this.ennemi.setAcceleration(1.f);
                break;
            case INFINITE:
                mireille.setLifes((byte) 3);
                this.mireilleLife = mireille.getLifes();
                istsToCatch = Integer.MAX_VALUE;
                upperLimitScore = Integer.MAX_VALUE;
                break;
        }
    }
    private MireilleBasic prepareMireille() {
        final MireilleBasic mireille = new MireilleBasic(MIN_X, MIN_Y);
        mireille.updateCollision(MIN_X, MIN_Y);
        mireille.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log(TAG, "Something changed lol");
            }
        });
        return mireille;
    }
    public void addMissedIST(boolean isMaybeIST, String virusName) {
        VirusContainer vc;
        if(isMaybeIST) {
            vc = searchInList(this.maybeIst, virusName);
        } else {
            vc = searchInList(this.ist, virusName);
        }
        missedIsts.add(vc);

        playMissedLabelAnim();
    }
    public HashSet<VirusContainer> getMissedIST() {
        return missedIsts;
    }
    /**
     * Search in a list of VirusContainers
     * @param l a list of VirusContainer
     * @param name the key
     * @return the corresponding VirusContaining or null if no Virus was found
     */
    private VirusContainer searchInList(List<VirusContainer> l, String name) {
        for (VirusContainer v : l) {
            if (v.getName().equals(name))
                return v;
        }
        return null;
    }
    private void gameOver() {
        if(musicOn) {
            if (musicJOn) {
                musicJ.setVolume(0.1f);
            } else {
                this.music.setVolume(0.1f);
            }
        }

        // We hide every actor
        for(Actor actor : stage.getActors()) {
            if(!(actor.equals(this.trees) || actor.equals(this.leaves) || actor.equals(lifeLabel))) {
                actor.setVisible(false);
            }
        }

        /*  Here we make the highscore label
         *  It can take two forms, whenever there is a highscore breaker or not
         */
        int highscore = game.getSettings().getG1Highscore();
        final int scoreLabXFactor, scoreLabYFactor;
        final String highscoreLabHead, highscoreLabTail;
        final Label endScoreLabel;
        if (highscore <= totalScore) {
            // We dont need to display the endScoreLabel
            endScoreLabel = null;
            // We did a new highscore !
            game.getSettings().setG1Highscore(totalScore);
            highscore = totalScore;
            scoreLabXFactor = 300;
            scoreLabYFactor = 60;
            highscoreLabHead = Localisation.getInstance().getString("high_score_lab_head");
            highscoreLabTail = " !";
        } else {
            endScoreLabel = new LabelBuilder(SCORE_TXT + totalScore).withStyle(style)
                    .withPosition((bounds.width / 2) - 135, (bounds.height / 2) - 60).build();
            stage.addActor(endScoreLabel);
            scoreLabXFactor = 200;
            scoreLabYFactor = 120;
            highscoreLabHead = "";
            highscoreLabTail = "";
        }

        final Label highscoreLabel = new LabelBuilder(highscoreLabHead + HIGHSCORE_TXT + highscore + highscoreLabTail)
                .withPosition((bounds.width / 2) - scoreLabXFactor, (bounds.height / 2) - scoreLabYFactor - 10)
                .withStyle(UseFont.CLASSIC_BOLD_NORMAL_YELLOW).build();
        stage.addActor(highscoreLabel);

        /*
         * We set up the general title Which is either equal to "Bravo" or "Game over"
         * and which on pressed, redirect the player on another screen
         */
        final String titleText;
        int titleLabXFactor;
        if(victory) {
            // We unlock the new difficulties for the player
            switch (difficulty) {
                case EASY:
                    game.getSettings().setG1UnlockedLvl(UnlockedLevel.MEDI_UNLOCKED);
                    break;
                case MEDIUM:
                    game.getSettings().setG1UnlockedLvl(UnlockedLevel.HARD_UNLOCKED);
                    break;
                case INFINITE:
                    game.getSettings().setG1UnlockedLvl(UnlockedLevel.HARD_UNLOCKED);
                    break;
            }
            this.successSound.play();
            titleText = Localisation.getInstance().getString("success");
            titleLabXFactor = 150;
        }else{
            this.failSound.play(0.7f);
            titleText = Localisation.getInstance().getString("fail");
            titleLabXFactor = 250;
        }
        final Button title = new TextButtonBuilder(titleText).withStyle(UseFont.CLASSIC_REG_BIG_WHITE)
                .withPosition((bounds.getWidth() / 2) - titleLabXFactor, (bounds.getHeight() / 2) + 40).build();
        title.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // We hide what's left on the screen
                lifeLabel.setVisible(false);
                title.setVisible(false);
                highscoreLabel.setVisible(false);
                if(endScoreLabel != null)
                    endScoreLabel.setVisible(false);

                // If the player missed some STI's, the game will prompt it to the player and once the player
                // come back here, we show the different options
                if (!missedIsts.isEmpty()) {
                    game.getTheValueGateway().addToTheStore("G1-missedIST", missedIsts);
                    game.changeScreen(ScreenType.BILANG1);
                }

                EventListener continueEvent = new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (musicJOn) {
                            resetMusic(musicJ);
                        } else {
                            resetMusic(music);
                        }
                        game.destroyScreen(ScreenType.GAME1);
                        switch (difficulty) {
                            case EASY:
                                game.getTheValueGateway().addToTheStore("difficulty", GameDifficulty.MEDIUM);
                                game.changeScreen(ScreenType.GAME1);
                                break;
                            case MEDIUM:
                                game.getTheValueGateway().addToTheStore("difficulty", GameDifficulty.HARD);
                                game.changeScreen(ScreenType.GAME1);
                                break;
                            case HARD:
                                // We send the player to the next game, so GAME 2
                                game.changeScreen(ScreenType.GAME2);
                                break;
                        }

                    }
                };

                EventListener restartEvent = new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (musicJOn) {
                            resetMusic(musicJ);
                        } else {
                            resetMusic(music);
                        }
                        game.getTheValueGateway().addToTheStore("difficulty", difficulty);
                        game.destroyScreen(ScreenType.GAME1);
                        game.changeScreen(ScreenType.GAME1);
                    }
                };

                // Only if the player won we display the continue button
                if (victory) {
                    final ImageTextButton continueBtn = new ImageTextButtonBuilder(game, "Continuer")
                            .withFontStyle(UseFont.CLASSIC_SANS_NORMAL_WHITE)
                            .withPosition((bounds.getWidth() / 2) - 130, (bounds.getHeight() / 2) + BTN_SPACING)
                            .withListener(continueEvent).withImageStyle(BTN_PATH).build();

                    stage.addActor(continueBtn);
                }
                final ImageTextButton restartBtn = new ImageTextButtonBuilder(game, "Recommencer")
                        .withFontStyle(UseFont.CLASSIC_SANS_NORMAL_WHITE)
                        .withPosition((bounds.getWidth() / 2) - 200, (bounds.getHeight() / 2))
                        .withListener(restartEvent).withImageStyle(BTN_PATH).build();

                stage.addActor(restartBtn);
                quitBtn.setVisible(true);
            }
        });
        stage.addActor(title);
    }

    /**
     * Method used by lifeListener
     * @param life the new life value given to MireilleLife
     */
    @Override
    public void lifeChanged(byte life) {
        touchedSound.play();
        if(life <= 0) {
            this.isFinish = true;
            this.victory = false;
        }
        this.mireilleLife = life;
    }
    @Override
    public void scoreChanged(int score) {
        if(score >= this.totalScore) {
            // In this case, Mireille has touchedSound an true IST
            istTouchedSound.play();
            istsCatched++;
        }
        this.totalScore = score;
        if(this.totalScore >= upperLimitScore && this.istsCatched >= istsToCatch) {
            this.isFinish = true;
            this.victory = true;
        }
    }
    public int getMOVE_VALUE_X() {
        return MOVE_VALUE_X;
    }
    public int getMIN_X() {
        return MIN_X;
    }

    // Controls

    private void setUpInputProcessor() {
        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(new StandardInputAdapter(this, game, true));
        im.addProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {
            @Override
            public void onLeft() {
                Jcount = 0;
                Gdx.app.debug(TAG, "swipe left - isPaused is " + isPaused);
                if(!isPaused) {
                    final float newX = Math.max(mireille.getX() - MOVE_VALUE_X, MIN_X);
                    updateMireille(newX);
                }
            }

            @Override
            public void onRight() {
                Jcount = 0;
                Gdx.app.debug(TAG, "swipe right - isPaused is " + isPaused);
                if(!isPaused) {
                    final float xAndMoveValue = mireille.getX() + MOVE_VALUE_X;
                    final float newX = Math.min(xAndMoveValue, MAX_X);
                    Gdx.app.log(TAG, "Math.min( " + xAndMoveValue + " , " + MAX_X + " )");
                    updateMireille(newX);
                }
            }

            @Override
            public void onUp() {
                Jcount++;
                Gdx.app.debug(TAG , "swipe up - isPaused is " + isPaused);
                if (Jcount >= 3) {
                    isPaused = true;
                    jojoAppears = true;
                }
            }

            @Override
            public void onDown() {
                Jcount = 0;
                Gdx.app.debug(TAG, "swipe down - isPaused is " + isPaused);
            }

            private void updateMireille(float newX) {
                if(!isFinish) {
                    Gdx.app.log(TAG, "Mireille pos(x:" + newX + " | y:" + MIN_Y + " )");
                    mireille.setX(newX);
                    mireille.updateCollision(newX, MIN_Y);
                    jumpSound.play();
                }
            }
        }));
        im.addProcessor(stage);
        Gdx.input.setInputProcessor(im);
    }
}
