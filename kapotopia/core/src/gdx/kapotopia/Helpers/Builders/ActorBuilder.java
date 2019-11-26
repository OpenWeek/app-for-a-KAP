package gdx.kapotopia.Helpers.Builders;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;

import java.util.ArrayList;

public class ActorBuilder {
    private ArrayList<EventListener> eventListeners;
    private ArrayList<EventListener> captureListeners;
    private float x, y;
    private float bx, by, bw, bh;
    private float width, height;
    private boolean visible;

    public ActorBuilder() {
        this.eventListeners = new ArrayList<EventListener>();
        this.captureListeners = new ArrayList<EventListener>();
        this.x = 0;
        this.y = 0;
        this.bx = -1;
        this.by = -1;
        this.bw = -1;
        this.bh = -1;
        this.width = -1;
        this.height = -1;
        this.visible = true;
    }

    /**
     * Add a listener to this actor
     * @param listener the listener
     * @return this builderObject
     */
    public ActorBuilder withListener(EventListener listener) {
        this.eventListeners.add(listener);
        return this;
    }

    public ActorBuilder withCaptureListener(EventListener event) {
        this.captureListeners.add(event);
        return this;
    }

    public ActorBuilder withPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Configure the bounds
     * @param boundX the boundary origin position x
     * @param boundY the boundary position y
     * @param boundWidth the width of the boundary, must be positive or 0
     * @param boundHeight the height of the boundary, must be positive or 0
     * @return this builderObject
     */
    public ActorBuilder withBounds(float boundX, float boundY, float boundWidth, float boundHeight) {
        this.bx = boundX;
        this.by = boundY;
        this.bw = boundWidth;
        this.bh = boundHeight;
        return this;
    }

    /**
     * Configure the width
     * @param width, must be positive or 0
     * @return this builderObject
     */
    public ActorBuilder withWidth(float width) {
        this.width = width;
        return this;
    }

    /**
     * Configure the height
     * @param height, must be positive or 0
     * @return this builderObject
     */
    public ActorBuilder withHeight(float height) {
        this.height = height;
        return this;
    }

    public ActorBuilder isVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public Actor build() {
        Actor actor = new Actor();
        actor.setPosition(x, y);
        // It shouldn't be possible to have a negative height or weight
        if (bw >= 0 && bh >= 0) {
            actor.setBounds(bx, by, bw, by);
        }
        if (width >= 0) {
            actor.setWidth(width);
        }
        if (height >= 0) {
            actor.setHeight(height);
        }
        actor.setVisible(visible);

        for (EventListener listener : this.eventListeners) {
            actor.addListener(listener);
        }
        for (EventListener listener : this.captureListeners) {
            actor.addCaptureListener(listener);
        }
        return actor;
    }
}
