package gdx.kapotopia;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;

import gdx.kapotopia.Screens.MainMenu;

public class StandardInputAdapter extends InputAdapter {

    private Screen current;
    private Kapotopia game;

    public StandardInputAdapter(Screen current, Kapotopia game) {
        super();
        this.current = current;
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            current.dispose();
            game.changeScreen(ScreenType.MAINMENU);
            return true;
        }
        return false;
    }
}
