package pl.bigmurloc.checkersai.checkers.game;

import org.springframework.stereotype.Component;
import pl.bigmurloc.checkersai.checkers.board.Board;
import pl.bigmurloc.checkersai.checkers.board.BoardPrinter;

@Component
public class Game {

    private final BoardPrinter boardPrinter;
    private final Board board;

    public Game(BoardPrinter boardPrinter, Board board) {
        this.boardPrinter = boardPrinter;
        this.board = board;
    }

    public void init() {
        board.init();
        boardPrinter.printBoard(board.getFields());
    }
}
