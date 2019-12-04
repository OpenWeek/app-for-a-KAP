package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import gdx.kapotopia.Animations.LetsgoG1Animation;
import gdx.kapotopia.Animations.MireilleBlinkingAnimation;
import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Helpers.ChangeScreenListener;
import gdx.kapotopia.Helpers.Builders.TextButtonBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localization;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.Utils;

public class World4 implements Screen {

    private Kapotopia game;
    private Stage stage;
    // Test Animation
    private Animation<TextureRegion> animTest;
    private Animation<TextureRegion> animTest2;
    private float stateTime;
    private SpriteBatch spriteBatch;

    public World4(final Kapotopia game) {

        this.game = game;
        Texture fond = AssetsManager.getInstance().getTextureByPath("FondNiveauBlanc2.png");
        Image imgFond = new Image(fond);
        stage = new Stage(game.viewport);
        stage.addActor(imgFond);

        TextButton.TextButtonStyle style = Utils.getStyleFont("COMMS.ttf", 60);
        Label soon = new Label(Localization.getInstance().getString("soon_label"), new Label.LabelStyle(style.font, style.fontColor));
        soon.setPosition(50, game.viewport.getWorldHeight() * 0.8f);
        soon.setWrap(true);
        soon.setWidth(game.viewport.getWorldWidth() - 200);
        soon.setHeight(300);
        soon.setVisible(true);
        stage.addActor(soon);

        TextButton back = new TextButtonBuilder(Localization.getInstance().getString("back_button")).withStyle(style)
                .withPosition(game.viewport.getWorldWidth() / 2, 50).isVisible(true)
                .withListener(new ChangeScreenListener(game, ScreenType.MAINMENU, ScreenType.WORLD4)).build();
        stage.addActor(back);

        // Animation test
        this.animTest = new LetsgoG1Animation(Animation.PlayMode.LOOP).getAnimation();
        this.animTest2 = new MireilleBlinkingAnimation(Animation.PlayMode.LOOP).getAnimation();
        spriteBatch = new SpriteBatch();
        stateTime = 0f;

        AssetsManager.getInstance().addStage(stage, "world3");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += delta;
        stage.act();
        stage.draw();

        //Animation test
        TextureRegion currentFrameTest1 = animTest.getKeyFrame(stateTime, true);
        TextureRegion currentFrameTest2 = animTest2.getKeyFrame(stateTime, true);
        spriteBatch.begin();
        spriteBatch.draw(currentFrameTest1, 100, 100);
        spriteBatch.draw(currentFrameTest2, -150, 100); // apparement qd x=0 on se trouve pas sur l'origine wtf
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        AssetsManager.getInstance().disposeStage("world3");
        spriteBatch.dispose();
    }

}
