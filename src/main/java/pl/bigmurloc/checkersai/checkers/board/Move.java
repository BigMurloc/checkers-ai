package pl.bigmurloc.checkersai.checkers.board;

import pl.bigmurloc.checkersai.checkers.shared.Position;

/**
 * Represents a move on the board
 * @param currentPosition Current position of checker
 * @param newPosition New position of checker
 * @param checkerColor Color of checker
 * @param captures Whether the move will result in capture or not
 */
public record Move(Position currentPosition, Position newPosition, CheckerColor checkerColor, boolean captures) {
    @Override
    public String toString() {
        return currentPosition + " to " + newPosition;
    }
}
