package pl.bigmurloc.checkersai.checkers.shared;

public class Position {
    enum Horizontal {
        A,
        B,
        C,
        D,
        E,
        F,
        G,
        H
    }

    enum Vertical {
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT
    }

    private Horizontal horizontal;
    private Vertical vertical;

    public Position(Horizontal horizontal, Vertical vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

}
