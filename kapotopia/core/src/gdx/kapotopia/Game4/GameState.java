package gdx.kapotopia.Game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.text.View;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Helpers.Builders.ImageBuilder;
import gdx.kapotopia.Helpers.Builders.ImageButtonBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.Screens.Game4;

public class GameState {

    private final String TAG = this.getClass().getSimpleName();

    private Kapotopia game;
    Game4 screen;

    private int totalScore;

    private int snakeSize = 10;  //  10-15 squares square
    private int boardSize = 11;
    private int yOffset = 308;
    private int xOffset = 30;
    private int direction = 0;
    private Queue<BodyPart> mBody = new Queue<BodyPart>();
    private CopyOnWriteArrayList<Food> foods= new CopyOnWriteArrayList<Food>();
    private int snakeLength = 3;
    long prevtime = 0;
    private boolean isPaused;
    private boolean isFinish;
    private final Rectangle bounds;

    private final Texture IST;

    private float mTimer = 0;

    public GameState(Kapotopia game, Game4 screen) {
        this.game = game;
        this.screen = screen;
        this.isPaused = false;
        this.bounds = new Rectangle(0,0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());

        switch (game.vars.getChosenSTD()){
            case 0:
                IST = game.ass.get(AssetDescriptors.VIH);
                break;
            case 1:
                IST = game.ass.get(AssetDescriptors.HEPA);
                break;
            case 2:
                IST = game.ass.get(AssetDescriptors.HEPB);
                break;
            case 3:
                IST = game.ass.get(AssetDescriptors.HEPC);
                break;
            case 4:
                IST = game.ass.get(AssetDescriptors.SYPHILIS);
                break;
            case 5:
                IST = game.ass.get(AssetDescriptors.HERPES);
                break;
            case 6:
                IST = game.ass.get(AssetDescriptors.PAPILLO);
                break;
            case 7:
                IST = game.ass.get(AssetDescriptors.CHLAMYDIA);
                break;
            case 8:
                IST = game.ass.get(AssetDescriptors.GONORRHEE);
                break;
            case 9:
                IST = game.ass.get(AssetDescriptors.TRICHOMONAS);
                break;
            default: //should never happen
                IST = game.ass.get(AssetDescriptors.TRICHOMONAS);
        }


        this.totalScore = 0;
        float scaleSnake = game.viewport.getWorldWidth()/snakeSize;
        BodyPart bp1 = new BodyPart(15,15, boardSize);
        mBody.addLast(bp1);
        ImageButton img1 = new ImageButtonBuilder()
                .withImageUp(IST)
                .withBounds(bp1.getX()*scaleSnake + xOffset, bp1.getY()*scaleSnake + yOffset, scaleSnake, scaleSnake)
                .build();
        screen.getStage().addActor(img1);
        bp1.setIb(img1);

        BodyPart bp2 = new BodyPart(15,14, boardSize);
        mBody.addLast(bp2);
        ImageButton img2 = new ImageButtonBuilder()
                .withImageUp(IST)
                .withBounds(bp2.getX()*scaleSnake + xOffset, bp2.getY()*scaleSnake + yOffset, scaleSnake, scaleSnake)
                .build();
        screen.getStage().addActor(img2);
        bp2.setIb(img2);

        BodyPart bp3 = new BodyPart(15,13, boardSize);
        mBody.addLast(bp3);
        ImageButton img3 = new ImageButtonBuilder()
                .withImageUp(IST)
                .withBounds(bp3.getX()*scaleSnake + xOffset, bp3.getY()*scaleSnake + yOffset, scaleSnake, scaleSnake)
                .build();
        screen.getStage().addActor(img3);
        bp3.setIb(img3);
    }

    public void setDirection(int nextDirection){
        if (direction == 0 && nextDirection != 2) {
            this.direction = nextDirection;
        } else if (direction == 2 && nextDirection != 0) {
            this.direction = nextDirection;
        } else if (direction == 1 && nextDirection != 3){
            this.direction = nextDirection;
        } else if (direction == 3 && nextDirection != 1){
            this.direction = nextDirection;
        }
    }

    public int getTotalScore() {
        return this.totalScore;
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

    public void update(float delta, Viewport viewport) {
        if (!isPaused()){
            mTimer += delta;
            long timestamp = System.currentTimeMillis() / 1000; // time in seconds
            if (timestamp % 4 == 0 && timestamp != prevtime) {
                float width = game.viewport.getWorldWidth();
                float scaleSnake = width/snakeSize;
                Food f = new Food(snakeSize, screen, scaleSnake, xOffset, yOffset, game);
                foods.add(f);
                if (foods.size() == 11) {   //max 10 foods on screen
                    foods.remove(0);
                }
            }
            prevtime = timestamp;

            if (mTimer > 0.17f) { //change 0.17f to change snake speed
                mTimer = 0;
                advance();
            }
        }

    }

    private void advance() {
        float headX = mBody.first().getX();
        float headY = mBody.first().getY();
        float scaleSnake = game.viewport.getWorldWidth()/snakeSize;
        switch(this.direction) {
            case 0: //up
                BodyPart bp = new BodyPart(headX, headY+1, boardSize);
                mBody.addFirst(bp);
                ImageButton img1 = new ImageButtonBuilder()
                        .withImageUp(IST)
                        .withBounds(bp.getX()*scaleSnake + xOffset, bp.getY()*scaleSnake + yOffset, scaleSnake, scaleSnake)
                        .build();
                screen.getStage().addActor(img1);
                bp.setIb(img1);
                break;
            case 1: //right
                BodyPart bp1 = new BodyPart(headX+1, headY, boardSize);
                mBody.addFirst(bp1);
                ImageButton img2 = new ImageButtonBuilder()
                        .withImageUp(IST)
                        .withBounds(bp1.getX()*scaleSnake + xOffset, bp1.getY()*scaleSnake + yOffset, scaleSnake, scaleSnake)
                        .build();
                screen.getStage().addActor(img2);
                bp1.setIb(img2);
                break;
            case 2: //down
                BodyPart bp2 = new BodyPart(headX, headY-1, boardSize);
                mBody.addFirst(bp2);
                ImageButton img3 = new ImageButtonBuilder()
                        .withImageUp(IST)
                        .withBounds(bp2.getX()*scaleSnake + xOffset, bp2.getY()*scaleSnake + yOffset, scaleSnake, scaleSnake)
                        .build();
                screen.getStage().addActor(img3);
                bp2.setIb(img3);
                break;
            case 3: //left
                BodyPart bp3 = new BodyPart(headX-1, headY, boardSize);
                mBody.addFirst(bp3);
                ImageButton img4 = new ImageButtonBuilder()
                        .withImageUp(IST)
                        .withBounds(bp3.getX()*scaleSnake + xOffset, bp3.getY()*scaleSnake + yOffset, scaleSnake, scaleSnake)
                        .build();
                screen.getStage().addActor(img4);
                bp3.setIb(img4);
                break;
            default://should never happen
                BodyPart bp4 = new BodyPart(headX, headY+1, boardSize);
                mBody.addFirst(bp4);
                ImageButton img5 = new ImageButtonBuilder()
                        .withImageUp(IST)
                        .withBounds(bp4.getX()*scaleSnake + xOffset, bp4.getY()*scaleSnake + yOffset, scaleSnake, scaleSnake)
                        .build();
                screen.getStage().addActor(img5);
                bp4.setIb(img5);
                break;
        }

        //see if food is eaten
        for (Food f: foods) {
            if (mBody.first().getX() == f.getX() && mBody.first().getY() == f.getY()) {
                if (f.getType() < 3) {
                    this.totalScore += 10;
                    snakeLength++;
                } else if (f.getType() == 3 || f.getType() == 4) {
                    snakeLength--;
                    this.totalScore -= 10;
                    if (snakeLength == 0) {
                        game.destroyScreen(screen);
                        game.changeScreen(ScreenType.WORLD2);
                    }
                } else { //should never happen
                    snakeLength++;
                }
                f.getSqr().remove();
                f.getLogo().remove();
                foods.remove(f);
            }
        }

        for (int i = 1; i < mBody.size; i++) {  //when dire reset to length 3
            if (mBody.get(i).getX() == mBody.first().getX() &&
                    mBody.get(i).getY() == mBody.first().getY()) {
                game.destroyScreen(screen);
                game.changeScreen(ScreenType.WORLD2);
            }
        }

        while (mBody.size - 1 >= snakeLength) {
            mBody.last().getIb().remove();
            mBody.removeLast();
        }
    }

    public void draw(float width, float height, OrthographicCamera camera) {
        //snake
        float scaleSnake = width / snakeSize;
        for (BodyPart bp : mBody) {
            bp.getIb().remove();
            ImageButton img = new ImageButtonBuilder()
                    .withImageUp(IST)
                    .withBounds(bp.getX() * scaleSnake + xOffset, bp.getY() * scaleSnake + yOffset, scaleSnake, scaleSnake)
                    .build();
            screen.getStage().addActor(img);
            bp.setIb(img);
        }
    }

    // GETTERS

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
