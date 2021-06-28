package gdx.kapotopia.Animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Helpers.Builders.AnimationBuilder;
import gdx.kapotopia.Kapotopia;

public class MireilleCoucouAnimation extends AnimationAbstract {

    public MireilleCoucouAnimation(Kapotopia game, Animation.PlayMode playMode) {
        //TODO Refactor this using an atlas
        game.ass.load(AssetDescriptors.ANIM_MIREILLE_COUCOU_1);
        game.ass.load(AssetDescriptors.ANIM_MIREILLE_COUCOU_2);
        game.ass.load(AssetDescriptors.ANIM_MIREILLE_COUCOU_3);
        game.ass.load(AssetDescriptors.ANIM_MIREILLE_COUCOU_4);
        game.ass.load(AssetDescriptors.ANIM_MIREILLE_COUCOU_5);
        game.ass.load(AssetDescriptors.ANIM_MIREILLE_COUCOU_6);
        game.ass.load(AssetDescriptors.ANIM_MIREILLE_COUCOU_7);
        game.ass.finishLoading();
        Gdx.app.log(this.getClass().getSimpleName(), game.ass.getDiagnostics());

        AnimationBuilder builder = new AnimationBuilder(0.05f)
                .withPlayMode(playMode)
                .addNewFrame(new TextureRegion(game.ass.get(AssetDescriptors.ANIM_MIREILLE_COUCOU_1)))
                .addNewFrame(new TextureRegion(game.ass.get(AssetDescriptors.ANIM_MIREILLE_COUCOU_2)))
                .addNewFrame(new TextureRegion(game.ass.get(AssetDescriptors.ANIM_MIREILLE_COUCOU_3)))
                .addNewFrame(new TextureRegion(game.ass.get(AssetDescriptors.ANIM_MIREILLE_COUCOU_4)))
                .addNewFrame(new TextureRegion(game.ass.get(AssetDescriptors.ANIM_MIREILLE_COUCOU_5)))
                .addNewFrame(new TextureRegion(game.ass.get(AssetDescriptors.ANIM_MIREILLE_COUCOU_6)))
                .addNewFrame(new TextureRegion(game.ass.get(AssetDescriptors.ANIM_MIREILLE_COUCOU_7)));

        setAnimation(builder.build());
    }
}
