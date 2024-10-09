package base;

import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {
  private static final int[][] DIRS = {
    {1, 0},
    {-1, 0},
    {0, 1},
    {0, -1},
  };

  public Chariot(Color color) {
    super(color);
  }

  @Override
  public List<Position> getCandidatePositions() {
    Position position = getPosition();
    List<Position> candidatePositions = new ArrayList<>();
    for (int[] dir : DIRS) {
      int step = 0;
      while (true) {
        step++;
        Position candidatePosition = position.add(dir[0] * step, dir[1] * step);
        if (!candidatePosition.isValid()) {
          break;
        }
        if (Game.board.getPiece(candidatePosition) != null) {
          candidatePositions.add(candidatePosition);
          break;
        } else {
          candidatePositions.add(candidatePosition);
        }
      }
    }
    return candidatePositions;
  }

  @Override
  public char getSymbol() {
    return '车';
  }

  @Override
  public double getRawHeuristicValue() {
    return 10.0 + 0.3 * getDistanceFromBase();
  }
}