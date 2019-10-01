package gdx.kapotopia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.FitViewport;

import gdx.kapotopia.Screens.BilanG1;
import gdx.kapotopia.Screens.Game1;
import gdx.kapotopia.Screens.Game2;
import gdx.kapotopia.Screens.Game3;
import gdx.kapotopia.Screens.MainMenu;
import gdx.kapotopia.Screens.World1;
import gdx.kapotopia.Screens.World2;
import gdx.kapotopia.Screens.World3;
import gdx.kapotopia.Screens.World4;
import gdx.kapotopia.Screens.mockupG1;
import gdx.kapotopia.Screens.mockupG2;

public class Kapotopia extends com.badlogic.gdx.Game {

	public FitViewport viewport;

	private static final float GAME_WIDTH = 1080;
	private static final int GAME_HEIGHT = 1920;

	public final static float SCALLING_FACTOR_ENTITY = 5.3f;

	private static final String TAG = "Class Kapotopia";

	// TODO changer VERSION_NAME ET VERSION_CODE à chaque fois que l'on update le jeu, pas trouvé de moyen pour les liés automatiquement au gradle build d'android
	public static final String VERSION_NAME = "Alpha-0.2.6";
	public static final int VERSION_CODE = 9;

	// Screens
	private Game1 game1;
	private Game2 game2;
	private Game3 game3;
	private MainMenu mainMenu;
	private mockupG1 mockupG1;
	private mockupG2 mockupG2;
	private BilanG1 bilanG1;
	private World1 world1;
	private World2 world2;
	private World3 world3;
	private World4 world4;

	// The value Gateway
	private ValueGateway gate;

	@Override
	public void create () {
		viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT);
		//We activate the BACK button for the whole app
		Gdx.input.setCatchBackKey(true);
		this.gate = new ValueGateway();
		changeScreen(ScreenType.MAINMENU);
	}

	@Override
	public void dispose () {
		Gdx.app.log(TAG, "Disposing every game resources");
		AssetsManager.getInstance().disposeAllResources();
	}

	public ValueGateway getTheValueGateway() {
		return this.gate;
	}

	/**
	 * Change the current screen, creating a new screen if needed, otherwise creating a new screen object
	 * @param TYPE the enum SCREENTYPE that specify which screen to change to
	 * @return true if the operation succeeded, false otherwise
	 */
	public boolean changeScreen(ScreenType TYPE) {
		Gdx.app.log(TAG,"Entering changeScreen function");
		return selectScreen(ScreenAction.CHANGE, TYPE);
	}

	/**
	 * Destroy the specified screen and dispose all it's ressources
	 * @param TYPE the enum SCREENTYPE that specify which screen to destroy
	 * @return true if the operation succeeded, false otherwise
	 */
	public boolean destroyScreen(ScreenType TYPE) {
		Gdx.app.log(TAG, "Entering destroyScreen function");
		return selectScreen(ScreenAction.DESTROY, TYPE);
	}

	/**
	 * Destroy the specified screen and dispose it's ressources
	 * @param sc The screen to destroy
	 * @return true if the operation succeeded, false otherwise
	 */
	boolean destroyScreen(Screen sc) {
		if (sc == game1) {
			return destroyScreen(ScreenType.GAME1);
		} else if(sc == game2) {
			return destroyScreen(ScreenType.GAME2);
		} else if(sc == game3) {
			return destroyScreen(ScreenType.GAME3);
		} else if(sc == mainMenu) {
			return destroyScreen(ScreenType.MAINMENU);
		} else if(sc == mockupG1) {
			return destroyScreen(ScreenType.MOCKUPG1);
		} else if(sc == mockupG2) {
			return destroyScreen(ScreenType.MOCKUPG2);
		} else if(sc == bilanG1) {
			return destroyScreen(ScreenType.BILANG1);
		} else if(sc == world1) {
			return destroyScreen(ScreenType.WORLD1);
		} else if(sc == world2) {
			return destroyScreen(ScreenType.WORLD2);
		} else if(sc == world3) {
			return destroyScreen(ScreenType.WORLD3);
		} else if(sc == world4) {
			return destroyScreen(ScreenType.WORLD4);
		}

		return false;
	}

	/**
	 * Select the specified screen and apply the specified method
	 * @param ACTION the action to apply to the screen, specified in ScreenAction enum
	 * @param TYPE the specified screen to apply the action
	 * @return true if the operation succeeded, false otherwise
	 */
	private boolean selectScreen(ScreenAction ACTION, ScreenType TYPE) {
		boolean succeeded = false;
		switch (TYPE) {
			case GAME1:
				switch (ACTION) {
					case CHANGE:
						if (game1 == null) game1 = new Game1(this);
						setScreen(game1);
						succeeded = true;
						break;
					case DESTROY:
						if (game1 != null) {
							game1.dispose();
							game1 = null;
							succeeded = true;
						}
						break;
				}
				break;
			case GAME2:
				switch (ACTION) {
					case CHANGE:
						if (game2 == null) game2 = new Game2(this);
						setScreen(game2);
						succeeded = true;
						break;
					case DESTROY:
						if (game2 != null) {
							game2.dispose();
							game2 = null;
							succeeded = true;
						}
						break;
				}
				break;
			case GAME3:
				switch (ACTION) {
					case CHANGE:
						if (game3 == null) game3 = new Game3(this);
						setScreen(game3);
						succeeded = true;
						break;
					case DESTROY:
						if (game3 != null) {
							game3.dispose();
							game3 = null;
							succeeded = true;
						}
						break;
				}
				break;
			case WORLD1:
				switch (ACTION) {
					case CHANGE:
						if (world1 == null) world1 = new World1(this);
						setScreen(world1);
						succeeded = true;
						break;
					case DESTROY:
						if (world1 != null) {
							world1.dispose();
							world1 = null;
							succeeded = true;
						}
						break;
				}
				break;
			case WORLD2:
				switch (ACTION) {
					case CHANGE:
						if (world2 == null) world2 = new World2(this);
						setScreen(world2);
						succeeded = true;
						break;
					case DESTROY:
						if (world2 != null) {
							world2.dispose();
							world2 = null;
							succeeded = true;
						}
						break;
				}
				break;
			case WORLD3:
				switch (ACTION) {
					case CHANGE:
						if (world3 == null) world3 = new World3(this);
						setScreen(world3);
						succeeded = true;
						break;
					case DESTROY:
						if (world3 != null) {
							world3.dispose();
							world3 = null;
							succeeded = true;
						}
						break;
				}
				break;
			case WORLD4:
				switch (ACTION) {
					case CHANGE:
						if (world4 == null) world4 = new World4(this);
						setScreen(world4);
						succeeded = true;
						break;
					case DESTROY:
						if (world4 != null) {
							world4.dispose();
							world4 = null;
							succeeded = true;
						}
						break;
				}
				break;
			case MAINMENU:
				switch (ACTION) {
					case CHANGE:
						if (mainMenu == null) mainMenu = new MainMenu(this);
						setScreen(mainMenu);
						succeeded = true;
						break;
					case DESTROY:
						if (mainMenu != null) {
							mainMenu.dispose();
							mainMenu = null;
							succeeded = true;
						}
						break;
				}
				break;
			case MOCKUPG1:
				switch (ACTION) {
					case CHANGE:
						if (mockupG1 == null) mockupG1 = new mockupG1(this);
						setScreen(mockupG1);
						succeeded = true;
						break;
					case DESTROY:
						if (mockupG1 != null) {
							mockupG1.dispose();
							mockupG1 = null;
							succeeded = true;
						}
						break;
				}
				break;
			case MOCKUPG2:
				switch (ACTION) {
					case CHANGE:
						if (mockupG2 == null) mockupG2 = new mockupG2(this);
						setScreen(mockupG2);
						succeeded = true;
						break;
					case DESTROY:
						if (mockupG2 != null) {
							mockupG2.dispose();
							mockupG2 = null;
							succeeded = true;
						}
						break;
				}
				break;
			case BILANG1:
				switch (ACTION) {
					case CHANGE:
						if (bilanG1 == null) bilanG1 = new BilanG1(this);
						setScreen(bilanG1);
						succeeded = true;
						break;
					case DESTROY:
						if (bilanG1 != null) {
							bilanG1.dispose();
							bilanG1 = null;
							succeeded = true;
						}
						break;
				}
				break;
		}
		return succeeded;
	}

	private enum ScreenAction {
		DESTROY,
		CHANGE
	}
}
