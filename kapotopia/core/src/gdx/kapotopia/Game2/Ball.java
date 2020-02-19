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
    private float finishX, finishY; //Position of ball after successfully been thrown
    final private float size = 200;
    private Label label;
    private ImageButton button;
    private final String TEXTURE_PATH = "World1/Game2/Ballon.png";

    /*Variables related to movement of ball*/
    private boolean moving;
    private boolean flying;
    private boolean rolling;
    private boolean sliding;
    private boolean winning;
    private float g = 20f;
    private final float v = 550;
    private final float v_lost = 250;
    private float speed;
    private float t;
    private double deg = 90;
    private double rad = Math.toRadians(90);
    private double ground;

    public Ball(int nbr, String name){
        this.STInbr = nbr;
        this.STIname = name;
        this.label = new LabelBuilder(name).withWidth(2).withStyle(CLASSIC_SANS_SMALL_WHITE).withPosition(10,75).build();
        this.button = new ImageButton(new TextureRegionDrawable(new TextureRegion(
                AssetsManager.getInstance().getTextureByPath(TEXTURE_PATH))));
        this.button.setSize(size,size);
        this.moving = false;
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
        this.label = new LabelBuilder(name).withWidth(2).withStyle(CLASSIC_SANS_SMALL_WHITE).withPosition(10,75).build();
        this.button = new ImageButton(new TextureRegionDrawable(new TextureRegion(
                AssetsManager.getInstance().getTextureByPath(TEXTURE_PATH))));
        this.button.setBounds(x,y,size,size);
        this.moving = false;
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
        this.moving = false;
    }

    public Ball(int nbr, float x, float y, float g){
        this.STInbr = nbr;
        this.initX = x;
        this.initY = y;
        this.posX = x;
        this.posY = y;
        this.finishX = x;
        this.finishY = y;
        this.ground = g;
        this.button = new ImageButton(new TextureRegionDrawable(new TextureRegion(
                AssetsManager.getInstance().getTextureByPath(TEXTURE_PATH))));
        this.button.setBounds(x,y,size,size);
        this.moving = false;
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
        this.moving = true;
        this.rolling = true;
        this.sliding = true;
    }

    public void win(float x, float y){
        this.finishX = x;
        this.finishY = y;
        this.moving = true;
        this.rolling = true;
        this.sliding = true;
        this.flying = true;
        this.speed = v;
    }

    public void lose(){
        this.finishX = initX;
        this.finishY = initY;
        this.moving = true;
        this.rolling = true;
        this.sliding = true;
        this.flying = true;
        this.speed = v_lost;
    }

    public void setName(String name){
        this.STIname = name;
        this.label = new LabelBuilder(name).withWidth(2).withStyle(CLASSIC_SANS_SMALL_WHITE).withPosition(10,75).build();
    }


    public void showLabel(){
        this.label.setVisible(true);
    }

    public void hideLabel(){
        this.label.setVisible(false);
    }

    public void update(float delta) {
        if(moving){
            Gdx.app.log(TAG,"moving is true");
            if(flying){
                float v0 = speed * delta;
                this.posY = this.posY + v0 * (float) Math.sin(rad) - g * (t - 1);
                t = t + delta;
                if(this.posY <= ground){ //Work only if ground is lower than initY
                    flying = false;
                }
            }
            else {
                if (rolling) {
                    Gdx.app.log(TAG,"rolling is true");
                    float vX = 3*(this.finishX - this.posX) * delta;
                    this.posX = this.posX + vX;
                    if (Math.abs(this.posX - this.finishX) < 1) {
                        rolling = false;
                    }
                }
                if (sliding) {
                    Gdx.app.log(TAG,"sliding is true");
                    float vY = 3*(this.finishY - this.posY) * delta;
                    this.posY = this.posY + vY;
                    if (Math.abs(this.posY - this.finishY) < 1) {
                        sliding = false;
                    }
                } else {//End of launch, reset variables
                    t = 0;
                    moving = false;
                }
            }
            this.button.setPosition(posX, posY);
        }
    }
}