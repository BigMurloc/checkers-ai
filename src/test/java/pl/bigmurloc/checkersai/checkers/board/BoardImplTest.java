package pl.bigmurloc.checkersai.checkers.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bigmurloc.checkersai.checkers.shared.Position;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static pl.bigmurloc.checkersai.checkers.shared.Position.Horizontal.A;
import static pl.bigmurloc.checkersai.checkers.shared.Position.Horizontal.B;
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
        var checker = new Checker(CheckerColor.WHITE, initialPosition);
        var finalPosition = new Position(B, THREE);
        board.init(List.of(checker));

        board.makeMove(checker, finalPosition);

        assertThat(board.isOccupied(initialPosition)).isFalse();
        assertThat(board.isOccupied(finalPosition)).isTrue();
    }

    @Test
    public void whenMakeMoveHorizontallyThenIllegalMove() {
        var initialPosition = new Position(A, TWO);
        var checker = new Checker(CheckerColor.WHITE, initialPosition);
        var finalPosition = new Position(B, TWO);
        board.init(List.of(checker));

        assertThatThrownBy(() -> board.makeMove(checker, finalPosition))
                .isInstanceOf(IllegalMoveException.class)
                .hasMessageContaining("Horizontal move is not allowed");
    }

    @Test
    public void whenMakeMoveVerticallyThenIllegalMove() {
        var initialPosition = new Position(A, TWO);
        var checker = new Checker(CheckerColor.WHITE, initialPosition);
        var finalPosition = new Position(A, THREE);
        board.init(List.of(checker));

        assertThatThrownBy(() -> board.makeMove(checker, finalPosition))
                .isInstanceOf(IllegalMoveException.class)
                .hasMessageContaining("Vertical move is not allowed");
    }

    //test for empty field move
    //test for the same position move
    @Test
    public void whenBoardIsInitializedByDefaultThenCheckersAreAtCorrectFields() {
        board.init();

        BoardInitTestUtils.assertThatIsBoardInitializedCorrectly(board);
    }
}
