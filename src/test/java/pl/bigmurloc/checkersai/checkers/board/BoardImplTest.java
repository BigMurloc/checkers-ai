package pl.bigmurloc.checkersai.checkers.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bigmurloc.checkersai.checkers.shared.Position;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static pl.bigmurloc.checkersai.checkers.shared.Position.Horizontal.A;
import static pl.bigmurloc.checkersai.checkers.shared.Position.Horizontal.B;
import static pl.bigmurloc.checkersai.checkers.shared.Position.Vertical.ONE;
import static pl.bigmurloc.checkersai.checkers.shared.Position.Vertical.TWO;

class BoardImplTest {

    BoardImpl board;

    @BeforeEach
    public void setUp() {
        board = new BoardImpl();
    }
    @Test
    public void whenMakeMoveDiagonallyThenMoveIsMade() {
        var initialPosition = new Position(A, ONE);
        var checker = new Checker(1, CheckerColor.WHITE, initialPosition);
        var finalPosition = new Position(B, TWO);
        board.init(List.of(checker));
        var moves = board.availableMoves(checker.getColor());

        board.makeMove(moves.get(0));

        assertThat(moves.size()).isEqualTo(1);
        assertThat(board.isOccupied(initialPosition)).isFalse();
        assertThat(board.isOccupied(finalPosition)).isTrue();
    }
    @Test
    public void whenBoardIsInitializedByDefaultThenCheckersAreAtCorrectFields() {
        board.init();

        BoardInitTestUtils.assertThatIsBoardInitializedCorrectly(board);
    }

    @Test
    public void whenMakeMoveCapturesEnemyCheckerThenEnemyCheckerIsRemovedFromTheBoard() {
        var whiteChecker = new Checker(1, CheckerColor.WHITE, new Position(A, ONE));
        var blackChecker = new Checker(2, CheckerColor.BLACK, new Position(B, TWO));
        var checkers = List.of(whiteChecker, blackChecker);
        board.init(checkers);

        board.makeMove(board.availableMoves(CheckerColor.WHITE).get(0));

        assertThat(board.existsOnBoard(blackChecker)).isFalse();
    }

    //todo king & man rules
    //todo test when makeMove is made to allied occupied field
    //todo test when makeMove is made to enemy occupied field then position is diagonally after the enemy checker piece
}
