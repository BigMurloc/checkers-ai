package pl.bigmurloc.checkersai.checkers.board;

import pl.bigmurloc.checkersai.checkers.shared.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static pl.bigmurloc.checkersai.checkers.shared.Position.Horizontal.*;

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

        boolean isOccupied(CheckerColor color) {
            return checker != null && checker.getColor() == color;
        }
    }

    private Field[][] fields;

    BoardImpl() {
    }

    public void init() {
        List<Checker> checkers =  BoardInitializationHelper.getCheckersAtPositions();
        init(checkers);
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

    public boolean isOccupied(Position position, CheckerColor color) {
        return getField(position).isOccupied(color);
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
        if (oldPosition.equals(newPosition)) {
            throw new IllegalMoveException("Move to the same position is not allowed");
        }
        if (oldPosition.getY() == newPosition.getY()) {
            throw new IllegalMoveException("Horizontal move is not allowed");
        }
        if (oldPosition.getX() == newPosition.getX()) {
            throw new IllegalMoveException("Vertical move is not allowed");
        }
    }
}

class BoardInitializationHelper {

    private static final List<Position.Horizontal> whiteRows = List.of(A, B, C, D);
    private static final List<Position.Horizontal> blackRows = List.of(G, H, I, J);
    static List<Checker> getCheckersAtPositions() {
        return initCheckers();
    }

    private static List<Checker> initCheckers() {
        List<Position.Horizontal> rows = Stream.of(whiteRows, blackRows).flatMap(List::stream).toList();
        List<Checker> result = new ArrayList<>();

        for(Position.Horizontal horizontal : rows) {
            for(Position.Vertical vertical : Position.Vertical.values()) {
                if(vertical.isOdd() && isOddNumberedRow(horizontal)) {
                    result.add(new Checker(resolveColorBasedOnRow(horizontal), new Position(horizontal, vertical)));
                }
                if (vertical.isEven() && isEvenNumberedRow(horizontal)) {
                    result.add(new Checker(resolveColorBasedOnRow(horizontal), new Position(horizontal, vertical)));
                }
            }
        }
        return result;
    }

    private static CheckerColor resolveColorBasedOnRow(Position.Horizontal horizontal) {
        if (!whiteRows.contains(horizontal) && !blackRows.contains(horizontal)) {
            throw new IllegalArgumentException("Horizontal position is not valid");
        }

        return whiteRows.contains(horizontal) ? CheckerColor.WHITE : CheckerColor.BLACK;
    }

    private static boolean isOddNumberedRow(Position.Horizontal horizontal) {
        return List.of(A, C, G, I).contains(horizontal);
    }

    private static boolean isEvenNumberedRow(Position.Horizontal horizontal) {
        return List.of(B, D, H, J).contains(horizontal);
    }
}
