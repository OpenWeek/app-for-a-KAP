package gdx.kapotopia.Game2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import gdx.kapotopia.Utils;

public class Ball extends Button {

    /*Characteristics of the STD represented by the ball*/
    private int STDnbr; //Integer that is linked to an STD and permits connection with the correct STD basket
    private String STDname;

    /*Variables related to the ball representation*/
    private final String TEXTURE_PATH = "World1/Game2/ball1.png";
    private float initX, initY; //Position of ball when waiting to be picked
    private float posX, posY; //Current position of ball
    private Label label;

    public Ball(int nbr, String name){
        this.STDnbr = nbr;
        this.STDname = name;
        this.label = new Label(name,new Label.LabelStyle(Utils.getStyleFont("SEASRN__.ttf").font, Color.BLACK));
    }

    public Ball(int nbr, String name, float x, float y){
        this.STDnbr = nbr;
        this.STDname = name;
        this.label = new Label(name,new Label.LabelStyle(Utils.getStyleFont("SEASRN__.ttf").font, Color.BLACK));
        this.label.setPosition(x,y);
        this.initX = x;
        this.initY = y;
        this.posX = x;
        this.posY = y;
    }

    public Label getLabel(){
        return this.label;
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


    public void setPosition(float x,float y){
        this.posX = x;
        this.posY = y;
        this.label.setPosition(x,y);
    }

    public void showLabel(){
        this.label.setVisible(true);
    }

    public void hideLabel(){
        this.label.setVisible(false);
    }

}
