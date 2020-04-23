package gdx.kapotopia.DialogsScreen;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

import gdx.kapotopia.AssetsManaging.AssetsManager;

public class FixedDialogueSequence implements Iterable {
    private int size;
    private DialogueElement[] sequence;

    /**
     * Constructor of an empty list with a fixed size
     * @param size the size of the sequence
     */
    public FixedDialogueSequence(int size) {
        this.size = size;
        this.sequence = new DialogueElement[size];
    }

    /**
     * Constructor of a fixed sequence with a list of Image paths and a list of labels
     * Note: cinnematicListImages and labelList must have the same size,
     * @param cinematicListImages a list of Images
     * @param labelList a list of labels
     */
    public FixedDialogueSequence(Image[] cinematicListImages, Label[] labelList) {
        if (cinematicListImages.length != labelList.length) throw new AssertionError();
        this.size = cinematicListImages.length;
        this.sequence = new DialogueElement[size];
        for (int i=0; i < size; i++) {
            Image[] imgSeq = new Image[1];
            Label[] labSeq = new Label[1];
            imgSeq[0] = cinematicListImages[i];
            labSeq[0] = labelList[i];
            sequence[i] = new DialogueElement(i, imgSeq, labSeq);
        }
    }

    /**
     * Constructor of a fixed sequence with a matrix of Image and a matrix of labels
     * Note: cinnematicImagesMatrix and labelMatrix must have the same size,
     * @param cinnematicImagesMatrix a matrix of Images
     * @param labelMatrix a matrix of Labels
     */
    public FixedDialogueSequence(Image[][] cinnematicImagesMatrix, Label[][] labelMatrix) {
        if (cinnematicImagesMatrix.length != labelMatrix.length) throw new AssertionError();
        this.size = cinnematicImagesMatrix.length;
        this.sequence = new DialogueElement[size];

        for (int i=0; i < size; i++) {
            final Image[] imgSeq = cinnematicImagesMatrix[i];
            final Label[] labSeq = labelMatrix[i];
            sequence[i] = new DialogueElement(i, imgSeq, labSeq);
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public DialogueElement[] getSequence() {
        return sequence;
    }

    public void setSequence(DialogueElement[] sequence) {
        this.sequence = sequence;
    }

    public DialogueElement getFirstElement() {
        return getDialogueElement(0);
    }

    public DialogueElement getDialogueElement(int index) {
        if (index < 0 || index >= size) throw new AssertionError();
        return this.sequence[index];
    }

    public void setDialogueElement(int index, DialogueElement diagElem) {
        if (index < 0 || index >= size) throw new AssertionError();

        this.sequence[index] = diagElem;
    }

    @NotNull
    @Override
    public Iterator<DialogueElement> iterator() {
        return new FixedDialogueSequenceIterator();
    }

    private class FixedDialogueSequenceIterator implements Iterator<DialogueElement> {

        private int i = 0;

        @Override
        public boolean hasNext() {
            return i < size;
        }

        @Override
        public DialogueElement next() {
            return sequence[i++];
        }
    }
}
