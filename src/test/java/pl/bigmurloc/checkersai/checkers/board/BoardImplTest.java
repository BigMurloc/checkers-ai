package pl.bigmurloc.checkersai.checkers.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bigmurloc.checkersai.checkers.shared.Position;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static pl.bigmurloc.checkersai.checkers.shared.Position.Horizontal.*;
import static pl.bigmurloc.checkersai.checkers.shared.Position.Vertical.*;

class BoardImplTest {

    BoardImpl board;

    @BeforeEach
    public void setUp() {
        board = new BoardImpl();
    }
    @Test
    public void whenMakeMoveDiagonallyThenMoveIsMade() {
        var initialPosition = new Position(A, TWO);
        var checker = new Checker(1, CheckerColor.WHITE, initialPosition);
        var finalPosition = new Position(B, THREE);
        board.init(List.of(checker));

        board.makeMove(checker, finalPosition);

        assertThat(board.isOccupied(initialPosition)).isFalse();
        assertThat(board.isOccupied(finalPosition)).isTrue();
    }

    @Test
    public void whenMakeMoveHorizontallyThenIllegalMove() {
        var initialPosition = new Position(A, TWO);
        var checker = new Checker(1, CheckerColor.WHITE, initialPosition);
        var finalPosition = new Position(B, TWO);
        board.init(List.of(checker));

        assertThatThrownBy(() -> board.makeMove(checker, finalPosition))
                .isInstanceOf(IllegalMoveException.class)
                .hasMessageContaining("Horizontal move is not allowed");
    }

    @Test
    public void whenMakeMoveVerticallyThenIllegalMove() {
        var initialPosition = new Position(A, TWO);
        var checker = new Checker(1, CheckerColor.WHITE, initialPosition);
        var finalPosition = new Position(A, THREE);
        board.init(List.of(checker));

        assertThatThrownBy(() -> board.makeMove(checker, finalPosition))
                .isInstanceOf(IllegalMoveException.class)
                .hasMessageContaining("Vertical move is not allowed");
    }

    @Test
    public void whenMakeMoveOnTheSamePositionThenIllegalMove() {
        var initialPosition = new Position(A, TWO);
        var checker = new Checker(1, CheckerColor.WHITE, initialPosition);
        var finalPosition = new Position(A, TWO);
        board.init(List.of(checker));

        assertThatThrownBy(() -> board.makeMove(checker, finalPosition))
                .isInstanceOf(IllegalMoveException.class)
                .hasMessageContaining("Move to the same position is not allowed");
    }
    @Test
    public void whenBoardIsInitializedByDefaultThenCheckersAreAtCorrectFields() {
        board.init();

        BoardInitTestUtils.assertThatIsBoardInitializedCorrectly(board);
    }

    // when capture is made then checker is removed from board
    @Test
    public void whenMakeMoveCapturesEnemyCheckerThenEnemyCheckerIsRemovedFromTheBoard() {
        var initialWhitePosition = new Position(A, ONE);
        var initialBlackPosition = new Position(B, TWO);
        var whiteChecker = new Checker(1, CheckerColor.WHITE, initialWhitePosition);
        var blackChecker = new Checker(2, CheckerColor.BLACK, initialBlackPosition);
        var checkers = List.of(whiteChecker, blackChecker);
        board.init(checkers);

        board.makeMove(whiteChecker, new Position(C, THREE));

        assertThat(board.existsOnBoard(blackChecker)).isFalse();
    }

    //todo king & man rules
    //todo test when makeMove is made to allied occupied field
    //todo test when makeMove is made to enemy occupied field then position is diagonally after the enemy checker piece
}
