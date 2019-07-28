package gdx.kapotopia;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;

import gdx.kapotopia.Screens.MainMenu;

public class StandardInputAdapter extends InputAdapter {

    private Screen current;
    private Kapotopia game;
    private boolean destroyOnChange;

    public StandardInputAdapter(Screen current, Kapotopia game) {
        super();
        this.current = current;
        this.game = game;
        this.destroyOnChange = false;
    }

    public StandardInputAdapter(Screen current, Kapotopia game, boolean destroyOnChange) {
        super();
        this.current = current;
        this.game = game;
        this.destroyOnChange = destroyOnChange;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            if(destroyOnChange) {
                game.destroyScreen(current);
            }
            game.changeScreen(ScreenType.MAINMENU);
            return true;
        }
        return false;
    }
}
