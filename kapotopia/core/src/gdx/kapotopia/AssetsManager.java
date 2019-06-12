package gdx.kapotopia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public final class AssetsManager {
    private static AssetsManager instance = new AssetsManager();
    private static List<RessourceHelper> textureList = new ArrayList<RessourceHelper>();
    private static List<RessourceHelper> soundList = new ArrayList<RessourceHelper>();

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
        final RessourceHelper researchResult = searchRessource(path, 0);
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
        final RessourceHelper researchResult = searchRessource(path, 1);
        if(researchResult != null) {
            return (Sound) researchResult.getRessource();
        }

        final RessourceHelper newRessourceHelper = new RessourceHelper<Sound>(path, Gdx.audio.newSound(Gdx.files.internal(path)));
        soundList.add(newRessourceHelper);
        return (Sound) newRessourceHelper.getRessource();
    }

    /**
     * Dispose the ressources of a VIRUS_TYPE given its internalPath
     * @param internalPath a String
     */
    public void disposeTexture(final String internalPath) {
        final RessourceHelper th = searchRessource(internalPath, 0);
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
        final RessourceHelper th = searchRessource(internalPath, 1);
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
     * Dispose all ressources taken by textures
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
    }

    /**
     * determine if the internal ressource list already contains or not the ressource
     * identified by the given path
     * @param path a String representing a path in the root of assets/
     * @param ressource a number, 0 for selecting textureList, 1 for soundList, default is textureList
     * @return the RessourceHelper if its already in memory, null otherwise
     */
    private RessourceHelper searchRessource(final String path, int ressource) {
        //TODO use another searching algorithm
        List<RessourceHelper> l;
        switch (ressource) {
            case 0:
                l = textureList;
                break;
            case 1:
                l = soundList;
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
