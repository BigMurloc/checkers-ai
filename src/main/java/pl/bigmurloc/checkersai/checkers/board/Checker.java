package pl.bigmurloc.checkersai.checkers.board;

import pl.bigmurloc.checkersai.checkers.shared.Position;

/**
 * Represents a checker on the board
 */
class Checker {

    enum CheckerRank {
        MAN,
        KING;
    }
    private int id;

    private CheckerColor color;
    private CheckerRank rank = CheckerRank.MAN;
    private Position position;
    public void promote() {
        this.rank = CheckerRank.KING;
    }

    Position getPosition() {
        return position;
    }

    CheckerColor getColor() {
        return color;
    }

    Checker(int id, CheckerColor color, Position position) {
        this.color = color;
        this.position = position;
    }

    Checker(CheckerColor color, Position position, CheckerRank rank) {
        this.color = color;
        this.position = position;
    }

    int getScore() {
        if (rank == CheckerRank.KING) {
            return 2;
        }
        return 1;
    }

    public Checker clone() {
        return new Checker(color, position, rank);
    }
}
