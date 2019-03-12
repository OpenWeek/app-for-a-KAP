package gdx.kapotopia.Game3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;

import java.util.Scanner;

public class EventHandler extends InputAdapter {

    private Core core;
    private Screen parent;
    public EventHandler(Core core, Screen parent){
        this.core = core;
        this.parent = parent;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            parent.dispose();
        }
        return false;
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
