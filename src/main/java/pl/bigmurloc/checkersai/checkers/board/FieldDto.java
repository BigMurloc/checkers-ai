package pl.bigmurloc.checkersai.checkers.board;

import pl.bigmurloc.checkersai.checkers.shared.Position;

class FieldDto {
    private Checker checker;
    private Position position;

    FieldDto(Checker checker, Position position) {
        this.checker = checker;
        this.position = position;
    }

    boolean isOccupied() {
        return checker != null;
    }

    boolean isOccupied(CheckerColor color) {
        return checker != null && checker.getColor() == color;
    }
}
