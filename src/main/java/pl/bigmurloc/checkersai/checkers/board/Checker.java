package pl.bigmurloc.checkersai.checkers.board;

public interface Checker {

    void promote();

}


class CheckerImpl implements Checker {

    enum CheckerColor {
        WHITE,
        BLACK
    }

    enum CheckerRank {
        MAN,
        KING
    }

    private CheckerColor color;
    private CheckerRank rank = CheckerRank.MAN;

    @Override
    public void promote() {
        this.rank = CheckerRank.KING;
    }

    CheckerImpl(CheckerColor color) {
        this.color = color;
    }
}
