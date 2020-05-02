package gdx.kapotopia;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.I18NBundle;

import gdx.kapotopia.AssetsManaging.AssetPaths;

import static gdx.kapotopia.AssetsManaging.AssetDescriptors.I18N_BUNDLE_FR;
import static gdx.kapotopia.AssetsManaging.AssetDescriptors.I18N_BUNDLE_ROOT;


public class Localisation {

    private final String TAG = this.getClass().getSimpleName();

    private AssetManager ass;

    private I18NBundle bundle;

    private Languages chosenLanguage;

    public Localisation(AssetManager ass) {
        this.ass = ass;
    }

    private void setBundle(AssetDescriptor<I18NBundle> assetDescriptor) {
        this.ass.load(assetDescriptor);
        this.ass.finishLoadingAsset(assetDescriptor);
        this.bundle = this.ass.get(assetDescriptor);
    }

    public void changeLanguage(Languages newLang) {
        // Why do we have to unload the previous bundle ? Here is the explanation : https://javadoc.io/static/com.badlogicgames.gdx/gdx/1.2.0/com/badlogic/gdx/assets/loaders/I18NBundleLoader.html
        if (ass.contains(AssetPaths.I18N_BUNDLE))
            ass.unload(AssetPaths.I18N_BUNDLE);

        switch (newLang) {
            case FRENCH:
                chosenLanguage = Languages.FRENCH;
                setBundle(I18N_BUNDLE_FR);
                break;
            default:
                chosenLanguage = Languages.ENGLISH;
                setBundle(I18N_BUNDLE_ROOT);
        }
    }

    public String getString(String key) {
        return this.bundle.get(key);
    }

    public Languages getChosenLanguage() {
        return chosenLanguage;
    }
}
