package gdx.kapotopia;

import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.Gdx;


public class Localization {

    private I18NBundle languageStrings = I18NBundle.createBundle(Gdx.files.internal("strings/strings"));
    private static Localization instance = new Localization();
    public Localization()
    {

    }

    public static Localization getInstance()
    {
        return instance;
    }

    public String getString(String key)
    {
        return languageStrings.get(key);
    }
}
