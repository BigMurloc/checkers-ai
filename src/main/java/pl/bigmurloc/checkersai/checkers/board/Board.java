package pl.bigmurloc.checkersai.checkers.board;

import pl.bigmurloc.checkersai.checkers.shared.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static pl.bigmurloc.checkersai.checkers.shared.Position.Horizontal.*;

public interface Board {

    @Deprecated
    void makeMove(Checker checker, Position position);
    void makeMove(Move move);

    boolean isOccupied(Position position);

    List<Move> availableMoves(CheckerColor color);
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
        List<Checker> checkers =  new BoardInitializationHelper().getCheckersAtPositions();
        init(checkers);
    }

    public void init(List<Checker> checkers) {
        initEmptyFields();
        initCheckers(checkers);
    }

    @Override
    @Deprecated
    public void makeMove(Checker checker, Position newPosition) {
        var oldPosition = checker.getPosition();
        var oldField = getField(oldPosition);
        var newField = getField(newPosition);

        verifyMoveIsLegal(oldPosition, newPosition);
        removeEnemyCheckersIfJumpedOver(checker.getColor(), oldPosition, newPosition);

        oldField.checker = null;
        newField.checker = checker;
    }

    @Override
    public void makeMove(Move move) {
        var oldField = getField(move.currentPosition());
        var newField = getField(move.newPosition());

        verifyMoveIsLegal(move.currentPosition(), move.newPosition());
        removeEnemyCheckersIfJumpedOver(move.checkerColor(), move.currentPosition(), move.newPosition());

        newField.checker = oldField.checker;
        oldField.checker = null;
    }

    public boolean isOccupied(Position position) {
        return getField(position).isOccupied();
    }

    @Override
    public List<Move> availableMoves(CheckerColor checkerColor) {
        List<Move> result = new ArrayList<>();

        for (Field[] fields : fields) {
            for (Field field : fields) {
                if (field.isOccupied(checkerColor)) {
                    result.addAll(analyzesMovesForField(field));
                }
            }
        }

        return result;
    }

    boolean existsOnBoard(Checker checker) {
        if (checker == null) {
            throw new IllegalArgumentException();
        }

        var checkerFromField = getField(checker.getPosition()).checker;

        if (checkerFromField != null) {
            return true;
        }

        for (int x = 0; x < xDimension; x++) {
            for (int y = 0; y < yDimension; y++) {
                if (checker.equals(fields[x][y].checker)) {
                    return true;
                }
            }
        }

        return false;
    }

    private void removeEnemyCheckersIfJumpedOver(CheckerColor checkerColor, Position oldPosition, Position newPosition) {
        List<Position> diagonalPositions = Position.getDiagonalPositionsBetweenTwoPoints(oldPosition, newPosition);
        CheckerColor enemyColor = checkerColor == CheckerColor.WHITE ? CheckerColor.BLACK : CheckerColor.WHITE;
        for (Position diagonalPosition : diagonalPositions) {
            var field = getField(diagonalPosition);
            if (field.isOccupied()) {
                getField(diagonalPosition);
                if (field.checker.getColor() == enemyColor) {
                    field.checker = null;
                }
            }
        }
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

    private List<Move> analyzesMovesForField(Field field) {
        List<Move> result = new ArrayList<>();
        Position currentPosition = field.position;
        CheckerColor currentColor = field.checker.getColor();
        List<Position> diagonalPositions = currentPosition.getDiagonalPositions();

        for (Position diagonalPosition : diagonalPositions) {
            verifyMoveIsLegal(currentPosition, diagonalPosition);
            var diagonalField = getField(diagonalPosition);
            if (diagonalField.isOccupied() && diagonalPosition.hasNextDiagonalPositionFrom(currentPosition)) {
                result.add(new Move(currentPosition, diagonalPosition.nextDiagonalPositionFrom(currentPosition), currentColor));
            } else {
                result.add(new Move(currentPosition, diagonalPosition, currentColor));
            }
        }

        return result;
    }
}

class BoardInitializationHelper {

    private int id = 1;
    private final List<Position.Horizontal> whiteRows = List.of(A, B, C, D);
    private final List<Position.Horizontal> blackRows = List.of(G, H, I, J);
    List<Checker> getCheckersAtPositions() {
        return initCheckers();
    }

    private List<Checker> initCheckers() {
        List<Position.Horizontal> rows = Stream.of(whiteRows, blackRows).flatMap(List::stream).toList();
        List<Checker> result = new ArrayList<>();

        for(Position.Horizontal horizontal : rows) {
            for(Position.Vertical vertical : Position.Vertical.values()) {
                if(vertical.isOdd() && isOddNumberedRow(horizontal)) {
                    result.add(new Checker(id, resolveColorBasedOnRow(horizontal), new Position(horizontal, vertical)));
                    id++;
                }
                if (vertical.isEven() && isEvenNumberedRow(horizontal)) {
                    result.add(new Checker(id, resolveColorBasedOnRow(horizontal), new Position(horizontal, vertical)));
                    id++;
                }
            }
        }
        return result;
    }

    private CheckerColor resolveColorBasedOnRow(Position.Horizontal horizontal) {
        if (!whiteRows.contains(horizontal) && !blackRows.contains(horizontal)) {
            throw new IllegalArgumentException("Horizontal position is not valid");
        }

        return whiteRows.contains(horizontal) ? CheckerColor.WHITE : CheckerColor.BLACK;
    }

    private boolean isOddNumberedRow(Position.Horizontal horizontal) {
        return List.of(A, C, G, I).contains(horizontal);
    }

    private boolean isEvenNumberedRow(Position.Horizontal horizontal) {
        return List.of(B, D, H, J).contains(horizontal);
    }
}
