package gdx.kapotopia.Game1;

/**
 * Listener Interface for anyone who need to know the number of lifes of someone else
 */
public interface MireilleListener {
    void lifeChanged(byte life);
    void scoreChanged(int score);
    void caughtNotSTD(boolean c);
}
