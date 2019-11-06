package gdx.kapotopia.Game2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import gdx.kapotopia.Utils;

public class Basket {

    /* Characteristics of the STD represented by the basket*/
    private int STDnbr; //Integer that is linked to an STD and permits connection with the correct STD ball
    private String symptom;
    private Label label;
    private float x;
    private float y;

    /*Previous and next node to make a double-linked list*/
    private Basket previous;
    private Basket next;

    public Basket(int nbr, String txt){
        this.STDnbr = nbr;
        this.symptom = txt;
        this.label = new Label(txt,new Label.LabelStyle(Utils.getStyleFont("SEASRN__.ttf").font, Color.BLACK));
        this.previous = null;
        this.next = null;
        //this.label.setPosition(x,y);
    }

    public Basket(int nbr, String txt, Basket prevB){
        this.STDnbr = nbr;
        this.symptom = txt;
        this.label = new Label(txt,new Label.LabelStyle(Utils.getStyleFont("SEASRN__.ttf").font, Color.BLACK));
        this.previous = prevB;
        this.next = null;
    }

    public Basket(int nbr, String txt, Basket prevB, Basket nextB){
        this.STDnbr = nbr;
        this.symptom = txt;
        this.label = new Label(txt,new Label.LabelStyle(Utils.getStyleFont("SEASRN__.ttf").font, Color.BLACK));
        this.previous = prevB;
        this.next = nextB;
    }

    public Basket getPrevious(){
        return this.previous;
    }

    public Basket getNext(){
        return this.next;
    }

    public Label getLabel(){
        return this.label;
    }

    public void setPrevious(Basket prev){
        this.previous = prev;
    }

    public void setNext(Basket next){
        this.next = next;
    }

    public void setPosition(float x,float y){
        this.x = x;
        this.y = y;
        this.label.setPosition(x,y);
    }

    public void showLabel(){
        this.label.setVisible(true);
    }

    public void hideLabel(){
        this.label.setVisible(false);
    }

}