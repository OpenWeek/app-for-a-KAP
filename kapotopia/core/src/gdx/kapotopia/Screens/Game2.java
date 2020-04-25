package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.Game2.Ball;
import gdx.kapotopia.Game2.Basket;
import gdx.kapotopia.Helpers.Alignement;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Helpers.SimpleDirectionGestureDetector;
import gdx.kapotopia.Helpers.StandardInputAdapter;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localisation;

import static java.util.Collections.shuffle;


public class Game2 implements Screen {

    private Kapotopia game;
    private Stage stage;

    private Sound successSound;
    private Sound nextSound;
    private Music music;
    private Image panneau;

    private boolean musicOn;

    private Basket currentBasket;
    private Ball currentBall;
    private float screenWidth;
    private float screenHeigth;
    private float readyBalX;
    private float readyBalY;
    private float finalBalX;
    private float finalBalY;
    private float ballDelta;
    private float middleX;
    private float middleY;
    private final float livesX;
    private final float livesY;
    private float ballSize;
    private float sympTextSize;

    //Flags to check if an action is waiting to be executed
    private boolean ballSet = true;
    private boolean changeBasketFlag = false;

    private final int STInbr = 6;
    private int STIfound = 0;
    private int lives = 5;

    private Label livesLabel;

    final Ball[] sittingBalls = new Ball[STInbr];

    private final String TAG = this.getClass().getSimpleName();

    private ChangeListener[] ballClick = new ChangeListener[STInbr];

    private final Localisation loc = Localisation.getInstance();

    /**
     * Prepare images to fullScreen and hidden
     * @param img the image to prepare
     */
    private void prepareMockup(Image img) {
        img.setVisible(false);
        img.setWidth(game.viewport.getWorldWidth());
        img.setHeight(game.viewport.getWorldHeight());
    }

    public Game2(final Kapotopia game){

        Gdx.app.log(TAG,"Entering Game2 function");
        screenHeigth = game.viewport.getWorldHeight();
        screenWidth = game.viewport.getWorldWidth();

        this.game = game;

        loadAssets();

        Image imgBckground = new Image(game.ass.get(AssetDescriptors.SABLE));
        Image imgBckground2 = new Image(game.ass.get(AssetDescriptors.SEA));
        Image imgBckground3 = new Image(game.ass.get(AssetDescriptors.SKY));
        Image imgBckground4 = new Image(game.ass.get(AssetDescriptors.PALMIER));
        Image imgBckground5 = new Image(game.ass.get(AssetDescriptors.BASKET));
        panneau = new Image(game.ass.get(AssetDescriptors.PANNAL));
        this.stage = new Stage(game.viewport);
        this.stage.addActor(imgBckground);
        this.stage.addActor(imgBckground2);
        this.stage.addActor(imgBckground3);
        this.stage.addActor(imgBckground4);
        this.stage.addActor(imgBckground5);
        this.stage.addActor(panneau);
        middleX = screenWidth/3;
        middleY = screenHeigth/2;

        this.musicOn = game.getSettings().isMusicOn();

        // Sounds and Music
        this.successSound = game.ass.get(AssetDescriptors.SOUND_SUCCESS);
        this.nextSound = game.ass.get(AssetDescriptors.SOUND_JUMP_V1);
        this.music = game.ass.get(AssetDescriptors.MUSIC_GAME2);
        this.music.setPosition(0f);
        this.music.setLooping(true);

        final Image outro0 = new Image(game.ass.get(AssetDescriptors.OUTRO));
        prepareMockup(outro0);

        /*Creation of instances for game*/
        final float symptX = screenWidth/3.5f;
        final float symptY = screenHeigth/2.25f;
        final float sitBalX = screenWidth/50;
        final float sitBalY = screenHeigth/24;
        livesX = screenWidth/1.3f;
        livesY = screenHeigth/1.225f;
        readyBalX = screenWidth/2.45f;
        readyBalY = screenHeigth/7;
        finalBalX = screenWidth/1.2f;
        finalBalY = screenHeigth/2.25f;
        ballDelta = screenWidth/6.5f;
        ballSize = screenWidth/(STInbr-1);
        sympTextSize = screenWidth/2;

        livesLabel = new LabelBuilder(game, loc.getString("lives_label")+lives)
                .withStyle(FontHelper.CLASSIC_BOLD_NORMAL_BLACK).isVisible(true).withPosition(livesX,livesY).build();
        stage.addActor(livesLabel);

        //Symptoms creation and set up (representation of symptoms)
        currentBasket = new Basket(game);
        currentBasket.setPosition(symptX,symptY);
        for(int i = 1; i< STInbr; i++){
            Basket newBasket = new Basket(game);
            newBasket.setPosition(symptX,symptY);
            currentBasket.setNext(newBasket);
            Basket intermediate = currentBasket;
            currentBasket = currentBasket.getNext();
            currentBasket.setPrevious(intermediate);
        }
        //Set currentBasket to the first STI symptom
        while(currentBasket.getPrevious()!=null)
            currentBasket = currentBasket.getPrevious();

        //STI's creation and set up (representation of STI)
        for(int i = 0; i < STInbr; i++) {
            sittingBalls[i] = new Ball(game, i, sitBalX + i * ballDelta, sitBalY, ballSize, screenHeigth, screenWidth);
        }
        for(int i = 0; i < STInbr; i++){
            final Ball temp = sittingBalls[i];
            ballClick[i] = new ChangeListener() {
                @Override
                public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                    changeBall(temp);
                }
            };
            sittingBalls[i].getButton().addListener(ballClick[i]);
        }

        //Link between balls, baskets and the STI they represent
        setUpSTI(currentBasket);
        //Adapt size of board to text
        //panneau.setWidth(sympTextSize*2f);  //sympTextSize could be used to have a better scale of board
        float delta = (currentBasket.getLabel().getText().length/22f-5f)/20f;
        panneau.setScale(1+delta);
        panneau.setX(-panneau.getPrefWidth()*delta/2);
        panneau.setY(-panneau.getPrefHeight()*delta/2);

        //Adding ball actors to stage (they have to be added after symptoms to be in front)
        for(int i = 0; i < STInbr; i++) {
            stage.addActor(sittingBalls[i].getButton());
        }

        AssetsManager.getInstance().addStage(stage, "game2");
    }

    private void loadAssets() {
        // Graphics
        game.ass.load(AssetDescriptors.BALL);
        game.ass.load(AssetDescriptors.SABLE);
        game.ass.load(AssetDescriptors.SEA);
        game.ass.load(AssetDescriptors.SKY);
        game.ass.load(AssetDescriptors.PALMIER);
        game.ass.load(AssetDescriptors.BASKET);
        game.ass.load(AssetDescriptors.PANNAL);
        game.ass.load(AssetDescriptors.OUTRO);
        // Sounds
        game.ass.load(AssetDescriptors.MUSIC_GAME2);
        game.ass.load(AssetDescriptors.SOUND_SUCCESS);

        game.ass.finishLoading();
        Gdx.app.log(TAG, game.ass.getDiagnostics());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        setUpInputProcessor(); //Custom Input processor that allows to detect swipes
        if (musicOn) music.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        int areMoving = 0;
        for(int i=0; i<STInbr;i++){
            sittingBalls[i].update(delta);
            if(sittingBalls[i].isMoving()){
                ballSet=false;
                areMoving ++;
            }
        }
        if(areMoving==0){ //All balls are set, update ballSet flag and check if there are functions to execute
            ballSet = true;
            if (changeBasketFlag) {
                changeBasket(currentBasket);
                changeBasketFlag = false;
            }
        }

    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        if (musicOn)
            music.pause();
    }

    @Override
    public void resume() {
        if (musicOn)
            this.music.play();
    }

    @Override
    public void hide() {
        if (musicOn)
            this.music.stop();
    }

    @Override
    public void dispose() {
        AssetsManager.getInstance().disposeStage("game2");
    }

    /**
     * changeball: Change the clicked ball position
     * @param ball: Ball that needs to be act upon
     * Set ball as the current ball and change its position to be ready to be launched if it was in its initial position,
     * set ball to its initial position and set currentBall to null if ball was the currentBall
     */
    private void changeBall(Ball ball) {
            Gdx.app.log(TAG, "Entering changeBall");
            if (ball == currentBall) {//ball is ready to be launched and needs to go back to initial state
                ball.setGoal(ball.getInitX(), ball.getInitY());
                currentBall = null;
            } else {//ball is on initial state and needs to be set to current ball to be ready to be launched
                if (currentBall != null) {
                    currentBall.setGoal(currentBall.getInitX(), currentBall.getInitY());
                }
                ball.setGoal(readyBalX, readyBalY);
                currentBall = ball;
            }

    }

    private void changeBasket(Basket basket){
        if(ballSet == true) {
            currentBasket.hideLabel();
            Gdx.app.log("TAG", "entering changeBasket function");
            if(currentBasket.getPrevious()!=null) {
                currentBasket.getPrevious().setNext(currentBasket.getNext());
            }
            if(currentBasket.getNext()!=null) {
                currentBasket.getNext().setPrevious(currentBasket.getPrevious());
                currentBasket=currentBasket.getNext();
            }
            else {
                currentBasket = currentBasket.getPrevious();
            }
            currentBasket.showLabel();
        }
        //TODO check if this doesn't lead to any memory leak because the old previous basket has not been explicitly deleted
    }

    /**
     *
     */
    private void setUpSTI(Basket firstbasket){
        String[] stiNames = {
                loc.getStiName("hiv"),
                loc.getStiName("c_hepatitis"),
                loc.getStiName("hpv"),
                loc.getStiName("gonorrhea"),
                loc.getStiName("chlamydia"),
                loc.getStiName("herpes")};
        String[] symptoms = {
                loc.getStiSymptom("hiv"),
                loc.getStiSymptom("c_hepatitis"),
                loc.getStiSymptom("hpv"),
                loc.getStiSymptom("gonorrhea"),
                loc.getStiSymptom("chlamydia"),
                loc.getStiSymptom("herpes")};
        ArrayList<Integer>numbers = new ArrayList<Integer>();
        ArrayList<Integer>ids = new ArrayList<Integer>();
        for(int i = 0; i < STInbr; i++){
            numbers.add(i);
            ids.add(i);
        }
        shuffle(numbers);
        shuffle(ids);
        Basket inter = firstbasket;
        for(int i = 0; i < STInbr; i++){
            sittingBalls[ids.get(i)].setName(stiNames[numbers.get(i)]);
            sittingBalls[ids.get(i)].getButton().addActor(sittingBalls[ids.get(i)].getLabel());
            inter.setId(ids.get(i));
            inter.setName(symptoms[numbers.get(i)], sympTextSize);
            stage.addActor(inter.getLabel());
            inter = inter.getNext();
        }
        firstbasket.showLabel();
    }

    /** Allows to detect sliding movements on the screen and decide which action needs to be executed */
    private void setUpInputProcessor() {
        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(new StandardInputAdapter(this, game, true));
        im.addProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {
            @Override
            public void onLeft() {
                if(ballSet == true) {
                    updateBasket(true);
                }
            }

            @Override
            public void onRight() {
                if(ballSet == true) {
                    updateBasket(false);
                }
            }

            @Override
            public void onUp() {
                if(ballSet == true && currentBall!=null) {
                    play();
                }
            }

            @Override
            public void onDown() {

            }

            /**
             * Change the symptom that is displayed and considered current
             * @param left: boolean that indicates in what direction the change needs to be done
             * the current basket is changed to the next one if there is one and if left is true,
             * it is changed to the previous one if there is one and left is false.
             * Nothing happens if there is no following or previous basket or if the game has been lost.
             */
            private void updateBasket(boolean left){
                    if (lives > 0) {
                        Gdx.app.log("TAG", "entering updateBasket function");
                        currentBasket.hideLabel();
                        if (left) {//get following basket on the right
                            if (currentBasket.getNext() != null)
                                currentBasket = currentBasket.getNext();
                        } else {//get following basket on the left
                            if (currentBasket.getPrevious() != null)
                                currentBasket = currentBasket.getPrevious();
                        }
                        //Adapt size of board to text
                        float delta = (currentBasket.getLabel().getText().length / 22f - 5f) / 20f;
                        //panneau.setWidth(sympTextSize*2f); //sympTextSize could be used to have a better scale of board
                        panneau.setScale(1 + delta);
                        panneau.setX(-panneau.getPrefWidth() * delta / 2);
                        panneau.setY(-panneau.getPrefHeight() * delta / 2);
                        currentBasket.showLabel();
                    }
            }

            /**
             * Function called when the player swipes up
             * Checks if @currentBall STI matches @currentBasket STI symptoms,
             *      set @currentBall position to ball finish position and remove listener if match
             *      set @currentBall position to ball start position if no match
             *      set @currentBall to null
             *      decreases @lives by one if no match
             *      increase @STIfound by one if match
             *      display end game message and remove the listeners if game is finished
             */
            private void play() {
                    Gdx.app.log(TAG, "Entering play function");
                    if (currentBall.getSTInbr() != currentBasket.getSTInbr()) {//wrong STI and symptom combination, ball is brought back to initial position
                        currentBall.lose();
                        currentBall = null;
                        lives--;
                        livesLabel.setVisible(false);
                        livesLabel = new LabelBuilder(game, loc.getString("lives_label") + lives).withStyle(FontHelper.CLASSIC_BOLD_NORMAL_BLACK).isVisible(true).withPosition(livesX, livesY).build();
                        stage.addActor(livesLabel);
                        if (lives == 0) {//Game is lost
                            //remove the listeners and hide the symptom
                            for (int i = 0; i < STInbr; i++) {
                                sittingBalls[i].getButton().removeListener(ballClick[i]);
                            }
                            currentBasket.hideLabel();
                            //Display losing message
                            if (STIfound >= (STInbr / 2)) {
                                Label gameWon0 = new LabelBuilder(game, loc.getString("game2_badending1"))
                                        .withStyle(FontHelper.CLASSIC_SANS_NORMAL_BLACK)
                                        .withAlignment(Alignement.CENTER).withY(middleY)
                                        .build();
                                Label gameWon1 = new LabelBuilder(game, loc.getString("game2_badending2") + STIfound + " " + loc.getString("game2_badending3"))
                                        .withStyle(FontHelper.CLASSIC_SANS_SMALL_BLACK)
                                        .withAlignment(Alignement.CENTER).withY(middleY - 60)
                                        .build();
                                Label gameWon2 = new LabelBuilder(game, loc.getString("game2_badending4"))
                                        .withStyle(FontHelper.CLASSIC_SANS_MIDDLE_BLACK)
                                        .withAlignment(Alignement.CENTER).withY(middleY - 125)
                                        .build();
                                stage.addActor(gameWon0);
                                stage.addActor(gameWon1);
                                stage.addActor(gameWon2);
                            } else {
                                Label gameWon0 = new LabelBuilder(game, loc.getString("game2_badending5"))
                                        .withStyle(FontHelper.CLASSIC_SANS_NORMAL_BLACK)
                                        .withAlignment(Alignement.CENTER).withY(middleY)
                                        .build();
                                Label gameWon1 = new LabelBuilder(game, loc.getString("game2_badending2") + STIfound + " " + loc.getString("game2_badending3"))
                                        .withStyle(FontHelper.CLASSIC_SANS_SMALL_BLACK)
                                        .withAlignment(Alignement.CENTER).withY(middleY - 60)
                                        .build();
                                Label gameWon2 = new LabelBuilder(game, loc.getString("game2_badending6"))
                                        .withStyle(FontHelper.CLASSIC_SANS_MIDDLE_BLACK)
                                        .withAlignment(Alignement.CENTER).withY(middleY - 125)
                                        .build();
                                stage.addActor(gameWon0);
                                stage.addActor(gameWon1);
                                stage.addActor(gameWon2);
                            }
                        }
                    } else {//right STI and symptom have been connected
                        currentBall.win(finalBalX, finalBalY - STIfound * ballDelta);
                        currentBall.getButton().removeListener(ballClick[currentBall.getSTInbr()]);
                        currentBall = null;
                        STIfound++;
                        if (STIfound < STInbr) {//game is not won yet
                            changeBasketFlag = true;
                            //IMPROVEMENT maybe add a success message (with a label) here
                        } else if (STIfound == STInbr) {//game has been won
                            currentBasket.hideLabel();
                            Label gameWon0 = new LabelBuilder(game, loc.getString("game2_goodending1"))
                                    .withAlignment(Alignement.CENTER).withY(middleY)
                                    .withStyle(FontHelper.CLASSIC_SANS_NORMAL_BLACK)
                                    .build();
                            Label gameWon1 = new LabelBuilder(game, loc.getString("game2_goodending2"))
                                    .withAlignment(Alignement.CENTER).withY(middleY - 60)
                                    .withStyle(FontHelper.CLASSIC_SANS_MIDDLE_BLACK)
                                    .build();
                            Label gameWon2 = new LabelBuilder(game, loc.getString("game2_goodending3"))
                                    .withAlignment(Alignement.CENTER).withY(middleY - 120)
                                    .withStyle(FontHelper.CLASSIC_SANS_MIDDLE_BLACK)
                                    .build();

                            stage.addActor(gameWon0);
                            stage.addActor(gameWon1);
                            stage.addActor(gameWon2);
                            //TODO check if there is no memory leak
                        } else {//STIfound is greater than STInbr. This situation should never happen
                            Gdx.app.log(TAG, "ERROR: Number of STI found is greater than number of total STI");
                        }

                    }
            }

        }));
        im.addProcessor(stage);
        Gdx.input.setInputProcessor(im);
    }
}
