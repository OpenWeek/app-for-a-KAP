package gdx.kapotopia.Bilan1;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import java.util.HashSet;
import java.util.List;

import gdx.kapotopia.AssetsManaging.FontHelper;
import gdx.kapotopia.AssetsManaging.UseFont;
import gdx.kapotopia.Game1.VirusContainer;
import gdx.kapotopia.GameConfig;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;

public class BilanController {

    public BilanController() {

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

        for (VirusContainer ist : missedIsts) {
            // Name
            final float xNext = wWidth / 3f;
            final float yNext = wHeight / 1.5f;
            final Label ln = new LabelBuilder(ist.getName()).withStyle(style).isVisible(false).withPosition(xNext, yNext).build();

            // Description
            final float xDescr = wWidth / 30f;
            final float yDescr = yNext - (wHeight / 3f);
            final float wDescr = wWidth - 2 * (wWidth / 30f);
            final float hDescr = wHeight / 3f;
            final Label ld = new LabelBuilder(ist.getDescription()).withStyle(style).isVisible(false)
                    .withPosition(xDescr, yDescr).withTextAlignement(Align.left).withWidth(wDescr)
                    .withHeight(hDescr).isWrapped(true).build();

            // General
            names.add(ln);
            descriptions.add(ld);
            stage.addActor(ln);
            stage.addActor(ld);
        }
    }
}
