package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.AssetsManaging.FontHelper;
import gdx.kapotopia.AssetsManaging.UseFont;
import gdx.kapotopia.Helpers.Alignement;
import gdx.kapotopia.Helpers.Builders.ImageButtonBuilder;
import gdx.kapotopia.Helpers.Builders.TextButtonBuilder;
import gdx.kapotopia.Helpers.ChangeScreenListener;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Languages;
import gdx.kapotopia.Localization;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.Settings;

public class Options implements Screen {

    private Kapotopia game;
    private Stage stage;
    private Settings settings;

    private Skin skin;
    private Image fond;

    private SelectBox<String> languageSelect;
    private ImageButton soundBtn;
    private TextButton backBtn;

    public Options(final Kapotopia game) {
        this.game = game;
        this.stage = new Stage(game.viewport);
        settings = game.getSettings();

        fond = new Image(AssetsManager.getInstance().getTextureByPath("FondNiveauBlanc2.png"));
        fond.setVisible(true);
        skin = new Skin(Gdx.files.internal("skins/comic/skin/comic-ui.json"));

        languageSelect = new SelectBox<String>(skin);
        languageSelect.setPosition(game.viewport.getWorldWidth() / 4, 300);
        languageSelect.setSize(game.viewport.getWorldWidth() / 2, 60);
        languageSelect.setVisible(true);
        languageSelect.setItems(settings.getSupportedLangsText());
        languageSelect.getStyle().font = FontHelper.getStyleFont(UseFont.CLASSIC_BOLD_NORMAL_BLACK).font;
        languageSelect.getStyle().listStyle.font = FontHelper.getStyleFont(UseFont.CLASSIC_BOLD_NORMAL_BLACK).font;
        languageSelect.setSelected(Languages.convertFromLocale(settings.getLanguage()));
        languageSelect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selectedLang = languageSelect.getSelected();
                settings.setLanguage(selectedLang);
            }
        });

        String soundBtnInitTexture = getSoundBtnTexturePath();
        soundBtn = new ImageButtonBuilder().withImageUp(soundBtnInitTexture)
                .withListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        settings.toggleMusic();
                        String soundBtnInitTexture = getSoundBtnTexturePath();

                        TextureRegionDrawable newTexture = new TextureRegionDrawable(AssetsManager.getInstance().getTextureByPath(soundBtnInitTexture));
                        soundBtn.setStyle(new ImageButton.ImageButtonStyle(newTexture,newTexture,newTexture,newTexture,newTexture,newTexture));
                    }
                })
                .withPosition(game.viewport.getWorldWidth() / 3, game.viewport.getWorldHeight() / 2)
                .isVisible(true).build();

        backBtn = new TextButtonBuilder(Localization.getInstance().getString("back_button"))
                .withY(50).withListener(new ChangeScreenListener(game, ScreenType.MAINMENU)).isVisible(true)
                .withStyle(UseFont.CLASSIC_BOLD_NORMAL_YELLOW).withAlignment(Alignement.CENTER).build();

        stage.addActor(fond);
        stage.addActor(languageSelect);
        stage.addActor(soundBtn);
        stage.addActor(backBtn);
    }

    private String getSoundBtnTexturePath() {
        if (settings.isMusicOn())
            return "icons/speaker.png";
        else
            return "icons/mute.png";
    }

    @Override
    public void show() {
        settings = game.getSettings();
        Gdx.input.setInputProcessor(stage);
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

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        skin.dispose();
    }
}
