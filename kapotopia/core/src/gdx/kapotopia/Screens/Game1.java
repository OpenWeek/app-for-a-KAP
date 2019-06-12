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
import java.util.List;
import java.util.Random;

import gdx.kapotopia.AssetsManager;
import gdx.kapotopia.Game1.CollisionManager;
import gdx.kapotopia.Game1.LifeListener;
import gdx.kapotopia.Game1.MireilleBasic;
import gdx.kapotopia.Game1.VIRUS_TYPE;
import gdx.kapotopia.Game1.Virus;
import gdx.kapotopia.Game1.VirusContainer;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Utils;

public class Game1 implements Screen, LifeListener {

    // Variables générales
    private Kapotopia game;
    private final Image imgFond;
    private Stage stage;
    private Random random;

    private TextButton.TextButtonStyle style;

    private Sound touched;
    private Sound fail;
    private Sound jump;
    private Sound pauseSound;
    private Music music;

    private boolean isFinish;
    private boolean didGameOverScreenAppeared;
    private byte mireilleLife;
    private Label lifeLabel;

    // Constantes
    private final static int MIN_X = 0;
    private final int maxX;
    private final static int MIN_Y = 25;
    private final static int MOVE_VALUE_X = 250;
    private final Rectangle bounds;

    private List<VirusContainer> ist;  // <Nom, VIRUS_TYPE>
    private List<VirusContainer> fake; // <Nom, VIRUS_TYPE>

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

        this.bounds = new Rectangle(0,0,game.viewport.getScreenWidth(),game.viewport.getScreenHeight());
        this.maxX = floorOfAMultipleOf250((game.viewport.getScreenWidth() / 2) + 250);

        this.isFinish = false;
        this.didGameOverScreenAppeared = false;

        initVirusTextures();

        // Mise en place du décor
        this.imgFond = new Image(AssetsManager.getInstance().getTextureByPath("FondNiveauBlanc2.png"));
        stage.addActor(imgFond);

        lifeLabel = new Label("Vies " + mireilleLife,new Label.LabelStyle(style.font, Color.BLACK));
        lifeLabel.setPosition(bounds.width - 230, bounds.height - 100);
        stage.addActor(lifeLabel);

        // Musique
        this.music = prepareMusic();
        this.touched = AssetsManager.getInstance().getSoundByPath("sound/bruitage/thefsoundman__punch-02.wav");
        this.fail = AssetsManager.getInstance().getSoundByPath("sound/bruitage/jivatma07__j1game-over-mono.wav");
        this.jump = AssetsManager.getInstance().getSoundByPath("sound/bruitage/lloydevans09__jump1.wav");
        this.pauseSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/crisstanza__pause.mp3");

        // Les acteurs principaux
        this.mireille = prepareMireille();
        this.mireille.addListener(this);
        this.mireilleLife = mireille.getLifes();
        this.ennemi = new Virus(this.bounds, this);

        stage.addActor(mireille);
        stage.addActor(ennemi);

        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    dispose();
                    game.setScreen(new MainMenu(game));
                    return true;
                }
                return false;
            }
        });
        im.addProcessor(stage);

        Gdx.input.setInputProcessor(im);
    }
    @Override
    public void show() {
        music.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        lifeLabel.setText("Vies " + mireilleLife);
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
                Button title = new TextButton("GAMEOVER",style);
                title.setPosition((bounds.width / 2) - 175, bounds.height / 2);
                title.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        dispose();
                        //TODO optimiser le dispose
                        AssetsManager.getInstance().disposeAllResources();
                        game.setScreen(new MainMenu(game));
                    }
                });
                stage.addActor(title);
                didGameOverScreenAppeared = true;
            }
        }else{
            stage.act(delta);

            CollisionManager.getInstance().checkCollision(mireille,ennemi);
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
        stage.dispose();
        music.dispose();
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
                        System.out.println("mireille : (" + mireille.getX() + "),(" + MOVE_VALUE_X + "),(" + maxX + ")");
                    }else{
                        newX = Math.max(mireille.getX() - MOVE_VALUE_X, MIN_X);
                    }
                    mireille.setX(newX);
                    mireille.updateCollision(newX, MIN_Y);
                    System.out.println("swipe!! " + velocityX + ", " + velocityY);
                    jump.play();
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
        Music music = Gdx.audio.newMusic(Gdx.files.internal("sound/Musique_fast_chiptune.ogg"));
        music.setLooping(false);
        music.setVolume(0.5f);
        music.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                music.setPosition(11.5f);
            }
        });
        return music;
    }

    private int floorOfAMultipleOf250(int nbr) {
        for (int i=2000; i > 0; i = i - 250) {
            if(nbr > i) return i;
        }
        return 0;
    }

    /**
     * Method used by lifeListener
     * @param life
     */
    @Override
    public void lifeChanged(byte life) {
        touched.play();
        if(life <= 0) {
            this.isFinish = true;
        }
        this.mireilleLife = life;
    }

    /**
     * Initalize les textures de virus (vrais et faux) en les enregistrant dans des listes
     */
    private void initVirusTextures() {
        XmlReader xml = new XmlReader();
        Element root = xml.parse(Gdx.files.internal("sprite.xml"));
        Element ist_xml = root.getChildByName("ist-l");
        Element fake_xml = root.getChildByName("fakeist-l");

        List<VirusContainer> ist = new ArrayList<VirusContainer>();
        List<VirusContainer> fake = new ArrayList<VirusContainer>();

        for (Element el : ist_xml.getChildrenByName("ist")) {
            ist.add(new VirusContainer(el.get("texture"),el.get("name")));
        }

        for (Element el : fake_xml.getChildrenByName("fakeist")) {
            fake.add(new VirusContainer(el.get("texture"),el.get("name")));
        }

        this.ist = ist;
        this.fake = fake;
    }

    public Texture getRdmVirusTexture(VIRUS_TYPE type) {
        final int r;
        switch (type) {
            case IST:
                r = Math.abs(random.nextInt() % ist.size());
                return AssetsManager.getInstance().getTextureByPath(ist.get(r).getTexturePath());
            case FAKEIST:
                r = Math.abs(random.nextInt() % fake.size());
                return AssetsManager.getInstance().getTextureByPath(fake.get(r).getTexturePath());
            case BOSS:
                r = Math.abs(random.nextInt() % ist.size());
                return AssetsManager.getInstance().getTextureByPath(ist.get(r).getTexturePath());
        }
        return null;
    }
}
