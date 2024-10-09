package base;

import java.util.ArrayList;
import java.util.List;

public class Horse extends Piece {
  private static final int[][] DIRS = {
    {1, 0, 2, -1},
    {1, 0, 2, 1},
    {0, -1, 1, -2},
    {0, -1, -1, -2},
    {-1, 0, -2, -1},
    {-1, 0, -2, 1},
    {0, 1, 1, 2},
    {0, 1, -1, 2},
  };

  public Horse(Color color) {
    super(color);
  }

  @Override
  public char getSymbol() {
    return '马';
  }

  @Override
  public List<Position> getCandidatePositions() {
    Position position = getPosition();
    List<Position> candidatePositions = new ArrayList<>();
    for (int[] dir : DIRS) {
      Position blockingPosition = position.add(dir[0], dir[1]);
      if (!blockingPosition.isValid()) {
        continue;
      }
      if (Game.board.getPiece(blockingPosition) != null) {
        continue;
      }
      candidatePositions.add(position.add(dir[2], dir[3]));
    }
    return candidatePositions;
  }

  @Override
  public double getRawHeuristicValue() {
    return 4.5 + 0.2 * getDistanceFromBase();
  }
}