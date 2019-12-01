package gdx.kapotopia.Helpers.Builders;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Arrays;

import gdx.kapotopia.AssetsManaging.AssetsManager;

public class AnimationBuilder {
    private ArrayList<TextureRegion> framesArrayList;
    private Animation.PlayMode playMode;
    private float frameDuration;

    public AnimationBuilder(float frameDuration) {
        this.framesArrayList = new ArrayList<TextureRegion>();
        this.playMode = Animation.PlayMode.NORMAL;
        this.frameDuration = frameDuration;
    }

    public AnimationBuilder addNewFrame(String framePath) {
        AssetsManager man = AssetsManager.getInstance();
        framesArrayList.add(new TextureRegion(man.getTextureByPath(framePath)));
        return this;
    }

    public AnimationBuilder addNewFrame(TextureRegion frame) {
        framesArrayList.add(frame);
        return this;
    }

    public AnimationBuilder addFrames(String[] framePaths) {
        AssetsManager man = AssetsManager.getInstance();
        for (String path : framePaths) {
            framesArrayList.add(new TextureRegion(man.getTextureByPath(path)));
        }
        return this;
    }

    public AnimationBuilder addFrames(TextureAtlas.AtlasRegion[] frames) {
        framesArrayList.addAll(Arrays.asList(frames));
        return this;
    }

    public AnimationBuilder withPlayMode(Animation.PlayMode playMode) {
        this.playMode = playMode;
        return this;
    }

    public Animation<TextureRegion> build() {
        final Animation<TextureRegion> animation;
        if (framesArrayList.isEmpty()) {
            animation = new Animation<TextureRegion>(frameDuration);
        }else{
            TextureRegion[] frames = framesArrayList.toArray(new TextureRegion[0]);
            animation = new Animation<TextureRegion>(frameDuration, frames);
        }
        animation.setPlayMode(playMode);

        return animation;
    }
}
