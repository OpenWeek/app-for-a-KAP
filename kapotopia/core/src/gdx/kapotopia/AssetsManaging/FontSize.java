package gdx.kapotopia.AssetsManaging;

public enum FontSize {
    TINY,
    SMALL,
    MIDDLE,
    NORMAL,
    BIG;

    public static int getRawSize(FontSize size) {
        switch (size) {
            case TINY:
                return 14;
            case SMALL:
                return 25;
            case MIDDLE:
                return 33;
            case NORMAL:
                return 40;
            case BIG:
                return 60;
        }
        return 0;
    }
}
