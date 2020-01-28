package gdx.kapotopia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;

import java.util.Locale;

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
    private Array<Languages> supportedLangs;
    /* CONSTANTES */
    private static final String TAG = "SETTINGS";
    private static final String GENERAL_SETTINGS_NAME = "general_settings";
    private static final String GAME1_SETTINGS_NAME = "game1_settings";
    // General
    private static final String PREF_LANGUAGE = "language";
    private static final String PREF_MUSIC_ON = "music_on";
    // Game 1
    private static final String PREF_UNLOCKED_LEVEL = "lvl-unlocked";
    private static final String PREF_HIGHSCORE = "highscore"; //TODO make an elaborate highscore system that can save and load multiple highscores instead of a single one

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
        } else {
            this.language = prefs_gen.getString(PREF_LANGUAGE, "fr");
        }

        if (!prefs_gen.contains(PREF_MUSIC_ON)) {
            prefs_gen.putBoolean(PREF_MUSIC_ON, true);
            this.isMusicOn = true;
            needChange = true;
        } else {
            this.isMusicOn = prefs_gen.getBoolean(PREF_MUSIC_ON, true);
        }

        if(needChange) {
            prefs_gen.flush();
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

        // SUPPORTED LANGUAGES - SET THEM UP IN MEMORY NOT IN FILES

        supportedLangs = resetSupportedLangs();
    }

    /* GENERAL SETTINGS */

    public void setLanguage(String lang) {
        Locale locale = Languages.convertToLocale(Languages.convert(lang));
        //TODO support region as well as languages
        prefs_gen.putString(PREF_LANGUAGE, locale.getLanguage());
        prefs_gen.flush();
        this.language = locale.getLanguage();
    }

    /**
     *
     * @return the preferred language as stated with java.utils.Locale
     */
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

    /* LANGUAGES SETTINGS */

    /*
     *  english : false,
     *  french : true,
     *  dutch : false
     */

    public Array<Languages> resetSupportedLangs() {
        Array<Languages> languages = new Array<Languages>();
        languages.add(Languages.FRENCH);
        // If we translate the app in other languages, lines should be added down here
        languages.add(Languages.ENGLISH);
        languages.add(Languages.DUTCH);
        return languages;
    }

    public Array<Languages> getSupportedLangs() {
        return supportedLangs;
    }

    /**
     *
     * @return an array containing the strings of the supported languages. The strings doesnt support java.util.local
     * but are wide names
     */
    public Array<String> getSupportedLangsText() {
        Array<String> ans = new Array<String>();
        for (Languages lang : supportedLangs) {
            ans.add(Languages.convert(lang));
        }
        return ans;
    }
}
