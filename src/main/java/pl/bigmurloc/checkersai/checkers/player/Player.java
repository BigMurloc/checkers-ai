package pl.bigmurloc.checkersai.checkers.player;

import pl.bigmurloc.checkersai.checkers.board.Board;
import pl.bigmurloc.checkersai.checkers.board.CheckerColor;
import pl.bigmurloc.checkersai.checkers.board.Move;

public interface Player {

    CheckerColor playingAs();
    Move makeMove(Board board);

}
