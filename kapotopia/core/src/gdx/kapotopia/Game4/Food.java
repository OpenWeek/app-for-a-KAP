package gdx.kapotopia.Game4;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import org.graalvm.compiler.loop.MathUtil;

import java.util.ArrayList;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Helpers.Builders.ImageButtonBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Screens.Game4;

public class Food {
    private float x;
    private float y;
    private int type; //0-2,3,4 unsafe, safe, protection
    private ImageButton sqr;
    private ImageButton logo;
    private Game4 screen;
    private float scaleSnake;
    private int xOffset;
    private int yOffset;
    private Kapotopia game;

    //[IST][Practice]
    private final int[][] tab = {{1,1,1,0,0,0,0,1},
            {0,1,0,0,1,0,0,0},
            {1,1,1,1,0,0,1,1},
            {0,1,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,0},
            {1,1,1,1,1,1,0,0},
            {1,1,1,1,1,1,0,0},
            {1,1,1,1,1,1,0,0},
            {1,0,1,1,1,1,0,0}
    };

    private Texture[] practices;

    private ArrayList<Texture> risked = new ArrayList<Texture>();
    private ArrayList<Texture> notRisked = new ArrayList<Texture>();
    private final Texture[] safe;

    public Food(int boardSize, Game4 screen, float scaleSnake, int xOffset, int yOffset, Kapotopia game) {
        this.scaleSnake = scaleSnake;
        this.screen = screen;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.game = game;
        practices = new Texture[]{game.ass.get(AssetDescriptors.PENE_VAGINALE), game.ass.get(AssetDescriptors.PENE_ANALE), game.ass.get(AssetDescriptors.FELLATION), game.ass.get(AssetDescriptors.CUNNI), game.ass.get(AssetDescriptors.ANULINGUS), game.ass.get(AssetDescriptors.CARESSES), game.ass.get(AssetDescriptors.EMBRASSADE), game.ass.get(AssetDescriptors.SERINGUE_CHANGE)};
        safe = new Texture[]{game.ass.get(AssetDescriptors.MIREILLE_FOOD), game.ass.get(AssetDescriptors.ALYX_OPEN), game.ass.get(AssetDescriptors.GANT)};
        fillRisks();
        randomise(boardSize);
    }

    public void randomise(int boardSize) {
        x = MathUtils.random(boardSize-2);
        y = MathUtils.random(boardSize-1);
        type = MathUtils.random(4);

        if (type < 3) {
            sqr = new ImageButtonBuilder()
                    .withBounds(x * scaleSnake + xOffset, y * scaleSnake + yOffset, scaleSnake, scaleSnake)
                    .withImageUp(game.ass.get(AssetDescriptors.RED_SQUARE))
                    .build();
            logo = new ImageButtonBuilder()
                    .withBounds(x * scaleSnake + xOffset, y * scaleSnake + yOffset, scaleSnake, scaleSnake)
                    .withImageUp(risked.get(MathUtils.random(risked.size()-1)))
                    .build();
        } else if (type == 3) {
            logo = new ImageButtonBuilder()
                    .withBounds(x * scaleSnake + xOffset, y * scaleSnake + yOffset, scaleSnake, scaleSnake)
                    .withImageUp(notRisked.get(MathUtils.random(notRisked.size()-1)))
                    .build();
            sqr = new ImageButtonBuilder()
                    .withBounds(x * scaleSnake + xOffset, y * scaleSnake + yOffset, scaleSnake, scaleSnake)
                    .withImageUp(game.ass.get(AssetDescriptors.GREEN_SQUARE))
                    .build();
        } else {  //always 4
            logo = new ImageButtonBuilder()
                    .withBounds(x * scaleSnake + xOffset, y * scaleSnake + yOffset, scaleSnake, scaleSnake)
                    .withImageUp(safe[MathUtils.random(safe.length-1)])
                    .build();
            sqr = new ImageButtonBuilder()
                    .withBounds(x * scaleSnake + xOffset, y * scaleSnake + yOffset, scaleSnake, scaleSnake)
                    .withImageUp(game.ass.get(AssetDescriptors.BLUE_SQUARE))
                    .build();
        }
        screen.getStage().addActor(sqr);
        screen.getStage().addActor(logo);
    }

    private void fillRisks() {
        for (int i = 0; i < tab[game.vars.getChosenSTD()].length; i++) {
            if (tab[game.vars.getChosenSTD()][i] == 1) {
                risked.add(practices[i]);
            } else {
                notRisked.add(practices[i]);
            }
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public ImageButton getSqr() {
        return sqr;
    }

    public ImageButton getLogo() {
        return logo;
    }
}
