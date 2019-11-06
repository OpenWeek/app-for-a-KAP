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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Game2.Ball;
import gdx.kapotopia.Game2.Basket;
import gdx.kapotopia.Helpers.SimpleDirectionGestureDetector;
import gdx.kapotopia.Helpers.StandardInputAdapter;
import gdx.kapotopia.AssetsManaging.AssetsManager;

public class Game2 implements Screen {

    private Kapotopia game;
    private Stage stage;

    private Sound successSound;
    private Sound nextSound;

    private Basket currentBasket;
    private Ball currentBall;
    private float readyBalX;
    private float readyBalY;

    private final String GAME_PATH = "World1/Game2/";

    private static final String TAG = "Screens-Game2";

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
        Image imgBckground = new Image(AssetsManager.getInstance().getTextureByPath("FondNiveauBlanc2.png"));
        this.stage = new Stage(game.viewport);
        this.stage.addActor(imgBckground);

        // Sounds and Music
        this.successSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/leszek-szary_success-1.wav");
        this.nextSound = AssetsManager.getInstance().getSoundByPath("sound/bruitage/cmdrobot_videogame-jump.ogg");

        //Creation the screen images
        final Image game0 = new Image(new Texture(GAME_PATH + "21_board_0.jpg"));
        prepareMockup(game0);
        game0.setVisible(true);
        final Image outro0 = new Image(new Texture(GAME_PATH + "22_board_0.jpg"));
        prepareMockup(outro0);
        stage.addActor(game0);

        /*Creation of instances for game*/
        final int STDnbr = 6;
        final float symptX = game.viewport.getWorldWidth()/2.5f;
        final float symptY = game.viewport.getWorldHeight()/1.2f;
        final float sitBalX = game.viewport.getWorldWidth()/12;
        final float sitBalY = game.viewport.getWorldHeight()/14;
        final float sitDelta = game.viewport.getWorldWidth()/7;
        readyBalX = game.viewport.getWorldWidth()/2.2f;
        readyBalY = game.viewport.getWorldHeight()/5;

        //Symptoms creation and set up (representation of symptoms)
        currentBasket = new Basket(0,"IST0");
        currentBasket.setPosition(symptX,symptY);
        currentBasket.hideLabel();
        stage.addActor(currentBasket.getLabel());
        //current.showLabel();
        for(int i=1;i<STDnbr;i++){
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
        final Ball[] sittingBalls = new Ball[STDnbr];
        for(int i = 0; i < STDnbr; i++) {
            sittingBalls[i] = new Ball(i, "IST" + i, sitBalX + i * sitDelta, sitBalY);
            stage.addActor(sittingBalls[i].getButton());
            sittingBalls[i].getButton().addActor(sittingBalls[i].getLabel());
        }
        for(int i = 0; i < STDnbr; i++){
            final Ball temp = sittingBalls[i];
            sittingBalls[i].getButton().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                    changeBall(temp);
                }
            });
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
    private void changeBall(Ball ball){
        Gdx.app.log(TAG,"Entering changeBall");
        if(ball==currentBall){//ball is ready to be launched and needs to go back to initial state
            ball.setPosition(ball.getInitX(),ball.getInitY());
            currentBall=null;
        }
        else{//ball is on initial state and needs to be set to current ball to be ready to be launched
            if(currentBall!=null){
                currentBall.setPosition(currentBall.getInitX(),currentBall.getInitY());
            }
            ball.setPosition(readyBalX,readyBalY);
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

        }));
        im.addProcessor(stage);
        Gdx.input.setInputProcessor(im);
    }
}
