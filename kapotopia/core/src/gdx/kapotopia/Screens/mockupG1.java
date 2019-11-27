package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.AssetsManaging.UseFont;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.UnlockedLevel;

public class mockupG1 extends CinematicScreen {

    public mockupG1(final Kapotopia game) {
        super(game, new Stage(game.viewport), "mockupG1");
        UseFont font = UseFont.CLASSIC_SANS_NORMAL_BLACK;
        Label[] labels = new Label[] {
                new LabelBuilder("Seigneur ! Il fait aussi chaud et sec que dans un vagin non lubrifié ici !")
                        .withStyle(font).withBounds(160, 845, 750 ,315)
                        .isWrapped(true).build(),
                new LabelBuilder("Bienvenue dans la Jungle Infectieuse de Chilly Beachy c'est là que les jeunes recrues comme toi prouvent de quoi elles sont capables pauv tache. C'est clair ?")
                        .withStyle(font).withBounds(275,925,750,315).isWrapped(true).build(),
                new LabelBuilder("C'est parti pour l'échauffement, on va commencer par un petit footing de la muerte dans la jungle infectieuse. Prends garde aux fausses ist qui se baladent dans la jungle !")
                        .withStyle(font).withBounds(125,875,750,315).isWrapped(true).build(),
                new LabelBuilder("RÈGLES: Déplace Mireille sur les différentes routes tout en évitant les fausses IST. Tu perds une vie lorsque tu ramasses une fausse IST. Au bout de 3 vies perdues, tu recommences l'aventure")
                        .withStyle(font).withBounds(40,40,1000,1880).isWrapped(true).build(),
                new LabelBuilder("”ok petite fiotte, montre moi de quoi t'es capable.")
                        .withStyle(font).withBounds(200,875,750,315).isWrapped(true).build()
        };
        applyBundle(new ParameterBundleBuilder(ScreenType.DIF)
                .withTextures(new String[]{"World1/Game1/World1Ecran1.png", "World1/Game1/World1Ecran2.png",
                        "World1/Game1/World1Ecran5.png", "World1/Game1/World1Ecran3.png",
                        "World1/Game1/World1Ecran4.png"})
                .withStyle(UseFont.CLASSIC_SANS_NORMAL_WHITE).withTimerScheduleTime(0).withLabels(labels));
        // Preload this sound for the BilanG1 screen
        AssetsManager.getInstance().getSoundByPath("sound/bruitage/littlerainyseasons_fail.mp3");
    }

    @Override
    public void show() {
        setUpInputProcessor();
        game.getTheValueGateway().addToTheStore("nextscreen", ScreenType.GAME1);
        final UnlockedLevel level = game.getSettings().getG1UnlockedLvl();
        game.getTheValueGateway().addToTheStore("unlockedLevel", level);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}