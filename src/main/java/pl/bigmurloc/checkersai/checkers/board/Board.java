package pl.bigmurloc.checkersai.checkers.board;

import pl.bigmurloc.checkersai.checkers.shared.Position;

public interface Board {

    void makeMove(Checker checker, Position position);

    boolean isOccupied(Position position);
}

class BoardImpl implements Board {

    BoardImpl() {
    }
    @Override
    public void makeMove(Checker checker, Position position) {
    }

    public boolean isOccupied(Position position) {
        return false;
    }
}
