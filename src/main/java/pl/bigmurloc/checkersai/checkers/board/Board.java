package pl.bigmurloc.checkersai.checkers.board;

import pl.bigmurloc.checkersai.checkers.shared.Position;

public interface Board {

    void makeMove(Checker checker, Position position);

}

class BoardImpl implements Board {

    @Override
    public void makeMove(Checker checker, Position position) {
    }
}
