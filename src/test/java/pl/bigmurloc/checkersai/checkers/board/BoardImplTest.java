package pl.bigmurloc.checkersai.checkers.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bigmurloc.checkersai.checkers.shared.Position;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
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

    @Test
    public void whenCheckerIsInTheCenterAndNoNeighboursThenFourMovesAreAvailable() {
        var whiteChecker = new Checker(1, CheckerColor.WHITE, new Position(D, SIX));
        var checkers = List.of(whiteChecker);
        board.init(checkers);

        var moves = board.availableMoves(whiteChecker.getColor());

        assertThat(moves.size()).isEqualTo(4);
    }

    @Test
    public void whenCheckerIsInTheCenterAndFourEnemyNeighboursThenFourMovesAreAvailable() {
        var whiteChecker = new Checker(1, CheckerColor.WHITE, new Position(D, SIX));
        var blackChecker1 = new Checker(2, CheckerColor.BLACK, new Position(C, FIVE));
        var blackChecker2 = new Checker(3, CheckerColor.BLACK, new Position(C, SEVEN));
        var blackChecker3 = new Checker(4, CheckerColor.BLACK, new Position(E, FIVE));
        var blackChecker4 = new Checker(5, CheckerColor.BLACK, new Position(E, SEVEN));
        var checkers = List.of(whiteChecker, blackChecker1, blackChecker2, blackChecker3, blackChecker4);
        board.init(checkers);

        var moves = board.availableMoves(whiteChecker.getColor());

        assertThat(moves.size()).isEqualTo(4);
    }

    @Test
    public void whenCheckerIsInTheCenterAndThreeEnemyNeighboursThenThreeMovesAreAvailable() {
        var whiteChecker = new Checker(1, CheckerColor.WHITE, new Position(D, SIX));
        var blackChecker1 = new Checker(2, CheckerColor.BLACK, new Position(C, FIVE));
        var blackChecker2 = new Checker(3, CheckerColor.BLACK, new Position(C, SEVEN));
        var blackChecker4 = new Checker(5, CheckerColor.BLACK, new Position(E, SEVEN));
        var checkers = List.of(whiteChecker, blackChecker1, blackChecker2, blackChecker4);
        board.init(checkers);

        var moves = board.availableMoves(whiteChecker.getColor());

        assertThat(moves.size()).isEqualTo(3);
    }

    @Test
    public void whenBoardIsInitializedByDefaultThereShouldBeOnlyNineMovesAvailable() {
        board.init();

        var moves = board.availableMoves(CheckerColor.WHITE);

        assertThat(moves.size()).isEqualTo(9);
    }

    //todo king & man rules
    //todo test when makeMove is made to allied occupied field
    //todo test when makeMove is made to enemy occupied field then position is diagonally after the enemy checker piece
}
