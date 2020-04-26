package gdx.kapotopia.Helpers.Builders;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.Iterator;

import gdx.kapotopia.DialogsScreen.DialogueElement;
import gdx.kapotopia.DialogsScreen.FixedDialogueSequence;
import gdx.kapotopia.Kapotopia;

public class FixedDialogSeqBuilder {

    /* STATIC METHODS */

    /**
     * Build the FixedDialogueSequence given it's parameters
     * Not every parameter must be not null, if no labels are provided, empty labels will be created.
     * However if no images are provided, this method will return null.
     *
     * There is a priority on the parameters which is the following :
     * imagesBigList > images > imagesTexturePaths
     * labelsBigList > labels
     *
     * imagesBigList is preferred over images which is again preferred over imagesTexturesPaths
     * labelsBigList is preferred over labels
     *
     * @param stage the stage to which the images/labels must be added
     * @param imagesBigList a matrix of Images. Each row is a new SequenceElement, every Image for each row are displayed at the same time
     * @param images a list of images, each image will be displayed in a new SequenceElement
     * @param imagesTexturePaths a list of imagesTexturesPaths, which will be transformed into a list of Images
     * @param labels a list of Labels that will be displayed for each new SequenceElement
     * @param labelsBigList a matrix of Labels. Each row is a new SequenceElement, every Label for each row are displayed at the same time
     * @return a new FixedDialogueSequence built with these parameters
     */
    public static FixedDialogueSequence buildSequence(Kapotopia game, Stage stage,
                                                      Image[][] imagesBigList, Image[] images,
                                                      String[] imagesTexturePaths, Label[] labels,
                                                      Label[][] labelsBigList) {
        FixedDialogueSequence newSeq;
        boolean isImagesBigList = imagesBigList != null;
        boolean isImages = images != null;
        boolean isImagesTexturePaths = imagesTexturePaths != null;
        boolean isLabels = labels != null;
        boolean isBigLabelsList = labelsBigList != null;

        /* Note: for simplicity, the FixedDialogueSequence constructor used is
        *               FixedDialogueSequence(Image[][], Label[][])
        * The used parameters are transformed by static methods to fit these constraints
        */
        if(isImagesBigList) {
            if(isBigLabelsList) {
                newSeq = new FixedDialogueSequence(imagesBigList, labelsBigList);
            } else if (isLabels) {
                newSeq = new FixedDialogueSequence(imagesBigList, LabelBuilder.convert(labels));
            }else {
                newSeq = new FixedDialogueSequence(imagesBigList, LabelBuilder.createEmptyMatrix(game, imagesBigList.length));
            }
        } else if(isImages) {
            if(isBigLabelsList) {
                newSeq = new FixedDialogueSequence(ImageBuilder.convert(images), labelsBigList);
            } else if (isLabels) {
                newSeq = new FixedDialogueSequence(ImageBuilder.convert(images), LabelBuilder.convert(labels));
            } else {
                newSeq = new FixedDialogueSequence(ImageBuilder.convert(images), LabelBuilder.createEmptyMatrix(game, images.length));
            }
        } else if(isImagesTexturePaths) {
            if(isBigLabelsList) {
                newSeq = new FixedDialogueSequence(ImageBuilder.convert(game, imagesTexturePaths), labelsBigList);
            } else if (isLabels) {
                newSeq = new FixedDialogueSequence(ImageBuilder.convert(game, imagesTexturePaths), LabelBuilder.convert(labels));
            } else {
                newSeq = new FixedDialogueSequence(ImageBuilder.convert(game, imagesTexturePaths), LabelBuilder.createEmptyMatrix(game, imagesTexturePaths.length));
            }
        } else {
            return null;
        }
        configSequence(newSeq, stage);
        return newSeq;
    }

    /**
     * Configure the images and labels of the given sequence to fit the given viewport and are added to the stage
     * @param seq the FixedDialogueSequence to be configured
     * @param stage the stage to which the images and labels will be added
     */
    private static void configSequence(FixedDialogueSequence seq, Stage stage) {
        Iterator<DialogueElement> iterator = seq.iterator();
        while (iterator.hasNext()) {
            DialogueElement element = iterator.next();
            // Images
            Image[] imgList = element.getImageList();
            for (Image img : imgList) {
                img.setVisible(false);
                stage.addActor(img);
            }
            // Labels
            Label[] labList = element.getLabelList();
            for (Label lab : labList) {
                lab.setVisible(false);
                stage.addActor(lab);
            }
        }
        setVisibility(seq.getFirstElement(), true);
    }

    /**
     * Set the visibility of the elements composing the DialogueElement (formaly the images and labels)
     * @param dialogueElement the DialogueElement
     * @param isVisible the visibility to set
     */
    public static void setVisibility(DialogueElement dialogueElement, boolean isVisible) {
        Image[] imgList = dialogueElement.getImageList();
        Label[] labList = dialogueElement.getLabelList();

        for (Image img : imgList) {
            img.setVisible(isVisible);
        }
        for (Label lab : labList) {
            lab.setVisible(isVisible);
        }
    }
}
