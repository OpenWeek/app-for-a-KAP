package gdx.kapotopia.Music;

import com.badlogic.gdx.audio.Music;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Kapotopia;

public class MusicController {
    private Kapotopia game;

    private boolean musicOn;

    private Music currentMusic;

    public MusicController(Kapotopia game) {
        this.game = game;
        this.currentMusic = null;
        this.musicOn = true;

        loadMusics(false);
    }

    public void changeMusic(Music music, float position, boolean looping) {
        changeMusic(music, 1, position, looping, null);
    }

    public void changeMusic(Music music, float volume, float position, boolean looping) {
        changeMusic(music, volume, position, looping, null);
    }

    public void changeMusic(Music music, float volume, float position, boolean looping,
                            Music.OnCompletionListener listener) {
        if (currentMusic != null) {
            currentMusic.stop();
        }
        currentMusic = music;
        currentMusic.setVolume(volume);
        currentMusic.setPosition(position);
        currentMusic.setLooping(looping);
        if (listener != null)
            currentMusic.setOnCompletionListener(listener);
    }

    public void playMusic() {
        if (currentMusic != null && musicOn) {
            currentMusic.play();
        }
    }

    public void stopMusic() {
        if (currentMusic != null) {
            currentMusic.stop();
        }
    }

    public void pauseMusic() {
        if (currentMusic != null) {
            currentMusic.pause();
        }
    }

    public void changeVolume(float newVolume) {
        if (currentMusic != null) {
            currentMusic.setVolume(newVolume);
        }
    }

    /**
     * Compare the music currently played with the one passed in argument
     * @param music
     * @return true if this is the music currently being played, else false
     */
    public boolean musicIsEquals(Music music) {
        if (currentMusic == null) return false;
        return currentMusic.equals(music);
    }

    private void loadMusics(boolean waitForLoading) {
        game.ass.load(AssetDescriptors.MUSIC_MM);
        game.ass.load(AssetDescriptors.MUSIC_J);
        game.ass.load(AssetDescriptors.MUSIC_GAME1);
        game.ass.load(AssetDescriptors.MUSIC_GAME2);
        game.ass.load(AssetDescriptors.MUSIC_GAME3);

        if (waitForLoading)
            game.ass.finishLoading();
    }

    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
        if (!musicOn) {
            if (currentMusic != null &&  currentMusic.isPlaying()) {
                currentMusic.stop();
            }
        }
    }
}
