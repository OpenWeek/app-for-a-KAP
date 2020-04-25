package gdx.kapotopia;

public class Utils {

    @org.jetbrains.annotations.Contract(pure = true)
    public static int floorOfAMultipleOf250(int nbr) {
        for (int i=2000; i > 0; i = i - 250) {
            if(nbr > i) return i;
        }
        return 0;
    }
}
