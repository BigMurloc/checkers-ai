package pl.bigmurloc.checkersai.checkers.shared;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Position {
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

        public int getX() {
            return this.ordinal();
        }

        public static Horizontal fromX(int x) {
            return Horizontal.values()[x];
        }
    }

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

        public int getY() {
            return this.ordinal();
        }

        public static Vertical fromY(int y) {
            return Vertical.values()[y];
        }

        public boolean isOdd() {
            return (this.ordinal() + 1) % 2 != 0; // +1 because ordinal starts from 0
        }

        public boolean isEven() {
            return (this.ordinal() + 1) % 2 == 0; // +1 because ordinal starts from 0
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

    enum Direction {
        UP_LEFT,
        UP_RIGHT,
        DOWN_LEFT,
        DOWN_RIGHT
    }

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
