package gdx.kapotopia;

import com.badlogic.gdx.utils.viewport.FitViewport;

import gdx.kapotopia.Screens.MainMenu;

public class Kapotopia extends com.badlogic.gdx.Game {

	public FitViewport viewport;

	private static final float GAME_WIDTH = 1080;
	private static final int GAME_HEIGHT = 1920;

	public final static int SCALLING_FACTOR_ENTITY = 2;


	@Override
	public void create () {
		viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT);
		setScreen(new MainMenu(this));
	}

	@Override
	public void dispose (){
	}
}
