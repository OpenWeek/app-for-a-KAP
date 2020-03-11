package gdx.kapotopia;

import com.badlogic.gdx.utils.Logger;

public class GameConfig {

    // TODO changer VERSION_NAME ET VERSION_CODE à chaque fois que l'on update le jeu, pas trouvé de moyen pour les liés automatiquement au gradle build d'android
    public static final String VERSION_NAME = "Alpha-0.4.1";
    public static final int VERSION_CODE = 14;

    public static final float GAME_WIDTH = 720;
    public static final int GAME_HEIGHT = 1280;

    public final static float SCALLING_FACTOR_BACKGROUND = 0.6666667f; // 2/3

    public final static float SCALLING_FACTOR_ENTITY = 4.5f;
    // Units in pixels for fonts characters sizes
    public final static float ONE_CHAR_STD_HEIGHT = 40f;
    public final static float ONE_CHAR_STD_WIDTH = 20f;
    public final static float ONE_CHAR_SMALL_HEIGHT = 20f;
    public final static float ONE_CHAR_SMALL_WIDTH = 10f;

    public final static int debugLvl = Logger.DEBUG;

    private GameConfig() {}
}
