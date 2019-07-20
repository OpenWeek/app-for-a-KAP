package gdx.kapotopia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.FitViewport;

import gdx.kapotopia.Screens.Game1;
import gdx.kapotopia.Screens.Game2;
import gdx.kapotopia.Screens.Game3;
import gdx.kapotopia.Screens.MainMenu;
import gdx.kapotopia.Screens.World1;
import gdx.kapotopia.Screens.World2;
import gdx.kapotopia.Screens.World3;
import gdx.kapotopia.Screens.World4;
import gdx.kapotopia.Screens.mockupG1;

public class Kapotopia extends com.badlogic.gdx.Game {

	public FitViewport viewport;

	private static final float GAME_WIDTH = 1080;
	private static final int GAME_HEIGHT = 1920;

	public final static float SCALLING_FACTOR_ENTITY = 5.3f;

	private static final String TAG = "Class Kapotopia";

	// Screens
	private Game1 game1;
	private Game2 game2;
	private Game3 game3;
	private MainMenu mainMenu;
	private mockupG1 mockupG1;
	private World1 world1;
	private World2 world2;
	private World3 world3;
	private World4 world4;

	@Override
	public void create () {
		viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT);
		changeScreen(ScreenType.MAINMENU);
	}

	@Override
	public void dispose (){
		AssetsManager.getInstance().disposeAllResources();
	}

	public void changeScreen(ScreenType TYPE) {

		Gdx.app.log(TAG,"Entering changeScreen function");

		switch (TYPE) {
			case GAME1:
				if (game1 == null) game1 = new Game1(this);
				setScreen(game1);
				break;
			case GAME2:
				if (game2 == null) game2 = new Game2(this);
				setScreen(game2);
				break;
			case GAME3:
				if (game3 == null) game3 = new Game3(this);
				setScreen(game3);
				break;
			case WORLD1:
				if (world1 == null) world1 = new World1(this);
				setScreen(world1);
				break;
			case WORLD2:
				if (world2 == null) world2 = new World2(this);
				setScreen(world2);
				break;
			case WORLD3:
				if (world3 == null) world3 = new World3(this);
				setScreen(world3);
				break;
			case WORLD4:
				if (world4 == null) world4 = new World4(this);
				setScreen(world4);
				break;
			case MAINMENU:
				Gdx.app.log(TAG,"Entering case MAINMENU");
				if (mainMenu == null) mainMenu = new MainMenu(this);
				setScreen(mainMenu);
				Gdx.app.log(TAG,"case MAINMENU completed");
				break;
			case MOCKUPG1:
				if (mockupG1 == null) mockupG1 = new mockupG1(this);
				setScreen(mockupG1);
				break;
		}
	}
}
