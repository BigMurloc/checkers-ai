package pl.bigmurloc.checkersai.checkers.board;

import pl.bigmurloc.checkersai.checkers.shared.Position;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static pl.bigmurloc.checkersai.checkers.shared.Position.Horizontal.*;
import static pl.bigmurloc.checkersai.checkers.shared.Position.Vertical.*;

class BoardInitTestUtils {

    public static void assertThatIsBoardInitializedCorrectly(Board board) {
        assertThatMiddleRowsAreEmpty(board);
        assertThatWhitesOccupiedFieldsAreCorrectlyInitialized(board);
        assertThatWhitesNonOccupiedFieldsAreCorrectlyInitialized(board);
        assertThatBlacksOccupiedFieldsAreCorrectlyInitialized(board);
        assertThatBlacksNonOccupiedFieldsAreCorrectlyInitialized(board);

    }

    private static void assertThatWhitesOccupiedFieldsAreCorrectlyInitialized(Board board) {
        List<Position.Horizontal> oddFieldsOnRow = List.of(A, C);
        List<Position.Horizontal> evenFieldsOnRow = List.of(B, D);

        for (Position.Horizontal horizontal : oddFieldsOnRow) {
            assertThat(board.isOccupied(new Position(horizontal, ONE))).isTrue();
            assertThat(board.isOccupied(new Position(horizontal, THREE))).isTrue();
            assertThat(board.isOccupied(new Position(horizontal, FIVE))).isTrue();
            assertThat(board.isOccupied(new Position(horizontal, SEVEN))).isTrue();
            assertThat(board.isOccupied(new Position(horizontal, NINE))).isTrue();
        }

        for (Position.Horizontal horizontal : evenFieldsOnRow) {
            assertThat(board.isOccupied(new Position(horizontal, TWO))).isTrue();
            assertThat(board.isOccupied(new Position(horizontal, FOUR))).isTrue();
            assertThat(board.isOccupied(new Position(horizontal, SIX))).isTrue();
            assertThat(board.isOccupied(new Position(horizontal, EIGHT))).isTrue();
            assertThat(board.isOccupied(new Position(horizontal, TEN))).isTrue();
        }
    }

    private static void assertThatWhitesNonOccupiedFieldsAreCorrectlyInitialized(Board board) {
        List<Position.Horizontal> oddFieldsOnRow = List.of(A, C);
        List<Position.Horizontal> evenFieldsOnRow = List.of(B, D);


        for (Position.Horizontal horizontal : oddFieldsOnRow) {
            assertThat(board.isOccupied(new Position(horizontal, TWO))).isFalse();
            assertThat(board.isOccupied(new Position(horizontal, FOUR))).isFalse();
            assertThat(board.isOccupied(new Position(horizontal, SIX))).isFalse();
            assertThat(board.isOccupied(new Position(horizontal, EIGHT))).isFalse();
            assertThat(board.isOccupied(new Position(horizontal, TEN))).isFalse();
        }

        for (Position.Horizontal horizontal : evenFieldsOnRow) {
            assertThat(board.isOccupied(new Position(horizontal, ONE))).isFalse();
            assertThat(board.isOccupied(new Position(horizontal, THREE))).isFalse();
            assertThat(board.isOccupied(new Position(horizontal, FIVE))).isFalse();
            assertThat(board.isOccupied(new Position(horizontal, SEVEN))).isFalse();
            assertThat(board.isOccupied(new Position(horizontal, NINE))).isFalse();
        }
    }

    private static void assertThatBlacksOccupiedFieldsAreCorrectlyInitialized(Board board) {
        List<Position.Horizontal> oddFieldsOnRow = List.of(G, I);
        List<Position.Horizontal> evenFieldsOnRow = List.of(H, J);

        for (Position.Horizontal horizontal : oddFieldsOnRow) {
            assertThat(board.isOccupied(new Position(horizontal, ONE))).isTrue();
            assertThat(board.isOccupied(new Position(horizontal, THREE))).isTrue();
            assertThat(board.isOccupied(new Position(horizontal, FIVE))).isTrue();
            assertThat(board.isOccupied(new Position(horizontal, SEVEN))).isTrue();
            assertThat(board.isOccupied(new Position(horizontal, NINE))).isTrue();
        }

        for (Position.Horizontal horizontal : evenFieldsOnRow) {
            assertThat(board.isOccupied(new Position(horizontal, TWO))).isTrue();
            assertThat(board.isOccupied(new Position(horizontal, FOUR))).isTrue();
            assertThat(board.isOccupied(new Position(horizontal, SIX))).isTrue();
            assertThat(board.isOccupied(new Position(horizontal, EIGHT))).isTrue();
            assertThat(board.isOccupied(new Position(horizontal, TEN))).isTrue();
        }
    }

    private static void assertThatBlacksNonOccupiedFieldsAreCorrectlyInitialized(Board board) {
        List<Position.Horizontal> oddFieldsOnRow = List.of(G, I);
        List<Position.Horizontal> evenFieldsOnRow = List.of(H, J);


        for (Position.Horizontal horizontal : oddFieldsOnRow) {
            assertThat(board.isOccupied(new Position(horizontal, TWO))).isFalse();
            assertThat(board.isOccupied(new Position(horizontal, FOUR))).isFalse();
            assertThat(board.isOccupied(new Position(horizontal, SIX))).isFalse();
            assertThat(board.isOccupied(new Position(horizontal, EIGHT))).isFalse();
            assertThat(board.isOccupied(new Position(horizontal, TEN))).isFalse();
        }

        for (Position.Horizontal horizontal : evenFieldsOnRow) {
            assertThat(board.isOccupied(new Position(horizontal, ONE))).isFalse();
            assertThat(board.isOccupied(new Position(horizontal, THREE))).isFalse();
            assertThat(board.isOccupied(new Position(horizontal, FIVE))).isFalse();
            assertThat(board.isOccupied(new Position(horizontal, SEVEN))).isFalse();
            assertThat(board.isOccupied(new Position(horizontal, NINE))).isFalse();
        }
    }

    private static void assertThatMiddleRowsAreEmpty(Board board) {
        List<Position.Horizontal> middleRows = List.of(E,F);
        for (Position.Horizontal horizontal : middleRows) {
            for (Position.Vertical vertical : Position.Vertical.values()) {
                assertThat(board.isOccupied(new Position(horizontal, vertical))).isFalse();
            }
        }
    }

}
