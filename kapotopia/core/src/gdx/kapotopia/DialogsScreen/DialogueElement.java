package gdx.kapotopia.DialogsScreen;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import gdx.kapotopia.AssetsManaging.AssetsManager;

public class DialogueElement {
    private int seqNumber;  /** Sequence number */
    private Image image;
    private Label label;

    public DialogueElement(int seqNumber, Image image, Label label) {
        this.seqNumber = seqNumber;
        this.image = image;
        this.label = label;
    }

    public DialogueElement(int seqNumber, String imagePath, Label label) {
        this.seqNumber = seqNumber;
        this.image = new Image(AssetsManager.getInstance().getTextureByPath(imagePath));
        this.label = label;
    }

    public int getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(int seqNumber) {
        this.seqNumber = seqNumber;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
}
