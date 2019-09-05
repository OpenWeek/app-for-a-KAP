package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;
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
    private Sound touched;
    private Sound fail;
    private Sound jump;
    private Sound pauseSound;
    private Music music;
    // Variables utiles
    private boolean isFinish;
    private boolean didGameOverScreenAppeared;
    private byte mireilleLife;
    private int totalScore;
    // Labels
    private Label lifeLabel;
    private Label scoreLabel;
    private Label ennemiName;

    private final static String LIFE_TXT = "Vies: ";
    private final static String SCORE_TXT = "Score: ";

    // Constantes
    private static final String TAG = "game1";
    private final static int MIN_X = 0;
    private final int maxX;
    private final static int MIN_Y = 25;
    private final static int MOVE_VALUE_X = 250;
    private final Rectangle bounds;
    private final int PERFECTENNEMYLABELLENGTH = 10;

    private final static String[] SOUNDSPATHS = {
            "sound/bruitage/thefsoundman_punch-02.wav",
            "sound/bruitage/jivatma07_j1game-over-mono.wav",
            "sound/bruitage/lloydevans09_jump1.wav",
            "sound/bruitage/crisstanza_pause.mp3"
    };
    private final static String MUSICPATH = "sound/Musique_fast_chiptune.ogg";

    private List<VirusContainer> ist;  // <Nom, VIRUS_TYPE>
    private List<VirusContainer> fake; // <Nom, VIRUS_TYPE>
    private List<VirusContainer> boss; // <Nom, VIRUS_TYPE>

    private HashSet<String> missedIsts;

    // Actors
    private final Virus ennemi;
    private final MireilleBasic mireille;

    /**
     * Constructeur
     * @param game
     */
    public Game1(final Kapotopia game) {
        // On initialize les attributs
        this.game = game;
        this.stage = new Stage(game.viewport);
        this.random = new Random();

        this.style = Utils.getStyleFont("SEASRN__.ttf");
        this.styleSmall = Utils.getStyleFont("SEASRN__.ttf", 38);

        this.bounds = new Rectangle(0,0,game.viewport.getScreenWidth(),game.viewport.getScreenHeight());
        this.maxX = floorOfAMultipleOf250((game.viewport.getScreenWidth() / 2) + 250);

        this.isFinish = false;
        this.didGameOverScreenAppeared = false;
        this.totalScore = 0;

        initVirusTextures();
        this.missedIsts = new HashSet<String>();

        // Mise en place du décor
        this.imgFond = new Image(AssetsManager.getInstance().getTextureByPath("FondNiveauBlanc2.png"));
        stage.addActor(imgFond);

        lifeLabel = new Label(LIFE_TXT + mireilleLife,new Label.LabelStyle(style.font, Color.BLACK));
        lifeLabel.setPosition(bounds.width - 240, bounds.height - 100);
        stage.addActor(lifeLabel);
        scoreLabel = new Label(SCORE_TXT  + totalScore, new Label.LabelStyle(style.font, Color.BLACK));
        scoreLabel.setPosition(25, bounds.height - 100);
        stage.addActor(scoreLabel);

        // Musique
        this.music = prepareMusic();
        this.touched = AssetsManager.getInstance().getSoundByPath(SOUNDSPATHS[0]);
        this.fail = AssetsManager.getInstance().getSoundByPath(SOUNDSPATHS[1]);
        this.jump = AssetsManager.getInstance().getSoundByPath(SOUNDSPATHS[2]);
        this.pauseSound = AssetsManager.getInstance().getSoundByPath(SOUNDSPATHS[3]);

        // Les acteurs principaux
        this.mireille = prepareMireille();
        this.mireille.addListener(this);
        this.mireilleLife = mireille.getLifes();
        this.ennemi = new Virus(this.bounds, this);

        this.ennemiName = new Label(ennemi.getName(), new Label.LabelStyle(styleSmall.font, Color.BLACK));
        this.ennemiName.setPosition(ennemi.getX(),ennemi.getY());
        stage.addActor(ennemiName);

        stage.addActor(mireille);
        stage.addActor(ennemi);

        AssetsManager.getInstance().addStage(stage, TAG);
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
                this.fail.play(0.7f);
                for(Actor actor : stage.getActors()) {
                    if(!(actor.equals(this.imgFond) || actor.equals(lifeLabel))) {
                        actor.setVisible(false);
                    }
                }
                final Button title = new TextButton("GAMEOVER",style);
                title.setPosition((bounds.getWidth() / 2) - 175, bounds.getHeight() / 2);
                title.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        title.setVisible(false);
                        if(missedIsts.isEmpty()) {
                            game.destroyScreen(ScreenType.MAINMENU);
                            game.changeScreen(ScreenType.MAINMENU);
                        }else{
                            game.getTheValueGateway().addToTheStore("G1-missedIST", missedIsts);
                            game.changeScreen(ScreenType.BILANG1);
                        }
                    }
                });
                stage.addActor(title);
                Label endScoreLabel = new Label(SCORE_TXT + totalScore, new Label.LabelStyle(style.font, Color.BLACK));
                endScoreLabel.setPosition((bounds.width / 2) - 150, (bounds.height / 2) - 60);
                stage.addActor(endScoreLabel);
                didGameOverScreenAppeared = true;
            }
        }else{
            stage.act(delta);

            if(CollisionManager.getInstance().checkCollision(mireille,ennemi)) {
                ennemi.setPosition(50 + 275 * random.nextInt(3), bounds.getHeight());
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
    }

    @Override
    public void resume() {
        music.play();
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

        stage.addListener(new ActorGestureListener() {
            public void fling (InputEvent event, float velocityX, float velocityY, int button) {
                if(!isFinish) {
                    final float newX;
                    if (velocityX > 0) {
                        newX = Math.min(mireille.getX() + MOVE_VALUE_X, maxX);
                        Gdx.app.log(TAG, "mireille : (" + mireille.getX() + "),(" + MOVE_VALUE_X + "),(" + maxX + ")");
                    }else{
                        newX = Math.max(mireille.getX() - MOVE_VALUE_X, MIN_X);
                    }
                    mireille.setX(newX);
                    mireille.updateCollision(newX, MIN_Y);
                    jump.play();
                    Gdx.app.log(TAG, "swipe!! " + velocityX + ", " + velocityY);
                }
            }
        });
        mireille.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("change");
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
        touched.play();
        if(life <= 0) {
            this.isFinish = true;
        }
        this.mireilleLife = life;
    }

    @Override
    public void scoreChanged(int score) {
        this.totalScore = score;
    }

    public void setNewEnnemiLabelPosition(float x, float y){
        this.ennemiName.setPosition(x, y);
    }

    public void changeEnnemiLabel(String newName) {
        String name = addBlankSpace(newName);
        this.ennemiName.setText(name);
    }

    /**
     * prepare ennemi label by adding blank space if needed
     * @param str
     * @return
     */
    private String addBlankSpace(String str) {
        if(str.length() < PERFECTENNEMYLABELLENGTH) {
            StringBuilder strBldr = new StringBuilder();
            for (int i = PERFECTENNEMYLABELLENGTH - str.length(); i < str.length(); i++ ) {
                strBldr.append(" ");
            }
            strBldr.append(str);
            return strBldr.toString();
        }
        return str;
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
        Element boss_xml = root.getChildByName("boss-l");

        List<VirusContainer> ist = new ArrayList<VirusContainer>();
        List<VirusContainer> fake = new ArrayList<VirusContainer>();
        List<VirusContainer> boss = new ArrayList<VirusContainer>();

        for (Element el : ist_xml.getChildrenByName("ist")) {
            ist.add(new VirusContainer(el.get("texture"),el.get("name"), true));
        }

        for (Element el : fake_xml.getChildrenByName("fakeist")) {
            fake.add(new VirusContainer(el.get("texture"),el.get("name"), false));
        }

        for (Element el : boss_xml.getChildrenByName("boss")) {
            boss.add(new VirusContainer(el.get("texture"),el.get("name"), false));
        }

        this.ist = ist;
        this.fake = fake;
        this.boss = boss;
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
            case BOSS:
                r = Math.abs(random.nextInt() % boss.size());
                return boss.get(r);
        }
        return null;
    }

    public void addMissedIST(String virusName) {
        missedIsts.add(virusName);
    }

    public HashSet<String> getMissedIST() {
        return missedIsts;
    }
}
