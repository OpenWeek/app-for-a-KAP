package gdx.kapotopia.Game1;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;

public class VirusContainer {
    private AssetDescriptor<Texture> texture;
    private String name;
    private String description;
    private boolean isIst;
    private boolean isMaybeIst;

    public VirusContainer(String texturePath, String name, boolean isIst, boolean isMaybeIst, String description) {
        this.texture = new AssetDescriptor<Texture>(texturePath, Texture.class);
        this.name = name;
        this.description = description;
        this.isIst = isIst;
        this.isMaybeIst = isMaybeIst;
    }

    public AssetDescriptor<Texture> getTexture() {
        return this.texture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIst() {
        return isIst;
    }

    public void setIst(boolean ist) {
        isIst = ist;
    }

    public boolean isMaybeIst() {
        return isMaybeIst;
    }

    public void setMaybeIst(boolean maybeIst) {
        isMaybeIst = maybeIst;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
