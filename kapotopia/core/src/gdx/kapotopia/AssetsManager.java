package gdx.kapotopia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Predicate;

import java.util.ArrayList;
import java.util.List;

public final class AssetsManager {
    private static AssetsManager instance = new AssetsManager();
    // ArrayList suffisant car pas bcp d'éléments
    private static List<RessourceHelper> textureList = new ArrayList<RessourceHelper>();
    private static List<RessourceHelper> soundList = new ArrayList<RessourceHelper>();
    private static List<RessourceHelper> stageList = new ArrayList<RessourceHelper>();
    private static List<RessourceHelper> musicList = new ArrayList<RessourceHelper>();

    public static AssetsManager getInstance() {
        return instance;
    }

    private AssetsManager() {}

    /**
     * Get The ressource by its path
     * @param path
     * @return
     */
    public Texture getTextureByPath(final String path) {
        final RessourceHelper researchResult = searchRessource(path, AssetType.TEXTURE);
        if(researchResult != null) {
            return (Texture) researchResult.getRessource();
        }
        // Si il elle n'est pas dedans, on la crée, on l'ajoute à la liste et on la renvoie
        final RessourceHelper newRessourceHelper = new RessourceHelper<Texture>(path, new Texture(Gdx.files.internal(path)));
        textureList.add(newRessourceHelper);
        return (Texture) newRessourceHelper.getRessource();
    }

    /**
     * Get The ressource by its path
     * @param path
     * @return
     */
    public Sound getSoundByPath(final String path) {
        final RessourceHelper researchResult = searchRessource(path, AssetType.SOUND);
        if(researchResult != null) {
            return (Sound) researchResult.getRessource();
        }

        final RessourceHelper newRessourceHelper = new RessourceHelper<Sound>(path, Gdx.audio.newSound(Gdx.files.internal(path)));
        soundList.add(newRessourceHelper);
        return (Sound) newRessourceHelper.getRessource();
    }

    /**
     * Get The ressource by its name
     * @param name
     * @return
     */
    public Stage getStageByName(final String name) {
        final RessourceHelper researchResult = searchRessource(name, AssetType.STAGE);
        if(researchResult != null)
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
        final RessourceHelper researchResult = searchRessource(name, AssetType.STAGE);
        if(researchResult != null)
            return false;
        final RessourceHelper newRessourceHelper = new RessourceHelper<Stage>(name, stage);
        stageList.add(newRessourceHelper);
        return true;
    }

    public Music getMusicByPath(final String path) {
        final RessourceHelper researchResult = searchRessource(path, AssetType.MUSIC);
        if(researchResult != null) {
            return (Music) researchResult.getRessource();
        }
        // Si il elle n'est pas dedans, on la crée, on l'ajoute à la liste et on la renvoie
        final RessourceHelper newRessourceHelper = new RessourceHelper<Music>(path, Gdx.audio.newMusic(Gdx.files.internal(path)));
        musicList.add(newRessourceHelper);
        return (Music) newRessourceHelper.getRessource();
    }

    /**
     * Dispose the ressources of a VIRUS_TYPE given its internalPath
     * @param internalPath a String
     */
    public void disposeTexture(final String internalPath) {
        final RessourceHelper th = searchRessource(internalPath, AssetType.TEXTURE);
        if(th != null) {
            final Texture t = (Texture) th.getRessource();
            t.dispose();
            textureList.remove(th);
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
        final RessourceHelper th = searchRessource(internalPath, AssetType.SOUND);
        if(th != null) {
            final Sound s = (Sound) th.getRessource();
            s.dispose();
            soundList.remove(th);
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
        final RessourceHelper th = searchRessource(name, AssetType.STAGE);
        if (th != null) {
            final Stage s = (Stage) th.getRessource();
            s.dispose();
            stageList.remove(th);
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
        final RessourceHelper th = searchRessource(internalPath, AssetType.MUSIC);
        if(th != null) {
            final Music s = (Music) th.getRessource();
            s.dispose();
            musicList.remove(th);
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
     * Dispose all ressources taken by textures, sounds and stages
     */
    public void disposeAllResources() {
        for (RessourceHelper th : textureList) {
            final Texture t = (Texture) th.getRessource();
            t.dispose();
            textureList.remove(th);
        }

        for (RessourceHelper th : soundList) {
            final Sound s = (Sound) th.getRessource();
            s.dispose();
            soundList.remove(th);
        }

        for (RessourceHelper th : stageList) {
            final Stage s = (Stage) th.getRessource();
            s.dispose();
            stageList.remove(th);
        }

        for (RessourceHelper th : musicList) {
            final Music m = (Music) th.getRessource();
            m.dispose();
            musicList.remove(th);
        }
    }

    /**
     * determine if the internal ressource list already contains or not the ressource
     * identified by the given path
     * @param path a String representing a path in the root of assets/
     * @param ressource a number, 0 for selecting textureList, 1 for soundList, default is textureList
     * @return the RessourceHelper if its already in memory, null otherwise
     */
    private RessourceHelper searchRessource(final String path, AssetType ressource) {
        List<RessourceHelper> l;
        switch (ressource) {
            case TEXTURE:
                l = textureList;
                break;
            case SOUND:
                l = soundList;
                break;
            case STAGE:
                l = stageList;
                break;
            case MUSIC:
                l = musicList;
                break;
            default:
                l = textureList;
        }
        for (RessourceHelper th : l) {
            if (th.getInternalPath().equals(path))
                return th;
        }
        return null;
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
