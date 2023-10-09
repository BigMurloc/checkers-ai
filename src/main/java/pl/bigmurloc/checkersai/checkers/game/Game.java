package pl.bigmurloc.checkersai.checkers.game;

import org.springframework.stereotype.Component;
import pl.bigmurloc.checkersai.checkers.board.Board;
import pl.bigmurloc.checkersai.checkers.board.BoardPrinter;
import pl.bigmurloc.checkersai.checkers.board.CheckerColor;
import pl.bigmurloc.checkersai.checkers.player.HumanPlayer;
import pl.bigmurloc.checkersai.checkers.player.Player;

import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);
        var turnColor = CheckerColor.WHITE;
        Player whitePlayer = new HumanPlayer(CheckerColor.WHITE, scanner);
        Player blackPlayer = new HumanPlayer(CheckerColor.BLACK, scanner);
        while (!board.isFinished()) {
            boardPrinter.print(board);
            if (turnColor == CheckerColor.WHITE) {
                whitePlayer.makeMove(board);
                turnColor = CheckerColor.BLACK;
            } else {
                blackPlayer.makeMove(board);
                turnColor = CheckerColor.WHITE;
            }
        }
    }
}
