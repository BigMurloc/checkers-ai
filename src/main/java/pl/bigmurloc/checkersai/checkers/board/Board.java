package pl.bigmurloc.checkersai.checkers.board;

import pl.bigmurloc.checkersai.checkers.shared.Position;

import java.util.List;

public interface Board {

    void makeMove(Checker checker, Position position);

    boolean isOccupied(Position position);
}

class BoardImpl implements Board {

    private int xDimension = 8;
    private int yDimension = 8;

    class Field {
        private Checker checker;
        private Position position;

        Field(Checker checker, Position position) {
            this.checker = checker;
            this.position = position;
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
    public void makeMove(Checker checker, Position position) {
    }

    public boolean isOccupied(Position position) {
        return false;
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
}
