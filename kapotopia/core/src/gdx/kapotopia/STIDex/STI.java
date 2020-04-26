package gdx.kapotopia.STIDex;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;

import org.jetbrains.annotations.NotNull;

public class STI implements Comparable {
    private String name;
    private String description;
    private AssetDescriptor<Texture> texture;
    private String type;

    public STI(String name, String description, String texturePath, String type) {
        this.name = name;
        this.description = description;
        this.texture = new AssetDescriptor<Texture>(texturePath, Texture.class);
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AssetDescriptor<Texture> getTexture() {
        return texture;
    }

    public void setTexturePath(AssetDescriptor<Texture> texture) {
        this.texture = texture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        if (o instanceof STI) {
            STI tmp = (STI) o;
            return tmp.name.compareTo(this.name);
        } else {
            return -1;
        }
    }
}