package gdx.kapotopia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Locale;

import gdx.kapotopia.Screens.UnlockedLevel;

public class Settings {

    /*******************************
     *          ATTRIBUTES         *
     *******************************/

    /* THE STORES */
    private Preferences prefs_gen;
    private Preferences prefs_game1;
    /* MEMORIZED VARIABLES */
    private boolean isMusicOn;
    private String language;
    private UnlockedLevel unlockedLevel;
    private int G1Highscore;
    /* CONSTANTES */
    private static final String TAG = "SETTINGS";
    private static final String GENERAL_SETTINGS_NAME = "general_settings";
    private static final String GAME1_SETTINGS_NAME = "game1_settings";
    // General
    private static final String PREF_LANGUAGE = "language";
    private static final String PREF_MUSIC_ON = "music_on";
    // Game 1
    private static final String PREF_UNLOCKED_LEVEL = "lvl-unlocked";
    private static final String PREF_HIGHSCORE = "highscore"; //TODO make an elaborate hiscore system that can save and load multiple highscores instead of a single one

    /*******************************
     *            METHODS          *
     *******************************/

    public Settings() {
        prefs_gen = Gdx.app.getPreferences(GENERAL_SETTINGS_NAME);
        prefs_game1 = Gdx.app.getPreferences(GAME1_SETTINGS_NAME);

        initialize();
    }

    /**
     * Initialize the game 1 settings
     */
    private void initialize() {
        boolean needChange = false;

        // GENERAL

        if (!prefs_gen.contains(PREF_LANGUAGE)) {
            final String language = Locale.getDefault().getLanguage();
            prefs_gen.putString(PREF_LANGUAGE, language);
            this.language = language;
            needChange = true;
        }else{
            this.language = prefs_gen.getString(PREF_LANGUAGE, "fr");
        }

        if (!prefs_gen.contains(PREF_MUSIC_ON)) {
            prefs_gen.putBoolean(PREF_MUSIC_ON, true);
            this.isMusicOn = true;
            needChange = true;
        } else {
            this.isMusicOn = prefs_gen.getBoolean(PREF_MUSIC_ON, true);
        }

        // GAME 1

        if (!prefs_game1.contains(PREF_UNLOCKED_LEVEL)) {
            // We set difficulty to easy per default
            prefs_game1.putInteger(PREF_UNLOCKED_LEVEL, 0);
            this.unlockedLevel = UnlockedLevel.EASY_UNLOCKED;
            needChange = true;
        } else {
            this.unlockedLevel = UnlockedLevel.getUnLevel(prefs_game1.getInteger(PREF_UNLOCKED_LEVEL, 0));
        }

        if (!prefs_game1.contains(PREF_HIGHSCORE)) {
            prefs_game1.putInteger(PREF_HIGHSCORE, 0);
            this.G1Highscore = 0;
            needChange = true;
        } else {
            this.G1Highscore = prefs_game1.getInteger(PREF_HIGHSCORE, 0);
        }

        if(needChange) {
            prefs_game1.flush();
        }
    }

    /* GENERAL SETTINGS */

    public void setLanguage(String lang) {
        prefs_gen.putString(PREF_LANGUAGE, lang);
        prefs_gen.flush();
        this.language = lang;
    }

    public String getLanguage() {
        return this.language;
    }

    public void toggleMusic() {
        prefs_gen.putBoolean(PREF_MUSIC_ON, !isMusicOn());
        prefs_gen.flush();
        this.isMusicOn = !isMusicOn();
    }

    public boolean isMusicOn() {
        return this.isMusicOn;
    }

    /* GAME 1 */

    public void setG1UnlockedLvl(UnlockedLevel level) {
        prefs_game1.putInteger(PREF_UNLOCKED_LEVEL, level.ordinal());
        prefs_game1.flush();
        this.unlockedLevel = level;
        Gdx.app.log(TAG, "UnlockedLevel updated : " + unlockedLevel);
    }

    public UnlockedLevel getG1UnlockedLvl() {
        return this.unlockedLevel;
    }

    public void setG1Highscore(int score) {
        prefs_game1.putInteger(PREF_HIGHSCORE, score);
        prefs_game1.flush();
        this.G1Highscore = score;
    }

    public int getG1Highscore() {
        return this.G1Highscore;
    }
}
