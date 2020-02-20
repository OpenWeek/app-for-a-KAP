package gdx.kapotopia.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.AssetsManaging.SoundHelper;
import gdx.kapotopia.AssetsManaging.UseSound;
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
    private Music music;
    private Music musicJ;

    // Constants
    private final String TAG = this.getClass().getSimpleName();
    private final String MUSICPATH = "sound/Musique_fast_chiptune.ogg";

    public SoundController(Kapotopia game, Game1 game1) {
        this.game = game;
        this.game1 = game1;

        // Musicsthis.music = prepareMusic();
        this.music = prepareMusic();
        this.musicJ = AssetsManager.getInstance().getMusicByPath("sound/bgm.ogg");
        this.musicJ.setLooping(true);
        this.musicJ.setPosition(0);
        // Sounds
        this.touchedSound = SoundHelper.getSound(UseSound.PUNCH);
        this.failSound = SoundHelper.getSound(UseSound.FAIL);
        this.jumpSound = SoundHelper.getSound(UseSound.JUMP_V2);
        this.pauseSound = SoundHelper.getSound(UseSound.PAUSE);
        this.istTouchedSound = SoundHelper.getSound(UseSound.COIN);
        this.successSound = SoundHelper.getSound(UseSound.SUCCESS);
    }

    private Music prepareMusic() {
        Music music = AssetsManager.getInstance().getMusicByPath(MUSICPATH);
        music.setPosition(0f);
        music.setLooping(false);
        music.setVolume(0.66f);
        music.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                music.setPosition(11.5f);
                music.play();
                Gdx.app.log(TAG, "Music stopped");
            }
        });
        return music;
    }

    public void stopMusics() {
        music.stop();
        musicJ.stop();
    }

    /**
     * Start music when jojo event appear
     * Note: This method is called from GameController and not from Game1
     */
    public void startJojo() {
        if (game1.getGameController().isMusicOn()) {
            music.stop();
            musicJ.play();
        }
    }

    // STANDARD SCREEN CALLS

    public void playAtPause() {
        pauseSound.play();
        if (game1.getGameController().isMusicOn()) {
            if (game1.getGameController().isMusicJOn()) {
                musicJ.pause();
            } else {
                music.pause();
            }
        }
    }

    public void playAtHide() {
        if(game1.getGameController().isMusicOn()) {
            if (game1.getGameController().isMusicJOn()) {
                musicJ.pause();
            } else {
                music.pause();
            }
        }
    }

    public void playAtShow() {
        if(game1.getGameController().isMusicOn()) {
            if (game1.getGameController().isMusicJOn()) {
                musicJ.play();
            } else {
                music.play();
            }
        }
    }

    //

    public void playAtResumeFromPause() {
        if (game1.getGameController().isMusicOn()) {
            if (game1.getGameController().isMusicJOn()) {
                musicJ.play();
            } else {
                music.play();
            }
        }
    }

    public void playWhenLifeChange() {
        touchedSound.play();
    }

    public void playAtGameOver() {
        if(game1.getGameController().isMusicOn()) {
            if (game1.getGameController().isMusicJOn()) {
                musicJ.setVolume(0.1f);
            } else {
                this.music.setVolume(0.1f);
            }
        }
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
