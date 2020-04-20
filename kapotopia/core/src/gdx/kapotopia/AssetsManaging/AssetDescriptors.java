package gdx.kapotopia.AssetsManaging;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetDescriptors {

    /* ********** *
     *  TEXTURES  *
     * ********** */
    // Main Menu
    public static final AssetDescriptor<Texture> MM_PART1 =
            new AssetDescriptor<Texture>(AssetPaths.MM_PART1, Texture.class);
    public static final AssetDescriptor<Texture> MM_PART3 =
            new AssetDescriptor<Texture>(AssetPaths.MM_PART3, Texture.class);
    public static final AssetDescriptor<Texture> MM_PART4 =
            new AssetDescriptor<Texture>(AssetPaths.MM_PART4, Texture.class);

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
            new AssetDescriptor<TextureAtlas>(AssetPaths.ANIM_NEON_DOOR, TextureAtlas.class);
    private AssetDescriptors() {}
}
