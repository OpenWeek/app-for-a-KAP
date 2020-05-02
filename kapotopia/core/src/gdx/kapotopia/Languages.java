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
        // Here we compare with languages names (in plain words)
        if (lang.equals(FR_KEY)) return FRENCH;
        if (lang.equals(NL_KEY)) return DUTCH;
        // Here we compare with locale.tostrings
        if (lang.equals(Locale.FRENCH.toString()) || lang.equals(Locale.FRANCE.toString()) || lang.equals(Locale.CANADA_FRENCH.toString()))
            return FRENCH;
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

    public static Languages convertFromLocale(Locale locale) {
        if(locale.equals(Locale.FRENCH) || locale.equals(Locale.FRANCE) || locale.equals(Locale.CANADA_FRENCH)) {
            return Languages.FRENCH;
        } else {
            return Languages.ENGLISH;
        }
    }
}
