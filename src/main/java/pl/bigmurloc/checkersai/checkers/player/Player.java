package pl.bigmurloc.checkersai.checkers.player;

import pl.bigmurloc.checkersai.checkers.board.Board;
import pl.bigmurloc.checkersai.checkers.board.CheckerColor;

public interface Player {

    CheckerColor playingAs();
    void makeMove(Board board);

}
