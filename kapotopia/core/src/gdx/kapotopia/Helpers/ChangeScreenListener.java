package gdx.kapotopia.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;

/**
 * Simple event class extending ChangeListener that plays a sound and change of screen on Trigger.
 * When not specified, a default sound is used.
 */
public class ChangeScreenListener extends ChangeListener {
    private Kapotopia game;
    private boolean destroyScreenOnChange;
    private ScreenType screenToDestroyOnChange;
    private ScreenType nextScreen;
    private Sound changeScreenSound;

    public ChangeScreenListener(Kapotopia game, ScreenType nextScreen) {
        super();
        this.game = game;
        this.nextScreen = nextScreen;
        if (!game.ass.containsAsset(AssetDescriptors.SOUND_CLICKED_BTN)) {
            game.ass.load(AssetDescriptors.SOUND_CLICKED_BTN);
            game.ass.finishLoadingAsset(AssetDescriptors.SOUND_CLICKED_BTN);
        }
        this.changeScreenSound = game.ass.get(AssetDescriptors.SOUND_CLICKED_BTN);

        this.destroyScreenOnChange = false;
        this.screenToDestroyOnChange = null;
    }

    public ChangeScreenListener(Kapotopia game, ScreenType nextScreen, ScreenType screenToDestroyOnChange) {
        super();
        this.game = game;
        this.nextScreen = nextScreen;
        if (!game.ass.containsAsset(AssetDescriptors.SOUND_CLICKED_BTN)) {
            game.ass.load(AssetDescriptors.SOUND_CLICKED_BTN);
            game.ass.finishLoadingAsset(AssetDescriptors.SOUND_CLICKED_BTN);
        }
        this.changeScreenSound = game.ass.get(AssetDescriptors.SOUND_CLICKED_BTN);

        this.screenToDestroyOnChange = screenToDestroyOnChange;
        this.destroyScreenOnChange = screenToDestroyOnChange != null;
    }

    public ChangeScreenListener(Kapotopia game, ScreenType nextScreen, ScreenType screenToDestroyOnChange, Sound changeScreenSound) {
        super();
        this.game = game;
        this.nextScreen = nextScreen;
        this.changeScreenSound = changeScreenSound;

        this.screenToDestroyOnChange = screenToDestroyOnChange;
        this.destroyScreenOnChange = screenToDestroyOnChange != null;
    }

    public ChangeScreenListener(Kapotopia game, ScreenType nextScreen, ScreenType screenToDestroyOnChange, AssetDescriptor<Sound> changeScreenSound) {
        super();
        this.game = game;
        this.nextScreen = nextScreen;
        if (!game.ass.containsAsset(changeScreenSound)) {
            game.ass.load(changeScreenSound);
            game.ass.finishLoadingAsset(changeScreenSound);
        }
        this.changeScreenSound = game.ass.get(changeScreenSound);

        this.screenToDestroyOnChange = screenToDestroyOnChange;
        this.destroyScreenOnChange = screenToDestroyOnChange != null;
    }

    @Override
    public void changed(ChangeEvent event, Actor actor) {
        Gdx.input.vibrate(50);
        changeScreenSound.play();
        if(destroyScreenOnChange) game.destroyScreen(screenToDestroyOnChange);
        game.changeScreen(nextScreen);
    }
}
