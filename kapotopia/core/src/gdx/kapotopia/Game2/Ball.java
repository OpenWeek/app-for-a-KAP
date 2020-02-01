package gdx.kapotopia.Game2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;

import static gdx.kapotopia.AssetsManaging.UseFont.CLASSIC_SANS_SMALL_WHITE;

public class Ball extends Button {

    final private String TAG = "Ball class";
    /*Characteristics of the STD represented by the ball*/
    private int STInbr; //Integer that is linked to an STD and permits connection with the correct STD basket
    private String STIname;
    /*Variables related to the ball representation*/
    private float initX, initY; //Position of ball when waiting to be picked
    private float posX, posY; //Current position of ball
    private float finishX, finishY;
    final private float size = 200;
    private Label label;
    private ImageButton button;
    private final String TEXTURE_PATH = "World1/Game2/Ballon.png";


    public Ball(int nbr, String name){
        this.STInbr = nbr;
        this.STIname = name;
        this.label = new LabelBuilder(name).build();
        this.button = new ImageButton(new TextureRegionDrawable(new TextureRegion(
                AssetsManager.getInstance().getTextureByPath(TEXTURE_PATH))));
        this.button.setSize(size,size);
    }

    public Ball(int nbr, String name, float x, float y){
        this.STInbr = nbr;
        this.STIname = name;
        this.initX = x;
        this.initY = y;
        this.posX = x;
        this.posY = y;
        this.finishX = x;
        this.finishY = y;
        this.label = new LabelBuilder(name).withPosition(15,40).build();
        this.button = new ImageButton(new TextureRegionDrawable(new TextureRegion(
                AssetsManager.getInstance().getTextureByPath(TEXTURE_PATH))));
        this.button.setBounds(x,y,size,size);
    }

    public Ball(int nbr, float x, float y){
        this.STInbr = nbr;
        this.initX = x;
        this.initY = y;
        this.posX = x;
        this.posY = y;
        this.finishX = x;
        this.finishY = y;
        this.button = new ImageButton(new TextureRegionDrawable(new TextureRegion(
                AssetsManager.getInstance().getTextureByPath(TEXTURE_PATH))));
        this.button.setBounds(x,y,size,size);
    }

    public Label getLabel(){
        return this.label;
    }

    public ImageButton getButton() {
        return this.button;
    }

    public float getX(){
        return this.posX;
    }

    public float getY(){
        return this.posY;
    }

    public float getInitX(){
        return this.initX;
    }

    public float getInitY(){
        return this.initY;
    }

    public float getGoalX(){
        return this.finishX;
    }

    public float getGoalY(){
        return this.finishY;
    }

    public int getSTInbr(){
        return this.STInbr;
    }

    public String getName() {
        return this.STIname;
    }


    public void setPosition(float x,float y){
        this.posX = x;
        this.posY = y;
        this.finishX = x;
        this.finishY = y;
        this.button.setPosition(x,y);
        if(this.label!=null){
            this.label.setPosition(0,0); //Relative position of label in ball
        }
    }

    public void setGoalX(float x){
        this.finishX = x;
    }

    public void setGoalY(float y){
        this.finishY = y;
    }

    public void setGoal(float x, float y){
        this.finishX = x;
        this.finishY = y;
    }

    public void setName(String name){
        this.STIname = name;
        this.label = new LabelBuilder(name).withWidth(2).withStyle(CLASSIC_SANS_SMALL_WHITE).build();
        this.label.setPosition(10,75); //Relative position of label in ball
    }


    public void showLabel(){
        this.label.setVisible(true);
    }

    public void hideLabel(){
        this.label.setVisible(false);
    }

    public void update(float delta) {
        //TODO improve ball displacement
        float speed = 400 * delta;
        if (this.posX < finishX - speed || this.posX > finishX + speed || this.posY < finishY - speed || this.posY > finishY + speed) {
            Gdx.app.log(TAG, "If of update entered");
            if (finishX > this.posX) {
                this.posX = this.posX + speed;
            } else if (finishX < this.posX) {
                this.posX = this.posX - speed;
            }
            if (finishY > this.posY) {
                this.posY = this.posY + speed;
            } else if (finishY < this.posY) {
                this.posY = this.posY - speed;
            }
            this.button.setPosition(posX, posY);
        }
    }
}
