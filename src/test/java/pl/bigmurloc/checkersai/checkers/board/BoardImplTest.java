package pl.bigmurloc.checkersai.checkers.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bigmurloc.checkersai.checkers.shared.Position;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static pl.bigmurloc.checkersai.checkers.shared.Position.Horizontal.A;
import static pl.bigmurloc.checkersai.checkers.shared.Position.Horizontal.B;
import static pl.bigmurloc.checkersai.checkers.shared.Position.Vertical.*;

class BoardImplTest {

    Board board;

    @BeforeEach
    public void setUp() {
        board = new BoardImpl();
    }
    @Test
    public void whenMakeMoveThenMoveIsMade() {
        var initialPosition = new Position(A, TWO);
        var checker = new Checker(CheckerColor.WHITE, initialPosition);
        var finalPosition = new Position(B, THREE);

        board.makeMove(checker, finalPosition);

        assertThat(board.isOccupied(initialPosition)).isFalse();
        assertThat(board.isOccupied(finalPosition)).isTrue();
    }

}
