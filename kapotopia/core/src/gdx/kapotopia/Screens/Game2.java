package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Game2.Ball;
import gdx.kapotopia.Game2.Basket;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Helpers.SimpleDirectionGestureDetector;
import gdx.kapotopia.Helpers.StandardInputAdapter;
import gdx.kapotopia.Kapotopia;

import static gdx.kapotopia.AssetsManaging.UseFont.CLASSIC_SANS_MIDDLE_BLACK;

public class Game2 implements Screen {

    private Kapotopia game;
    private Stage stage;

    private Sound successSound;
    private Sound nextSound;

    private Basket currentBasket;
    private Ball currentBall;
    private float readyBalX;
    private float readyBalY;
    private float finalBalX;
    private float finalBalY;
    private float ballDelta;
    private float middleX;
    private float middleY;

    private final int STInbr = 6;
    private int STIfound = 0;
    private int lives = 5;

    final Ball[] sittingBalls = new Ball[STInbr];

    private final String GAME_PATH = "World1/Game2/";

    private static final String TAG = "Screens-Game2";

    private ChangeListener ballClick[] = new ChangeListener[STInbr];
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

        this.game = game;
        Image imgBckground = new Image(AssetsManager.getInstance().getTextureByPath(GAME_PATH+"Sable.png"));
        Image imgBckground2 = new Image(AssetsManager.getInstance().getTextureByPath(GAME_PATH+"Mer.png"));
        Image imgBckground3 = new Image(AssetsManager.getInstance().getTextureByPath(GAME_PATH+"ciel.png"));
        Image imgBckground3 = new Image(AssetsManager.getInstance().getTextureByPath(GAME_PATH+"basket.png"));
        this.stage = new Stage(game.viewport);
        this.stage.addActor(imgBckground);
        this.stage.addActor(imgBckground2);
        this.stage.addActor(imgBckground3);
        this.stage.addActor(imgBckground4);
        middleX = game.viewport.getWorldWidth()/3;
        middleY = game.viewport.getWorldHeight()/2;

        // Sounds and Music
        this.successSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/leszek-szary_success-1.wav");
        this.nextSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/cmdrobot_videogame-jump.ogg");

        final Image outro0 = new Image(new Texture(GAME_PATH + "20_board_1.png"));
        prepareMockup(outro0);

        /*Creation of instances for game*/
        final float symptX = game.viewport.getWorldWidth()/2.5f;
        final float symptY = game.viewport.getWorldHeight()/1.15f;
        final float sitBalX = game.viewport.getWorldWidth()/12;
        final float sitBalY = game.viewport.getWorldHeight()/24;
        readyBalX = game.viewport.getWorldWidth()/2.2f;
        readyBalY = game.viewport.getWorldHeight()/7;
        finalBalX = game.viewport.getWorldWidth()/1.2f;
        finalBalY = game.viewport.getWorldHeight()/2.25f;
        ballDelta = game.viewport.getWorldWidth()/7.3f;

        //Symptoms creation and set up (representation of symptoms)
        currentBasket = new Basket(0,"IST0");
        currentBasket.setPosition(symptX,symptY);
        currentBasket.hideLabel();
        stage.addActor(currentBasket.getLabel());
        for(int i = 1; i< STInbr; i++){
            Basket newBasket = new Basket(i,"IST"+i);
            newBasket.setPosition(symptX,symptY);
            newBasket.hideLabel();
            stage.addActor(newBasket.getLabel());
            currentBasket.setNext(newBasket);
            Basket intermediate = currentBasket;
            currentBasket = currentBasket.getNext();
            currentBasket.setPrevious(intermediate);
        }
        //Set currentBasket to the first STI symptom
        while(currentBasket.getPrevious()!=null)
            currentBasket = currentBasket.getPrevious();
        currentBasket.showLabel();

        //STI's creation and set up (representation of STI)

        for(int i = 0; i < STInbr; i++) {
            sittingBalls[i] = new Ball(i, "IST" + i, sitBalX + i * ballDelta, sitBalY);
            stage.addActor(sittingBalls[i].getButton());
            sittingBalls[i].getButton().addActor(sittingBalls[i].getLabel());
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

        AssetsManager.getInstance().addStage(stage, "game2");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        setUpInputProcessor(); //Custom Input processor that allows to detect swipes
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        for(int i=0; i<STInbr;i++){
            sittingBalls[i].update(delta);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

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
        //ball.getButton().moveBy(10,10);
        Gdx.app.log(TAG, "Entering changeBall");
        if (ball == currentBall) {//ball is ready to be launched and needs to go back to initial state
            ball.setGoal(ball.getInitX(),ball.getInitY());
            currentBall = null;
        } else {//ball is on initial state and needs to be set to current ball to be ready to be launched
            if (currentBall != null) {
                currentBall.setGoal(currentBall.getInitX(), currentBall.getInitY());
            }
            ball.setGoal(readyBalX, readyBalY);
            currentBall = ball;
        }

    }

    /*Allows to detect sliding movements on the screen and decide which action needs to be executed*/
    private void setUpInputProcessor() {
        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(new StandardInputAdapter(this, game, true));
        im.addProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {
            @Override
            public void onLeft() {
                updateBasket(true);
            }

            @Override
            public void onRight() {
                updateBasket(false);
            }

            @Override
            public void onUp() {
                if(currentBall!=null) {
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
             * Nothing happens if there is no following or previous basket.
             */
            private void updateBasket(boolean left){
                Gdx.app.log("TAG","entering updateBasket function");
                currentBasket.hideLabel();
                if(left){//get following basket on the right
                    if(currentBasket.getNext()!=null)
                        currentBasket = currentBasket.getNext();
                }
                else {//get following basket on the left
                    if(currentBasket.getPrevious()!=null)
                        currentBasket = currentBasket.getPrevious();
                }
                currentBasket.showLabel();
            }

            /**
             * Function called when the player launch a ball
             * Checks if @currentBall STI matches @currentBasket STI symptoms,
             *      set @currentBall position to ball finish position and remove listener if match
             *      set @currentBall position to ball start position if no match
             *      set @currentBall to null
             *      decreases @lives by one if no match
             *      increase @STIfound by one if match
             *      display end game message if game is finished
             */
            private void play(){
                Gdx.app.log(TAG,"Entering play function");
                if(currentBall.getSTInbr() != currentBasket.getSTInbr()){//wrong STI and symptom combination, ball is brought back to initial position
                    changeBall(currentBall);
                    lives--;
                    if(lives==0){
                        if(STIfound>=(STInbr/2)){
                            Label gameWon0 = new LabelBuilder("Pas mal!")
                                    .withPosition(game.viewport.getWorldWidth()/2.5f,middleY)
                                    .build();
                            Label gameWon1 = new LabelBuilder("Tu as les bons symptômes pour "+STIfound+" IST.")
                                    .withPosition(game.viewport.getWorldWidth()/10,middleY-60)
                                    .withStyle(CLASSIC_SANS_MIDDLE_BLACK)
                                    .build();
                            Label gameWon2 = new LabelBuilder("Tu y es presque!")
                                    .withPosition(game.viewport.getWorldWidth()/4,middleY-125)
                                    .build();
                            stage.addActor(gameWon0);
                            stage.addActor(gameWon1);
                            stage.addActor(gameWon2);
                        }
                        else{
                            Label gameWon0 = new LabelBuilder("Bien essayé!")
                                    .withPosition(game.viewport.getWorldWidth()/2.5f,middleY)
                                    .build();
                            Label gameWon1 = new LabelBuilder("Tu as les bons symptômes pour "+STIfound+" IST.")
                                    .withPosition(game.viewport.getWorldWidth()/10,middleY-60)
                                    .withStyle(CLASSIC_SANS_MIDDLE_BLACK)
                                    .build();
                            Label gameWon2 = new LabelBuilder("Persévère! Tu peux y arriver.")
                                    .withPosition(game.viewport.getWorldWidth()/8,middleY-125)
                                    .build();
                            stage.addActor(gameWon0);
                            stage.addActor(gameWon1);
                            stage.addActor(gameWon2);
                        }
                    }
                }
                else{//right STI and symptom have been connected
                    currentBasket.hideLabel();
                    currentBall.setGoal(finalBalX,finalBalY-STIfound*ballDelta);
                    //currentBall.slide(finalBalX,finalBalY-STIfound*ballDelta);
                    currentBall.getButton().removeListener(ballClick[currentBall.getSTInbr()]);
                    currentBall = null;
                    STIfound++;
                    if(STIfound < STInbr){//game is not won yet
                        //updateBasket(true, true);
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
                        //TODO check if this doesn't lead to any memory leak because the old previous basket has not been explicitly deleted
                        //IMPROVEMENT maybe add a success message (with a label) here
                    }
                    else if(STIfound == STInbr){//game has been won
                        currentBasket.hideLabel();
                        Label gameWon0 = new LabelBuilder("Félicitation!")
                                .withPosition(game.viewport.getWorldWidth()/3,middleY)
                                .build();
                        Label gameWon1 = new LabelBuilder("Tu as associé tous les bons")
                                .withPosition(game.viewport.getWorldWidth()/8,middleY-60)
                                .build();
                        Label gameWon2 = new LabelBuilder("symptômes aux bonnes IST!")
                                .withPosition(game.viewport.getWorldWidth()/8,middleY-120)
                                .build();

                        stage.addActor(gameWon0);
                        stage.addActor(gameWon1);
                        stage.addActor(gameWon2);
                        //TODO check if there is no memory leak
                    }
                    else{//STIfound is greater than STInbr. This situation should never happen
                        Gdx.app.log(TAG,"ERROR: Number of STI found is greater than number of total STI");
                    }

                }
            }

        }));
        im.addProcessor(stage);
        Gdx.input.setInputProcessor(im);
    }
}
