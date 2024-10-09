package evaluation;

import base.Move;

/**
 * The evaluation result produced by an EvaluationTask. It contains the estimated score based
 * on the current game situation, and the recommended move.
 */
public final class EvaluationResult {
  public double value;

  // Optional. If using a heuristic evaluation, this field is not set.
  public Move move;

  // Optional. If using a heuristic evaluation, this field is not set.
  public EvaluationResult subResult;

  public EvaluationResult(double value, Move move, EvaluationResult subResult) {
    this.value = value;
    this.move = move;
    this.subResult = subResult;
  }

  @Override
  public String toString() {
    return String.format("EvaluationResult(value=%f, move=%s)", value, move);
  }

  public void printTrace() {
    System.out.println(this);
    int count = 0;
    while (subResult != null) {
      count++;
      for (int i = 0; i < count; ++i) {
        System.out.print(' ');
      }
      System.out.println(subResult);
      subResult = subResult.subResult;
    }
  }
}