package gdx.kapotopia.Helpers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;

import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;

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

    /**
     *
     * @param current the current Screen
     * @param game The game
     * @param destroyOnChange if set to true, the current screen is destroyed and ressources disposed when the user type on the BACK button
     */
    public StandardInputAdapter(Screen current, Kapotopia game, boolean destroyOnChange) {
        super();
        this.current = current;
        this.game = game;
        this.destroyOnChange = destroyOnChange;
    }

    /**
     * @param keycode
     * @return true if an action has been activated, else otherwise
     */
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            if(destroyOnChange) {
                game.destroyScreen(current);
            }
            game.getReturnButtonManager().goBack();
            return true;
        }
        return false;
    }
}
