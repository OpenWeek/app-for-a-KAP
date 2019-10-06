package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import gdx.kapotopia.AssetsManager;
import gdx.kapotopia.Game1.CollisionManager;
import gdx.kapotopia.Game1.MireilleBasic;
import gdx.kapotopia.Game1.MireilleListener;
import gdx.kapotopia.Game1.VIRUS_TYPE;
import gdx.kapotopia.Game1.Virus;
import gdx.kapotopia.Game1.VirusContainer;
import gdx.kapotopia.GameDifficulty;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.SimpleDirectionGestureDetector;
import gdx.kapotopia.StandardInputAdapter;
import gdx.kapotopia.Utils;

public class Game1 implements Screen, MireilleListener {

    // Variables générales
    private Kapotopia game;
    private final Image imgFond;
    private Stage stage;
    private Random random;
    // Style de texte
    private TextButton.TextButtonStyle style;
    private TextButton.TextButtonStyle styleSmall;
    // Sons et musique
    private Sound touchedSound;
    private Sound failSound;
    private Sound successSound;
    private Sound jumpSound;
    private Sound pauseSound;
    private Sound istTouchedSound;
    private Music music;
    // Variables utiles
    private boolean isFinish;
    private boolean didGameOverScreenAppeared;
    private boolean isPaused; // To check if the game is paused or not
    private boolean victory;
    private byte mireilleLife;
    private int totalScore;
    private int istsCatched;
    private int istsToCatch;
    private int upperLimitScore;
    // Labels
    private Label lifeLabel;
    private Label scoreLabel;
    private Label ennemiName;
    private Label pauseLabel;
    private Label missedLabel;
    private ImageButton pauseIcon;

    // Constantes
    private final static String LIFE_TXT = "Vies: ";
    private final static String SCORE_TXT = "Score: ";
    private static final String TAG = "game1";
    private final static int MIN_X = 15;
    private final int maxX;
    private final static int MIN_Y = 25;
    private final int MOVE_VALUE_X;
    private final Rectangle bounds;
    private final static String[] SOUNDSPATHS = {
            "sound/bruitage/thefsoundman_punch-02.wav",
            "sound/bruitage/jivatma07_j1game-over-mono.wav",
            "sound/bruitage/lloydevans09_jump1.wav",
            "sound/bruitage/crisstanza_pause.mp3",
            "sound/bruitage/leszek-szary_coin-object.wav",
            "sound/bruitage/leszek-szary_success-1.wav"
    };
    private final static String MUSICPATH = "sound/Musique_fast_chiptune.ogg";

    // Actors
    private final Virus ennemi;
    private final MireilleBasic mireille;

    private List<VirusContainer> ist;  // <Nom, VIRUS_TYPE>
    private List<VirusContainer> fake; // <Nom, VIRUS_TYPE>

    private HashSet<String> missedIsts;

    /**
     * Constructeur
     * @param game
     */
    public Game1(final Kapotopia game) {
        // On initialize les attributs
        this.game = game;
        this.stage = new Stage(game.viewport);
        this.random = new Random();

        this.style = Utils.getStyleFont("SEASRN__.ttf", 60, Color.WHITE);
        this.styleSmall = Utils.getStyleFont("SEASRN__.ttf", 38, Color.WHITE);

        this.bounds = new Rectangle(0,0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        this.maxX = floorOfAMultipleOf250( ( ((int) game.viewport.getWorldWidth()) / 2) + 250);
        this.MOVE_VALUE_X = 250;

        this.isFinish = false;
        this.didGameOverScreenAppeared = false;
        this.isPaused = false;
        this.victory = false;
        this.totalScore = 0;
        this.istsCatched = 0;

        initVirusTextures();
        this.missedIsts = new HashSet<String>();

        //// Setting up the stage
        this.imgFond = new Image(AssetsManager.getInstance().getTextureByPath("World1/Game1/JungleEtFeuilles.png"));
        stage.addActor(imgFond);

        // Labels and buttons
        lifeLabel = new Label(LIFE_TXT + mireilleLife,new Label.LabelStyle(style.font, style.fontColor));
        lifeLabel.setPosition(bounds.width - 240, bounds.height - 100);

        stage.addActor(lifeLabel);
        pauseIcon = new ImageButton(new TextureRegionDrawable(new TextureRegion(
                AssetsManager.getInstance().getTextureByPath("pause_logo_2.png"))));
        pauseIcon.setBounds(bounds.width - 240, bounds.height - 170, 140, 80);
        pauseIcon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(isPaused) {
                    resumeFromPause();
                }else{
                    pause();
                }
            }
        });
        stage.addActor(pauseIcon);
        scoreLabel = new Label(SCORE_TXT  + totalScore, new Label.LabelStyle(style.font, style.fontColor));
        scoreLabel.setPosition(25, bounds.height - 100);
        stage.addActor(scoreLabel);
        pauseLabel = new Label("Pause", new Label.LabelStyle(style.font, style.fontColor));
        pauseLabel.setPosition((bounds.width / 5) * 2, bounds.height / 2);
        pauseLabel.setVisible(false);
        stage.addActor(pauseLabel);
        missedLabel = new Label("Loupé", new Label.LabelStyle(styleSmall.font, styleSmall.fontColor));
        missedLabel.setVisible(false);
        stage.addActor(missedLabel);

        // Music and Sounds
        this.music = prepareMusic();
        this.touchedSound = AssetsManager.getInstance().getSoundByPath(SOUNDSPATHS[0]);
        this.failSound = AssetsManager.getInstance().getSoundByPath(SOUNDSPATHS[1]);
        this.jumpSound = AssetsManager.getInstance().getSoundByPath(SOUNDSPATHS[2]);
        this.pauseSound = AssetsManager.getInstance().getSoundByPath(SOUNDSPATHS[3]);
        this.istTouchedSound = AssetsManager.getInstance().getSoundByPath(SOUNDSPATHS[4]);
        this.successSound = AssetsManager.getInstance().getSoundByPath(SOUNDSPATHS[5]);

        // Major actors
        this.mireille = prepareMireille();
        this.mireille.addListener(this);
        this.ennemi = new Virus(this.bounds, this);

        this.ennemiName = new Label(ennemi.getName(), new Label.LabelStyle(styleSmall.font, styleSmall.fontColor));
        this.ennemiName.setPosition(ennemi.getX(),ennemi.getY() - 15);
        stage.addActor(ennemiName);

        stage.addActor(mireille);
        stage.addActor(ennemi);

        GameDifficulty difficulty = (GameDifficulty) game.getTheValueGateway().removeFromTheStore("difficulty");
        if(difficulty == null)
            difficulty = GameDifficulty.MEDIUM;
        configureGame(difficulty);
        AssetsManager.getInstance().addStage(stage, TAG);
    }

    private void configureGame(GameDifficulty dif) {
        switch (dif) {
            case EASY:
                mireille.setLifes((byte) 3);
                this.mireilleLife = mireille.getLifes();
                istsToCatch = 5;
                upperLimitScore = -1;
                break;
            case MEDIUM:
                mireille.setLifes((byte) 3);
                this.mireilleLife = mireille.getLifes();
                istsToCatch = 15;
                upperLimitScore = 200;
                break;
            case HARD:
                mireille.setLifes((byte) 1);
                this.mireilleLife = mireille.getLifes();
                istsToCatch = 20;
                upperLimitScore = 300;
                break;
            case INFINITE:
                mireille.setLifes((byte) 3);
                this.mireilleLife = mireille.getLifes();
                istsToCatch = Integer.MAX_VALUE;
                upperLimitScore = Integer.MAX_VALUE;
                break;
        }
    }

    @Override
    public void show() {
        music.play();
        setUpInputProcessor();
        //In case there are problems to restart the game where it was left after going to another screen and returning, it could maybe be solved by setting the Input Processor (Gdx.input.setInputProcessor(im);) here and not when the game is first created
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        lifeLabel.setText(LIFE_TXT + mireilleLife);
        scoreLabel.setText(SCORE_TXT + totalScore);
        if(isFinish) {
            // GAME OVER
            if(!didGameOverScreenAppeared) {
                this.music.setVolume(0.1f);
                String titleText;
                if(victory) {
                    this.successSound.play();
                    titleText = "Bravo !";
                }else{
                    this.failSound.play(0.7f);
                    titleText = "GAME OVER";
                }
                for(Actor actor : stage.getActors()) {
                    if(!(actor.equals(this.imgFond) || actor.equals(lifeLabel))) {
                        actor.setVisible(false);
                    }
                }
                final Button title = new TextButton(titleText,style);
                title.setPosition((bounds.getWidth() / 2) - 175, bounds.getHeight() / 2);
                title.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        title.setVisible(false);
                        if(missedIsts.isEmpty()) {
                            game.destroyScreen(ScreenType.GAME1);
                            game.destroyScreen(ScreenType.MAINMENU);
                            game.changeScreen(ScreenType.MAINMENU);
                        }else{
                            game.getTheValueGateway().addToTheStore("G1-missedIST", missedIsts);
                            game.changeScreen(ScreenType.BILANG1);
                        }
                    }
                });
                stage.addActor(title);
                Label endScoreLabel = new Label(SCORE_TXT + totalScore, new Label.LabelStyle(style.font, style.fontColor));
                endScoreLabel.setPosition((bounds.width / 2) - 150, (bounds.height / 2) - 60);
                stage.addActor(endScoreLabel);
                didGameOverScreenAppeared = true;
            }
        }else{
            if(!isPaused) {
                stage.act(delta);

                if(CollisionManager.getInstance().checkCollision(mireille,ennemi)) {
                    ennemi.setPosition(50 + 275 * random.nextInt(3), bounds.getHeight());
                }
            }
        }
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        music.pause();
        pauseSound.play();
        pauseLabel.setVisible(true);
        isPaused = true;
    }

    @Override
    public void resume() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                resumeFromPause();
            }
        }, 3f);
    }

    private void resumeFromPause() {
        pauseLabel.setVisible(false);
        music.play();
        isPaused = false;
    }

    @Override
    public void hide() {
        music.pause();
    }

    @Override
    public void dispose() {
        AssetsManager.getInstance().disposeStage(TAG);
        AssetsManager.getInstance().disposeMusic(MUSICPATH);
        for (VirusContainer v : ist) {
            AssetsManager.getInstance().disposeTexture(v.getTexturePath());
        }
        for (VirusContainer v : fake) {
            AssetsManager.getInstance().disposeTexture(v.getTexturePath());
        }
        AssetsManager.getInstance().disposeSound(SOUNDSPATHS);
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

    private Music prepareMusic() {
        Music music = AssetsManager.getInstance().getMusicByPath(MUSICPATH);
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

    private void setUpInputProcessor() {
        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(new StandardInputAdapter(this, game, true));
        im.addProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {
            @Override
            public void onLeft() {
                if(!isPaused) {
                    final float newX = Math.max(mireille.getX() - MOVE_VALUE_X, MIN_X);
                    updateMireille(newX);
                }
            }

            @Override
            public void onRight() {
                if(!isPaused) {
                    final float xAndMoveValue = mireille.getX() + MOVE_VALUE_X;
                    final float newX = Math.min(xAndMoveValue, maxX);
                    Gdx.app.log(TAG, "Math.min( " + xAndMoveValue + " , " + maxX + " )");
                    updateMireille(newX);
                }
            }

            @Override
            public void onUp() {

            }

            @Override
            public void onDown() {

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

    @org.jetbrains.annotations.Contract(pure = true)
    private int floorOfAMultipleOf250(int nbr) {
        for (int i=2000; i > 0; i = i - 250) {
            if(nbr > i) return i;
        }
        return 0;
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

    public void setNewEnnemiLabelPosition(float x, float y){
        this.ennemiName.setPosition(x, y);
    }

    public void changeEnnemiLabel(String newName) {
        this.ennemiName.setText(newName);
    }

    /**
     * Initialize virus textures (true and fake ones) selected by reading sprite.xml
     * and by saving them into ArrayLists
     */
    private void initVirusTextures() {
        XmlReader xml = new XmlReader();
        Element root = xml.parse(Gdx.files.internal("sprite.xml"));
        Element ist_xml = root.getChildByName("ist-l");
        Element fake_xml = root.getChildByName("fakeist-l");

        List<VirusContainer> ist = new ArrayList<VirusContainer>();
        List<VirusContainer> fake = new ArrayList<VirusContainer>();

        for (Element el : ist_xml.getChildrenByName("ist")) {
            ist.add(new VirusContainer(el.get("texture"),el.get("name"), true));
        }

        for (Element el : fake_xml.getChildrenByName("fakeist")) {
            fake.add(new VirusContainer(el.get("texture"),el.get("name"), false));
        }

        this.ist = ist;
        this.fake = fake;
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
        }
        return null;
    }

    public void addMissedIST(String virusName) {
        missedLabel.setVisible(true);
        final float x = mireille.getX(), y = mireille.getY() + mireille.getHeight();
        missedLabel.setPosition(x,y);
        missedLabel.addAction(Actions.moveTo(x,y + 50f, 1f));
        missedIsts.add(virusName);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                missedLabel.setVisible(false);
            }
        },1f);
    }

    public HashSet<String> getMissedIST() {
        return missedIsts;
    }
}
