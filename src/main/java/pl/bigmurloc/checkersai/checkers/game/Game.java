package pl.bigmurloc.checkersai.checkers.game;

import org.springframework.stereotype.Component;
import pl.bigmurloc.checkersai.checkers.board.Board;
import pl.bigmurloc.checkersai.checkers.board.BoardPrinter;
import pl.bigmurloc.checkersai.checkers.board.CheckerColor;
import pl.bigmurloc.checkersai.checkers.board.Move;
import pl.bigmurloc.checkersai.checkers.player.AIPlayer;
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

    /**
     * Method that initializes the game and runs it
     * It decides who's turn it is and asks the player for a move
     * Then it prints the board, move and score
     */
    public void init() {
        board.init();
        Scanner scanner = new Scanner(System.in);
        var turnColor = CheckerColor.WHITE;
        Player whitePlayer = new HumanPlayer(CheckerColor.WHITE, scanner);
        Player blackPlayer = new AIPlayer(CheckerColor.BLACK);
        while (!board.isFinished()) {
            boardPrinter.print(board);
            Move chosenMove = null;
            if (turnColor == CheckerColor.WHITE) {
                chosenMove = whitePlayer.makeMove(board);
                turnColor = CheckerColor.BLACK;
            } else {
                chosenMove = blackPlayer.makeMove(board);
                turnColor = CheckerColor.WHITE;
            }
            System.out.printf("Turn: %s%n", turnColor);
            System.out.println("White pieces left: " + board.piecesLeft(CheckerColor.WHITE));
            System.out.println("Black pieces left: " + board.piecesLeft(CheckerColor.BLACK));
            System.out.println("Chosen move: " + chosenMove);
            System.out.println("Score: " + board.scoreFor(turnColor));
        }
    }
}
