package gdx.kapotopia.Game1;

import java.util.Random;

public enum VIRUS_TYPE {
    IST, FAKEIST, BOSS;

    private static final Random RANDOM = new Random();

    public static VIRUS_TYPE getRandomType() {
        return VIRUS_TYPE.values()[RANDOM.nextInt(VIRUS_TYPE.values().length)];
    }
}
