package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import gdx.kapotopia.AssetsManager;
import gdx.kapotopia.Game2.Ball;
import gdx.kapotopia.Game2.Basket;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.SimpleDirectionGestureDetector;
import gdx.kapotopia.StandardInputAdapter;
import gdx.kapotopia.Utils;

public class Game2 implements Screen {

    private Kapotopia game;
    private Stage stage;

    private Sound successSound;
    private Sound nextSound;

    private Basket currentBasket;
    private Ball currentBall;
    private final float readyBalX = game.viewport.getWorldWidth()/2;
    private final float readyBalY = game.viewport.getWorldHeight()/7;

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

        //Creation of buttons
        TextButton.TextButtonStyle style = Utils.getStyleFont("SEASRN__.ttf");
        float x = game.viewport.getWorldWidth() / 2.5f;
        float y = game.viewport.getWorldHeight() / 10;
        float xRight = game.viewport.getWorldWidth() / 1.5f;

        //Creation of button Play
        final Button btnPlay = new TextButton("Play", style);
        btnPlay.setPosition(x, y);
        btnPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                successSound.play();
                //TODO
            }
        });
        //btnPlay.setVisible(true);
        btnPlay.setVisible(false);
        stage.addActor(btnPlay);

        final Button btnBack = new TextButton("Back to Menu",style);
        //btnBack = new TextButton("Back to Menu",style); //Unsuccessfull test to use menu after using back button
        btnBack.setPosition(xRight ,y);
        btnBack.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                nextSound.play();
                Gdx.input.vibrate(200);
                //dispose();
                game.changeScreen(ScreenType.MAINMENU);
            }
        });
        btnBack.setVisible(true);
        stage.addActor(btnBack);

        //Creation of button next
        /*final Button btnNext = new TextButton("Next", style);
        btnNext.setPosition(x, y);
        btnNext.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                nextSound.play();
                Gdx.input.vibrate(200);
                if (intro0.isVisible()) {
                    intro0.setVisible(false);
                    intro1.setVisible(true);
                } else if (intro1.isVisible()) {
                    intro1.setVisible(false);
                    game0.setVisible(true);
                    btnNext.setVisible(false);
                    btnPlay.setVisible(true);
                    btnBack.setVisible(true);
                }
            }
        });
        btnNext.setVisible(true);*/


        /*Creation of instances for game*/
        final int STDnbr = 6;
        final float labelX = game.viewport.getWorldWidth()/2.5f;
        final float labelY = game.viewport.getWorldHeight()/1.2f;


        //Symptoms creation and set up
        currentBasket = new Basket(0,"IST0");
        currentBasket.setPosition(labelX,labelY);
        currentBasket.hideLabel();
        stage.addActor(currentBasket.getLabel());
        //current.showLabel();
        for(int i=1;i<STDnbr;i++){
            Basket newBasket = new Basket(i,"IST"+i);
            newBasket.setPosition(labelX,labelY);
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

        //Set up of balls representing STI's
        final Ball[] sittingBalls = new Ball[STDnbr];
        float sitBalX = game.viewport.getWorldWidth()/10;
        float sitBalY = game.viewport.getWorldHeight()/10;
        float sitDelta = game.viewport.getWorldWidth()/7;
        for(int i = 0; i < STDnbr; i++) {
            sittingBalls[i] = new Ball(i, "IST" + i, sitBalX + i * sitDelta, sitBalY);
            /*sittingBalls[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    //TODO what the thing does
                    changeBall(sittingBalls[i]);
                }
            });*/
            stage.addActor(sittingBalls[i].getLabel());

        }
        /*for(int i = 0; i < STDnbr; i++){
            final Ball temp = sittingBalls[i];
            sittingBalls[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    changeBall(temp);
                }
            });
        }*/

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
    /*
    private void changeBall(Ball ball){
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
    }*/

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
