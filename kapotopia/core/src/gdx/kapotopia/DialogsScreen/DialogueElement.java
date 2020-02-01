package gdx.kapotopia.DialogsScreen;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import gdx.kapotopia.AssetsManaging.AssetsManager;

public class DialogueElement {
    private int seqNumber;
    private Image[] imageList;
    private Label[] labelList;

    public DialogueElement(int seqNumber, Image[] imgSeq, Label[] labSeq) {
        this.seqNumber = seqNumber;

        imageList = imgSeq;
        labelList = labSeq;
    }

    public DialogueElement(int seqNumber, String[] imagePaths, Label[] labSeq) {
        this.seqNumber = seqNumber;

        imageList = new Image[imagePaths.length];
        for (int i = 0; i < imagePaths.length; i++) {
            imageList[i] = new Image(AssetsManager.getInstance().getTextureByPath(imagePaths[i]));
        }
        labelList = labSeq;
    }

    public int getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(int seqNumber) {
        this.seqNumber = seqNumber;
    }

    public Image getImage() {
        return getImage(0);
    }

    public Image getImage(int index) {
        if (index < 0 || index >= imageList.length) throw new AssertionError();
        return imageList[index];
    }

    public Label getLabel() {
        return getLabel(0);
    }

    public Label getLabel(int index) {
        if (index < 0 || index >= labelList.length) throw new AssertionError();
        return labelList[index];
    }

    /** Lists */

    public Image[] getImageList() {
        return imageList;
    }

    public Label[] getLabelList() {
        return labelList;
    }
}
