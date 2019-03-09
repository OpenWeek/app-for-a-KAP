package gdx.kapotopia.Game3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public class EventHandler extends InputAdapter {

    private Core core;
    public EventHandler(Core core){
        this.core = core;
    }

    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        core.touchHandler(x, Gdx.graphics.getHeight()-y);
        return true;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        return false; // return true to indicate the event was handled
    }
}
