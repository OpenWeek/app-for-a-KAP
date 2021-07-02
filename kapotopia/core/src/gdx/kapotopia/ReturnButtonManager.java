package gdx.kapotopia;

import java.util.HashMap;

public class ReturnButtonManager {

    private HashMap<ScreenType, ScreenType> previousScreens;
    private Kapotopia game;
    private ScreenType returnScreen;
    public ReturnButtonManager(Kapotopia game) {
        this.game = game;
        this.previousScreens = new HashMap<ScreenType, ScreenType>();
        this.previousScreens.put(ScreenType.INTROCUTSCENE, ScreenType.INTROCUTSCENE);
        this.previousScreens.put(ScreenType.MAINMENU, ScreenType.INTROCUTSCENE);
        this.previousScreens.put(ScreenType.GAME1, ScreenType.MOCKUPG1);
        this.previousScreens.put(ScreenType.GAME2, ScreenType.MOCKUPG2);
        this.previousScreens.put(ScreenType.GAME3, ScreenType.MOCKUPG3);
        this.previousScreens.put(ScreenType.MOCKUPG1, ScreenType.WORLD1);
        this.previousScreens.put(ScreenType.MOCKUPG2, ScreenType.WORLD2);
        this.previousScreens.put(ScreenType.MOCKUPG3, ScreenType.WORLD3);
        this.previousScreens.put(ScreenType.BILANG1, ScreenType.GAME1);
        this.previousScreens.put(ScreenType.WORLD1, ScreenType.MAINMENU);
        this.previousScreens.put(ScreenType.WORLD2, ScreenType.MAINMENU);
        this.previousScreens.put(ScreenType.WORLD3, ScreenType.MAINMENU);
        this.previousScreens.put(ScreenType.STIDEX, ScreenType.MAINMENU);
        this.previousScreens.put(ScreenType.DIFGAME1, ScreenType.WORLD1);
        this.previousScreens.put(ScreenType.OPTIONS, ScreenType.MAINMENU);
        this.returnScreen = previousScreens.get(ScreenType.INTROCUTSCENE);
    }
    public void updateReturn(ScreenType newScreen) {
        this.returnScreen = this.previousScreens.get(newScreen);
    }
    public void goBack() {
       this.game.changeScreen(this.returnScreen);
    }
}
