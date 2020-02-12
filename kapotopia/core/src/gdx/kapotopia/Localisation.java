package gdx.kapotopia;

import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.Gdx;


public class Localisation {

    private I18NBundle languageStrings = I18NBundle.createBundle(Gdx.files.internal("strings/strings"));
    private I18NBundle stiStrings = I18NBundle.createBundle(Gdx.files.internal("strings/stiNames"));
    private I18NBundle symptomsStrings = I18NBundle.createBundle(Gdx.files.internal("strings/stiSymptoms"));
    private I18NBundle practices = I18NBundle.createBundle(Gdx.files.internal("strings/practices"));
    private static Localisation instance = new Localisation();
    public Localisation()
    {

    }

    public static Localisation getInstance()
    {
        return instance;
    }

    public String getString(String key)
    {
        return languageStrings.get(key);
    }

    public String getStiName(String key){
        return stiStrings.get(key);
    }

    public String getStiSymptom(String key){
        return symptomsStrings.get(key);
    }

    public String getPractice(String key){
        return practices.get(key);
    }
}
