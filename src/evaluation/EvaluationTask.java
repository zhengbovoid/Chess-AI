package evaluation;

import base.Board;
import base.Color;
import base.Constants;
import base.Move;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/** The task to evaluate a board's current situation. */
public final class EvaluationTask {
  // The color of the current turn.
  Color turn;
  Color oppositeTurn;

  // the remaining depth to search. If it's 0, can only use heuristic value to evaluate.
  int remainingDepth;

  // max width of searching.
  int maxWidth;

  boolean debug;

  double widthDecayFactor;

  public EvaluationTask(
      Color turn, int remainingDepth, int maxWidth, boolean debug, double widthDecayFactor) {
    this.turn = turn;
    this.remainingDepth = remainingDepth;
    this.maxWidth = maxWidth;
    this.oppositeTurn = (turn == Color.RED ? Color.BLACK : Color.RED);
    this.debug = debug;
    this.widthDecayFactor = widthDecayFactor;
  }

  // Invariant: the state of the board should be recovered to the original state, when the
  // evaluation is done.
  private static class Pair implements Comparable<Pair> {
    private static final double MIN_DIFF = 1e-6;
    Move move;
    double heuristicValue;

    public Pair(Move move, double heuristicValue) {
      this.move = move;
      this.heuristicValue = heuristicValue;
    }

    @Override
    public int compareTo(Pair other) {
      if (Math.abs(heuristicValue - other.heuristicValue) < MIN_DIFF) {
        return 0;
      }
      if (heuristicValue > other.heuristicValue) {
        return 1;
      } else {
        return -1;
      }
    }
  }

  public EvaluationResult evaluate(Board board) {
    return evaluate(board, -Constants.MAX_VALUE, Constants.MAX_VALUE);
  }

  public EvaluationResult evaluate(Board board, double alpha, double beta) {
    if (remainingDepth == 0) {
      return new EvaluationResult(board.getHeuristicValue(turn), null, null);
    }
    // handle the case that general has been captured.
    if (board.getGeneral(Color.RED) == null) {
      return new EvaluationResult(-Constants.GENERAL_VALUE, null, null);
    }
    if (board.getGeneral(Color.BLACK) == null) {
      return new EvaluationResult(Constants.GENERAL_VALUE, null, null);
    }

    // retursion needed.
    List<Move> candidateMoves = board.getAllValidMoves(turn);

    debugPrint("candidateMoves size: " + candidateMoves.size());

    // Color RED wants max values. Color BLACK wants min values.
    PriorityQueue<Pair> queue;
    if (turn == Color.RED) {
      queue = new PriorityQueue<>();
    } else {
      queue = new PriorityQueue<>(Collections.reverseOrder());
    }

    // Step 1: evaluate all the candidate moves with heuristic value.
    for (Move move : candidateMoves) {
      board.executeMove(move);
      queue.offer(new Pair(move, board.getHeuristicValue(turn)));
      if (queue.size() > maxWidth) {
        queue.poll();
      }
      board.revertMove(move);
    }

    debugPrint("queue size: " + queue.size());

    // For all surviving moves, evaluate them recursively.

    double value;
    if (turn == Color.RED) {
      value = -Constants.MAX_VALUE;
    } else {
      value = Constants.MAX_VALUE;
      ;
    }
    Move bestMove = null;
    EvaluationResult bestResult = null;
    while (!queue.isEmpty()) {
      Pair pair = queue.poll();
      board.executeMove(pair.move);
      EvaluationTask subTask =
          new EvaluationTask(
              oppositeTurn,
              remainingDepth - 1,
              (int) (maxWidth * widthDecayFactor),
              false,
              widthDecayFactor);
      EvaluationResult subResult = subTask.evaluate(board, alpha, beta);
      if (turn == Color.RED) {
        // try to get the max value.
        debugPrint("value = " + value + ", subResult.value = " + subResult.value);
        if (value < subResult.value) {
          value = subResult.value;
          bestMove = pair.move;
          bestResult = subResult;
          debugPrint("bestMove updated RED");
        }
        alpha = Math.max(alpha, value);
        if (beta <= alpha) {
          board.revertMove(pair.move);
          break;
        }
      } else {
        // try to get the min value for color BLACK.
        if (value > subResult.value) {
          value = subResult.value;
          bestMove = pair.move;
          bestResult = subResult;
          debugPrint("bestMove updated BLACK");
        }
        beta = Math.min(beta, value);
        if (beta <= alpha) {
          board.revertMove(pair.move);
          break;
        }
      }
      board.revertMove(pair.move);
    }
    return new EvaluationResult(value, bestMove, bestResult);
  }

  private void debugPrint(String message) {
    if (debug) {
      System.out.println(message);
    }
  }
}
