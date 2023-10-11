package pl.bigmurloc.checkersai.checkers.shared;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Representation of position on the board
 */
public class Position {

    /**
     * @return Returns possible the diagonal positions relative to current position, excludes positions outside of board bounds
     */
    public List<Position> getDiagonalPositions() {
        List<Position> positions = new ArrayList<>();
        int x = getX();
        int y = getY();

        if (x > 0 && y > 0) {
            positions.add(new Position(Horizontal.fromX(x - 1), Vertical.fromY(y - 1)));
        }

        if (x < 9 && y < 9) {
            positions.add(new Position(Horizontal.fromX(x + 1), Vertical.fromY(y + 1)));
        }

        if (x > 0 && y < 9) {
            positions.add(new Position(Horizontal.fromX(x - 1), Vertical.fromY(y + 1)));
        }

        if (x < 9 && y > 0) {
            positions.add(new Position(Horizontal.fromX(x + 1), Vertical.fromY(y - 1)));
        }

        return positions;
    }

    @Override
    public String toString() {
        return horizontal.toString() + vertical.toString();
    }

    /**
     * @param originPosition The origin position
     * @return Returns the next diagonal position from the origin position, empty if outside of board bounds
     */
    public Optional<Position> nextDiagonalPositionFrom(Position originPosition) {
        return new DiagonalPointsFinder().nextDiagonalPositionFrom(this, originPosition);
    }

    public boolean hasNextDiagonalPositionFrom(Position originPosition) {
        return new DiagonalPointsFinder().hasNextDiagonalPosition(this, originPosition);
    }

    /**
     * Horizontal representation of fields on the board
     */
    public enum Horizontal {
        A,
        B,
        C,
        D,
        E,
        F,
        G,
        H,
        I,
        J;


        /**
         * @return Returns the X coordinate of the Horizontal enum in cartesian coordinate system
         */
        public int getX() {
            return this.ordinal();
        }

        /**
         * @param x The X coordinate of the Horizontal enum in cartesian coordinate system
         * @return Returns the Horizontal enum value for given X coordinate
         */
        public static Horizontal fromX(int x) {
            return Horizontal.values()[x];
        }
    }

    /**
     * Vertical representation of fields on the board
     */
    public enum Vertical {
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN;

        /**
         * @return Returns the Y coordinate of the Vertical enum in cartesian coordinate system
         */
        public int getY() {
            return this.ordinal();
        }

        /**
         * @param y The Y coordinate of the Vertical enum in cartesian coordinate system
         * @return Returns the Vertical enum value for given Y coordinate
         */
        public static Vertical fromY(int y) {
            return Vertical.values()[y];
        }

        /**
         * @return Returns true if the Vertical enum is odd, false otherwise
         */
        public boolean isOdd() {
            return (this.ordinal() + 1) % 2 != 0; // +1 because ordinal starts from 0
        }

        /**
         * @return Returns true if the Vertical enum is even, false otherwise
         */
        public boolean isEven() {
            return (this.ordinal() + 1) % 2 == 0; // +1 because ordinal starts from 0
        }

        /**
         * @return Returns the string representation of the Vertical enum
         */
        @Override
        public String toString() {
            return switch (this) {
                case ONE -> "1";
                case TWO -> "2";
                case THREE -> "3";
                case FOUR -> "4";
                case FIVE -> "5";
                case SIX -> "6";
                case SEVEN -> "7";
                case EIGHT -> "8";
                case NINE -> "9";
                case TEN -> "10";
            };
        }
    }

    private Horizontal horizontal;
    private Vertical vertical;

    public Position(Horizontal horizontal, Vertical vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public int getX() {
        return horizontal.getX();
    }

    public int getY() {
        return vertical.getY();
    }

    public static List<Position> getDiagonalPositionsBetweenTwoPoints(Position a, Position b) {
        return new DiagonalPointsFinder().findDiagonalPointsBetweenTwoPoints(a, b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return horizontal == position.horizontal && vertical == position.vertical;
    }

    @Override
    public int hashCode() {
        return Objects.hash(horizontal, vertical);
    }
}

class DiagonalPointsFinder {

    /**
     * Representation of direction relative to destination position from origin position
     */
    enum Direction {
        UP_LEFT,
        UP_RIGHT,
        DOWN_LEFT,
        DOWN_RIGHT
    }

    /**
     * @param a The origin position
     * @param b The destination position
     * @return Returns the list of positions between a and b, a & b exclusive
     */
    List<Position> findDiagonalPointsBetweenTwoPoints(Position a, Position b) {
        var direction = getDirection(a, b);
        switch (direction) {
            case UP_LEFT:
                return findDiagonalPointsUpLeft(a, b);
            case UP_RIGHT:
                return findDiagonalPointsUpRight(a, b);
            case DOWN_LEFT:
                return findDiagonalPointsDownLeft(a, b);
            case DOWN_RIGHT:
                return findDiagonalPointsDownRight(a, b);
            default:
                throw new IllegalArgumentException("Direction not supported");
        }
    }

    /**
     * @param currentPosition The current position
     * @param originPosition The origin position
     * @return Optional of Position, empty if the position would be outside of board bounds
     */
    Optional<Position> nextDiagonalPositionFrom(Position currentPosition, Position originPosition) {
        if (!hasNextDiagonalPosition(currentPosition, originPosition)) {
            return Optional.empty();
        }

        var direction = getDirection(originPosition, currentPosition);

        return switch (direction) {
            case UP_LEFT ->
                    Optional.of(new Position(Position.Horizontal.fromX(currentPosition.getX() - 1), Position.Vertical.fromY(currentPosition.getY() + 1)));
            case UP_RIGHT ->
                    Optional.of(new Position(Position.Horizontal.fromX(currentPosition.getX() + 1), Position.Vertical.fromY(currentPosition.getY() + 1)));
            case DOWN_LEFT ->
                    Optional.of(new Position(Position.Horizontal.fromX(currentPosition.getX() - 1), Position.Vertical.fromY(currentPosition.getY() - 1)));
            case DOWN_RIGHT ->
                    Optional.of(new Position(Position.Horizontal.fromX(currentPosition.getX() + 1), Position.Vertical.fromY(currentPosition.getY() - 1)));
        };
    }

    /**
     *
     * @param currentPosition The current position
     * @param originPosition The origin position
     * @return Returns true if there is a next diagonal position from the current position in the direction of the origin position and false if the position would be outside of board bounds
     */
    boolean hasNextDiagonalPosition(Position currentPosition, Position originPosition) {
        var direction = getDirection(originPosition, currentPosition);

        return switch (direction) {
            case UP_LEFT -> currentPosition.getX() > 0 && currentPosition.getY() < 9;
            case UP_RIGHT -> currentPosition.getX() < 9 && currentPosition.getY() < 9;
            case DOWN_LEFT -> currentPosition.getX() > 0 && currentPosition.getY() > 0;
            case DOWN_RIGHT -> currentPosition.getX() < 9 && currentPosition.getY() > 0;
        };
    }

    /**
     * @param a The origin position
     * @param b The destination position
     * @return Returns the diagonal points between a and b in the down-right direction a & b exclusive
     */
    private List<Position> findDiagonalPointsDownRight(Position a, Position b) {
        var startingX = a.getX();
        var startingY = a.getY();
        var endingX = b.getX();
        var endingY = b.getY();

        var diagonalPoints = new ArrayList<Position>();

        while(startingX != endingX && startingY != endingY) {
            startingX++;
            startingY--;
            diagonalPoints.add(new Position(Position.Horizontal.fromX(startingX), Position.Vertical.fromY(startingY)));
        }

        return diagonalPoints;
    }

    /**
     * @param a The origin position
     * @param b The destination position
     * @return Returns the diagonal points between a and b in the down-left direction a & b exclusive
     */
    private List<Position> findDiagonalPointsDownLeft(Position a, Position b) {
        var startingX = a.getX();
        var startingY = a.getY();
        var endingX = b.getX();
        var endingY = b.getY();

        var diagonalPoints = new ArrayList<Position>();

        while(startingX != endingX && startingY != endingY) {
            startingX--;
            startingY--;
            diagonalPoints.add(new Position(Position.Horizontal.fromX(startingX), Position.Vertical.fromY(startingY)));
        }

        return diagonalPoints;
    }

    /**
     * @param a The origin position
     * @param b The destination position
     * @return Returns the diagonal points between a and b in the up-right direction a & b exclusive
     */
    private List<Position> findDiagonalPointsUpRight(Position a, Position b) {
        var startingX = a.getX();
        var startingY = a.getY();
        var endingX = b.getX();
        var endingY = b.getY();

        var diagonalPoints = new ArrayList<Position>();

        while(startingX != endingX && startingY != endingY) {
            startingX++;
            startingY++;
            diagonalPoints.add(new Position(Position.Horizontal.fromX(startingX), Position.Vertical.fromY(startingY)));
        }

        return diagonalPoints;
    }

    /**
     * @param a The origin position
     * @param b The destination position
     * @return Returns the diagonal points between a and b in the up-left direction a & b exclusive
     */
    private List<Position> findDiagonalPointsUpLeft(Position a, Position b) {
        var startingX = a.getX();
        var startingY = a.getY();
        var endingX = b.getX();
        var endingY = b.getY();

        var diagonalPoints = new ArrayList<Position>();

        while(startingX != endingX && startingY != endingY) {
            startingX--;
            startingY++;
            diagonalPoints.add(new Position(Position.Horizontal.fromX(startingX), Position.Vertical.fromY(startingY)));
        }

        return diagonalPoints;
    }

    /**
     * @param a The origin position
     * @param b The destination position
     * @return Returns the direction from a to b (whether the b is in the up-left, up-right, down-left or down-right corner relative to a)
     */
    private Direction getDirection(Position a, Position b) {
        if (a.getX() > b.getX() && a.getY() < b.getY()) {
            return Direction.UP_LEFT;
        }

        if (a.getX() < b.getX() && a.getY() < b.getY()) {
            return Direction.UP_RIGHT;
        }

        if (a.getX() > b.getX() && a.getY() > b.getY()) {
            return Direction.DOWN_LEFT;
        }

        if (a.getX() < b.getX() && a.getY() > b.getY()) {
            return Direction.DOWN_RIGHT;
        }

        throw new IllegalArgumentException("Direction not supported");
    }
}
