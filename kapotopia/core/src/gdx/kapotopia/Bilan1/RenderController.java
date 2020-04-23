package gdx.kapotopia.Bilan1;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Queue;

import java.util.Iterator;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Screens.BilanG1;

public class RenderController {

    /* Basic Variables */

    private Kapotopia game;
    private BilanG1 bilan;

    private OrthographicCamera camera;

    private SpriteBatch batch;

    private boolean showStiSprites;
    private Queue<Sprite> stiSprites;

    // References to objects to be rendered
    private Texture background;
    private Texture bubble;
    private TextureRegion mireilleUni;

    public RenderController(Kapotopia game, BilanG1 bilan) {
        this.game = game;
        this.bilan = bilan;

        this.camera = new OrthographicCamera(game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        game.viewport.setCamera(camera);

        this.batch = new SpriteBatch();

//        final float wWidth = game.viewport.getWorldWidth();
//        final float wHeight = game.viewport.getWorldHeight();

        // Defining visual elements

        background = game.ass.get(AssetDescriptors.B1_BACK);
        bubble = game.ass.get(AssetDescriptors.BUBBLE_EXPL);

        final Texture mireille = game.ass.get(AssetDescriptors.MI_UNI);
        this.mireilleUni = new TextureRegion(mireille, 85, 105, 595, mireille.getHeight() - 200); // We leave out the blanks
        showStiSprites = false;

        stiSprites = new Queue<Sprite>();
    }

    public void enqueueStiSprite(Sprite sprite) {
        stiSprites.addLast(sprite);
    }

    public void dequeueStiSprite() {
        stiSprites.removeFirst();
        prepareFirstStiSprite();
    }

    public void setShowStiSprites() {
        showStiSprites = true;
        prepareFirstStiSprite();
    }

    private void prepareFirstStiSprite() {
        final Sprite sti = stiSprites.first();
        sti.setScale(sti.getScaleX() + 0.05f);
    }

    public void update() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(background,0,0);
        batch.draw(bubble,0,0);
        batch.draw(mireilleUni, 0, 0, 0, 0, mireilleUni.getRegionWidth(), mireilleUni.getRegionHeight(), 0.45f, 0.45f, 0);

        if (showStiSprites) {
            Iterator iter = stiSprites.iterator();
            if (iter.hasNext()) {
                iter.next();
                while (iter.hasNext()) {
                    Sprite sti = (Sprite) iter.next();
                    sti.draw(batch);
                }
                // We draw the first sti so that it appear on top of the others
                stiSprites.first().draw(batch);
            }
        }

        batch.end();
    }
}
