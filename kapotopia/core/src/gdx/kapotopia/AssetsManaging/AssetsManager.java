package gdx.kapotopia.AssetsManaging;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import gdx.kapotopia.DataStructures.RedBlackBST;
import gdx.kapotopia.DataStructures.Tree;
import gdx.kapotopia.Fonts.FontHelper;

public final class AssetsManager {
    private static AssetsManager instance = new AssetsManager();
    private static Tree<String, RessourceHelper> textureL = new RedBlackBST<String, RessourceHelper>();
    private static Tree<String, RessourceHelper> soundList = new RedBlackBST<String, RessourceHelper>();
    private static Tree<String, RessourceHelper> stageList = new RedBlackBST<String, RessourceHelper>();
    private static Tree<String, RessourceHelper> musicList = new RedBlackBST<String, RessourceHelper>();
    private static Tree<String, RessourceHelper> fontList = new RedBlackBST<String, RessourceHelper>();
    private static Tree<String, RessourceHelper> atlasList = new RedBlackBST<String, RessourceHelper>();
    private static Tree<String, RessourceHelper> skinList = new RedBlackBST<String, RessourceHelper>();

    public static AssetsManager getInstance() {
        return instance;
    }

    private AssetsManager() {}

    /* ***************** *
     *  ADD/GET METHODS
     * ***************** */

    /**
     * Get The ressource by its path
     * @param path
     * @return
     */
    public Texture getTextureByPath(final String path) {
        final RessourceHelper res = textureL.get(path);
        if (res != null) {
            return (Texture) res.getRessource();
        }

        final RessourceHelper<Texture> newResHelper = new RessourceHelper<Texture>(path, new Texture(Gdx.files.internal(path)));
        textureL.put(path, newResHelper);
        return newResHelper.getRessource();
    }

    /**
     * Get The ressource by its path
     * @param path
     * @return
     */
    public Sound getSoundByPath(final String path) {
        final RessourceHelper researchResult = soundList.get(path);
        if(researchResult != null) {
            return (Sound) researchResult.getRessource();
        }

        final RessourceHelper<Sound> newRessourceHelper = new RessourceHelper<Sound>(path, Gdx.audio.newSound(Gdx.files.internal(path)));
        soundList.put(path, newRessourceHelper);
        return newRessourceHelper.getRessource();
    }

    /**
     * Get The ressource by its name
     * @param name
     * @return
     */
    public Stage getStageByName(final String name) {
        final RessourceHelper researchResult = stageList.get(name);
        if (researchResult != null)
            return (Stage) researchResult.getRessource();
        return null;
    }

    /**
     * add a new stage in the ressourceList
     * @param stage the Stage object to add
     * @param name it's name (have to be unique)
     * @return
     */
    public boolean addStage(final Stage stage, final String name) {
        final RessourceHelper researchResult = stageList.get(name);
        if(researchResult != null)
            return false;
        final RessourceHelper<Stage> newRessourceHelper = new RessourceHelper<Stage>(name, stage);
        stageList.put(name, newRessourceHelper);
        return true;
    }

    public Music getMusicByPath(final String path) {
        final RessourceHelper researchResult = musicList.get(path);
        if(researchResult != null) {
            return (Music) researchResult.getRessource();
        }
        // Si il elle n'est pas dedans, on la crée, on l'ajoute à la liste et on la renvoie
        final RessourceHelper<Music> newRessourceHelper = new RessourceHelper<Music>(path, Gdx.audio.newMusic(Gdx.files.internal(path)));
        musicList.put(path, newRessourceHelper);
        return newRessourceHelper.getRessource();
    }

    /**
     * add a new font in the ressourceList. The name given rules as it's searching key.
     * It is created given it's internalPath, it's size and color
     *  NOTE:   if a new font with the same name of another one already stored, the add operation will fail,
     *          it won't overwrite the existing font
     * @param name the name of the font, it is used as a key and therefore has to be unique to avoid conflicts
     * @param path
     * @param size
     * @param color
     * @return the asked style
     */
    public TextButton.TextButtonStyle addStyleFont(final String name, final String path, final int size, final Color color) {
        final RessourceHelper researchResult = fontList.get(name);
        if(researchResult != null) {
            return (TextButton.TextButtonStyle) researchResult.getRessource();
        }

        final TextButton.TextButtonStyle style = null;
        final RessourceHelper<TextButton.TextButtonStyle> newRessourceHelper = new RessourceHelper<TextButton.TextButtonStyle>(name, style);
        fontList.put(name, newRessourceHelper);
        return style;
    }

    /**
     * Get the font associated with the given key/name.
     * @param name the name attributed to the font
     * @return the style given the name or null if no font associated to that name was found
     */
    public TextButton.TextButtonStyle getStyleFontByName(final String name) {
        final RessourceHelper researchResult = fontList.get(name);
        if(researchResult != null) {
            return (TextButton.TextButtonStyle) researchResult.getRessource();
        }
        return null;
    }

    public TextureAtlas getAtlasByPath(final String path) {
        final RessourceHelper researchResult = atlasList.get(path);
        if(researchResult != null) {
            return (TextureAtlas) researchResult.getRessource();
        }
        // Si il elle n'est pas dedans, on la crée, on l'ajoute à la liste et on la renvoie
        final RessourceHelper<TextureAtlas> newRessourceHelper = new RessourceHelper<TextureAtlas>(path, new TextureAtlas(path));
        atlasList.put(path, newRessourceHelper);
        return newRessourceHelper.getRessource();
    }

    public Skin getSkinByPath(final String path) {
        final RessourceHelper researchResult = skinList.get(path);
        if (researchResult != null) {
            return (Skin) researchResult.getRessource();
        }
        final RessourceHelper<Skin> newRessourceHelper = new RessourceHelper<Skin>(path, new Skin(Gdx.files.internal(path)));
        skinList.put(path, newRessourceHelper);
        return newRessourceHelper.getRessource();
    }

    /* ***************** *
     *  DISPOSE METHODS
     * ***************** */

    /**
     * Dispose the ressources of a VIRUS_TYPE given its internalPath
     * @param internalPath the texture file internalPath
     */
    public void disposeTexture(final String internalPath) {
        final RessourceHelper th = textureL.get(internalPath);
        if(th != null) {
            final Texture t = (Texture) th.getRessource();
            t.dispose();
            textureL.delete(internalPath);
        }
    }

    /**
     * Dispose the ressources of an array of Textures given their internalPath
     * @param internalPaths an Array of Strings
     */
    public void disposeTexture(final String[] internalPaths) {
        for(String path : internalPaths) {
            disposeTexture(path);
        }
    }

    /**
     * Dispose the ressources of a Sound given its internalPath
     * @param internalPath a String
     */
    public void disposeSound(final String internalPath) {
        final RessourceHelper th = soundList.get(internalPath);
        if(th != null) {
            final Sound s = (Sound) th.getRessource();
            s.dispose();
            soundList.delete(internalPath);
        }
    }

    /**
     * Dispose the ressources of an array of Sounds given their internalPath
     * @param internalPaths an Array of Strings
     */
    public void disposeSound(final String[] internalPaths) {
        for (String path : internalPaths) {
            disposeSound(path);
        }
    }

    /**
     * Dispose the ressources of a Stage given its name
     * @param name a String
     */
    public void disposeStage(final String name) {
        final RessourceHelper th = stageList.get(name);
        if (th != null) {
            final Stage s = (Stage) th.getRessource();
            s.dispose();
            stageList.delete(name);
        }
    }

    /**
     * Dispose the ressources of an array of Stages given their names
     * @param names an Array of Strings
     */
    public void disposeStage(final String[] names) {
        for (String name : names) {
            disposeStage(name);
        }
    }

    /**
     * Dispose the ressources of a Music given its internalPath
     * @param internalPath a String
     */
    public void disposeMusic(final String internalPath) {
        final RessourceHelper th = musicList.get(internalPath);
        if(th != null) {
            final Music s = (Music) th.getRessource();
            s.dispose();
            musicList.delete(internalPath);
        }
    }

    /**
     * Dispose the ressources of an array of Musics given their internalPath
     * @param internalPaths an Array of Strings
     */
    public void disposeMusic(final String[] internalPaths) {
        for (String path : internalPaths) {
            disposeMusic(path);
        }
    }

    /**
     * Dispose the stylefont given it's name
     * @param name the key representing the font
     */
    public void disposeFont(final String name) {
        final RessourceHelper th = fontList.get(name);
        if(th != null) {
            final TextButton.TextButtonStyle tbs = (TextButton.TextButtonStyle) th.getRessource();
            tbs.font.dispose();
            fontList.delete(name);
        }
    }

    /**
     * Dispose multiple stylefonts given their names
     * @param names the keys representing the fonts
     */
    public void disposeFont(final String[] names) {
        for ( final String name: names ) {
            disposeFont(name);
        }
    }

    public void disposeAtlas(final String path) {
        final RessourceHelper th = atlasList.get(path);
        if(th != null) {
            final TextureAtlas atlas = (TextureAtlas) th.getRessource();
            atlas.dispose();
            atlasList.delete(path);
        }
    }

    public void disposeAtlas(final String[] paths) {
        for (final String path : paths) {
            disposeAtlas(path);
        }
    }

    public void disposeSkin(final String path) {
        final RessourceHelper th = skinList.get(path);
        if (th != null) {
            final Skin skin = (Skin) th.getRessource();
            skin.dispose();
            skinList.delete(path);
        }
    }

    public void disposekin(final String[] paths) {
        for (final String path : paths) {
            disposeSkin(path);
        }
    }

    /**
     * Dispose all ressources taken by textures, sounds, musics, fonts and stages
     */
    public void disposeAllResources() {
        for (String key : textureL) {
            RessourceHelper res = textureL.get(key);
            final Texture t = (Texture) res.getRessource();
            t.dispose();
            textureL.delete(key);
        }

        for (String key : soundList) {
            RessourceHelper res = soundList.get(key);
            final Sound s = (Sound) res.getRessource();
            s.dispose();
            soundList.delete(key);
        }

        for (String key : stageList) {
            RessourceHelper res = stageList.get(key);
            final Stage s = (Stage) res.getRessource();
            s.dispose();
            stageList.delete(key);
        }

        for (String key : musicList) {
            RessourceHelper res = musicList.get(key);
            final Music m = (Music) res.getRessource();
            m.dispose();
            musicList.delete(key);
        }

        for (String key : fontList) {
            RessourceHelper res = fontList.get(key);
            final TextButton.TextButtonStyle style = (TextButton.TextButtonStyle) res.getRessource();
            style.font.dispose();
            fontList.delete(key);
        }

        for (String key : atlasList) {
            RessourceHelper res = atlasList.get(key);
            final TextureAtlas atlas = (TextureAtlas) res.getRessource();
            atlas.dispose();
            atlasList.delete(key);
        }

        for (String key : skinList) {
            RessourceHelper res = atlasList.get(key);
            final Skin skin = (Skin) res.getRessource();
            skin.dispose();
            skinList.delete(key);
        }
    }

    private final class RessourceHelper<T> {
        private String internalPath;
        private T ressource;

        public RessourceHelper(String internalPath, T t) {
            this.internalPath = internalPath;
            this.ressource = t;
        }

        public String getInternalPath() {
            return internalPath;
        }

        public void setInternalPath(String internalPath) {
            this.internalPath = internalPath;
        }

        public T getRessource() {
            return ressource;
        }

        public void setRessource(T texture) {
            this.ressource = texture;
        }
    }
}
