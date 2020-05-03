package gdx.kapotopia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;

import java.util.Locale;

public class Settings {

    /*******************************
     *          ATTRIBUTES         *
     *******************************/
    private Kapotopia game;
    private Localisation localisation;

    /* THE STORES */
    private Preferences prefs_gen;
    private Preferences prefs_game1;
    /* MEMORIZED VARIABLES */
    private boolean isMusicOn;
    private UnlockedLevel unlockedLevel;
    private int G1Highscore;
    private Array<Languages> supportedLangs;
    private boolean intro_1_skip;
    private boolean intro_2_skip;
    private boolean intro_3_skip;
    /* CONSTANTES */
    private final String TAG = "k" + this.getClass().getSimpleName();
    private final String GENERAL_SETTINGS_NAME = "general_settings";
    private final String GAME1_SETTINGS_NAME = "game1_settings";
    // General
    private final String PREF_LOCALE = "language";
    private final String PREF_MUSIC_ON = "music_on";
    //      Skip buttons
    private final String PREF_INTRO_1_SKIP = "intro_1_skip";
    private final String PREF_INTRO_2_SKIP = "intro_2_skip";
    private final String PREF_INTRO_3_SKIP = "intro_3_skip";
    // Game 1
    private final String PREF_UNLOCKED_LEVEL = "lvl-unlocked";
    private final String PREF_HIGHSCORE = "highscore"; //TODO make an elaborate highscore system that can save and load multiple highscores instead of a single one

    /*******************************
     *            METHODS          *
     *******************************/

    public Settings(Kapotopia game) {
        this.game = game;
        this.localisation = game.loc;
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

        final Languages prefLang;
        if (!prefs_gen.contains(PREF_LOCALE)) {
            final Locale defaultLoc = Locale.getDefault();
            Gdx.app.log(TAG, "The default system language is : " + defaultLoc);
            final String language = defaultLoc.toString();
            prefs_gen.putString(PREF_LOCALE, language);
            prefLang = Languages.convertFromLocale(defaultLoc);
            needChange = true;
        } else {
            String lang = prefs_gen.getString(PREF_LOCALE, "");
            Gdx.app.log(TAG, "Retrieved lang is : " + lang);
            prefLang = Languages.convert(lang);
        }

        Gdx.app.log(TAG, "The choosen language is : " + Languages.convert(prefLang));
        localisation.changeLanguage(prefLang);

        if (!prefs_gen.contains(PREF_MUSIC_ON)) {
            prefs_gen.putBoolean(PREF_MUSIC_ON, true);
            this.isMusicOn = true;
            needChange = true;
        } else {
            this.isMusicOn = prefs_gen.getBoolean(PREF_MUSIC_ON, true);
        }

        // SKIP BUTTONS

        if (!prefs_gen.contains(PREF_INTRO_1_SKIP)) {
            prefs_gen.putBoolean(PREF_INTRO_1_SKIP, false);
            intro_1_skip = false;
            needChange = true;
        } else {
            this.intro_1_skip = prefs_gen.getBoolean(PREF_INTRO_1_SKIP, false);
        }

        if (!prefs_gen.contains(PREF_INTRO_2_SKIP)) {
            prefs_gen.putBoolean(PREF_INTRO_2_SKIP, false);
            intro_2_skip = false;
            needChange = true;
        } else {
            this.intro_2_skip = prefs_gen.getBoolean(PREF_INTRO_2_SKIP, false);
        }

        if (!prefs_gen.contains(PREF_INTRO_3_SKIP)) {
            prefs_gen.putBoolean(PREF_INTRO_3_SKIP, false);
            intro_3_skip = false;
            needChange = true;
        } else {
            this.intro_3_skip = prefs_gen.getBoolean(PREF_INTRO_3_SKIP, false);
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

    /**
     * Set the new prefered language into the settings files and also update language used
     * in the Localisation class
     * @param lang the new language to use
     */
    public void setPrefLanguage(Languages lang) {
        Locale locale = Languages.convertToLocale(lang);
        //TODO support region as well as languages
        prefs_gen.putString(PREF_LOCALE, locale.toString());
        prefs_gen.flush();
        localisation.changeLanguage(lang);
        game.vars.getStiData().updateLists();
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
        //languages.add(Languages.DUTCH);
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

    public boolean isIntro_1_skip() {
        return intro_1_skip;
    }

    public void setIntro_1_skip(boolean intro_1_skip) {
        prefs_gen.putBoolean(PREF_INTRO_1_SKIP, intro_1_skip);
        prefs_gen.flush();
        this.intro_1_skip = intro_1_skip;
    }

    public boolean isIntro_2_skip() {
        return intro_2_skip;
    }

    public void setIntro_2_skip(boolean intro_2_skip) {
        prefs_gen.putBoolean(PREF_INTRO_2_SKIP, intro_2_skip);
        prefs_gen.flush();
        this.intro_2_skip = intro_2_skip;
    }

    public boolean isIntro_3_skip() {
        return intro_3_skip;
    }

    public void setIntro_3_skip(boolean intro_3_skip) {
        prefs_gen.putBoolean(PREF_INTRO_3_SKIP, intro_3_skip);
        prefs_gen.flush();
        this.intro_3_skip = intro_3_skip;
    }
}
