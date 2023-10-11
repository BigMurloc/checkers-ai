package pl.bigmurloc.checkersai.checkers.player;

import pl.bigmurloc.checkersai.checkers.board.Board;
import pl.bigmurloc.checkersai.checkers.board.Move;

class MinimaxAlgorithm {

    int findTheBestMove(Move move, int depth, boolean maximizingPlayer, Board board) {
        board = board.clone();
        if (depth == 0 || board.isFinished()) {
            return board.scoreFor(move.checkerColor());
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;

            for (Move childMove : board.availableMoves(move.checkerColor())) {
                int eval = findTheBestMove(childMove, depth - 1, false, board);
                maxEval = Math.max(maxEval, eval);
            }

            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;

            for (Move childMove : board.availableMoves(move.checkerColor())) {
                int eval = findTheBestMove(childMove, depth - 1, true, board);
                minEval = Math.min(minEval, eval);
            }

            return minEval;
        }
    }

}
