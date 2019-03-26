package gdx.kapotopia.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import gdx.kapotopia.Game4.Choice;
import gdx.kapotopia.Game4.Mireille;
import gdx.kapotopia.Game4.Question;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Utils;

public class World4 implements Screen {

    private Kapotopia game;
    private Texture fond;
    private Stage stage;
    private Mireille mireille;
    private Question[] questions;
    private TextButton choice1;
    private TextButton choice2;
    private TextButton choice3;
    private TextButton choice4;
    private TextButton retour;
    private TextButton question;
    private Label score2;
    private Label timer;
    private int index;
    private int score;
    private int goodAnswer;
    private float time;
    private float totalTime;
    private TextureRegion frame1;
    private TextureRegion frame2;
    private TextureRegion frame3;
    private TextureRegion[] allFrame;
    Animation<TextureRegion> mireilleAnimation;

    public World4(final Kapotopia game) {


        frame1 = new TextureRegion(new Texture(Gdx.files.internal("Mireille2b.png")));
        frame2 = new TextureRegion(new Texture(Gdx.files.internal("Mireille1b.png")));
        frame3 = new TextureRegion(new Texture(Gdx.files.internal("Mireille3b.png")));
        allFrame = new TextureRegion[] {frame1, frame2, frame3};
        mireilleAnimation = new Animation<TextureRegion>(0.1f, allFrame);
        TextButton.TextButtonStyle style = Utils.getStyleFont("SEASRN__.ttf");
        choice1 = new TextButton("Choix1", style);
        choice2= new TextButton("Choix2", style);
        choice3= new TextButton("Choix3", style);
        choice4= new TextButton("Choix4", style);
        question = new TextButton("Question", style);
        retour = new TextButton("Back to Menu", style);
        retour.setPosition(game.viewport.getWorldWidth()/4 - retour.getWidth()/2, 200);
        retour.setVisible(false);
        choice1.setPosition(game.viewport.getWorldWidth()/4 - choice1.getWidth()/2, 300);
        choice2.setPosition(game.viewport.getWorldWidth()/4 - choice2.getWidth()/2, 100);
        choice3.setPosition(game.viewport.getWorldWidth()*3/4 - choice3.getWidth()/2, 300);
        choice4.setPosition(game.viewport.getWorldWidth()*3/4 - choice4.getWidth()/2, 100);
        question.setPosition(game.viewport.getWorldWidth()/2 - question.getWidth()/2, 500);
        goodAnswer = 1;
        score = 0;
        index = 0;
        totalTime = 0;
        score2 = new Label("Score : "+score,new Label.LabelStyle(style.font, Color.WHITE));
        timer = new Label("Timer : "+time,new Label.LabelStyle(style.font, Color.WHITE));
        score2.setPosition (game.viewport.getWorldWidth()*3/4 - timer.getWidth()/2, 1700);
        timer.setPosition(game.viewport.getWorldWidth()/4 - timer.getWidth()/2, 1700);
        this.game = game;
        fond = new Texture("FondNiveauBlanc2.png");
        Image imgFond = new Image(fond);
        mireille = new Mireille();
        stage = new Stage(game.viewport);
        time = 0;
        questions = new Question[2];
        questions[0] = new Question("blanc", "rouge", "vert", "bleu", "De quel couleur est le cheval blanc de napoléon?", 1);
        questions[1] = new Question("bleu", "jaune", "vert", "blanc", "Mireille color ?", 2);
        stage.addActor(imgFond);
        stage.addActor(choice1);
        stage.addActor(timer);
        stage.addActor(choice2);
        stage.addActor(choice3);
        stage.addActor(choice4);
        stage.addActor(score2);
        stage.addActor(mireille);
        stage.addActor(question);
        stage.addActor(retour);
        choice1.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                if (goodAnswer == 1)
                {
                    score++;
                    time = 20;
                }
                else
                {
                    time = 20;
                }
            }
        });
        retour.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });
        choice2.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                if (goodAnswer == 2)
                {
                    score++;
                    time = 20;
                }
                else
                {
                    time = 20;
                }
            }
        });
        choice3.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                if (goodAnswer == 3)
                {
                    score++;
                    time = 20;
                }
                else
                {
                    time = 20;
                }
            }
        });
        choice4.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                if (goodAnswer == 4)
                {
                    score++;
                    time = 20;
                }
                else
                {
                    time = 20;
                }
            }
        });


        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (totalTime < 30) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            time = time + delta;
            totalTime += delta;
            if (time > 10f) {
                setupNewQuestion();
                setupNewAnswer();
                time = 0f;
            }
            stage.draw();
            score2.setText("Score : "+score);
            timer.setText("Time left : "+(30-Math.round(totalTime)));
            stage.act();
        }
        else {
            question.setText("Your score is : " + score);
            choice1.setVisible(false);
            score2.setVisible(false);
            choice2.setVisible(false);
            choice3.setVisible(false);
            choice4.setVisible(false);
            retour.setVisible(true);
            stage.draw();
            stage.act();
        }
    }

    public void setupNewAnswer()
    {
        choice1.setText(questions[index].choix1);
        choice2.setText(questions[index].choix2);
        choice3.setText(questions[index].choix3);
        choice4.setText(questions[index].choix4);
        goodAnswer = questions[index].goodAnswer;
        index = (index+1)%2;
    }

    public void setupNewQuestion()
    {
        question.setText(questions[index].question);

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
        fond.dispose();
        stage.dispose();
    }
}