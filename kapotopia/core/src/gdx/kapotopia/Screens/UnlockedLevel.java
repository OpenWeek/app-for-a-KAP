package gdx.kapotopia.Screens;

/**
 * This enum is used to show or not to show certain parts of screens depending of the difficulty
 * EASY_UNLOCKED define the EASY difficulty
 * MEDI_UNLOCKED define the MEDIUM difficulty
 * HARD_UNLOCKED define the HARD difficulty
 * they are in relationship with GameDifficulty enum
 */
public enum UnlockedLevel {
    EASY_UNLOCKED,
    MEDI_UNLOCKED,
    HARD_UNLOCKED;

    public static UnlockedLevel getUnLevel(final int code) {
        // We follow the ordinal order; first element in the enum is 0
        switch (code) {
            case 0:
                return EASY_UNLOCKED;
            case 1:
                return MEDI_UNLOCKED;
            case 2:
                return HARD_UNLOCKED;
            default:
                return HARD_UNLOCKED;
        }
    }
}
