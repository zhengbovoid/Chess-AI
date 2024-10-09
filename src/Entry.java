
import base.Color;
import base.Game;
import base.Move;
import base.Piece;
import base.Position;
import endgame.EndgameLoader;
import evaluation.EvaluationResult;
import evaluation.EvaluationTask;
import java.util.List;
import java.util.Arrays;

/** The entry point of Chinese Chess. */
public final class Entry {
  public static void main(String[] args) {
    Game.board = EndgameLoader.loadDefault();
    Game.board.display();
    pve();
  }

  // Player vs AI
  public static void pve() {
    Color turn = Color.RED;
    EvaluationTask blackEvalTask = new EvaluationTask(Color.BLACK, 7, 20, false, 0.8);
    while (true) {
      if (turn == Color.RED) {
        while (true) {
          String input = Util.readFromInput("enter move [a,b]->[c,d]:");
          List<String> parts = Arrays.asList(input.split(" "));
          if (parts.size() != 4) {
            System.out.println("Invalid input.");
            continue;
          }
          Position from;
          Position to;
          try {
            from = new Position(Integer.parseInt(parts.get(0)), Integer.parseInt(parts.get(1)));
            to = new Position(Integer.parseInt(parts.get(2)), Integer.parseInt(parts.get(3)));
          } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            continue;
          }
          Piece piece = Game.board.getPiece(from);
          if (piece == null) {
            System.out.println("No piece at " + from);
            continue;
          }
          if (piece.getColor() != turn) {
            System.out.println("It is not your turn.");
            continue;
          }
          boolean valid = false;
          for (Move move : piece.getValidMoves()) {
            if (move.from.equals(from) && move.to.equals(to)) {
              valid = true;
              break;
            }
          }
          if (valid) {
            Move move = new Move(from, to);
            Game.board.executeMove(move);
            break;
          } else {
            System.out.println("Invalid move.");
          }
        }
      } else {
        EvaluationResult evalResult = blackEvalTask.evaluate(Game.board);
        System.out.println(evalResult.toString());
        Game.board.executeMove(evalResult.move);
      }

      Game.board.display();
      System.out.println("-----------------------------------------------------------------------");

      turn = turn == Color.RED ? Color.BLACK : Color.RED;
    }
  }

  // AI vs AI
  public static void eve() {
    Color turn = Color.RED;
    EvaluationTask redEvalTask = new EvaluationTask(Color.RED, 7, 20, false, 0.8);
    EvaluationTask blackEvalTask = new EvaluationTask(Color.BLACK, 7, 10, false, 1.0);
    while (true) {
      EvaluationTask evalTask = turn == Color.RED ? redEvalTask : blackEvalTask;
      EvaluationResult evalResult = evalTask.evaluate(Game.board);

      System.out.println(evalResult.toString());
      Game.board.executeMove(evalResult.move);
      Game.board.display();

      System.out.println("-----------------------------------------------------------------------");

      turn = turn == Color.RED ? Color.BLACK : Color.RED;
    }
  }

  private Entry() {}
}
