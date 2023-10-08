package pl.bigmurloc.checkersai.checkers.board;

import pl.bigmurloc.checkersai.checkers.shared.Position;

import java.util.List;

public interface Board {

    void makeMove(Checker checker, Position position);

    boolean isOccupied(Position position);
}

class BoardImpl implements Board {

    private int xDimension = 10;
    private int yDimension = 10;

    class Field {
        private Checker checker;
        private Position position;

        Field(Checker checker, Position position) {
            this.checker = checker;
            this.position = position;
        }

        boolean isOccupied() {
            return checker != null;
        }
    }

    private Field[][] fields;

    BoardImpl() {
    }

    public void init(List<Checker> checkers) {
        initEmptyFields();
        initCheckers(checkers);
    }

    @Override
    public void makeMove(Checker checker, Position newPosition) {
        var oldPosition = checker.getPosition();
        var oldField = getField(oldPosition);
        var newField = getField(newPosition);

        verifyMoveIsLegal(oldPosition, newPosition);

        oldField.checker = null;
        newField.checker = checker;
    }

    public boolean isOccupied(Position position) {
        return getField(position).isOccupied();
    }

    private void initEmptyFields() {
        fields = new Field[xDimension][yDimension];
        for (int x = 0; x < xDimension; x++) {
            for (int y = 0; y < yDimension; y++) {
                fields[x][y] = new Field(null, new Position(Position.Horizontal.fromX(x), Position.Vertical.fromY(y)));
            }
        }
    }

    private void initCheckers(List<Checker> checkers) {
        for (Checker checker : checkers) {
            fields[checker.getPosition().getX()][checker.getPosition().getY()].checker = checker;
        }
    }

    private Field getField(Position position) {
        return fields[position.getX()][position.getY()];
    }

    private void verifyMoveIsLegal(Position oldPosition, Position newPosition) {
        if (oldPosition.getY() == newPosition.getY()) {
            throw new IllegalMoveException("Horizontal move is not allowed");
        }
        if (oldPosition.getX() == newPosition.getX()) {
            throw new IllegalMoveException("Vertical move is not allowed");
        }
    }
}
