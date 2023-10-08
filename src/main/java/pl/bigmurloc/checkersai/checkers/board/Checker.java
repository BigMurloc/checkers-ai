package pl.bigmurloc.checkersai.checkers.board;

import pl.bigmurloc.checkersai.checkers.shared.Position;

class Checker {

    enum CheckerRank {
        MAN,
        KING
    }

    private CheckerColor color;
    private CheckerRank rank = CheckerRank.MAN;
    private Position position;

    public void promote() {
        this.rank = CheckerRank.KING;
    }

    Checker(CheckerColor color, Position position) {
        this.color = color;
    }
}
