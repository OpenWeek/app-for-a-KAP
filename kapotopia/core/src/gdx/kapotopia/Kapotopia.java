package gdx.kapotopia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.viewport.FitViewport;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.Music.MusicController;
import gdx.kapotopia.STIDex.STI;
import gdx.kapotopia.Screens.BilanG1;
import gdx.kapotopia.Screens.ChoosingDifficultyScreen;
import gdx.kapotopia.Screens.ChoosingSTDScreen;
import gdx.kapotopia.Screens.Game1;
import gdx.kapotopia.Screens.Game2;
import gdx.kapotopia.Screens.Game3;
import gdx.kapotopia.Screens.Game4;
import gdx.kapotopia.Screens.IntroCutscene;
import gdx.kapotopia.Screens.LoadingScreen;
import gdx.kapotopia.Screens.MainMenu;
import gdx.kapotopia.Screens.Options;
import gdx.kapotopia.Screens.World1;
import gdx.kapotopia.Screens.World2;
import gdx.kapotopia.Screens.World3;
import gdx.kapotopia.Screens.STIDex;
import gdx.kapotopia.Screens.mockupG1;
import gdx.kapotopia.Screens.mockupG2;
import gdx.kapotopia.Screens.mockupG3;
import gdx.kapotopia.Screens.mockupG4;

public class Kapotopia extends com.badlogic.gdx.Game {

	/* APP-WIDE VARIABLES */

    // CONSTANTS

	private final String TAG = this.getClass().getSimpleName();

	// COMPLEX OBJECTS

	public AssetManager ass = new AssetManager();
	public Localisation loc;

    public FitViewport viewport;
    // GlobalVariables
	public GlobalVariables vars;
    // Settings
    private Settings settings;
    // Music Controller
	private MusicController musicControl;

    /* INNER VARIABLES */

	// Screens
	private Game1 game1;
	private Game2 game2;
	private Game3 game3;
	private Game4 game4;
	private MainMenu mainMenu;
	private mockupG1 mockupG1;
	private mockupG2 mockupG2;
	private mockupG3 mockupG3;
	private mockupG4 mockupG4;
	private BilanG1 bilanG1;
	private World1 world1;
	private World2 world2;
	private World3 world3;
	private STIDex STIDex;
	private ChoosingDifficultyScreen dif;
	private ChoosingSTDScreen STDGAME4;
	private Options options;
	private IntroCutscene introCutscene;

	public ReturnButtonManager getReturnButtonManager() {
		return returnButtonManager;
	}

	private ReturnButtonManager returnButtonManager;

	@Override
	public void create () {
		Gdx.app.setLogLevel(GameConfig.debugLvl);

		// AssetManager
		// We set up the AssetManager so it accepts FreeType Fonts
		ScreenType[] nextScreenType = {null};
		FileHandleResolver resolver = new InternalFileHandleResolver();
		this.ass.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		this.ass.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
		viewport = new FitViewport(GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT);

		this.ass.load(AssetDescriptors.MI_LOADING);
		this.ass.finishLoading();


		this.setScreen(new LoadingScreen(this, nextScreenType));
		// Loading every assets here, loadInitialTextures must come AFTER every call
        FontHelper.buildAllFonts(ass);
        this.musicControl = new MusicController(this);

		//We activate the BACK button for the whole app
		Gdx.input.setCatchBackKey(true);
		this.loc = new Localisation(ass);
		this.settings = new Settings(this);
		this.vars = new GlobalVariables(loc);
		this.returnButtonManager = new ReturnButtonManager(this);

		// If the first cutscene has already been showed, we go to the main menu directly

		nextScreenType[0] = settings.isFirstCinematicShowed() ? ScreenType.MAINMENU:ScreenType.INTROCUTSCENE;
		loadInitialTextures();

	}

	@Override
	public void dispose () {
	    Gdx.app.log(TAG, "Disposing every game resources");
		this.ass.dispose();
	}

	private void loadInitialTextures() {
		//loading
		/* Graphics */
		this.ass.load(AssetDescriptors.BLANK_BACK);
		this.ass.load(AssetDescriptors.PLAY_LOGO);
		// Main Menu
		this.ass.load(AssetDescriptors.MM_PART1);
		this.ass.load(AssetDescriptors.MM_PART1_CUT);
		this.ass.load(AssetDescriptors.ANIM_NEON_DOOR);
		this.ass.load(AssetDescriptors.MM_PART3);
		this.ass.load(AssetDescriptors.MM_PART3_CUT);
		this.ass.load(AssetDescriptors.MM_PART4);
		this.ass.load(AssetDescriptors.MM_PART4_CUT);
		// Options
		this.ass.load(AssetDescriptors.SKIN_COMIC_UI);
		this.ass.load(AssetDescriptors.OP_BACK);
		this.ass.load(AssetDescriptors.OP_MUTE);
		this.ass.load(AssetDescriptors.OP_SPEAKER);
		// World 2
		this.ass.load(AssetDescriptors.MM1_W2);
		this.ass.load(AssetDescriptors.MM2_W2);
		// Game 1
		this.ass.load(AssetDescriptors.B1_BACK);
		// Game 4
		this.ass.load(AssetDescriptors.YELLOW_BACK);
		this.ass.load(AssetDescriptors.PALEBLUE_BACK);

		// IntroG1
		this.ass.load(AssetDescriptors.DIF_PART1);
        this.ass.load(AssetDescriptors.JUNGLE);
        this.ass.load(AssetDescriptors.NIGHT_SKY);
        this.ass.load(AssetDescriptors.LEAVES);
        this.ass.load(AssetDescriptors.CROQUIS);
        this.ass.load(AssetDescriptors.MM_W1);
        this.ass.load(AssetDescriptors.WORLD1_GAME1);
        this.ass.load(AssetDescriptors.WORLD1_GAME2);
        // IntroG2
		this.ass.load(AssetDescriptors.SABLE);
		this.ass.load(AssetDescriptors.SEA);
		this.ass.load(AssetDescriptors.SKY);
		this.ass.load(AssetDescriptors.ALYX_OPEN);
		this.ass.load(AssetDescriptors.ALYX_NORMAL);
        this.ass.load(AssetDescriptors.MM_W2);
        // IntroG3
		this.ass.load(AssetDescriptors.I3_HOUSE);
		this.ass.load(AssetDescriptors.I3_INSIDE);
        // Characters
        this.ass.load(AssetDescriptors.MI_HAPPY);
		this.ass.load(AssetDescriptors.MI_NORMAL);
		this.ass.load(AssetDescriptors.MI_WORRIED);
		this.ass.load(AssetDescriptors.MI_SURPRISED);
		this.ass.load(AssetDescriptors.MI_SCARED);
        this.ass.load(AssetDescriptors.MI_CRY);
        this.ass.load(AssetDescriptors.MI_TIRED);
        this.ass.load(AssetDescriptors.SERGENT1);
        this.ass.load(AssetDescriptors.SERGENT2);
        this.ass.load(AssetDescriptors.GODIVA);
        // Gadgets
		this.ass.load(AssetDescriptors.BTN_LEAF);
		this.ass.load(AssetDescriptors.BTN_ROCK);
		this.ass.load(AssetDescriptors.BTN_SAND);
		this.ass.load(AssetDescriptors.BTN_WOOD);
        this.ass.load(AssetDescriptors.BUBBLE_EXPL);
		this.ass.load(AssetDescriptors.BUBBLE_LEFT);
        this.ass.load(AssetDescriptors.BUBBLE_MID_LEFT);
		this.ass.load(AssetDescriptors.BUBBLE_RIGHT);
        this.ass.load(AssetDescriptors.BUBBLE_MID_RIGHT);
		/* Sounds */
		this.ass.load(AssetDescriptors.SOUND_PAUSE);
		this.ass.load(AssetDescriptors.SOUND_GAMESTART);
		this.ass.load(AssetDescriptors.SOUND_BOUP1);
		this.ass.load(AssetDescriptors.SOUND_BOUP9);
		this.ass.load(AssetDescriptors.SOUND_CLICKED_BTN);
		this.ass.load(AssetDescriptors.SOUND_HINT);
		this.ass.load(AssetDescriptors.SOUND_SUCCESS);
		//STI ASSETS
		for (STI sti: vars.getStiData().getAllISTs()) {
			this.ass.load(sti.getTexture());
		}
		//ASSETS FOR GAME 1
		// Graphics
		this.ass.load(AssetDescriptors.ANIM_ACTIONTEXT);
		this.ass.load(AssetDescriptors.ANIM_SKY);
		this.ass.load(AssetDescriptors.ANIM_MIREILLU);
		this.ass.load(AssetDescriptors.MI_HAPPY);
		this.ass.load(AssetDescriptors.MI_UNI);
		this.ass.load(AssetDescriptors.MI_JOJO_FACE);
		this.ass.load(AssetDescriptors.MI_JOJO_POSE);
		this.ass.load(AssetDescriptors.MI_JOJO_KANJI);
		this.ass.load(AssetDescriptors.PAUSE_LOGO);
		this.ass.load(AssetDescriptors.Failed);
		// Musics
		this.ass.load(AssetDescriptors.MUSIC_GAME1);
		this.ass.load(AssetDescriptors.MUSIC_J);
		// Sounds
		this.ass.load(AssetDescriptors.SOUND_FAIL);
		this.ass.load(AssetDescriptors.SOUND_JUMP_V1);
		this.ass.load(AssetDescriptors.SOUND_JUMP_V2);
		this.ass.load(AssetDescriptors.SOUND_PUNCH);
		this.ass.load(AssetDescriptors.SOUND_FAIL);
		this.ass.load(AssetDescriptors.SOUND_COIN);

		//ASSETS FOR GAME 2
		// Graphics
		this.ass.load(AssetDescriptors.BALL);
		this.ass.load(AssetDescriptors.PALMIER);
		this.ass.load(AssetDescriptors.BASKET);
		this.ass.load(AssetDescriptors.PANNAL);
		// Sounds
		this.ass.load(AssetDescriptors.MUSIC_GAME2);
		//GAME 3 ASSETS
		this.ass.load(AssetDescriptors.NEON_ROSE);
		this.ass.load(AssetDescriptors.NEON_RED);
		this.ass.load(AssetDescriptors.NEON_TURQUOISE);
		this.ass.load(AssetDescriptors.NEON_GREEN);
		this.ass.load(AssetDescriptors.NEON_VIOLET);
		this.ass.load(AssetDescriptors.DOOR);
		this.ass.load(AssetDescriptors.DOOR_LOCK);
		this.ass.load(AssetDescriptors.BATTERY);
		this.ass.load(AssetDescriptors.CLOSED_LOCK1);
		this.ass.load(AssetDescriptors.CLOSED_LOCK2);
		this.ass.load(AssetDescriptors.OPENED_LOCK1);
		this.ass.load(AssetDescriptors.OPENED_LOCK2);
		this.ass.load(AssetDescriptors.CROSS_T);
		this.ass.load(AssetDescriptors.TCROSS_T);
		this.ass.load(AssetDescriptors.LINE_T);
		this.ass.load(AssetDescriptors.HALF_LINE_T);
		this.ass.load(AssetDescriptors.TURN_T);
		// Sounds
		this.ass.load(AssetDescriptors.MUSIC_GAME3);

		//ASSETS FOR GAME 4
		this.ass.load(AssetDescriptors.BACKGROUND_GAME4);
		this.ass.load(AssetDescriptors.BACKGROUND_DISC);
		this.ass.load(AssetDescriptors.COVER_GAME4);
		this.ass.load(AssetDescriptors.DOWN_ARROW4);
		this.ass.load(AssetDescriptors.UP_ARROW4);
		this.ass.load(AssetDescriptors.LEFT_ARROW4);
		this.ass.load(AssetDescriptors.RIGHT_ARROW4);
		this.ass.load(AssetDescriptors.PAUSE_LOGO4);
		this.ass.load(AssetDescriptors.PLAY_LOGO4);
		this.ass.load(AssetDescriptors.ANULINGUS);
		this.ass.load(AssetDescriptors.CARESSES);
		this.ass.load(AssetDescriptors.CUNNI);
		this.ass.load(AssetDescriptors.SERINGUE_CHANGE);
		this.ass.load(AssetDescriptors.EMBRASSADE);
		this.ass.load(AssetDescriptors.FELLATION);
		this.ass.load(AssetDescriptors.GANT);
		this.ass.load(AssetDescriptors.PENE_VAGINALE);
		this.ass.load(AssetDescriptors.PENE_ANALE);
		this.ass.load(AssetDescriptors.ISTCHOICE);

		this.ass.load(AssetDescriptors.VIH);
		this.ass.load(AssetDescriptors.HEPA);
		this.ass.load(AssetDescriptors.HEPB);
		this.ass.load(AssetDescriptors.HEPC);
		this.ass.load(AssetDescriptors.SYPHILIS);
		this.ass.load(AssetDescriptors.HERPES);
		this.ass.load(AssetDescriptors.PAPILLO);
		this.ass.load(AssetDescriptors.CHLAMYDIA);
		this.ass.load(AssetDescriptors.GONORRHEE);
		this.ass.load(AssetDescriptors.TRICHOMONAS);
		this.ass.load(AssetDescriptors.MIREILLE_FOOD);
		this.ass.load(AssetDescriptors.LEGEND_EN);
		this.ass.load(AssetDescriptors.LEGEND_FR);

		this.ass.load(AssetDescriptors.RED_SQUARE);
		this.ass.load(AssetDescriptors.GREEN_SQUARE);
		this.ass.load(AssetDescriptors.BLUE_SQUARE);
		// IST-INDEX
		this.ass.load(AssetDescriptors.CLOSE);

	}

	public Settings getSettings() {
		return this.settings;
	}

	/**
	 * Change the current screen, creating a new screen if needed, otherwise creating a new screen object
	 * @param TYPE the enum SCREENTYPE that specify which screen to change to
	 * @return true if the operation succeeded, false otherwise
	 */
	public boolean changeScreen(ScreenType TYPE) {
	    Gdx.app.debug(TAG, "Changing screen to " + TYPE.name());
	    this.returnButtonManager.updateReturn(TYPE);
		return selectScreen(ScreenAction.CHANGE, TYPE);
	}

	/**
	 * Destroy the specified screen and dispose all it's ressources
	 * @param TYPE the enum SCREENTYPE that specify which screen to destroy
	 * @return true if the operation succeeded, false otherwise
	 */
	public boolean destroyScreen(ScreenType TYPE) {
	    Gdx.app.debug(TAG, "Destroying Screen " + TYPE.name());
		return selectScreen(ScreenAction.DESTROY, TYPE);
	}

	/**
	 * Destroy the specified screen and dispose it's ressources
	 * @param sc The screen to destroy
	 * @return true if the operation succeeded, false otherwise
	 */
	public boolean destroyScreen(Screen sc) {
		if (sc == game1) {
			return destroyScreen(ScreenType.GAME1);
		} else if(sc == game2) {
			return destroyScreen(ScreenType.GAME2);
		} else if(sc == game3) {
			return destroyScreen(ScreenType.GAME3);
		} else if (sc == game4) {
			return destroyScreen(ScreenType.GAME4);
		} else if(sc == mainMenu) {
			return destroyScreen(ScreenType.MAINMENU);
		} else if(sc == mockupG1) {
			return destroyScreen(ScreenType.MOCKUPG1);
		} else if(sc == mockupG2) {
			return destroyScreen(ScreenType.MOCKUPG2);
		} else if(sc == mockupG3) {
			return destroyScreen(ScreenType.MOCKUPG3);
		} else if(sc == mockupG4) {
			return destroyScreen(ScreenType.MOCKUPG4);
		} else if(sc == bilanG1) {
			return destroyScreen(ScreenType.BILANG1);
		} else if(sc == world1) {
			return destroyScreen(ScreenType.WORLD1);
		} else if(sc == world2) {
			return destroyScreen(ScreenType.WORLD2);
		} else if(sc == world3) {
			return destroyScreen(ScreenType.WORLD3);
		} else if(sc == STIDex) {
			return destroyScreen(ScreenType.STIDEX);
		} else if(sc == dif) {
			return destroyScreen(ScreenType.DIFGAME1);
		} else if(sc == options) {
		    return destroyScreen(ScreenType.OPTIONS);
        } else if(sc == introCutscene) {
			return destroyScreen(ScreenType.INTROCUTSCENE);
		} else if (sc == STDGAME4) {
			return destroyScreen(ScreenType.STDGAME4);
		}

		return false;
	}

	/**
	 * This method will reset everyScreen, so setting their reference to null and dispose every ressources
	 * (beside AssetManager ones) and will redirect to
	 * @param nextScreen
	 */
	public void resetEveryScreen(ScreenType nextScreen) {
		destroyScreen(ScreenType.GAME1);
		destroyScreen(ScreenType.GAME2);
		destroyScreen(ScreenType.GAME3);
		destroyScreen(ScreenType.GAME4);
		destroyScreen(ScreenType.MAINMENU);
		destroyScreen(ScreenType.MOCKUPG1);
		destroyScreen(ScreenType.MOCKUPG2);
		destroyScreen(ScreenType.MOCKUPG3);
		destroyScreen(ScreenType.MOCKUPG4);
		destroyScreen(ScreenType.BILANG1);
		destroyScreen(ScreenType.WORLD1);
		destroyScreen(ScreenType.WORLD2);
		destroyScreen(ScreenType.WORLD3);
		destroyScreen(ScreenType.STIDEX);
		destroyScreen(ScreenType.DIFGAME1);
		destroyScreen(ScreenType.STDGAME4);
		destroyScreen(ScreenType.OPTIONS);
		destroyScreen(ScreenType.INTROCUTSCENE);
		changeScreen(nextScreen);
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
			case GAME4:
				switch (ACTION) {
					case CHANGE:
						if (game4 == null) game4 = new Game4(this);
						setScreen(game4);
						succeeded = true;
						break;
					case DESTROY:
						if (game4 != null) {
							game4.dispose();
							game4 = null;
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
			case STIDEX:
				switch (ACTION) {
					case CHANGE:
						if (STIDex == null) STIDex = new STIDex(this);
						setScreen(STIDex);
						succeeded = true;
						break;
					case DESTROY:
						if (STIDex != null) {
							STIDex.dispose();
							STIDex = null;
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
			case MOCKUPG3:
				switch (ACTION) {
					case CHANGE:
						if (mockupG3 == null) mockupG3 = new mockupG3(this);
						setScreen(mockupG3);
						succeeded = true;
						break;
					case DESTROY:
						if (mockupG3 != null) {
							mockupG3.dispose();
							mockupG3 = null;
							succeeded = true;
						}
						break;
				}
				break;
			case MOCKUPG4:
				switch (ACTION) {
					case CHANGE:
						if (mockupG4 == null) mockupG4 = new mockupG4(this);
						setScreen(mockupG4);
						succeeded = true;
						break;
					case DESTROY:
						if (mockupG4 != null) {
							mockupG4.dispose();
							mockupG4 = null;
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
			case DIFGAME1:
				switch (ACTION) {
					case CHANGE:
						if (dif == null) dif = new ChoosingDifficultyScreen(this);
						setScreen(dif);
						succeeded = true;
						break;
					case DESTROY:
						if (dif != null) {
							dif.dispose();
							dif = null;
							succeeded = true;
						}
						break;
				}
				break;
			case STDGAME4:
				switch (ACTION) {
					case CHANGE:
						if (STDGAME4 == null) STDGAME4 = new ChoosingSTDScreen(this);
						setScreen(STDGAME4);
						succeeded = true;
						break;
					case DESTROY:
						if (STDGAME4 != null) {
							STDGAME4.dispose();
							STDGAME4 = null;
							succeeded = true;
						}
						break;
				}
				break;
            case OPTIONS:
                switch (ACTION) {
                    case CHANGE:
                        if (options == null) options = new Options(this);
                        setScreen(options);
                        succeeded = true;
                        break;
                    case DESTROY:
                        if (options != null) {
                            options.dispose();
                            options = null;
                            succeeded = true;
                        }
                        break;
                }
                break;
			case INTROCUTSCENE:
				switch (ACTION) {
					case CHANGE:
						if (introCutscene == null) introCutscene = new IntroCutscene(this);
						setScreen(introCutscene);
						succeeded = true;
						break;
					case DESTROY:
						if (introCutscene != null) {
							introCutscene.dispose();
							introCutscene = null;
							succeeded = true;
						}
						break;
				}
				break;
		}
		return succeeded;
	}

	public Game1 getGame1() {
		return game1;
	}

	public Game2 getGame2() {
		return game2;
	}

	public Game3 getGame3() {
		return game3;
	}

	public MainMenu getMainMenu() {
		return mainMenu;
	}

	public mockupG1 getMockupG1() {
		return mockupG1;
	}

	public mockupG2 getMockupG2() {
		return mockupG2;
	}

	public mockupG3 getMockupG3() {
		return mockupG3;
	}

	public BilanG1 getBilanG1() {
		return bilanG1;
	}

	public World1 getWorld1() {
		return world1;
	}

	public World2 getWorld2() {
		return world2;
	}

	public World3 getWorld3() {
		return world3;
	}

	public STIDex getSTIDex() {
		return STIDex;
	}

	public ChoosingDifficultyScreen getDif() {
		return dif;
	}

	public Options getOptions() {
		return options;
	}

	public IntroCutscene getIntroCutscene() {
		return introCutscene;
	}

	public MusicController getMusicControl() {
		return musicControl;
	}

	public ChoosingSTDScreen getChoosingSTDScreen() {
		return STDGAME4;
	}

	private enum ScreenAction {
		DESTROY,
		CHANGE
	}


}
