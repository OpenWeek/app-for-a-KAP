package gdx.kapotopia.Bilan1;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import java.util.HashSet;
import java.util.List;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.Fonts.UseFont;
import gdx.kapotopia.Game1.VirusContainer;
import gdx.kapotopia.GameConfig;
import gdx.kapotopia.Helpers.Alignement;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Screens.BilanG1;

public class BilanController {

    private Kapotopia game;
    private BilanG1 bilan;

    public BilanController(Kapotopia game, BilanG1 bilan) {
        this.game = game;
        this.bilan = bilan;
    }

    /**
     * Populate List of names and descriptions
     * @param stage
     * @param names
     * @param descriptions
     * @param missedIsts
     */
    public void init(Stage stage, List<Label> names, List<Label> descriptions, HashSet<VirusContainer> missedIsts) {

        final float wWidth = GameConfig.GAME_WIDTH;
        final float wHeight = GameConfig.GAME_HEIGHT;
        TextButton.TextButtonStyle style = FontHelper.getStyleFont(UseFont.CLASSIC_SANS_NORMAL_BLACK);

        float initStiImgX = - (wWidth / 5);
        for (VirusContainer ist : missedIsts) {

            // Name
            final float yName = wHeight * 0.75f; // 960
            final Label ln = new LabelBuilder(ist.getName()).withStyle(style).isVisible(false)
                    .withAlignment(Alignement.CENTER).withY(yName)
                    .build();

            // Description
            final float xDescr = wWidth / 30f;
            final float yDescr = yName - (wHeight * 0.35f); // 426.66
            final float wDescr = wWidth - 2 * (wWidth / 30f);
            final float hDescr = wHeight * 0.35f; //426.66
            final Label ld = new LabelBuilder(ist.getDescription()).withStyle(style).isVisible(false)
                    .withPosition(xDescr, yDescr).withTextAlignement(Align.left).withWidth(wDescr)
                    .withHeight(hDescr).isWrapped(true).build();

            // Image
            final float xImg = initStiImgX;
            final float yImg = yName - (wHeight * 0.01f);
            final float scalling_factor = wHeight * 0.0002f;
            final Sprite stiImg = new Sprite(AssetsManager.getInstance().getTextureByPath(ist.getTexturePath()));
            stiImg.setPosition(xImg, yImg);
            stiImg.setScale(scalling_factor);
            initStiImgX += stiImg.getWidth() / 8;
            bilan.getRenderController().enqueueStiSprite(stiImg);
            // General
            names.add(ln);
            descriptions.add(ld);
            stage.addActor(ln);
            stage.addActor(ld);
        }
    }
}
