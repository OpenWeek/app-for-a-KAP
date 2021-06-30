package gdx.kapotopia.AssetsManaging;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

public class AssetDescriptors {

    /* ************ *
     *  I18NBUNDLE  *
     * ************ */
    public static final AssetDescriptor<I18NBundle> I18N_BUNDLE_ROOT =
            new AssetDescriptor<I18NBundle>(AssetPaths.I18N_BUNDLE, I18NBundle.class,
                    new I18NBundleLoader.I18NBundleParameter(Locale.ROOT));
    public static final AssetDescriptor<I18NBundle> I18N_BUNDLE_FR =
            new AssetDescriptor<I18NBundle>(AssetPaths.I18N_BUNDLE, I18NBundle.class,
                    new I18NBundleLoader.I18NBundleParameter(Locale.FRENCH));

    /* ********** *
     *  TEXTURES  *
     * ********** */
    public static final AssetDescriptor<Texture> BLANK_BACK =
            new AssetDescriptor<Texture>(AssetPaths.BLANK_BACK, Texture.class);
    // Main Menu
    public static final AssetDescriptor<Texture> MM_PART1 =
            new AssetDescriptor<Texture>(AssetPaths.MM_PART1, Texture.class); // % loaded at the start
    public static final AssetDescriptor<Texture> MM_PART3 =
            new AssetDescriptor<Texture>(AssetPaths.MM_PART3, Texture.class); // % loaded at the start
    public static final AssetDescriptor<Texture> MM_PART4 =
            new AssetDescriptor<Texture>(AssetPaths.MM_PART4, Texture.class); // % loaded at the start
    public static final AssetDescriptor<Texture> MM_W1 =
            new AssetDescriptor<Texture>(AssetPaths.MM_W1, Texture.class); // % loaded at the start
    public static final AssetDescriptor<Texture> MM_W2 =
            new AssetDescriptor<Texture>(AssetPaths.MM_W2, Texture.class); // % loaded at the start
    // Options
    public static final AssetDescriptor<Texture> OP_BACK =
            new AssetDescriptor<Texture>(AssetPaths.OP_BACK, Texture.class); // % loaded at the start
    public static final AssetDescriptor<Texture> OP_SPEAKER =
            new AssetDescriptor<Texture>(AssetPaths.OP_SPEAKER, Texture.class); // % loaded at the start
    public static final AssetDescriptor<Texture> OP_MUTE =
            new AssetDescriptor<Texture>(AssetPaths.OP_MUTE, Texture.class); // % loaded at the start
    // Game1
    public static final AssetDescriptor<Texture> B1_BACK =
            new AssetDescriptor<Texture>(AssetPaths.B1_BACK, Texture.class);
    // Game2
    public static final AssetDescriptor<Texture> BALL =
            new AssetDescriptor<Texture>(AssetPaths.BALL, Texture.class);
    public static final AssetDescriptor<Texture> SABLE =
            new AssetDescriptor<Texture>(AssetPaths.SABLE, Texture.class);
    public static final AssetDescriptor<Texture> SEA =
            new AssetDescriptor<Texture>(AssetPaths.SEA, Texture.class);
    public static final AssetDescriptor<Texture> SKY =
            new AssetDescriptor<Texture>(AssetPaths.SKY, Texture.class);
    public static final AssetDescriptor<Texture> PALMIER =
            new AssetDescriptor<Texture>(AssetPaths.PALMIER, Texture.class);
    public static final AssetDescriptor<Texture> BASKET =
            new AssetDescriptor<Texture>(AssetPaths.BASKET, Texture.class);
    public static final AssetDescriptor<Texture> PANNAL =
            new AssetDescriptor<Texture>(AssetPaths.PANNAL, Texture.class);
    // Game3
    public static final AssetDescriptor<Texture> DOOR =
            new AssetDescriptor<Texture>(AssetPaths.DOOR, Texture.class);
    public static final AssetDescriptor<Texture> DOOR_LOCK =
            new AssetDescriptor<Texture>(AssetPaths.DOOR_LOCK, Texture.class);
    public static final AssetDescriptor<Texture> BATTERY =
            new AssetDescriptor<Texture>(AssetPaths.BATTERY, Texture.class);
    public static final AssetDescriptor<Texture> CLOSED_LOCK1 =
            new AssetDescriptor<Texture>(AssetPaths.CLOSED_LOCK1, Texture.class);
    public static final AssetDescriptor<Texture> CLOSED_LOCK2 =
            new AssetDescriptor<Texture>(AssetPaths.CLOSED_LOCK2, Texture.class);
    public static final AssetDescriptor<Texture> OPENED_LOCK1 =
            new AssetDescriptor<Texture>(AssetPaths.OPENED_LOCK1, Texture.class);
    public static final AssetDescriptor<Texture> OPENED_LOCK2 =
            new AssetDescriptor<Texture>(AssetPaths.OPENED_LOCK2, Texture.class);
    public static final AssetDescriptor<Texture> CROSS_T =
            new AssetDescriptor<Texture>(AssetPaths.CROSS_T, Texture.class);
    public static final AssetDescriptor<Texture> TCROSS_T =
            new AssetDescriptor<Texture>(AssetPaths.TCROSS_T, Texture.class);
    public static final AssetDescriptor<Texture> LINE_T =
            new AssetDescriptor<Texture>(AssetPaths.LINE_T, Texture.class);
    public static final AssetDescriptor<Texture> HALF_LINE_T =
            new AssetDescriptor<Texture>(AssetPaths.HALF_LINE_T, Texture.class);
    public static final AssetDescriptor<Texture> TURN_T =
            new AssetDescriptor<Texture>(AssetPaths.TURN_T, Texture.class);
    public static final AssetDescriptor<Texture> NEON_ROSE =
            new AssetDescriptor<Texture>(AssetPaths.NEON_ROSE, Texture.class);
    public static final AssetDescriptor<Texture> NEON_RED =
            new AssetDescriptor<Texture>(AssetPaths.NEON_RED, Texture.class);
    public static final AssetDescriptor<Texture> NEON_TURQUOISE =
            new AssetDescriptor<Texture>(AssetPaths.NEON_TURQUOISE, Texture.class);
    public static final AssetDescriptor<Texture> NEON_GREEN =
            new AssetDescriptor<Texture>(AssetPaths.NEON_GREEN, Texture.class);
    public static final AssetDescriptor<Texture> NEON_VIOLET =
            new AssetDescriptor<Texture>(AssetPaths.NEON_VIOLET, Texture.class);
    // STIDex
    public static final AssetDescriptor<Texture> DEX_BACK =
            new AssetDescriptor<Texture>(AssetPaths.DEX_BACK, Texture.class);
    public static final AssetDescriptor<Texture> ARROW =
            new AssetDescriptor<Texture>(AssetPaths.ARROW, Texture.class);
    // IntroG1
    public static final AssetDescriptor<Texture> DIF_PART1 =
            new AssetDescriptor<Texture>(AssetPaths.DIF_PART1, Texture.class); // % loaded at the start
    public static final AssetDescriptor<Texture> JUNGLE =
            new AssetDescriptor<Texture>(AssetPaths.JUNGLE, Texture.class);
    public static final AssetDescriptor<Texture> NIGHT_SKY =
            new AssetDescriptor<Texture>(AssetPaths.NIGHT_SKY, Texture.class);
    public static final AssetDescriptor<Texture> LEAVES =
            new AssetDescriptor<Texture>(AssetPaths.LEAVES, Texture.class);
    public static final AssetDescriptor<Texture> CROQUIS =
            new AssetDescriptor<Texture>(AssetPaths.CROQUIS, Texture.class);
    // IntroG3
    public static final AssetDescriptor<Texture> I3_HOUSE =
            new AssetDescriptor<Texture>(AssetPaths.I3_HOUSE, Texture.class);
    public static final AssetDescriptor<Texture> I3_INSIDE =
            new AssetDescriptor<Texture>(AssetPaths.I3_INSIDE, Texture.class);
    // Characters
    public static final AssetDescriptor<Texture> MI_NORMAL =
            new AssetDescriptor<Texture>(AssetPaths.MI_NORMAL, Texture.class);
    public static final AssetDescriptor<Texture> MI_HAPPY =
            new AssetDescriptor<Texture>(AssetPaths.MI_HAPPY, Texture.class);
    public static final AssetDescriptor<Texture> MI_WORRIED =
            new AssetDescriptor<Texture>(AssetPaths.MI_WORRIED, Texture.class);
    public static final AssetDescriptor<Texture> MI_SCARED =
            new AssetDescriptor<Texture>(AssetPaths.MI_SCARED, Texture.class);
    public static final AssetDescriptor<Texture> MI_SURPRISED =
            new AssetDescriptor<Texture>(AssetPaths.MI_SURPRISED, Texture.class);
    public static final AssetDescriptor<Texture> MI_CRY =
            new AssetDescriptor<Texture>(AssetPaths.MI_CRY, Texture.class);
    public static final AssetDescriptor<Texture> MI_TIRED =
            new AssetDescriptor<Texture>(AssetPaths.MI_TIRED, Texture.class);
    public static final AssetDescriptor<Texture> MI_UNI =
            new AssetDescriptor<Texture>(AssetPaths.MI_UNI, Texture.class);
    public static final AssetDescriptor<Texture> MI_JOJO_FACE =
            new AssetDescriptor<Texture>(AssetPaths.MI_JOJO_FACE, Texture.class);
    public static final AssetDescriptor<Texture> MI_JOJO_POSE =
            new AssetDescriptor<Texture>(AssetPaths.MI_JOJO_POSE, Texture.class);
    public static final AssetDescriptor<Texture> MI_JOJO_KANJI =
            new AssetDescriptor<Texture>(AssetPaths.MI_JOJO_KANJI, Texture.class);
    public static final AssetDescriptor<Texture> SERGENT1 =
            new AssetDescriptor<Texture>(AssetPaths.SERGENT1, Texture.class);
    public static final AssetDescriptor<Texture> SERGENT2 =
            new AssetDescriptor<Texture>(AssetPaths.SERGENT2, Texture.class);
    public static final AssetDescriptor<Texture> GODIVA =
            new AssetDescriptor<Texture>(AssetPaths.GODIVA, Texture.class);
    public static final AssetDescriptor<Texture> ALYX_OPEN =
            new AssetDescriptor<Texture>(AssetPaths.ALYX_OPEN, Texture.class);
    public static final AssetDescriptor<Texture> ALYX_NORMAL =
            new AssetDescriptor<Texture>(AssetPaths.ALYX_NORMAL, Texture.class);
    /*------------------
            Gadgets
     -------------------*/
    // Buttons
    public static final AssetDescriptor<Texture> BTN_CLOUD =
            new AssetDescriptor<Texture>(AssetPaths.BTN_CLOUD, Texture.class);
    public static final AssetDescriptor<Texture> BTN_LEAF =
            new AssetDescriptor<Texture>(AssetPaths.BTN_LEAF, Texture.class);
    public static final AssetDescriptor<Texture> BTN_ROCK =
            new AssetDescriptor<Texture>(AssetPaths.BTN_ROCK, Texture.class);
    public static final AssetDescriptor<Texture> BTN_SAND =
            new AssetDescriptor<Texture>(AssetPaths.BTN_SAND, Texture.class);
    public static final AssetDescriptor<Texture> BTN_VIRUS =
            new AssetDescriptor<Texture>(AssetPaths.BTN_VIRUS, Texture.class);
    public static final AssetDescriptor<Texture> BTN_WOOD =
            new AssetDescriptor<Texture>(AssetPaths.BTN_WOOD, Texture.class);
    // Bubbles
    public static final AssetDescriptor<Texture> BUBBLE_EXPL =
            new AssetDescriptor<Texture>(AssetPaths.BUBBLE_EXPL, Texture.class);
    public static final AssetDescriptor<Texture> BUBBLE_LEFT =
            new AssetDescriptor<Texture>(AssetPaths.BUBBLE_LEFT, Texture.class);
    public static final AssetDescriptor<Texture> BUBBLE_MID_LEFT =
            new AssetDescriptor<Texture>(AssetPaths.BUBBLE_MID_LEFT, Texture.class);
    public static final AssetDescriptor<Texture> BUBBLE_MID_RIGHT =
            new AssetDescriptor<Texture>(AssetPaths.BUBBLE_MID_RIGHT, Texture.class);
    public static final AssetDescriptor<Texture> BUBBLE_RIGHT =
            new AssetDescriptor<Texture>(AssetPaths.BUBBLE_RIGHT, Texture.class);
    // Others
    public static final AssetDescriptor<Texture> PAUSE_LOGO =
            new AssetDescriptor<Texture>(AssetPaths.PAUSE_LOGO, Texture.class);
    public static final AssetDescriptor<Texture> PLAY_LOGO =
            new AssetDescriptor<Texture>(AssetPaths.PLAY_LOGO, Texture.class);

    /* ******* *
     *  ATLAS  *
     * ******* */
    public static final AssetDescriptor<TextureAtlas> ANIM_DIF_HELL =
            new AssetDescriptor<TextureAtlas>(AssetPaths.ANIM_DIF_HELL, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> ANIM_DIF_INF =
            new AssetDescriptor<TextureAtlas>(AssetPaths.ANIM_DIF_INF, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> ANIM_SKY =
            new AssetDescriptor<TextureAtlas>(AssetPaths.ANIM_SKY, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> ANIM_EVIL_TOM =
            new AssetDescriptor<TextureAtlas>(AssetPaths.ANIM_EVIL_TOM, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> ANIM_EYES =
            new AssetDescriptor<TextureAtlas>(AssetPaths.ANIM_EYES, TextureAtlas.class);
//    public static final AssetDescriptor<TextureAtlas> ANIM_LEAVES =
//            new AssetDescriptor<TextureAtlas>(AssetPaths.ANIM_LEAVES, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> ANIM_ACTIONTEXT =
            new AssetDescriptor<TextureAtlas>(AssetPaths.ANIM_ACTIONTEXT, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> ANIM_MIREILLE_BLINK =
            new AssetDescriptor<TextureAtlas>(AssetPaths.ANIM_MIREILLE_BLINK, TextureAtlas.class);
    public static final AssetDescriptor<Texture> ANIM_MIREILLE_COUCOU_1 =
            new AssetDescriptor<Texture>(AssetPaths.ANIM_MIREILLE_COUCOU[0], Texture.class);
    public static final AssetDescriptor<Texture> ANIM_MIREILLE_COUCOU_2 =
            new AssetDescriptor<Texture>(AssetPaths.ANIM_MIREILLE_COUCOU[1], Texture.class);
    public static final AssetDescriptor<Texture> ANIM_MIREILLE_COUCOU_3 =
            new AssetDescriptor<Texture>(AssetPaths.ANIM_MIREILLE_COUCOU[2], Texture.class);
    public static final AssetDescriptor<Texture> ANIM_MIREILLE_COUCOU_4 =
            new AssetDescriptor<Texture>(AssetPaths.ANIM_MIREILLE_COUCOU[3], Texture.class);
    public static final AssetDescriptor<Texture> ANIM_MIREILLE_COUCOU_5 =
            new AssetDescriptor<Texture>(AssetPaths.ANIM_MIREILLE_COUCOU[4], Texture.class);
    public static final AssetDescriptor<Texture> ANIM_MIREILLE_COUCOU_6 =
            new AssetDescriptor<Texture>(AssetPaths.ANIM_MIREILLE_COUCOU[5], Texture.class);
    public static final AssetDescriptor<Texture> ANIM_MIREILLE_COUCOU_7 =
            new AssetDescriptor<Texture>(AssetPaths.ANIM_MIREILLE_COUCOU[6], Texture.class);
//    public static final AssetDescriptor<TextureAtlas> ANIM_MIREILLE_COUCOU =
//            new AssetDescriptor<TextureAtlas>(AssetPaths.ANIM_MIREILLE_COUCOU, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> ANIM_MIREILLU =
            new AssetDescriptor<TextureAtlas>(AssetPaths.ANIM_MIREILLU, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> ANIM_NEON_DOOR =
            new AssetDescriptor<TextureAtlas>(AssetPaths.ANIM_NEON_DOOR, TextureAtlas.class); // % loaded at the start
    /* ******* *
     *  SKINS  *
     * ******* */
    public static final AssetDescriptor<Skin> SKIN_COMIC_UI =
            new AssetDescriptor<Skin>(AssetPaths.SKIN_COMIC_UI, Skin.class);
    /* ******** *
     *  MUSICS  *
     * ******** */

    public static final AssetDescriptor<Music> MUSIC_MM =
            new AssetDescriptor<Music>(AssetPaths.MUSIC_MM, Music.class); // % loaded at the start
    public static final AssetDescriptor<Music> MUSIC_J =
            new AssetDescriptor<Music>(AssetPaths.MUSIC_J, Music.class);
    public static final AssetDescriptor<Music> MUSIC_GAME1 =
            new AssetDescriptor<Music>(AssetPaths.MUSIC_GAME1, Music.class);
    public static final AssetDescriptor<Music> MUSIC_GAME2 =
            new AssetDescriptor<Music>(AssetPaths.MUSIC_GAME2, Music.class);
    public static final AssetDescriptor<Music> MUSIC_GAME3 =
            new AssetDescriptor<Music>(AssetPaths.MUSIC_GAME3, Music.class);

    /* ******** *
     *  SOUNDS  *
     * ******** */

    public static final AssetDescriptor<Sound> SOUND_CLICKED_BTN =
            new AssetDescriptor<Sound>(AssetPaths.SOUND_CLICKED_BTN, Sound.class); // % loaded at the start
    public static final AssetDescriptor<Sound> SOUND_COIN =
            new AssetDescriptor<Sound>(AssetPaths.SOUND_COIN, Sound.class);
    public static final AssetDescriptor<Sound> SOUND_FAIL =
            new AssetDescriptor<Sound>(AssetPaths.SOUND_FAIL, Sound.class);
    public static final AssetDescriptor<Sound> SOUND_GAMEOVER =
            new AssetDescriptor<Sound>(AssetPaths.SOUND_GAMEOVER, Sound.class);
    public static final AssetDescriptor<Sound> SOUND_GAMESTART =
            new AssetDescriptor<Sound>(AssetPaths.SOUND_GAMESTART, Sound.class); // % loaded at the start
    public static final AssetDescriptor<Sound> SOUND_HINT =
            new AssetDescriptor<Sound>(AssetPaths.SOUND_HINT, Sound.class); // % loaded at the start
    public static final AssetDescriptor<Sound> SOUND_JUMP_V1 =
            new AssetDescriptor<Sound>(AssetPaths.SOUND_JUMP_V1, Sound.class);
    public static final AssetDescriptor<Sound> SOUND_JUMP_V2 =
            new AssetDescriptor<Sound>(AssetPaths.SOUND_JUMP_V2, Sound.class);
    public static final AssetDescriptor<Sound> SOUND_LEVEL_UP =
            new AssetDescriptor<Sound>(AssetPaths.SOUND_LEVEL_UP, Sound.class);
    public static final AssetDescriptor<Sound> SOUND_MENU_SEL =
            new AssetDescriptor<Sound>(AssetPaths.SOUND_MENU_SEL, Sound.class);
    public static final AssetDescriptor<Sound> SOUND_NOM_NOM =
            new AssetDescriptor<Sound>(AssetPaths.SOUND_NOM_NOM, Sound.class);
    public static final AssetDescriptor<Sound> SOUND_PAUSE =
            new AssetDescriptor<Sound>(AssetPaths.SOUND_PAUSE, Sound.class); // % loaded at the start
    public static final AssetDescriptor<Sound> SOUND_POWER_UP =
            new AssetDescriptor<Sound>(AssetPaths.SOUND_POWER_UP, Sound.class);
    public static final AssetDescriptor<Sound> SOUND_PUNCH =
            new AssetDescriptor<Sound>(AssetPaths.SOUND_PUNCH, Sound.class);
    public static final AssetDescriptor<Sound> SOUND_SUCCESS =
            new AssetDescriptor<Sound>(AssetPaths.SOUND_SUCCESS, Sound.class);
    public static final AssetDescriptor<Sound> SOUND_BOUP1 =
            new AssetDescriptor<Sound>(AssetPaths.SOUND_BOUP1, Sound.class); // % loaded at the start
    public static final AssetDescriptor<Sound> SOUND_BOUP9 =
            new AssetDescriptor<Sound>(AssetPaths.SOUND_BOUP9, Sound.class); // % loaded at the start

    private AssetDescriptors() {}
}
