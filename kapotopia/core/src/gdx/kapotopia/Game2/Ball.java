package gdx.kapotopia.Game2;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Helpers.LabelBuilder;

public class Ball extends Button {

    /*Characteristics of the STD represented by the ball*/
    private int STInbr; //Integer that is linked to an STD and permits connection with the correct STD basket
    private String STIname;

    /*Variables related to the ball representation*/
    private float initX, initY; //Position of ball when waiting to be picked
    private float posX, posY; //Current position of ball
    final private float size = 150;
    private Label label;
    private ImageButton button;
    private final String TEXTURE_PATH = "World1/Game2/ball1.png";


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
        this.label = new LabelBuilder(name).withPosition(15,40).build();
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

    public int getSTInbr(){
        return this.STInbr;
    }


    public void setPosition(float x,float y){
        this.posX = x;
        this.posY = y;
        this.button.setPosition(x,y);
    }

    public void showLabel(){
        this.label.setVisible(true);
    }

    public void hideLabel(){
        this.label.setVisible(false);
    }

}
