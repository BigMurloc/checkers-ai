package pl.bigmurloc.checkersai.checkers.player;

import pl.bigmurloc.checkersai.checkers.board.Board;
import pl.bigmurloc.checkersai.checkers.board.Move;

class MinimaxAlgorithm {

    /**
     * Minimax algorithm implementation for the checkers game
     * It assumes that the player is maximizing the score and the opponent is minimizing it.
     *
     * @param move Root point for the algorithm
     * @param depth How many moves ahead the algorithm should evaluate
     * @param maximizingPlayer Is the algorithm maximizing or minimizing the score
     * @param board Current board
     * @return The best score for the move
     */
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
