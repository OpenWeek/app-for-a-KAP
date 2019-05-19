package gdx.kapotopia;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public final class AssetsManager {
    private static AssetsManager instance = new AssetsManager();
    private static List<TextureHelper> textureList = new ArrayList<TextureHelper>();

    public static AssetsManager getInstance() {
        return instance;
    }

    private AssetsManager() {}

    /**
     * Get The texture by its path
     * @param path
     * @return
     */
    public Texture getTextureByPath(final String path) {
        final TextureHelper researchResult = searchTexture(path);
        if(researchResult != null) {
            return researchResult.getTexture();
        }
        // Si il elle n'est pas dedans, on la crée, on l'ajoute à la liste et on la renvoie
        final TextureHelper newTextureHelper = new TextureHelper(path);
        textureList.add(newTextureHelper);
        return newTextureHelper.getTexture();
    }

    /**
     * Dispose the ressources of a Texture given its internalPath
     * @param internalPath a String
     */
    public void disposeTexture(final String internalPath) {
        final TextureHelper th = searchTexture(internalPath);
        if(th != null) {
            th.getTexture().dispose();
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
     * Dispose all ressources taken by textures
     */
    public void disposeAllResources() {
        for (TextureHelper th : textureList) {
            th.getTexture().dispose();
            textureList.remove(th);
        }
    }

    /**
     * determine if the internal texture list already contains or not the texture
     * identified by the given path
     * @param path a String representing a path in the root of assets/
     * @return the TextureHelper if its already in memory, null otherwise
     */
    private TextureHelper searchTexture(final String path) {
        //TODO use another searching algorithm
        for (TextureHelper th : textureList) {
            if (th.getInternalPath().equals(path))
                return th;
        }
        return null;
    }

    public final class TextureHelper {
        private String internalPath;
        private Texture texture;

        public TextureHelper(String internalPath) {
            this.internalPath = internalPath;
            this.texture = new Texture(internalPath);
        }

        public String getInternalPath() {
            return internalPath;
        }

        public void setInternalPath(String internalPath) {
            this.internalPath = internalPath;
        }

        public Texture getTexture() {
            return texture;
        }

        public void setTexture(Texture texture) {
            this.texture = texture;
        }
    }
}
