package pl.bigmurloc.checkersai.checkers.player;

import pl.bigmurloc.checkersai.checkers.board.Board;
import pl.bigmurloc.checkersai.checkers.board.CheckerColor;
import pl.bigmurloc.checkersai.checkers.board.Move;

import java.util.HashMap;
import java.util.Map;

public class AIPlayer implements Player {

    private final CheckerColor color;
    private final MinimaxAlgorithm minimaxAlgorithm = new MinimaxAlgorithm();

    public AIPlayer(CheckerColor color) {
        this.color = color;
    }

    @Override
    public CheckerColor playingAs() {
        return color;
    }

    @Override
    public void makeMove(Board board) {
        var moves = board.availableMoves(color);

        Map<Integer, Move> scoreToMoveMap = new HashMap<>();

        for (Move move: moves) {
            int evaluateScore = minimaxAlgorithm.findTheBestMove(move, 3, true, board);
            scoreToMoveMap.put(evaluateScore, move);
        }

        Integer maxScore = scoreToMoveMap.keySet().stream().max(Integer::compareTo).get();

        board.makeMove(scoreToMoveMap.get(maxScore));
    }
}
