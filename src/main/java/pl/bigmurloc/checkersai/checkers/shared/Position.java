package pl.bigmurloc.checkersai.checkers.shared;

public class Position {
    public int getX() {
        return horizontal.getX();
    }

    public int getY() {
        return vertical.getY();
    }

    public enum Horizontal {
        A,
        B,
        C,
        D,
        E,
        F,
        G,
        H;

        public int getX() {
            return this.ordinal();
        }

        public static Horizontal fromX(int x) {
            return Horizontal.values()[x];
        }
    }

    public enum Vertical {
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT;

        public int getY() {
            return this.ordinal();
        }

        public static Vertical fromY(int y) {
            return Vertical.values()[y];
        }
    }

    private Horizontal horizontal;
    private Vertical vertical;

    public Position(Horizontal horizontal, Vertical vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

}
