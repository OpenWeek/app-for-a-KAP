package gdx.kapotopia.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import gdx.kapotopia.GameDifficulty;
import gdx.kapotopia.Helpers.SimpleDirectionGestureDetector;
import gdx.kapotopia.Helpers.StandardInputAdapter;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.STIDex.STI;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.Screens.Game1;
import gdx.kapotopia.UnlockedLevel;

import static gdx.kapotopia.GameDifficulty.HARD;
import static gdx.kapotopia.GameDifficulty.MEDIUM;

public class GameController {

    /* *******************************************************
     *                 A T T R I B U T E S                   *
     ******************************************************* */

    // Shared variables
    private Kapotopia game;
    private Game1 game1;

    private String TAG = this.getClass().getSimpleName();

    //JOJO
    private byte Jcount;
    private boolean jojoAppears;
    private boolean jojoHasAppeared;
    private boolean jojoTimerLaunched;


    // Useful Variables
    private Random random;
    private boolean letsGoAppeared;
    private boolean isFinish;
    private boolean didGameOverScreenAppeared;
    private boolean isPaused;   // To check if the game is paused or not
    private boolean victory;
    private byte mireilleLife;
    private int totalScore;
    private int istsCatched;
    private int istsToCatch;
    private int upperLimitScore;

    // Constants
    private float MIN_X;
    private float MAX_X;    // Max value on the X axis
    private float MIN_Y;
    private float MOVE_VALUE_X;
    private final Rectangle bounds;
    private GameDifficulty difficulty;

    // Actors
    private Virus ennemi;
    private final MireilleBasic mireille;

    private List<VirusContainer> ist;  // <Nom, VIRUS_TYPE>
    private List<VirusContainer> fake; // <Nom, VIRUS_TYPE>
    private List<VirusContainer> maybeIst;
    private HashSet<VirusContainer> missedIsts;

    /* *******************************************************
     *                      M E T H O D S                   *
     ******************************************************* */

    public GameController(Kapotopia game, Game1 game1) {
        this.game = game;
        this.game1 = game1;

        setConstants(game.viewport.getWorldWidth(), game.viewport.getWorldHeight());

        this.random = new Random();
        this.letsGoAppeared = false;
        this.isFinish = false;
        this.didGameOverScreenAppeared = false;
        this.isPaused = true;
        this.victory = false;
        this.totalScore = 0;
        this.istsCatched = 0;
        this.bounds = new Rectangle(0,0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());

        // JOJO
        Jcount = 0;
        jojoAppears = false;
        jojoHasAppeared = false;
        jojoTimerLaunched = false;


        // Lists
        initVirusTextures();
        this.missedIsts = new HashSet<VirusContainer>();

        // Major actors
        this.mireille = prepareMireille();
        this.mireille.addListener(game1);

        // Difficulty configuration
        this.difficulty = game.vars.getChoosenDifficultyG1();
        if(this.difficulty == null)
            this.difficulty = GameDifficulty.MEDIUM;
    }

    private void setConstants(float worldW, float worldH) {
        float x = (worldW / 72);
        float z = 21 * x;
        float y = (worldW - x - z) / 3;
        this.MIN_X = x;
        this.MAX_X = worldW - z;
        this.MIN_Y = 25;
        this.MOVE_VALUE_X = y;
    }

    public void init() {
        this.ennemi = new Virus(this.bounds, game1);
        configureGame(this.difficulty);
    }

    // STANDARD SCREEN CALLS

    /**
     * Method called when show() method is called in the base screen
     */
    public void updateOnShow(Stage stage) {

        //In case there are problems to restart the game where it was left after going to another screen and returning, it could maybe be solved by setting the Input Processor (Gdx.input.setInputProcessor(im);) here and not when the game is first created
        // We ensure that after the animation has played, the game really start
        if(!letsGoAppeared) {
            final float delay = game1.getRenderController().getFirstAnimationTimeLeft();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    letsGoAppeared = true;
                    isPaused = false;
                    Gdx.app.debug(TAG, "LetsGoAppeared - isPaused is false");
                }
            }, delay);
        }
        setUpInputProcessor(stage);
    }

    public void updateOnPause() {
        isPaused = true;
    }

    public void updateOnResume() {
        isPaused = true;
    }

    public void resumeFromPause() {
        isPaused = false;
    }

    public void updateOnHide() {
        isPaused = true;
        Gdx.app.debug(TAG, "game hidden - isPaused is true");

    }

    public void update(float delta, Stage stage) {
        if(isFinish) {
            // GAME OVER
            if(!didGameOverScreenAppeared) {
                gameOver(stage);
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
    }

    public void makeJojoAppear(float delta) {
        if (jojoAppears && !jojoHasAppeared) {
            game1.getRenderController().jojo(delta);
            if (!jojoTimerLaunched) {
                game1.getSoundController().startJojo();
                // We upgrade the difficulty => NIGHTMARE MODE
                ennemi.setAccAddFactor(0.09f);
                ennemi.setAccMaxLim(12f);
                ennemi.setAcceleration(ennemi.getAcceleration() + 2);
                mireille.toggleJojo();
                mireille.setPosition(-500, - 500);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        jojoHasAppeared = true;
                        isPaused = false;
                        Gdx.app.debug(TAG, "Jojo has appeared - isPaused is false");
                        mireille.resetPosition();
                    }
                }, 6f);
                jojoTimerLaunched = true;
            }
        }
    }

    // GAME MECHANICS

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
        final MireilleBasic mireille = new MireilleBasic(game, MIN_X, MIN_Y);
        mireille.updateCollision(MIN_X, MIN_Y);
        mireille.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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

        game1.getRenderController().playMissedLabelAnim();
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

    private void gameOver(Stage stage) {
        game1.getSoundController().playAtGameOver();

        final EventListener continueEvent = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game1.getSoundController().stopMusics();
                game.destroyScreen(ScreenType.GAME1);
                switch (game1.getGameController().getDifficulty()) {
                    case EASY:
                        game.vars.setChoosenDifficultyG1(MEDIUM);
                        game.changeScreen(ScreenType.GAME1);
                        break;
                    case MEDIUM:
                        game.vars.setChoosenDifficultyG1(HARD);
                        game.changeScreen(ScreenType.GAME1);
                        break;
                    case HARD:
                        // We send the player to the next game, so GAME 2
                        game.changeScreen(ScreenType.MOCKUPG2);
                        break;
                }
            }
        };

        final EventListener restartEvent = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game1.getSoundController().stopMusics();
                game.vars.setChoosenDifficultyG1(game1.getGameController().getDifficulty());
                game.destroyScreen(ScreenType.GAME1);
                game.changeScreen(ScreenType.GAME1);
            }
        };

        game1.getRenderController().updateAtGameOver(stage, continueEvent, restartEvent);

        /*
         * We set up the general title Which is either equal to "Bravo" or "Game over"
         * and which on pressed, redirect the player on another screen
         */

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
            game1.getSoundController().playSuccessSound();
        }else{
            game1.getSoundController().playFailSound();
        }

    }

    /**
     * Initialize virus textures (true and fake ones) selected by reading sprite.xml
     * and by saving them into ArrayLists
     */
    private void initVirusTextures() {
        STI[] s1 = game.vars.getStiData().getIsts();
        STI[] s2 = game.vars.getStiData().getFakeists();
        STI[] s3 = game.vars.getStiData().getMaybeists();

        List<VirusContainer> ist = new ArrayList<VirusContainer>();
        List<VirusContainer> fake = new ArrayList<VirusContainer>();
        List<VirusContainer> maybe = new ArrayList<VirusContainer>();

        for (STI sti : s1) {
            ist.add(new VirusContainer(sti.getTexture(), sti.getName(), true, false, sti.getDescription()));
        }

        for (STI sti : s2) {
            fake.add(new VirusContainer(sti.getTexture(), sti.getName(), false, false, sti.getDescription()));
        }

        for (STI sti : s3) {
            maybe.add(new VirusContainer(sti.getTexture(), sti.getName(),true, true, sti.getDescription()));
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

    // Listeners
    public void lifeChanged(byte life) {
        if(life <= 0) {
            this.isFinish = true;
            this.victory = false;
        }
        this.mireilleLife = life;
    }

    public void scoreChanged(int score) {
        if(score >= this.totalScore) {
            // In this case, Mireille has touchedSound an true IST
            game1.getSoundController().playScoreChangedSuccess();
            istsCatched++;
        }
        this.totalScore = score;
        if(this.totalScore >= upperLimitScore && this.istsCatched >= istsToCatch) {
            this.isFinish = true;
            this.victory = true;
        }
    }

    // Controls

    private void setUpInputProcessor(Stage stage) {
        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(new StandardInputAdapter(game1, game, true));
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
                    game1.getSoundController().playWhenMireilleMove();
                }
            }
        }));
        im.addProcessor(stage);
        Gdx.input.setInputProcessor(im);
    }


    // GETTERS

    public Rectangle getBounds() {
        return bounds;
    }


    public boolean isPaused() {
        return isPaused;
    }

    public byte getMireilleLife() {
        return mireilleLife;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getIstsCatched() {
        return istsCatched;
    }

    public Virus getEnnemi() {
        return ennemi;
    }

    public GameDifficulty getDifficulty() {
        return difficulty;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public MireilleBasic getMireille() {
        return mireille;
    }

    public boolean isLetsGoAppeared() {
        return letsGoAppeared;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public HashSet<VirusContainer> getMissedIsts() {
        return missedIsts;
    }

    public boolean isVictory() {
        return victory;
    }

    public float getMIN_X() {
        return MIN_X;
    }

    public float getMAX_X() {
        return MAX_X;
    }

    public float getMIN_Y() {
        return MIN_Y;
    }

    public float getMOVE_VALUE_X() {
        return MOVE_VALUE_X;
    }

    public List<VirusContainer> getIst() {
        return ist;
    }

    public List<VirusContainer> getFake() {
        return fake;
    }

    public List<VirusContainer> getMaybeIst() {
        return maybeIst;
    }
}
