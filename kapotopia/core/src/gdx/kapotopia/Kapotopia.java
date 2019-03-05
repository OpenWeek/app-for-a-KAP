package gdx.kapotopia;

import com.badlogic.gdx.utils.viewport.FitViewport;

import gdx.kapotopia.Screens.MainMenu;

public class Kapotopia extends com.badlogic.gdx.Game {

	public FitViewport viewport;

	public static final float GAME_WIDTH = 1080;
	public static final int GAME_HEIGHT = 1920;

	@Override
	public void create () {
		viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT);
		setScreen(new MainMenu(this));
	}
	@Override
	public void dispose (){
	}
}
