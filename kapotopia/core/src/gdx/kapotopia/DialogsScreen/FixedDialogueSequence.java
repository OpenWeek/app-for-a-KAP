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
            sequence[i] = new DialogueElement(i, cinematicListImages[i], labelList[i]);
        }
    }

    /**
     * Constructor of a fixed sequence with a list of Image paths and a list of labels
     * Note: cinnematicListPaths and labelList must have the same size,
     * @param cinematicListPaths a list of Image Paths, or more precisely textures that will be loaded
     *                           with the assetManager
     * @param labelList a list of labels
     */
    public FixedDialogueSequence(String[] cinematicListPaths, Label[] labelList) {
        if (cinematicListPaths.length != labelList.length) throw new AssertionError();
        this.size = cinematicListPaths.length;
        this.sequence = new DialogueElement[size];
        for (int i=0; i < size; i++) {
            final Image img = new Image(AssetsManager.getInstance().getTextureByPath(cinematicListPaths[i]));
            sequence[i] = new DialogueElement(i, img, labelList[i]);
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
