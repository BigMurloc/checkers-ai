package pl.bigmurloc.checkersai.checkers.board;

import pl.bigmurloc.checkersai.checkers.shared.Position;

public record Move(Position currentPosition, Position newPosition, CheckerColor checkerColor) {
    @Override
    public String toString() {
        return currentPosition + " to " + newPosition;
    }
}
