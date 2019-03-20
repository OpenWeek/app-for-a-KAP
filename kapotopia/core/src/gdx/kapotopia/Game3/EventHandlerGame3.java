package gdx.kapotopia.Game3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public class EventHandlerGame3 extends InputAdapter {

    private Core core;

    public EventHandlerGame3(Core core) {
        this.core = core;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        core.touchHandler(x, Gdx.graphics.getHeight() - y);
        return true;
    }
}
