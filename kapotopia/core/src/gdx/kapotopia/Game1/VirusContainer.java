package gdx.kapotopia.Game1;

public class VirusContainer {
    private String texturePath;
    private String name;

    public VirusContainer(String texturePath, String name) {
        this.texturePath = texturePath;
        this.name = name;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
