package gdx.kapotopia.Game2;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import gdx.kapotopia.Helpers.LabelBuilder;

public class Basket {

    /* Characteristics of the STD represented by the basket*/
    private int STInbr; //Integer that is linked to an STD and permits connection with the correct STD ball
    private String symptom;
    private Label label;
    private float x;
    private float y;

    /*Previous and next node to make a double-linked list*/
    private Basket previous;
    private Basket next;

    public Basket(int nbr, String txt){
        this.STInbr = nbr;
        this.symptom = txt;
        this.label = new LabelBuilder(txt).isVisible(false).build();
        this.previous = null;
        this.next = null;
    }

    public Basket(int nbr, String txt, Basket prevB){
        this.STInbr = nbr;
        this.symptom = txt;
        this.label = new LabelBuilder(txt).isVisible(false).build();
        this.previous = prevB;
        this.next = null;
    }

    public Basket(int nbr, String txt, Basket prevB, Basket nextB){
        this.STInbr = nbr;
        this.symptom = txt;
        this.label = new LabelBuilder(txt).isVisible(false).build();
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

    public int getSTInbr(){
        return this.STInbr;
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