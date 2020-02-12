package gdx.kapotopia.Game2;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import gdx.kapotopia.Helpers.Builders.LabelBuilder;

import static gdx.kapotopia.AssetsManaging.UseFont.CLASSIC_SANS_SMALL_BLACK;
import static gdx.kapotopia.AssetsManaging.UseFont.CLASSIC_SANS_SMALL_WHITE;

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
        this.label = new LabelBuilder(txt).withStyle(CLASSIC_SANS_SMALL_WHITE).isVisible(false).withWidth(500).isWrapped(true).build();
        this.previous = null;
        this.next = null;
    }

    public Basket(int nbr, String txt, Basket prevB){
        this.STInbr = nbr;
        this.symptom = txt;
        this.label = new LabelBuilder(txt).withStyle(CLASSIC_SANS_SMALL_WHITE).isVisible(false).withWidth(500).isWrapped(true).build();
        this.previous = prevB;
        this.next = null;
    }

    public Basket(int nbr, String txt, Basket prevB, Basket nextB){
        this.STInbr = nbr;
        this.symptom = txt;
        this.label = new LabelBuilder(txt).withStyle(CLASSIC_SANS_SMALL_WHITE).isVisible(false).withWidth(500).isWrapped(true).build();
        this.previous = prevB;
        this.next = nextB;
    }

    public Basket(Basket prevB, Basket nextB){
        this.previous = prevB;
        this.next = nextB;
    }

    public Basket(){
        this.previous = null;
        this.next = null;
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

    public String getname(){
        return this.symptom;
    }

    public void setId(int id){
        this.STInbr = id;
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
        if(label!=null) {
            this.label.setPosition(x, y);
        }
    }

    public void setName(String name){
        this.symptom = name;
        this.label = new LabelBuilder(name).withStyle(CLASSIC_SANS_SMALL_WHITE).isVisible(false).withWidth(500).isWrapped(true).withPosition(x,y).build();
    }

    public void showLabel(){
        this.label.setVisible(true);
    }

    public void hideLabel(){
        this.label.setVisible(false);
    }

}