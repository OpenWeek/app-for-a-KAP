package gdx.kapotopia.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Screens.Game1;

public class SoundController {

    private Kapotopia game;
    private Game1 game1;

    // Sounds and musics
    private Sound touchedSound;
    private Sound failSound;
    private Sound successSound;
    private Sound jumpSound;
    private Sound pauseSound;
    private Sound istTouchedSound;

    // Constants
    private final String TAG = this.getClass().getSimpleName();

    public SoundController(Kapotopia game, Game1 game1) {
        this.game = game;
        this.game1 = game1;

        // Musicsthis.music = prepareMusic();
        game.getMusicControl().changeMusic(game.ass.get(AssetDescriptors.MUSIC_GAME1), 0.66f,
                0, false, new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                music.setPosition(11.5f);
                music.play();
                Gdx.app.log(TAG, "Music stopped");
            }
        });
        // Sounds
        this.touchedSound = game.ass.get(AssetDescriptors.SOUND_PUNCH);
        this.failSound = game.ass.get(AssetDescriptors.SOUND_FAIL);
        this.jumpSound = game.ass.get(AssetDescriptors.SOUND_JUMP_V2);
        this.pauseSound = game.ass.get(AssetDescriptors.SOUND_PAUSE);
        this.istTouchedSound = game.ass.get(AssetDescriptors.SOUND_COIN);
        this.successSound = game.ass.get(AssetDescriptors.SOUND_SUCCESS);
    }

    public void stopMusics() {
        game.getMusicControl().stopMusic();
    }

    /**
     * Start music when jojo event appear
     * Note: This method is called from GameController and not from Game1
     */
    public void startJojo() {
        game.getMusicControl().stopMusic();
        game.getMusicControl().changeMusic(game.ass.get(AssetDescriptors.MUSIC_J), 0, true);
        game.getMusicControl().playMusic();
    }

    // STANDARD SCREEN CALLS

    public void playAtPause() {
        pauseSound.play();
        game.getMusicControl().pauseMusic();
    }

    public void playAtHide() {}

    public void playAtShow() {
        game.getMusicControl().playMusic();
    }

    public void playAtResumeFromPause() {
        game.getMusicControl().playMusic();
    }

    public void playWhenLifeChange() {
        touchedSound.play();
    }

    public void playAtGameOver() {
        game.getMusicControl().changeVolume(0.1f);
    }

    public void playSuccessSound() {
        successSound.play();
    }

    public void playFailSound() {
        failSound.play(0.7f);
    }

    public void playScoreChangedSuccess() {
        istTouchedSound.play();
    }

    public void playWhenMireilleMove() {
        jumpSound.play();
    }
}
