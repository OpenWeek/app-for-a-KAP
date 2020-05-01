package gdx.kapotopia;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;


public class Localisation {

    private I18NBundle bundleDefault;
    private I18NBundle bundleFrench;

    private Languages choosenLanguage;

    public Localisation(AssetManager ass) {
        this.bundleDefault = ass.get(AssetDescriptors.I18N_BUNDLE_ROOT);
        this.bundleFrench = ass.get(AssetDescriptors.I18N_BUNDLE_FR);
        if(Locale.getDefault().equals(Locale.FRENCH)) {
            choosenLanguage = Languages.FRENCH;
        } else {
            choosenLanguage = Languages.ENGLISH;
        }
    }

    public String getString(String key) {
        switch (choosenLanguage) {
            case FRENCH:
                return bundleFrench.get(key);
            default:
                return bundleDefault.get(key);
        }
    }

}
