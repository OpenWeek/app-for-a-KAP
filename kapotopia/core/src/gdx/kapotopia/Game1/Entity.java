package gdx.kapotopia.Game1;

import com.badlogic.gdx.math.Circle;

/**
 * Represents an entity in the game
 */
public interface Entity {

    /**
     * Return the collision circle
     * @return a circle
     */
    Circle getCollisionBounds();

    /**
     * moves the artifact according to its speed on the 2 axes and
     * according to the elapsed delta time.
     *
     * @param delta
     */
    void act(float delta);

    /**
     * affects the size of the collision circle.
     *
     * @param radius
     */
    void setRadius(float radius);

    void updateCollision(float cx, float cy);

    /**
     * return true if this entity enter in collision
     * with the one given in parameters
     *
     * @param otherEntity
     * @return "true" if there is a collision, else "false".
     */
    boolean isCollision(Entity otherEntity);

    /**
     * reset the entity to its original position
     */
    void resetPosition();

    void hide();
}
