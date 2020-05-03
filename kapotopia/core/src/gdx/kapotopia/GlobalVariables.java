package gdx.kapotopia;

import gdx.kapotopia.STIDex.STIData;

/**
 * Utility class for holding variables that have to be passed from one screen to another
 */
public class GlobalVariables {

    private STIData stiData;
    private GameDifficulty choosenDifficultyG1;
    private UnlockedLevel game1UnlockedLevels;
    private ScreenType nextScreenOfChoosingDifScreen;

    public GlobalVariables(Localisation loc) {
        // We define the default value of variables
        stiData = new STIData(loc);
        choosenDifficultyG1 = GameDifficulty.EASY;
        game1UnlockedLevels = UnlockedLevel.EASY_UNLOCKED;
        nextScreenOfChoosingDifScreen = ScreenType.MAINMENU;
    }

    public GameDifficulty getChoosenDifficultyG1() {
        return choosenDifficultyG1;
    }

    public void setChoosenDifficultyG1(GameDifficulty choosenDifficultyG1) {
        this.choosenDifficultyG1 = choosenDifficultyG1;
    }

    public UnlockedLevel getGame1UnlockedLevels() {
        return game1UnlockedLevels;
    }

    public void setGame1UnlockedLevels(UnlockedLevel game1UnlockedLevels) {
        this.game1UnlockedLevels = game1UnlockedLevels;
    }

    public ScreenType getNextScreenOfChoosingDifScreen() {
        return nextScreenOfChoosingDifScreen;
    }

    public void setNextScreenOfChoosingDifScreen(ScreenType nextScreenOfChoosingDifScreen) {
        this.nextScreenOfChoosingDifScreen = nextScreenOfChoosingDifScreen;
    }

    public STIData getStiData() {
        return stiData;
    }
}
