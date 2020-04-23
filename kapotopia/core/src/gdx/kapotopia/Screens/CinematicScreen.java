package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;

import java.util.Iterator;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.DialogsScreen.DialogueElement;
import gdx.kapotopia.DialogsScreen.FixedDialogueSequence;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.Fonts.UseFont;
import gdx.kapotopia.Helpers.Builders.FixedDialogSeqBuilder;
import gdx.kapotopia.Helpers.Builders.TextButtonBuilder;
import gdx.kapotopia.Helpers.StandardInputAdapter;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;

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
    private boolean initialized; // Indicate if the applyBundle function has been called or not
    private final String TAG = this.getClass().getSimpleName();
    // Graphics
    private FixedDialogueSequence sequence;
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
     * There are three ways to show images, using the @param images, the @param imagesBigList or the @param imagesTexturePaths
     * Only one of these parameters will be used if multiple parameters are provided, the priority
     * is given by this : imagesBigList > images > imagesTexturePaths
     * Which means that a double array of Image is prefered over an array of Image and over an array of Images Paths (as String)
     *
     * Each row is a new SequenceElement. If matrix of labels or Images are provided, each row is a new SequenceElement,
     * every Image/Label in the columns are displayed at the same time
     *
     * @param nextScreen of enum type ScreenType, is the screen that will be shown after the user touched the finish button
     * @param images the images to show, in increasing order
     * @param imagesBigList the images sequence, a matrix of Image to display multiple images per Sequence Element.
     * @param imagesTexturePaths the paths of the images shown, shown by increasing order
     * @param labels a list of labels to be displayed at the same time as the images
     * @param labelsBigList a matrix of labels to display multiple labels per Sequence Element.
     * @param fond the AssetDescriptor of the background shown when the finish button appear
     * @param changeOfImageSoundDescr the path of the sound file that plays when screen is changed
     * @param endSoundDescr the path of the sound file that plays before the screen is changed to @nextScreen
     * @param pauseSoundDescr the path of the sound file that plays when the game is paused
     * @param nextBtnLabel the text displayed by the "next" button
     * @param finishBtnLabel the text displayed by the "finish" button
     * @param nextBtnFont the font used by the last button
     * @param finishBtnFont the font used by the last button
     * @param timerScheduleTime the time between the player pressed the "finish" button and when it change screen
     * @param vibrationTime the amount of time that the phone vibrate when pressing "next" and "finish" buttons (the time for "next" button pressed is fourth time less than the time given)
     * @param withFinishBtn define if there are a final button appearing (typically "play") or if after the last Image the game directly change to the next screen
     */
    private void builder(final ScreenType nextScreen,
                         Image[] images, Image[][] imagesBigList, String[] imagesTexturePaths,
                         Label[] labels, Label[][] labelsBigList, AssetDescriptor<Texture> fond,
                         AssetDescriptor<Sound> changeOfImageSoundDescr, AssetDescriptor<Sound> endSoundDescr,
                         AssetDescriptor<Sound> pauseSoundDescr, String nextBtnLabel,
                         String finishBtnLabel, UseFont nextBtnFont, UseFont finishBtnFont,
                         final float timerScheduleTime, final int vibrationTime, final boolean withFinishBtn) {
        // Graphics
        this.sequence = FixedDialogSeqBuilder.buildSequence(game, stage, imagesBigList, images, imagesTexturePaths,
                labels, labelsBigList);

        this.curImg = 0;
        this.fond = new Image(game.ass.get(fond));
        this.fond.setVisible(false);
        this.stage.addActor(this.fond);
        // Sounds
        loadAssets(changeOfImageSoundDescr, endSoundDescr, pauseSoundDescr);
        this.changeOfImageSound = game.ass.get(changeOfImageSoundDescr);
        this.endSound = game.ass.get(endSoundDescr);
        this.pauseSound = game.ass.get(pauseSoundDescr);
        // Buttons
        TextButton.TextButtonStyle styleNextBtn = FontHelper.getStyleFont(nextBtnFont);
        TextButton.TextButtonStyle styleFinishBtn = FontHelper.getStyleFont(finishBtnFont);

        final float xButton = this.game.viewport.getWorldWidth() / 2.5f;
        this.next = new TextButtonBuilder(nextBtnLabel).withStyle(styleNextBtn).isVisible(true)
                .withPosition(xButton, this.game.viewport.getWorldHeight() / 30f).withListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                         if(!nextImage()) {
                            // In the case when the image queue is empty (is == null or we saw every image)
                            if (withFinishBtn) {
                                next.setVisible(false);
                                finish.setVisible(true);
                            } else {
                                Gdx.input.vibrate(vibrationTime);
                                endSound.play();
                                resetScreen();
                                game.changeScreen(nextScreen);
                            }
                        }
                        Gdx.input.vibrate(vibrationTime / 4);
                    }
                }).build();
        this.finish = new TextButtonBuilder(finishBtnLabel).withStyle(styleFinishBtn).isVisible(false)
                .withPosition(xButton, this.game.viewport.getWorldHeight() / 2f).withListener(new ChangeListener() {
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
                }).build();

        this.stage.addActor(this.next);
        this.stage.addActor(this.finish);
    }

    private void loadAssets(AssetDescriptor<Sound> changeOfImageSound, AssetDescriptor<Sound> endSound,
                            AssetDescriptor<Sound> pauseSound) {
        if (!game.ass.containsAsset(changeOfImageSound)) {
            game.ass.load(changeOfImageSound);
        }
        if (!game.ass.containsAsset(endSound)) {
            game.ass.load(endSound);
        }
        if (!game.ass.containsAsset(pauseSound)) {
            game.ass.load(pauseSound);
        }
        game.ass.finishLoading();
    }

    /**
     * Initialize the basic variables using the game, the stage and the screenName.
     * ATTENTION : the method *applyBundle* MUST come after this call ! Or nothing will appear on the screen
     * @param game the Kapotopia game
     * @param stage a stage that has been instancied beforehand
     * @param screenName the name of the screen, e.g. "mockupG1"
     */
    public CinematicScreen(final Kapotopia game, Stage stage, String screenName) {
        this.game = game;
        this.stage = stage;
        this.screenName = screenName;
        this.initialized = false;

        //We set elements to null because they MUST be initialized beforehand
        this.sequence = null;
        this.fond = null;
        this.curImg = -1;
        this.changeOfImageSound = null;
        this.endSound = null;
        this.pauseSound = null;
        this.next = null;
        this.finish = null;

        AssetsManager.getInstance().addStage(stage, screenName);
    }

    /**
     * MUST come after the constructor
     * @param params the bundle of parameters that will define the screen, use the specified object API for more information
     */
    protected void applyBundle(ParameterBundleBuilder params) {
        builder(params.getNextScreen(), params.getImages(), params.getImagesBigList(), params.getImagesTexturePaths(),
                params.getLabels(), params.getLabelsBigList(), params.getFond(), params.getChangeOfImageSound(),
                params.getEndSound(), params.getPauseSound(), params.getNextBtnLabel(),
                params.getFinishBtnLabel(), params.getNextBtnFont(), params.getFinishBtnFont(),
                params.getTimerScheduleTime(), params.getVibrationTime(), params.getWithFinishBtn());
        initialized = true;
    }

    // Regular functions

    /**
     * Show the next image in the queue. If the queue is null or is empty, return false
     * @return false if the queue is null, or if is empty. True otherwise
     */
    public boolean nextImage() {
        if(sequence != null) {
            // We hide the current element
            setElementVisibility(false, curImg);
            if (curImg < sequence.getSize()-1) {
                // We make the next element visible
                setElementVisibility(true, ++curImg);
                changeOfImageSound.play();
                return true;
            } else {
                // We hide the last elements
                setElementVisibility(false, curImg-1);
                fond.setVisible(true);
            }
        }
        Gdx.app.debug(TAG, "The sequence is ended");
        return false;
    }

    /**
     * Reset the screen at it's initial state
     */
    public void resetScreen() {
        curImg = 0;

        if(sequence != null) {
            Iterator<DialogueElement> iterator = sequence.iterator();
            while(iterator.hasNext()) {
                DialogueElement element = iterator.next();
                for (Image img : element.getImageList() ) {
                    img.setVisible(false);
                }
                for (Label lab : element.getLabelList() ) {
                    lab.setVisible(false);
                }
            }
            setElementVisibility(true, 0);
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
     * Set the element at the index in the sequence as visible or not
     * @param isVisible value to set for the visibility of the images/labels at the given indexed sequenceElement
     * @param index the index must be within 0 and sequence.size-1 or it will throw an AssertionError
     */
    private void setElementVisibility(boolean isVisible, int index) {
        Gdx.app.debug(TAG, "Image at index " + index + " set to " + isVisible);
        DialogueElement element = sequence.getDialogueElement(index);
        FixedDialogSeqBuilder.setVisibility(element, isVisible);
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
        if(this.pauseSound != null) {
            this.pauseSound.play();
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        if(initialized)
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

    public int getCurrentSeqIndex() {
        return curImg;
    }

    public int getMaxSeqIndex() {
        return sequence.getSize() - 1;
    }

    public class ParameterBundleBuilder {
        private ScreenType nextScreen;
        private Image[] images;
        private Image[][] imagesBigList;
        private String[] imagesTexturePaths;
        private Label[] labels;
        private Label[][] labelsBigList;
        private AssetDescriptor<Texture> fond;
        private AssetDescriptor<Sound> changeOfImageSound;
        private AssetDescriptor<Sound> endSound;
        private AssetDescriptor<Sound> pauseSound;
        private String nextBtnLabel;
        private String finishBtnLabel;
        private UseFont nextBtnFont;
        private UseFont finishBtnFont;
        private float timerScheduleTime;
        private int vibrationTime;
        private boolean withFinishBtn;

        public ParameterBundleBuilder(ScreenType nextScreen) {
            this.nextScreen = nextScreen;
            this.images = null;
            this.imagesBigList = null;
            this.imagesTexturePaths = null;
            this.labels = null;
            this.fond = AssetDescriptors.BLANK_BACK;
            this.changeOfImageSound = AssetDescriptors.SOUND_JUMP_V1;
            this.endSound = AssetDescriptors.SOUND_GAMESTART;
            this.pauseSound = AssetDescriptors.SOUND_PAUSE;
            this.nextBtnLabel = "Next";
            this.finishBtnLabel = "Play";
            this.nextBtnFont = UseFont.CLASSIC_SANS_NORMAL_BLACK;
            this.finishBtnFont = UseFont.CLASSIC_SANS_NORMAL_BLACK;
            this.timerScheduleTime = 2f;
            this.vibrationTime = 200;
            this.withFinishBtn = true;
        }

        public ParameterBundleBuilder withImages(Image[] images) {
            this.images = images;
            return this;
        }

        public ParameterBundleBuilder withImages(Image[][] imagesBigList) {
            this.imagesBigList = imagesBigList;
            return this;
        }

        public ParameterBundleBuilder withTextures(String[] texturePaths) {
            this.imagesTexturePaths = texturePaths;
            return this;
        }

        public ParameterBundleBuilder withLabels(Label[] labels) {
            this.labels = labels;
            return this;
        }

        public ParameterBundleBuilder withLabels(Label[][] labelsBigList) {
            this.labelsBigList = labelsBigList;
            return this;
        }

        public ParameterBundleBuilder withFond(AssetDescriptor<Texture> fond) {
            this.fond = fond;
            return this;
        }

        public ParameterBundleBuilder withSoundToChangeImg(AssetDescriptor<Sound> changeOfImageSound) {
            this.changeOfImageSound = changeOfImageSound;
            return this;
        }

        public ParameterBundleBuilder withSoundToEnd(AssetDescriptor<Sound> soundToEnd) {
            this.endSound = soundToEnd;
            return this;
        }

        public ParameterBundleBuilder withSoundToPause(AssetDescriptor<Sound> pauseSound) {
            this.pauseSound = pauseSound;
            return this;
        }

        public ParameterBundleBuilder withNextBtnTxt(String nextBtnTxt) {
            this.nextBtnLabel = nextBtnTxt;
            return this;
        }

        public ParameterBundleBuilder withFinishBtnTxt(String finishBtnTxt) {
            this.finishBtnLabel = finishBtnTxt;
            return this;
        }

        public ParameterBundleBuilder withNextBtnStyle(UseFont nextBtnFont) {
            this.nextBtnFont = nextBtnFont;
            return this;
        }

        public ParameterBundleBuilder withFinishBtnStyle(UseFont finishBtnFont) {
            this.finishBtnFont = finishBtnFont;
            return this;
        }

        public ParameterBundleBuilder withTimerScheduleTime(float timerScheduleTime) {
            this.timerScheduleTime = timerScheduleTime;
            return this;
        }

        public ParameterBundleBuilder withVibrationTime(int vibrationTime) {
            this.vibrationTime = vibrationTime;
            return this;
        }

        public ParameterBundleBuilder withFinishBtn(boolean withFinishBtn) {
            this.withFinishBtn = withFinishBtn;
            return this;
        }

        public ScreenType getNextScreen() {
            return nextScreen;
        }

        public Image[] getImages() {
            return images;
        }

        public Image[][] getImagesBigList() {
            return imagesBigList;
        }

        public String[] getImagesTexturePaths() {
            return imagesTexturePaths;
        }

        public Label[] getLabels() {
            return labels;
        }

        public AssetDescriptor<Texture> getFond() {
            return fond;
        }

        public AssetDescriptor<Sound> getChangeOfImageSound() {
            return changeOfImageSound;
        }

        public AssetDescriptor<Sound> getEndSound() {
            return endSound;
        }

        public AssetDescriptor<Sound> getPauseSound() {
            return pauseSound;
        }

        public String getNextBtnLabel() {
            return nextBtnLabel;
        }

        public String getFinishBtnLabel() {
            return finishBtnLabel;
        }

        public UseFont getNextBtnFont() {
            return nextBtnFont;
        }

        public UseFont getFinishBtnFont() {
            return finishBtnFont;
        }

        public float getTimerScheduleTime() {
            return timerScheduleTime;
        }

        public int getVibrationTime() {
            return vibrationTime;
        }

        public boolean getWithFinishBtn() {
            return withFinishBtn;
        }

        public Label[][] getLabelsBigList() {
            return labelsBigList;
        }
    }

}
