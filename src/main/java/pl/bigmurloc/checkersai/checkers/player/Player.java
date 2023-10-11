package pl.bigmurloc.checkersai.checkers.player;

import pl.bigmurloc.checkersai.checkers.board.Board;
import pl.bigmurloc.checkersai.checkers.board.CheckerColor;
import pl.bigmurloc.checkersai.checkers.board.Move;

/**
 * Interface representing a player
 */
public interface Player {

    CheckerColor playingAs();

    /**
     * Method that makes a move on the board
     *
     * @param board current board
     * @return player chosen move
     */
    Move makeMove(Board board);

}
