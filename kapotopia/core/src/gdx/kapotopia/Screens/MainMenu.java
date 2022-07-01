package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import gdx.kapotopia.Animations.NeonDoorAnimation;
import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Fonts.Font;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.GameConfig;
import gdx.kapotopia.Helpers.Alignement;
import gdx.kapotopia.Helpers.AnimatedDrawable;
import gdx.kapotopia.Helpers.Builders.ImageButtonBuilder;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Helpers.Builders.TextButtonBuilder;
import gdx.kapotopia.Helpers.ChangeScreenListener;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;

public class MainMenu implements Screen {

    private Kapotopia game;
    private Stage stage;

    private Sound pauseSound;

    // Background
    private OrthographicCamera camera;
    private SpriteBatch backgroundBatch;
    private float stateTime;
    private Texture part_1, part_3, part_4;
    private Animation<TextureRegion> part_2;

    private static final String TAG = "Screen-MainMenu";

    public MainMenu(final Kapotopia game) {

        Gdx.app.log(TAG,"Entering MainMenu function");

        this.game = game;
        stage = new Stage(game.viewport);

        // If we're here, it means that the player already saw the intro
        if (!game.getSettings().isFirstCinematicShowed()) {
            game.getSettings().setFirstCinematicShowed(true);
        }

        // Background
        this.camera = new OrthographicCamera(game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        this.camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f,0); // I dont understand why, but this works. If someone knows plz explain me. F.D.
        this.camera.update();
        this.backgroundBatch = new SpriteBatch();
        stateTime = 0f;

        this.part_1 = game.ass.get(AssetDescriptors.MM_PART1_CUT);
        this.part_2 = new NeonDoorAnimation(game, Animation.PlayMode.LOOP_RANDOM).getAnimation();
        this.part_3 = game.ass.get(AssetDescriptors.MM_PART3_CUT);
        this.part_4 = game.ass.get(AssetDescriptors.MM_PART4_CUT);



        // Import sounds
        this.pauseSound = game.ass.get(AssetDescriptors.SOUND_PAUSE);

        //Import fonts
        Font style = FontHelper.AESTHETIC_NORMAL_WHITE;

        //setup Button
        final float x = game.viewport.getWorldWidth() / 2.6f;
        final float y = game.viewport.getWorldHeight();

        ImageButton imageBtnToWorld1 = new ImageButtonBuilder()
                .withImageUp(part_1)
                .withListener(new ChangeScreenListener(game, ScreenType.WORLD1))
                .withBounds(0, (float) (2*game.viewport.getWorldHeight()/3)+15, game.viewport.getWorldWidth(), game.viewport.getWorldHeight()/3)
                .build();
        final TextButton btnToWorld1 = new TextButtonBuilder(game, game.loc.getString("text_world1"))
                .withStyle(style)
                .withY(y * 0.75f).withAlignment(Alignement.CENTER)
                .withListener(new ChangeScreenListener(game, ScreenType.WORLD1)).build();

        ImageButton imageBtnToWorld2 = new ImageButton(new AnimatedDrawable(part_2));
        imageBtnToWorld2.addListener(new ChangeScreenListener(game, ScreenType.WORLD2));
        final TextButton btnToWorld2 = new TextButtonBuilder(game, game.loc.getString("text_world2"))
                .withStyle(style)
                .withY(y * 0.43f).withAlignment(Alignement.CENTER)
                .withListener(new ChangeScreenListener(game, ScreenType.WORLD2)).build();

        final ImageButton imageBtnToWorld3 = new ImageButtonBuilder()
                .withImageUp(part_3)
                .withListener(new ChangeScreenListener(game, ScreenType.STIDEX))
                .withBounds(0, y*0.05f, game.viewport.getWorldWidth(), (float) (game.viewport.getWorldHeight()/3))
                .build();
        final TextButton btnToWorld3 = new TextButtonBuilder(game, game.loc.getString("text_istdex"))
                .withStyle(style)
                .withY(y * 0.12f).withAlignment(Alignement.CENTER)
                .withListener(new ChangeScreenListener(game, ScreenType.STIDEX))
                .build();

        final ImageButton imageButtonToOptions = new ImageButtonBuilder()
                .withImageUp(part_4)
                .withListener(new ChangeScreenListener(game, ScreenType.OPTIONS))
                .build();
        final TextButton optionsBtn = new TextButtonBuilder(game, "Options")
                .withStyle(style)
                .withY(y*0.01f).withAlignment(Alignement.CENTER)
                .withListener(new ChangeScreenListener(game, ScreenType.OPTIONS))
                .build();

        Label version = new LabelBuilder(game, "v:" + GameConfig.VERSION_NAME + " | code:" + GameConfig.VERSION_CODE)
                .withStyle(FontHelper.AESTHETIC_TINY_BLACK).withPosition(15, 0).build();



        //stage.addActor(imgFond);
        //add button to the scene
        stage.addActor(imageBtnToWorld2);  //world 2 must be loaded first to avoid it overlapping the other 2
        stage.addActor(btnToWorld2);
        stage.addActor(imageBtnToWorld1);
        stage.addActor(btnToWorld1);
        stage.addActor(imageBtnToWorld3);
        stage.addActor(btnToWorld3);
        stage.addActor(imageButtonToOptions);
        stage.addActor(optionsBtn);
        stage.addActor(version);
    }

    @Override
    public void show() {
        Gdx.app.log(TAG,"Entering show function");
        Gdx.input.setInputProcessor(stage);

        Music music = game.ass.get(AssetDescriptors.MUSIC_MM);
        if (!game.getMusicControl().musicIsEquals(music)) {
            game.getMusicControl().changeMusic(music, 0f, true);
        }
        game.getMusicControl().playMusic();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        backgroundBatch.setProjectionMatrix(camera.combined);

        stateTime += delta;

        backgroundBatch.begin();
        backgroundBatch.draw(part_4, 0, 0);
        //backgroundBatch.draw(part_3, 0, 0);
        //backgroundBatch.draw(part_2.getKeyFrame(stateTime, true), 0, 0);
        //backgroundBatch.draw(part_1, 0, 0);
        backgroundBatch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        this.pauseSound.play();
        game.getMusicControl().pauseMusic();
    }

    @Override
    public void resume() {
        game.getMusicControl().playMusic();
    }

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        backgroundBatch.dispose();
    }
}
