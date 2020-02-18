package gdx.kapotopia.Game1;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Kapotopia;

public class MireilleJojo {
    private Kapotopia game;

    private final float wW;
    private final float wH;

    private float stateTime;

    private Sprite mireilleJojoFace;
    private Sprite mireilleJojoPose;
    private Sprite mireilleJojoKanji;
    private SpriteBatch batch;

    public MireilleJojo(Kapotopia game) {
        this.game = game;
        this.wW = game.viewport.getWorldWidth();
        this.wH = game.viewport.getWorldHeight();
        stateTime = 0;
        this.mireilleJojoFace = new Sprite(AssetsManager.getInstance().getTextureByPath("MireilleImages/MireilleJojo.png"));
        this.mireilleJojoPose = new Sprite(AssetsManager.getInstance().getTextureByPath("MireilleImages/MireilleJojoPose.png"));
        this.mireilleJojoKanji = new Sprite(AssetsManager.getInstance().getTextureByPath("MireilleImages/MireilleJojoPoseKanji.png"));
        this.batch = new SpriteBatch();
    }

    public void draw(float delta) {
        stateTime += delta;

        batch.begin();
        if (stateTime < 4) {
            batch.draw(mireilleJojoFace, (wW - mireilleJojoFace.getWidth()) / 2, (wH - mireilleJojoFace.getHeight()) / 2, 0, 0,
                    mireilleJojoFace.getWidth(), mireilleJojoFace.getHeight(),
                    1, 1, 0);
        } else if (stateTime < 6) {

            batch.draw(mireilleJojoPose, (wW - mireilleJojoFace.getWidth()) / 2, (wH - mireilleJojoFace.getHeight()) / 2, 0, 0,
                    mireilleJojoFace.getWidth(), mireilleJojoFace.getHeight(),
                    1, 1, 0);
        } else {
            batch.draw(mireilleJojoKanji, (wW - mireilleJojoFace.getWidth()) / 2, (wH - mireilleJojoFace.getHeight()) / 2, 0, 0,
                    mireilleJojoFace.getWidth(), mireilleJojoFace.getHeight(),
                    1, 1, 0);
        }

        batch.end();
    }

    public void upProjMatrBatch(Matrix4 projection) {
        this.batch.setProjectionMatrix(projection);
    }
}
