package pl.bigmurloc.checkersai.checkers.board;

import org.springframework.stereotype.Component;
import pl.bigmurloc.checkersai.checkers.shared.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static pl.bigmurloc.checkersai.checkers.shared.Position.Horizontal.*;

public interface Board {

    int X_DIMENSION = 10;
    int Y_DIMENSION = 10;
    @Deprecated
    void makeMove(Checker checker, Position position);
    void makeMove(Move move);

    boolean isOccupied(Position position);

    List<Move> availableMoves(CheckerColor color);

    List<FieldDto> getFields();

    void init();

    boolean isFinished();

    int scoreFor(CheckerColor color);

    Board clone();

    int piecesLeft(CheckerColor checkerColor);
}

@Component
class BoardImpl implements Board {

    public BoardImpl(Field[][] fields) {
        initEmptyFields();
        List<Checker> copiedCheckers = fieldsStream(fields)
                .filter(Field::isOccupied)
                .map(field -> field.checker.clone())
                .toList();
        init(copiedCheckers);
    }

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

        FieldDto toDto() {
            return new FieldDto(checker, position);
        }
    }

    private Field[][] fields;

    BoardImpl() {
    }

    public void init() {
        List<Checker> checkers =  new BoardInitializationHelper().getCheckersAtPositions();
        init(checkers);
    }

    @Override
    public boolean isFinished() {
        var whiteCheckers = fieldsStream()
                .filter(field -> field.isOccupied(CheckerColor.WHITE))
                .count();
        var blackCheckers = fieldsStream()
                .filter(field -> field.isOccupied(CheckerColor.BLACK))
                .count();

        return (whiteCheckers == 0) || (blackCheckers == 0);
    }

    @Override
    public int scoreFor(CheckerColor color) {
        int whiteScore = calculateScoreFor(CheckerColor.WHITE);
        int blackScore = calculateScoreFor(CheckerColor.BLACK);

        return color == CheckerColor.WHITE ? whiteScore - blackScore : blackScore - whiteScore;
    }

    private int calculateScoreFor(CheckerColor color) {
        return fieldsStream()
                .filter(field -> field.isOccupied(color))
                .map(field -> field.checker.getScore())
                .reduce(0, Integer::sum);
    }

    @Override
    public Board clone() {
        return new BoardImpl(fields);
    }

    @Override
    public int piecesLeft(CheckerColor checkerColor) {
        return fieldsStream().filter(field -> field.isOccupied(checkerColor)).toList().size();
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

        if (result.stream().filter(Move::captures).toList().size() > 0) {
            return result.stream().filter(Move::captures).toList();
        }

        return result;
    }

    @Override
    public List<FieldDto> getFields() {
        List<FieldDto> result = new ArrayList<>();

        for (Field[] fields : fields) {
            for (Field field : fields) {
                result.add(field.toDto());
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

        for (int x = 0; x < X_DIMENSION; x++) {
            for (int y = 0; y < Y_DIMENSION; y++) {
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
        fields = new Field[X_DIMENSION][Y_DIMENSION];
        for (int x = 0; x < X_DIMENSION; x++) {
            for (int y = 0; y < Y_DIMENSION; y++) {
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
        CheckerColor enemyColor = field.checker.getColor() == CheckerColor.BLACK ? CheckerColor.WHITE : CheckerColor.BLACK;
        List<Position> diagonalPositions = currentPosition.getDiagonalPositions();
        var hasEnemyNeighboursAvailableForCapture = hasEnemyNeighboursAvailableForCapture(currentPosition, currentColor);

        for (Position diagonalPosition : diagonalPositions) {
            verifyMoveIsLegal(currentPosition, diagonalPosition);
            var diagonalField = getField(diagonalPosition);
            var nextDiagonalPosition = diagonalPosition.nextDiagonalPositionFrom(currentPosition);
            if (diagonalField.isOccupied(enemyColor) &&  nextDiagonalPosition.isPresent()) {
                result.add(new Move(currentPosition, nextDiagonalPosition.get(), currentColor, true));
            } else if (!hasEnemyNeighboursAvailableForCapture && !diagonalField.isOccupied(currentColor)) {
                result.add(new Move(currentPosition, diagonalPosition, currentColor, false));
            }
        }

        return result;
    }

    private boolean hasEnemyNeighboursAvailableForCapture(Position position, CheckerColor color) {
        List<Position> diagonalPositions = position.getDiagonalPositions();
        for (Position diagonalPosition : diagonalPositions) {
            var field = getField(diagonalPosition);
            if (field.isOccupied() && field.checker.getColor() != color) {
                var nextDiagonalPosition = diagonalPosition.nextDiagonalPositionFrom(position);
                if (nextDiagonalPosition.isPresent() && !getField(nextDiagonalPosition.get()).isOccupied()) {
                    return true;
                }
            }
        }
        return false;
    }

    private Stream<Field> fieldsStream() {
        return Arrays.stream(fields).flatMap(Arrays::stream);
    }

    private Stream<Field> fieldsStream(Field[][] fields) {
        return Arrays.stream(fields).flatMap(Arrays::stream);
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
