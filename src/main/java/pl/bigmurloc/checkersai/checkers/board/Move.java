package pl.bigmurloc.checkersai.checkers.board;

import pl.bigmurloc.checkersai.checkers.shared.Position;

record Move(Position currentPosition, Position newPosition, CheckerColor checkerColor) {
}
