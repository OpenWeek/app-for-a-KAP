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
			//The function corresponding to the different screens need to be called every time there is a change in screen otherwise the buttons don't work.
			//In other words, doing if(screen == null) screen = new Screen(this) doesn't work
			//If someone find a more efficient way than calling the function corresponding to a screen every time there is a change in screen, it would be very welcome!
			case GAME1:
				game1 = new Game1(this);
				setScreen(game1);
				break;
			case GAME2:
				game2 = new Game2(this);
				setScreen(game2);
				break;
			case GAME3:
				game3 = new Game3(this);
				setScreen(game3);
				break;
			case WORLD1:
				world1 = new World1(this);
				setScreen(world1);
				break;
			case WORLD2:
				world2 = new World2(this);
				setScreen(world2);
				break;
			case WORLD3:
				world3 = new World3(this);
				setScreen(world3);
				break;
			case WORLD4:
				world4 = new World4(this);
				setScreen(world4);
				break;
			case MAINMENU:
				Gdx.app.log(TAG,"Entering case MAINMENU");
				mainMenu = new MainMenu(this);
				setScreen(mainMenu);
				Gdx.app.log(TAG,"case MAINMENU completed");
				break;
			case MOCKUPG1:
				mockupG1 = new mockupG1(this);
				setScreen(mockupG1);
				break;
		}
	}
}
