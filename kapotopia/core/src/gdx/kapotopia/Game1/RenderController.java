package gdx.kapotopia.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

import gdx.kapotopia.Animations.EyesBackgroundAnimation;
import gdx.kapotopia.Animations.LeavesBackgroundAnimation;
import gdx.kapotopia.Animations.LetsgoG1Animation;
import gdx.kapotopia.Animations.SkyBackgroundAnimation;
import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Fonts.Font;
import gdx.kapotopia.Fonts.FontHelper;
import gdx.kapotopia.GameConfig;
import gdx.kapotopia.Helpers.Alignement;
import gdx.kapotopia.Helpers.Builders.ImageButtonBuilder;
import gdx.kapotopia.Helpers.Builders.ImageTextButtonBuilder;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Helpers.Builders.TextButtonBuilder;
import gdx.kapotopia.Helpers.Padding;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.Screens.Game1;

public class RenderController {

    private final Kapotopia game;
    private final Game1 game1;

    private OrthographicCamera camera;

    // Graphisms and animations
    private final Texture trees;
    private final Animation<TextureRegion> sky;
    private final Animation<TextureRegion> eyes;
    private final Animation<TextureRegion> leaves;
    private final Animation<TextureRegion> letsGoAnimation;

    private SpriteBatch animationSpriteBatch;
    private SpriteBatch backgroundBatch;
    private Font normalFont;
    private Font smallFont;
    private MireilleJojo jojo;

    private Label lifeLabel;
    private Label scoreLabel;
    private Label istCatchedLabel;
    private Label ennemiNameLabel;
    private Label missedLabel;
    private ImageButton pauseIcon;
    private ImageTextButton quitBtn;
    private ImageTextButton pauseBtn;

    // Constants

    private final String TAG = this.getClass().getSimpleName();

    private final float BTN_SPACING = 90f;

    public  RenderController(final Kapotopia game, final Game1 game1, Stage stage) {
        this.game = game;
        this.game1 = game1;

        final float ww = game.viewport.getWorldWidth();
        final float wh = game.viewport.getWorldHeight();

        final Localisation loc = game.loc;

        this.normalFont = FontHelper.CLASSIC_SANS_NORMAL_WHITE;
        this.smallFont = FontHelper.CLASSIC_SANS_SMALL_WHITE;

        this.camera = new OrthographicCamera(game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        this.camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f,0);
        this.camera.update();

        // Graphisms and animations
        this.sky = new SkyBackgroundAnimation(game, Animation.PlayMode.LOOP_RANDOM).getAnimation();
        this.trees = game.ass.get(AssetDescriptors.JUNGLE);
        this.eyes = new EyesBackgroundAnimation(game, Animation.PlayMode.LOOP).getAnimation();
        this.leaves = new LeavesBackgroundAnimation(game, Animation.PlayMode.LOOP_RANDOM).getAnimation();

        this.letsGoAnimation = new LetsgoG1Animation(game, Animation.PlayMode.NORMAL).getAnimation();
        this.animationSpriteBatch = new SpriteBatch();
        this.backgroundBatch = new SpriteBatch();

        this.jojo = new MireilleJojo(game);

        // Buttons
        final RenderController dis = this;
        this.pauseIcon = new ImageButtonBuilder().withImageUp(game.ass.get(AssetDescriptors.PAUSE_LOGO))
                .withImageChecked(game.ass.get(AssetDescriptors.PLAY_LOGO))
                .withImageDown(game.ass.get(AssetDescriptors.PAUSE_LOGO))
                .withBounds(game1.getGameController().getBounds().width - (ww / 5f),
                        game1.getGameController().getBounds().height - (wh / 10f), ww / 7.2f, wh / 25f)
                .withListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(game1.getGameController().isPaused()) {
                            dis.pauseIcon.setChecked(false);
                            game1.resumeFromPause();
                        }else{
                            dis.pauseIcon.setChecked(true);
                            game1.pause();
                        }
                    }
                }).build();
        stage.addActor(pauseIcon);

        EventListener quitEvent = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game1.getSoundController().stopMusics();
                game.destroyScreen(ScreenType.GAME1);
                game.destroyScreen(ScreenType.MAINMENU);
                game.changeScreen(ScreenType.MAINMENU);
            }
        };

        EventListener pauseEvent = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(game1.getGameController().isPaused()) {
                    pauseIcon.setChecked(false);
                    game1.resumeFromPause();
                } else {
                    pauseIcon.setChecked(true);
                    game1.pause();
                }
                Gdx.app.debug(TAG, "pauseLabel clicked - isPaused is " + game1.getGameController().isPaused());
            }
        };

        quitBtn = new ImageTextButtonBuilder(game, loc.getString("quit_button_text"))
                .withFontStyle(FontHelper.CLASSIC_SANS_NORMAL_WHITE)
                .withAlignment(Alignement.CENTER)
                .withY((game1.getGameController().getBounds().getHeight() / 2) - BTN_SPACING)
                .withPadding(Padding.STANDARD)
                .withListener(quitEvent)
                .withImageStyle(game.ass.get(AssetDescriptors.BTN_LEAF))
                .isVisible(false)
                .build();

        pauseBtn = new ImageTextButtonBuilder(game, loc.getString("continue_button"))
                .withFontStyle(normalFont).withAlignment(Alignement.CENTER) // faut rajouter le x
                .withY(game1.getGameController().getBounds().height / 2)
                .isVisible(false)
                .withImageStyle(game.ass.get(AssetDescriptors.BTN_LEAF))
                .withPadding(Padding.STANDARD)
                .withListener(pauseEvent)
                .build();

        stage.addActor(quitBtn);
        stage.addActor(pauseBtn);

        // Labels
        lifeLabel = new LabelBuilder(game, game.loc.getString("lives_label") + game1.getGameController().getMireilleLife()).withStyle(normalFont)
                .withPosition(game1.getGameController().getBounds().width - (ww / 4.5f), game1.getGameController().getBounds().height - (ww / 10.8f)).build();
        istCatchedLabel = new LabelBuilder(game, game.loc.getString("istattrapees")  + game1.getGameController().getIstsCatched()).withStyle(normalFont)
                .withPosition(25, game1.getGameController().getBounds().height - (ww / 10.8f)).build();
        scoreLabel = new LabelBuilder(game, game.loc.getString("score2_label")  + game1.getGameController().getTotalScore()).withStyle(normalFont)
                .withPosition(25, game1.getGameController().getBounds().height - (ww / 5.4f)).build();
        missedLabel = new LabelBuilder(game, loc.getString("missed_label_text")).withStyle(smallFont).isVisible(false).build();
        ennemiNameLabel = new LabelBuilder(game, game1.getGameController().getEnnemi().getName()).withStyle(smallFont).withTextAlignement(Align.center)
                .withPosition(game1.getGameController().getEnnemi().getX() + (game1.getGameController().getEnnemi().getRealWidth() - game1.getGameController().getEnnemi().getName().length()) /2,
                        game1.getGameController().getEnnemi().getY() - (ww / 10.8f)).build();

        stage.addActor(lifeLabel);
        stage.addActor(istCatchedLabel);
        stage.addActor(scoreLabel);
        stage.addActor(missedLabel);
        stage.addActor(ennemiNameLabel);
    }

    public void update(float stateTime) {
        camera.update();
        backgroundBatch.setProjectionMatrix(camera.combined);
        animationSpriteBatch.setProjectionMatrix(camera.combined);
        jojo.upProjMatrBatch(camera.combined);

        renderBackground(stateTime);

        lifeLabel.setText(game.loc.getString("lives_label") + game1.getGameController().getMireilleLife());
        scoreLabel.setText(game.loc.getString("score2_label") + game1.getGameController().getTotalScore());
        istCatchedLabel.setText(game.loc.getString("istattrapees") + game1.getGameController().getIstsCatched());
    }

    private void renderBackground(float stateTime) {

        backgroundBatch.begin();

        final TextureRegion skyFrame = sky.getKeyFrame(stateTime, true);
        backgroundBatch.draw(skyFrame,0, game.viewport.getWorldHeight() - skyFrame.getRegionHeight());
        backgroundBatch.draw(trees, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight(),
                0, 0,720,1280, false, false);
        backgroundBatch.draw(eyes.getKeyFrame(stateTime, true),0, 0);
        if(!game1.getGameController().isFinish()) // We hide mireille  when the game is finished
            game1.getGameController().getMireille().draw(backgroundBatch, 0);
        game1.getGameController().getEnnemi().draw(backgroundBatch, 0);
        backgroundBatch.draw(leaves.getKeyFrame(stateTime, true), 0, 0);

        backgroundBatch.end();
    }

    public void renderFirstAnimation(float stateTime) {
        TextureRegion currentFrame = letsGoAnimation.getKeyFrame(stateTime, false);
        animationSpriteBatch.begin();
        animationSpriteBatch.draw(currentFrame, (game1.getGameController().getBounds().width / 5) * 2,
                game1.getGameController().getBounds().height / 2,0,0,currentFrame.getRegionWidth(),
                currentFrame.getRegionHeight(),0.6666f,0.6666f,0);
        animationSpriteBatch.end();
    }

    public void updateWhenResumeFromPause() {
        pauseBtn.setVisible(false);
        quitBtn.setVisible(false);
    }

    public void updateAtPause() {
        pauseBtn.setVisible(true);
        quitBtn.setVisible(true);
    }

    public void updateAtGameOver(final Stage stage, final EventListener continueBtnEvent, final EventListener restartBtnEvent) {
        for(Actor actor : stage.getActors()) {
            if(!(actor.equals(this.trees) || actor.equals(this.leaves) || actor.equals(lifeLabel))) {
                actor.setVisible(false);
            }
        }

        /*  Here we make the highscore label
         *  It can take two forms, whenever there is a highscore breaker or not
         */
        int highscore = game.getSettings().getG1Highscore();
        final float highScoreY;
        final String highscoreLabHead, highscoreLabTail;
        final Label endScoreLabel;
        if (highscore <= game1.getGameController().getTotalScore()) {
            // We dont need to display the endScoreLabel
            endScoreLabel = null;
            // We did a new highscore !
            game.getSettings().setG1Highscore(game1.getGameController().getTotalScore());
            highscore = game1.getGameController().getTotalScore();
            highScoreY = (game1.getGameController().getBounds().height / 2) - (game.viewport.getWorldHeight() / 38.4f);
            highscoreLabHead = game.loc.getString("high_score_lab_head");
            highscoreLabTail = " !";
        } else {
            endScoreLabel = new LabelBuilder(game, game.loc.getString("score2_label") + game1.getGameController().getTotalScore())
                    .withStyle(normalFont)
                    .withPosition((game1.getGameController().getBounds().width / 2) - (game.viewport.getWorldWidth() / 8f),
                            (game1.getGameController().getBounds().height / 2) - (game.viewport.getWorldHeight() / 32))
                    .build();
            stage.addActor(endScoreLabel);
            highScoreY = (game1.getGameController().getBounds().height / 2) - (game.viewport.getWorldHeight() / 16f);
            highscoreLabHead = "";
            highscoreLabTail = "";
        }

        final String highScoreLabelTxt = highscoreLabHead + game.loc.getString("highscore") + highscore + highscoreLabTail;
        final Label highscoreLabel = new LabelBuilder(game, highScoreLabelTxt).withY(highScoreY)
                .withAlignment(Alignement.CENTER).withStyle(FontHelper.CLASSIC_BOLD_NORMAL_YELLOW).build();
        stage.addActor(highscoreLabel);

        final String titleText;
        if (game1.getGameController().isVictory()) {
            titleText = game.loc.getString("success");
        } else {
            titleText = game.loc.getString("fail");
        }

        final Button title = new TextButtonBuilder(game, titleText).withStyle(FontHelper.CLASSIC_REG_BIG_WHITE)
                .withY((game1.getGameController().getBounds().getHeight() / 2) + (game.viewport.getWorldHeight() / 48f))
                .withAlignment(Alignement.CENTER).build();

        final ImageTextButton nextBtn = new ImageTextButtonBuilder(game, game.loc.getString("continue_button"))
                .withFontStyle(normalFont)
                .withY( (game1.getGameController().getBounds().getHeight() / 4) + BTN_SPACING )
                .withAlignment(Alignement.CENTER).withPadding(Padding.STANDARD)
                .withListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        title.getClickListener().clicked(event, x, y);
                    }
                }).withImageStyle(game.ass.get(AssetDescriptors.BTN_LEAF)).build();

        stage.addActor(nextBtn);

        title.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            // We hide what's left on the screen
            lifeLabel.setVisible(false);
            title.setVisible(false);
            highscoreLabel.setVisible(false);
            if(endScoreLabel != null)
                endScoreLabel.setVisible(false);

            // If the player missed some STI's, the game will prompt it to the player and once the player
            // come back here, we show the different options
            if (!game1.getGameController().getMissedIsts().isEmpty()) {
                game.changeScreen(ScreenType.BILANG1);
            }

            // Only if the player won we display the continue button
            if (game1.getGameController().isVictory()) {
                final ImageTextButton continueBtn = new ImageTextButtonBuilder(game, game.loc.getString("continue_button"))
                        .withFontStyle(normalFont)
                        .withY( (game1.getGameController().getBounds().getHeight() / 2) + BTN_SPACING )
                        .withAlignment(Alignement.CENTER).withPadding(Padding.STANDARD)
                        .withListener(continueBtnEvent).withImageStyle(game.ass.get(AssetDescriptors.BTN_LEAF)).build();

                stage.addActor(continueBtn);
            }
            final ImageTextButton restartBtn = new ImageTextButtonBuilder(game, game.loc.getString("restart_button"))
                    .withFontStyle(normalFont)
                    .withY((game1.getGameController().getBounds().getHeight() / 2))
                    .withAlignment(Alignement.CENTER).withPadding(Padding.STANDARD)
                    .withListener(restartBtnEvent).withImageStyle(game.ass.get(AssetDescriptors.BTN_LEAF)).build();

            stage.addActor(restartBtn);
            quitBtn.setVisible(true);
            nextBtn.setVisible(false);
            }
        });
        stage.addActor(title);
    }

    public void dispose() {
        animationSpriteBatch.dispose();
        backgroundBatch.dispose();
    }

    public void setNewEnnemiLabelPosition(float x, float y) {
        this.ennemiNameLabel.setPosition(x, y);
    }

    public void changeEnnemiLabel(String newName) {
        this.ennemiNameLabel.setText(newName);
        final float labelLength = newName.length() * GameConfig.ONE_CHAR_SMALL_WIDTH;
        this.ennemiNameLabel.setWidth(labelLength);
    }

    public void playMissedLabelAnim() {
        missedLabel.setVisible(true);
        final float x = game1.getGameController().getMireille().getX(),
                y = game1.getGameController().getMireille().getY() + game1.getGameController().getMireille().getHeight();
        missedLabel.setPosition(x,y);
        missedLabel.addAction(Actions.moveTo(x,y + 50f, 1f));
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                missedLabel.setVisible(false);
            }
        },1f);
    }

    public float getFirstAnimationTimeLeft() {
        return this.letsGoAnimation.getFrameDuration() * this.letsGoAnimation.getKeyFrames().length;
    }

    public float getTotalAnimationDuration() {
        return this.letsGoAnimation.getAnimationDuration();
    }

    public void jojo(float delta) {
        jojo.draw(delta);
    }

    public ImageButton getPauseIcon() {
        return this.pauseIcon;
    }

    /*
     *      DEBUG METHODS
     */

    private void drawVirusBounds_debug(ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Line);

        Virus ennemi = game1.getGameController().getEnnemi();

        final float x = ennemi.getX();
        final float y = ennemi.getY();
        final float width = ennemi.getWidth();
        final float height = ennemi.getHeight();
        final float widthReal = ennemi.getRealWidth();
        final float heightReal = ennemi.getRealHeight();

        renderer.setColor(Color.WHITE);
        renderer.box(x, y, 0, widthReal, heightReal, 0);
        renderer.setColor(Color.BLUE);
        renderer.box(x, y, 0, width, height, 0);

        renderer.end();
    }
    private void drawVirusLabelBounds_debug(ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Line);

        final float x = ennemiNameLabel.getX();
        final float y = ennemiNameLabel.getY();
        final float width = ennemiNameLabel.getWidth();
        final float height = ennemiNameLabel.getHeight();
        final float widthPref = ennemiNameLabel.getPrefWidth();
        final float heightPref = ennemiNameLabel.getPrefHeight();

        renderer.setColor(Color.RED);
        renderer.box(x, y, 0, widthPref, heightPref, 0);
        renderer.setColor(Color.GREEN);
        renderer.box(x, y, 0, width, height, 0);

        renderer.end();
    }
}
