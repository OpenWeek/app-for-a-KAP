package gdx.kapotopia;

import java.util.Locale;

public enum Languages {
    ENGLISH,
    FRENCH,
    DUTCH;

    private static final String EN_KEY = "english";
    private static final String FR_KEY = "french";
    private static final String NL_KEY = "dutch";

    public static String convert(Languages lang) {
        switch (lang) {
            case FRENCH:
                return FR_KEY;
            case DUTCH:
                return NL_KEY;
                default:
                    return EN_KEY;
        }
    }

    public static Languages convert(String lang) {
        if (lang.equals(FR_KEY)) return FRENCH;
        if (lang.equals(NL_KEY)) return DUTCH;
        return ENGLISH;
    }

    public static Locale convertToLocale(Languages lang) {
        Locale res;
        //TODO manage Locale regions
        switch (lang) {
            case FRENCH:
                res = new Locale("fr");
                break;
            case DUTCH:
                res = new Locale("nl");
                break;
            default:
                res = new Locale("en");
                break;
        }
        return res;
    }

    /**
     * Convert from the locale language format to a longer format (e.g. "fr" -> "french")
     * @param lang the Locale language
     * @return the complete name of the language
     */
    public static String convertFromLocale(String lang) {
        if (lang.equals(new Locale("fr").toString())) return convert(Languages.FRENCH);
        if (lang.equals(new Locale("nl").toString())) return convert(Languages.DUTCH);
        return convert(Languages.ENGLISH);
    }
}
