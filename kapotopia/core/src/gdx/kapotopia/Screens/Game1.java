package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import gdx.kapotopia.Game1.MireilleBasic;
import gdx.kapotopia.Game1.Virus;
import gdx.kapotopia.Kapotopia;

public class Game1 implements Screen {

    // Variables générales
    private Kapotopia game;
    private Texture fond;
    private Stage stage;

    private Music music;

    // Constantes
    private final static int minX = 25;
    private final int maxX;
    private final static int moveValueX = 275;
    private final Rectangle bounds;

    // Actors
    private Virus ennemi;

    /**
     * Constructeur
     * @param game
     */
    public Game1(Kapotopia game) {
        // On initialize les attributs
        this.game = game;
        this.stage = new Stage(game.viewport);
        this.fond = new Texture("FondNiveauBlanc2.png");
        this.bounds = new Rectangle(0,0,game.viewport.getScreenWidth(),game.viewport.getScreenHeight());
        music = prepareMusic();
        maxX = game.viewport.getScreenWidth();

        // Mise en place du décor
        final Image imgFond = new Image(fond);
        stage.addActor(imgFond);

        // Les acteurs principaux
        final MireilleBasic mireille = prepareMireille();
        this.ennemi = new Virus(this.bounds);

        stage.addActor(mireille);
        stage.addActor(ennemi);
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
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
        music.pause();
    }

    @Override
    public void resume() {
        music.play();
    }

    @Override
    public void hide() {
        music.pause();
    }

    @Override
    public void dispose() {
        fond.dispose();
        stage.dispose();
        music.dispose();
    }

    private MireilleBasic prepareMireille() {
        final MireilleBasic mireille = new MireilleBasic(minX, minX);
        mireille.addListener(new ActorGestureListener() {
            public void fling (InputEvent event, float velocityX, float velocityY, int button) {
                if (velocityX > 0) {
                    mireille.setX(Math.min(mireille.getX() + moveValueX, maxX));
                }else{
                    mireille.setX(Math.max(mireille.getX() - moveValueX, minX));
                }
                System.out.println("swipe!! " + velocityX + ", " + velocityY);
            }
        });
        mireille.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("change");
            }
        });
        return mireille;
    }

    private Music prepareMusic() {
        Music music = Gdx.audio.newMusic(Gdx.files.internal("sound/Musique_fast_chiptune.ogg"));
        music.setLooping(false);
        music.setVolume(0.5f);
        music.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                music.setPosition(11.5f);
            }
        });
        return music;
    }
}
