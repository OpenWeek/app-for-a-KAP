package gdx.kapotopia;

/**
 * Utility class for holding variables that have to be passed from one screen to another
 */
public class GlobalVariables {

    private GameDifficulty choosenDifficultyG1;

    public GlobalVariables() {
        // We define the default value of variables
        choosenDifficultyG1 = GameDifficulty.EASY;
    }

    public GameDifficulty getChoosenDifficultyG1() {
        return choosenDifficultyG1;
    }

    public void setChoosenDifficultyG1(GameDifficulty choosenDifficultyG1) {
        this.choosenDifficultyG1 = choosenDifficultyG1;
    }
}
