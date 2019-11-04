package gdx.kapotopia.Game1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import gdx.kapotopia.AssetsManaging.AssetsManager;

import static gdx.kapotopia.Kapotopia.SCALLING_FACTOR_ENTITY;

public abstract class EntityAbstract extends Actor implements Entity {
    private Circle collisionBounds;
    protected TextureRegion texture;
    private float originalX, originalY;

    void builderHelper(Texture texture, float X, float Y) {
        if(texture == null) {
            this.texture = new TextureRegion(AssetsManager.getInstance().getTextureByPath("virus1.png"));
        }else{
            this.texture = new TextureRegion(texture);
        }
        this.setHeight((float) this.texture.getRegionHeight() / SCALLING_FACTOR_ENTITY);
        this.setWidth((float) this.texture.getRegionWidth() / SCALLING_FACTOR_ENTITY);
        this.setX(X);
        this.setY(Y);
        this.originalX = X;
        this.originalY = Y;
        this.collisionBounds = buildCollisionBounds(X,Y,this.getWidth(),this.getHeight());
    }

    /**
     * Build the collision screenBounds in the form of a Circle
     * @param x the X position of the entity
     * @param y the Y position of the entity
     * @param width the width of the entity
     * @param height the height of the entity
     * @return a Circle with the right dimensions
     */
    private Circle buildCollisionBounds(float x, float y, float width, float height) {
        final float cx = y/2, cy = x/2;
        final float r = Math.max(width,height) / 3.5f;
        return new Circle(cx,cy,r);
    }

    /**
     * Return the collision circle
     * @return a circle
     */
    @Override
    public Circle getCollisionBounds() {
        return this.collisionBounds;
    }

    /**
     * affects the size of the collision circle.
     *
     * @param radius
     */
    @Override
    public void setRadius(float radius) {
        this.collisionBounds.setRadius(radius);
    }

    @Override
    public void updateCollision(float cx, float cy) {
        this.collisionBounds.setX(cx);
        this.collisionBounds.setY(cy);
    }

    /**
     * return true if this entity enter in collision
     * with the one given in parameters
     *
     * @param otherEntity
     * @return "true" if there is a collision, else "false".
     */
    @Override
    public boolean isCollision(Entity otherEntity) {
        Circle otherEntityCollision = otherEntity.getCollisionBounds();
        return isCollisionWithCircle(otherEntityCollision);
    }

    /**
     * Decide if the point with coordonates x and y is contained inside mireille screenBounds
     * @param x
     * @param y
     * @return true if there is collision, else false
     */
    private boolean isCollisionWithPoint(float x, float y) {
        final Circle collision = this.collisionBounds;
        final float d2 = (x-collision.x)*(x-collision.x) + (y-collision.y)*(y-collision.y);
        return !(d2 > collision.radius * collision.radius);
    }

    /**
     * Decide if the circle touch mireille screenBounds
     * @param C2
     * @return true if there is collision, else false
     */
    private boolean isCollisionWithCircle(Circle C2) {
        final Circle C1 = this.collisionBounds;
        final float d2 = (C1.x-C2.x)*(C1.x-C2.x) + (C1.y-C2.y)*(C1.y-C2.y);
        return !(d2 > (C1.radius + C2.radius) * (C1.radius + C2.radius));
    }

    @Override
    public void resetPosition() {
        this.setX(originalX);
        this.setY(originalY);
    }

    @Override
    public void hide() {
        final int p = -100;
        this.setX(p);
        this.setY(p);
        this.collisionBounds.setX(p);
        this.collisionBounds.setY(p);
    }
}
