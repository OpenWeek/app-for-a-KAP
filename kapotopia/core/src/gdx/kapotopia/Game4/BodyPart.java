package gdx.kapotopia.Game4;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class BodyPart {
    private float x;
    private float y;
    private ImageButton ib;

    public BodyPart(float x, float y, int boardSize) {
        this.x  = x % (boardSize - 2);
        if (this.x<0) {
            this.x += boardSize - 2;
        }
        this.y  = y % boardSize;
        if (this.y<0) {
            this.y += boardSize;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public ImageButton getIb() {
        return ib;
    }

    public void setIb(ImageButton ib) {
        this.ib = ib;
    }
}
