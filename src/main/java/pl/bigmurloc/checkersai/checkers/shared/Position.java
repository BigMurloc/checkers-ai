package pl.bigmurloc.checkersai.checkers.shared;

public class Position {
    public enum Horizontal {
        A,
        B,
        C,
        D,
        E,
        F,
        G,
        H,
        I,
        J;

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
        EIGHT,
        NINE,
        TEN;

        public int getY() {
            return this.ordinal();
        }

        public static Vertical fromY(int y) {
            return Vertical.values()[y];
        }

        public boolean isOdd() {
            return (this.ordinal() + 1) % 2 != 0; // +1 because ordinal starts from 0
        }

        public boolean isEven() {
            return (this.ordinal() + 1) % 2 == 0; // +1 because ordinal starts from 0
        }


    }

    private Horizontal horizontal;
    private Vertical vertical;

    public Position(Horizontal horizontal, Vertical vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public int getX() {
        return horizontal.getX();
    }

    public int getY() {
        return vertical.getY();
    }

}
