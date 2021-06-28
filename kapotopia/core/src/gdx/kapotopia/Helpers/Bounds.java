package gdx.kapotopia.Helpers;

/**
 * Wrapper class for bounds used to build buttons, labels, collisionsBounds, etc..
 * Is defined by a coordonnates (x,y) and width/height, with possible extra horizontal and vertical Padding (default 0)
 */
public class Bounds {
    private float x;
    private float y;
    private float width;
    private float height;
    private float horPad; // Horizontal Padding
    private float verPad; // Vertical Padding
    private float leftPad;
    private float rightPad;
    private float topPad;
    private float bottomPad;

    public Bounds(float x, float y, float width, float height) {
        this(x, y, width, height, 0, 0, 0, 0 ,0 ,0);
    }

    public Bounds(float x, float y, float width, float height, float horPad, float verPad) {
        this(x, y, width, height, horPad, verPad, 0, 0 ,0 ,0);
    }

    public Bounds(float x, float y, float width, float height, float horPad, float verPad,
                  float leftPad, float rightPad, float topPad, float bottomPad) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.horPad = horPad;
        this.verPad = verPad;
        this.leftPad = leftPad;
        this.rightPad = rightPad;
        this.topPad = topPad;
        this.bottomPad = bottomPad;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getHorPad() {
        return horPad;
    }

    public void setHorPad(float horPad) {
        this.horPad = horPad;
    }

    public float getVerPad() {
        return verPad;
    }

    public void setVerPad(float verPad) {
        this.verPad = verPad;
    }

    public float getLeftPad() {
        return leftPad;
    }

    public void setLeftPad(float leftPad) {
        this.leftPad = leftPad;
    }

    public float getRightPad() {
        return rightPad;
    }

    public void setRightPad(float rightPad) {
        this.rightPad = rightPad;
    }

    public float getTopPad() {
        return topPad;
    }

    public void setTopPad(float topPad) {
        this.topPad = topPad;
    }

    public float getBottomPad() {
        return bottomPad;
    }

    public void setBottomPad(float bottomPad) {
        this.bottomPad = bottomPad;
    }
}
