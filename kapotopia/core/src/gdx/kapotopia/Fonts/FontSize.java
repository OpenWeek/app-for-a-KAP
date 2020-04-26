package gdx.kapotopia.Fonts;

public enum FontSize {
    TINY,
    SMALL,
    MIDDLE,
    NORMAL,
    BIG;

    private static final int TINY_SIZE = 14;
    private static final int SMALL_SIZE = 25;
    private static final int MIDDLE_SIZE = 33;
    private static final int NORMAL_SIZE = 40;
    private static final int BIG_SIZE = 60;

    public static int getRawSize(FontSize size) {
        switch (size) {
            case TINY:
                return TINY_SIZE;
            case SMALL:
                return SMALL_SIZE;
            case MIDDLE:
                return MIDDLE_SIZE;
            case NORMAL:
                return NORMAL_SIZE;
            case BIG:
                return BIG_SIZE;
        }
        return 0;
    }

    public static FontSize getSize(int rawSize) {
        switch (rawSize) {
            case TINY_SIZE:
                return TINY;
            case SMALL_SIZE:
                return SMALL;
            case MIDDLE_SIZE:
                return MIDDLE;
            case NORMAL_SIZE:
                return NORMAL;
            case BIG_SIZE:
                return BIG;
            default:
                return NORMAL;
        }
    }
}
