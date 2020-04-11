package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Sound.SoundHelper;
import gdx.kapotopia.Fonts.UseFont;
import gdx.kapotopia.Sound.UseSound;
import gdx.kapotopia.Helpers.Alignement;
import gdx.kapotopia.Helpers.Builders.ImageBuilder;
import gdx.kapotopia.Helpers.Builders.ImageButtonBuilder;
import gdx.kapotopia.Helpers.Builders.SelectBoxBuilder;
import gdx.kapotopia.Helpers.Builders.TextButtonBuilder;
import gdx.kapotopia.Helpers.ChangeScreenListener;
import gdx.kapotopia.Helpers.StandardInputAdapter;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Languages;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.Settings;

public class Options implements Screen {

    private Kapotopia game;
    private Stage stage;
    private Settings settings;

    private Skin skin;
    private Image fond;

    private Sound pauseSound;
    private Sound soundOnSound;
    private Sound soundOffSound;

    private SelectBox<String> languageSelect;
    private ImageButton soundOnBtn;
    private ImageButton soundOffBtn;
    private TextButton backBtn;

    public Options(final Kapotopia game) {
        this.game = game;
        this.stage = new Stage(game.viewport);
        settings = game.getSettings();

        fond = new ImageBuilder().withTexture("EcranMenu/EcranOption.png").isVisible(true).build();
        skin = AssetsManager.getInstance().getSkinByPath("skins/comic/skin/comic-ui.json");

        pauseSound = SoundHelper.getSound(UseSound.PAUSE);
        soundOnSound = SoundHelper.getSound(UseSound.BOUP9);
        soundOffSound = SoundHelper.getSound(UseSound.BOUP1);

        languageSelect = new SelectBoxBuilder<String>().withSkin(skin).withItems(settings.getSupportedLangsText())
                .withPosition(game.viewport.getWorldWidth() / 4, 300)
                .withSize(game.viewport.getWorldWidth() / 2, 60)
                .withTitleFont(UseFont.CLASSIC_BOLD_NORMAL_BLACK).withElemsFont(UseFont.CLASSIC_BOLD_NORMAL_BLACK)
                .withSelectedItem(Languages.convertFromLocale(settings.getLanguage()))
                .addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        String selectedLang = languageSelect.getSelected();
                        settings.setLanguage(selectedLang);
                    }
                }).build();

        final float soundBtnWidth = game.viewport.getWorldWidth() / 4;
        soundOnBtn = new ImageButtonBuilder().withImageUp("icons/speaker.png")
                .withListener(new toggleMusicListener()).withWidth(soundBtnWidth)
                .withPosition(game.viewport.getWorldWidth() / 3, game.viewport.getWorldHeight() / 2)
                .isVisible(settings.isMusicOn()).build();
        soundOffBtn = new ImageButtonBuilder().withImageUp("icons/mute.png")
                .withListener(new toggleMusicListener()).withWidth(soundBtnWidth)
                .withPosition(game.viewport.getWorldWidth() / 3, game.viewport.getWorldHeight() / 2)
                .isVisible(!settings.isMusicOn()).build();

        backBtn = new TextButtonBuilder(Localisation.getInstance().getString("back_button"))
                .withY(50).withListener(new ChangeScreenListener(game, ScreenType.MAINMENU)).isVisible(true)
                .withStyle(UseFont.CLASSIC_BOLD_NORMAL_WHITE).withAlignment(Alignement.CENTER).build();

        stage.addActor(fond);
        //stage.addActor(languageSelect);
        stage.addActor(soundOnBtn);
        stage.addActor(soundOffBtn);
        stage.addActor(backBtn);
    }

    @Override
    public void show() {
        settings = game.getSettings();
        setUpInputProcessor();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        pauseSound.play();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void setUpInputProcessor() {
        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(new StandardInputAdapter(this, game));
        im.addProcessor(stage);
        Gdx.input.setInputProcessor(im);
    }

    private class toggleMusicListener extends ChangeListener {
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            settings.toggleMusic();
            soundOnBtn.setVisible(settings.isMusicOn());
            soundOffBtn.setVisible(!settings.isMusicOn());
            if (soundOnBtn.isVisible()) soundOnSound.play();
            else soundOffSound.play();
        }
    }
}
