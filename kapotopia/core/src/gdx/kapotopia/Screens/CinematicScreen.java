package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;

import gdx.kapotopia.AssetsManager;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.StandardInputAdapter;
import gdx.kapotopia.Utils;

/**
 * This class define a common base for screens where only cinematics, shown with static pictures, are shown
 * The class has a list of Image to show in a specific order, only two buttons are used "next" and "finish".
 * "Next" is used to change of picture, "Finish" end the cinematic and leads to another screen.
 * Sounds can be played when Images are changed
 */
public abstract class CinematicScreen implements Screen {
    /* VARIABLES */
    protected Kapotopia game;
    protected Stage stage;
    private String screenName;
    // Graphics
    private Image[] cinematicList;
    private Image fond;
    private int curImg;
    // Sounds
    private Sound changeOfImageSound;
    private Sound endSound;
    private Sound pauseSound;
    // Interaction
    private Button next;
    private Button finish;

    /* FUNCTIONS */

    // Constructors

    /**
     * Build helper function. Create A cinematicScreen with the given arguments
     * @param game the Kapotopia game
     * @param stage a stage that has been instancied beforehand
     * @param screenName the name of the screen, e.g. "mockupG1"
     * @param nextScreen of enum type ScreenType, is the screen that will be shown after the user touched the finish button
     * @param imagesTexturePaths the paths of the images shown, shown by increasing order
     * @param fondPath the path of the background shown when the finish button appear
     * @param changeOfImageSoundPath the path of the sound file that plays when screen is changed
     * @param endSoundPath the path of the sound file that plays before the screen is changed to @nextScreen
     * @param pauseSoundPath the path of the sound file that plays when the game is paused
     * @param nextBtnLabel the text displayed by the "next" button
     * @param finishBtnLabel the text displayed by the "finish" button
     * @param stylePath the path to the style used for texts
     * @param timerScheduleTime the time between the player pressed the "finish" button and when it change screen
     * @param vibrationTime the amount of time that the phone vibrate when pressing "next" and "finish" buttons (the time for "next" button pressed is fourth time less than the time given)
     */
    private void builder(final Kapotopia game, Stage stage, String screenName, final ScreenType nextScreen,
                         String[] imagesTexturePaths, String fondPath, String changeOfImageSoundPath,
                         String endSoundPath, String pauseSoundPath, String nextBtnLabel,
                         String finishBtnLabel, String stylePath, final float timerScheduleTime, final int vibrationTime) {
        this.game = game;
        this.stage = stage;
        // Graphics
        if(imagesTexturePaths == null) {
            this.cinematicList = null;
        }else{
            this.cinematicList = new Image[imagesTexturePaths.length];
            int i = 0;
            for (String path : imagesTexturePaths) {
                final Image img = new Image(AssetsManager.getInstance().getTextureByPath(path));
                //img.setVisible(false);
                img.setWidth(game.viewport.getWorldWidth());
                img.setHeight(game.viewport.getWorldHeight());
                img.setVisible(false);
                this.stage.addActor(img);
                this.cinematicList[i] = img;
                i++;
            }
            this.cinematicList[0].setVisible(true);
        }

        this.curImg = 0;
        this.fond = new Image(AssetsManager.getInstance().getTextureByPath(fondPath));
        this.fond.setVisible(false);
        this.stage.addActor(this.fond);
        // Sounds
        this.changeOfImageSound = AssetsManager.getInstance().getSoundByPath(changeOfImageSoundPath);
        this.endSound = AssetsManager.getInstance().getSoundByPath(endSoundPath);
        this.pauseSound = AssetsManager.getInstance().getSoundByPath(pauseSoundPath);
        // Buttons
        TextButton.TextButtonStyle style = Utils.getStyleFont(stylePath);

        this.next = new TextButton(nextBtnLabel, style);
        this.finish = new TextButton(finishBtnLabel, style);

        final float xButton = game.viewport.getWorldWidth() / 2.5f;
        final float yNext = game.viewport.getWorldHeight() / 10f;
        final float yFinish = game.viewport.getWorldHeight() / 2f;
        this.next.setPosition(xButton, yNext);
        this.finish.setPosition(xButton, yFinish);
        this.next.setVisible(true);
        this.finish.setVisible(false);

        this.next.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.vibrate(vibrationTime / 4);
                if(!nextImage()) {
                    // In the case when the image queue is empty (is == null or we saw every image)
                    next.setVisible(false);
                    finish.setVisible(true);
                }
            }
        });

        this.finish.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.vibrate(vibrationTime);
                endSound.play();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        resetScreen();
                        game.changeScreen(nextScreen);
                    }
                }, timerScheduleTime);
            }
        });

        this.stage.addActor(this.next);
        this.stage.addActor(this.finish);

        this.screenName = screenName;
        AssetsManager.getInstance().addStage(stage, screenName);
    }

    /**
     * @param game the Kapotopia game
     * @param stage a stage that has been instancied beforehand
     * @param screenName the name of the screen, e.g. "mockupG1"
     * @param nextScreen of enum type ScreenType, is the screen that will be shown after the user touched the finish button
     * @param imagesTexturePaths the paths of the images shown, shown by increasing order
     * @param fondPath the path of the background shown when the finish button appear
     * @param changeOfImageSoundPath the path of the sound file that plays when screen is changed
     * @param endSoundPath the path of the sound file that plays before the screen is changed to @nextScreen
     * @param pauseSoundPath the path of the sound file that plays when the game is paused
     * @param nextBtnLabel the text displayed by the "next" button
     * @param finishBtnLabel the text displayed by the "finish" button
     */
    public CinematicScreen(final Kapotopia game, Stage stage, String screenName, final ScreenType nextScreen,
                           String[] imagesTexturePaths, String fondPath, String changeOfImageSoundPath,
                           String endSoundPath, String pauseSoundPath, String nextBtnLabel,
                           String finishBtnLabel) {
        builder(game, stage, screenName, nextScreen, imagesTexturePaths, fondPath, changeOfImageSoundPath,
                endSoundPath, pauseSoundPath, nextBtnLabel, finishBtnLabel, "SEASRN__.ttf",
                2f, 200);
    }

    /**
     * @param game the Kapotopia game
     * @param stage a stage that has been instancied beforehand
     * @param screenName the name of the screen, e.g. "mockupG1"
     * @param nextScreen of enum type ScreenType, is the screen that will be shown after the user touched the finish button
     * @param imagesTexturePaths the paths of the images shown, shown by increasing order
     * @param nextBtnLabel the text displayed by the "next" button
     * @param finishBtnLabel the text displayed by the "finish" button
     */
    public CinematicScreen(final Kapotopia game, Stage stage, String screenName, final ScreenType nextScreen,
                           String[] imagesTexturePaths, String nextBtnLabel, String finishBtnLabel) {
        builder(game, stage, screenName, nextScreen, imagesTexturePaths, "FondNiveauBlanc2.png",
                "sound/bruitage/cmdrobot_videogame-jump.ogg",
                "sound/bruitage/plasterbrain_game-start.ogg",
                "sound/bruitage/crisstanza_pause.mp3", nextBtnLabel, finishBtnLabel,
                "SEASRN__.ttf",2f, 200);
    }

    /**
     * @param game the Kapotopia game
     * @param stage a stage that has been instancied beforehand
     * @param screenName the name of the screen, e.g. "mockupG1"
     * @param nextScreen of enum type ScreenType, is the screen that will be shown after the user touched the finish button
     * @param imagesTexturePaths the paths of the images shown, shown by increasing order
     */
    public CinematicScreen(final Kapotopia game, Stage stage, String screenName, final ScreenType nextScreen,
                           String[] imagesTexturePaths) {
        builder(game, stage, screenName, nextScreen, imagesTexturePaths, "FondNiveauBlanc2.png",
                "sound/bruitage/cmdrobot_videogame-jump.ogg",
                "sound/bruitage/plasterbrain_game-start.ogg",
                "sound/bruitage/crisstanza_pause.mp3", "Next",
                "Play", "SEASRN__.ttf",2f, 200);
    }

    /**
     * @param game the Kapotopia game
     * @param stage a stage that has been instancied beforehand
     * @param screenName the name of the screen, e.g. "mockupG1"
     * @param nextScreen of enum type ScreenType, is the screen that will be shown after the user touched the finish button
     */
    public CinematicScreen(final Kapotopia game, Stage stage, String screenName, final ScreenType nextScreen) {
        builder(game, stage, screenName, nextScreen, null, "FondNiveauBlanc2.png",
                "sound/bruitage/cmdrobot_videogame-jump.ogg",
                "sound/bruitage/plasterbrain_game-start.ogg",
                "sound/bruitage/crisstanza_pause.mp3", "Next",
                "Play", "SEASRN__.ttf",2f, 200);
    }

    // Regular functions

    /**
     * Show the next image in the queue. If the queue is null or is empty, return false
     * @return false if the queue is null, or if is empty. True otherwise
     */
    public boolean nextImage() {
        if(cinematicList != null) {
            cinematicList[curImg].setVisible(false);
            if (curImg < cinematicList.length-1) {
                final Image img = cinematicList[++curImg];
                img.setVisible(true);
                changeOfImageSound.play();
                return true;
            } else {
                cinematicList[curImg-1].setVisible(false);
                fond.setVisible(true);
            }
        }
        return false;
    }

    /**
     * Reset the screen at it's initial state
     */
    public void resetScreen() {
        curImg = 0;

        for (Image pic : cinematicList)
            pic.setVisible(false);

        if (cinematicList != null) {
            cinematicList[curImg].setVisible(true);
            finish.setVisible(false);
            next.setVisible(true);
            fond.setVisible(false);
        } else {
            finish.setVisible(true);
            next.setVisible(false);
            fond.setVisible(true);
        }
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



    @Override
    public void pause() {
        this.pauseSound.play();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        resetScreen();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        AssetsManager.getInstance().disposeStage(screenName);
    }

}
