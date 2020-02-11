package gdx.kapotopia.AssetsManaging;

import com.badlogic.gdx.audio.Sound;

public class SoundHelper {

    public static String getSoundPath(UseSound sound) {
        final String result;
        switch (sound) {
            case CLICKED_BTN:
                result = "sound/bruitage/kickhat_open-button-2.wav";
                break;
            case COIN:
                result = "sound/bruitage/leszek-szary_coin-object.wav";
                break;
            case FAIL:
                result = "sound/bruitage/littlerainyseasons_fail.mp3";
                break;
            case GAMEOVER:
                result = "sound/bruitage/jivatma07_j1game-over-mono.wav";
                break;
            case GAMESTART:
                result = "sound/bruitage/plasterbrain_game-start.ogg";
                break;
            case HINT:
                result = "sound/bruitage/dland_hint.wav";
                break;
            case JUMP_V1:
                result = "sound/bruitage/cmdrobot_videogame-jump.ogg";
                break;
            case JUMP_V2:
                result = "sound/bruitage/lloydevans09_jump1.wav";
                break;
            case LEVEL_UP:
                result = "sound/bruitage/cabeeno-rossley_level-up.wav";
                break;
            case MENU_SEL:
                result = "sound/bruitage/runnerpack_menusel.wav";
                break;
            case NOM_NOM:
                result = "sound/bruitage/josepharaoh99_bite-cartoon-style.mp3";
                break;
            case PAUSE:
                result = "sound/bruitage/crisstanza_pause.mp3";
                break;
            case POWER_UP:
                result = "sound/bruitage/lulyc_retro-game-heal-sound.wav";
                break;
            case PUNCH:
                result = "sound/bruitage/thefsoundman_punch-02.wav";
                break;
            case SUCCESS:
                result = "sound/bruitage/leszek-szary_success-1.wav";
                break;
            case BOUP1:
                result = "sound/bruitage/boup1.ogg";
                break;
            case BOUP9:
                result = "sound/bruitage/boup9.ogg";
                break;
            default:
                result = null;
                break;
        }
        return result;
    }

    public static Sound getSound(UseSound sound) {
        return AssetsManager.getInstance().getSoundByPath(getSoundPath(sound));
    }

}
