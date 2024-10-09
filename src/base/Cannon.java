package base;

import java.util.ArrayList;
import java.util.List;

public class Cannon extends Piece {
  private static final int[][] DIRS = {
    {1, 0},
    {-1, 0},
    {0, 1},
    {0, -1},
  };

  public Cannon(Color color) {
    super(color);
  }

  @Override
  public List<Move> getValidMoves() {
    Position position = getPosition();
    List<Move> validMoves = new ArrayList<>();
    for (int[] dir : DIRS) {
      boolean metObstacle = false;
      for (int step = 1; step <= Constants.ROW_COUNT; step++) {
        Position candidatePosition = position.add(dir[0] * step, dir[1] * step);
        if (!candidatePosition.isValid()) {
          break;
        }
        Piece victim = Game.board.getPiece(candidatePosition);
        if (!metObstacle) {
          if (victim == null) {
            // a normal move before meeting an obstacle.
            validMoves.add(new Move(this, position, candidatePosition, null));
          } else {
            // met an obstacle. The color does not matter as an obstacle.
            metObstacle = true;
          }
        } else {
          if (victim == null) {
            // can not move to this position. keep going.
          } else {
            if (victim.getColor() == color) {
              // can not move to this position. terminate.
              break;
            } else {
              // can move to this position, but only once.
              validMoves.add(new Move(this, position, candidatePosition, victim));
              break;
            }
          }
        }
      }
    }
    return validMoves;
  }

  @Override
  public List<Position> getCandidatePositions() {
    throw new UnsupportedOperationException("Cannon.getCandidatePositions() should not be called.");
  }

  @Override
  public char getSymbol() {
    return '炮';
  }

  @Override
  public double getRawHeuristicValue() {
    return 5.0 + 0.1 * getDistanceFromBase();
  }
}