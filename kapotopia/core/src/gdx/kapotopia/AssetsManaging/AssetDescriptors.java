package gdx.kapotopia.AssetsManaging;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetDescriptors {

    /* ********** *
     *  TEXTURES  *
     * ********** */
    // Main Menu
    public static final AssetDescriptor<Texture> MM_PART1 =
            new AssetDescriptor<Texture>(AssetPaths.MM_PART1, Texture.class); // % loaded at the start
    public static final AssetDescriptor<Texture> MM_PART3 =
            new AssetDescriptor<Texture>(AssetPaths.MM_PART3, Texture.class); // % loaded at the start
    public static final AssetDescriptor<Texture> MM_PART4 =
            new AssetDescriptor<Texture>(AssetPaths.MM_PART4, Texture.class); // % loaded at the start
    public static final AssetDescriptor<Texture> DIF_PART1 =
            new AssetDescriptor<Texture>(AssetPaths.DIF_PART1, Texture.class); // % loaded at the start

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
//    public static final AssetDescriptor<TextureAtlas> ANIM_MIREILLE_COUCOU =
//            new AssetDescriptor<TextureAtlas>(AssetPaths.ANIM_MIREILLE_COUCOU, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> ANIM_MIREILLU =
            new AssetDescriptor<TextureAtlas>(AssetPaths.ANIM_MIREILLU, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> ANIM_NEON_DOOR =
            new AssetDescriptor<TextureAtlas>(AssetPaths.ANIM_NEON_DOOR, TextureAtlas.class); // % loaded at the start

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
