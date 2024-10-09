package base;

import java.util.ArrayList;
import java.util.List;

public class Elephant extends Piece {
  private static final int[][] DIRS = {
    {1, 1, 2, 2},
    {1, -1, 2, -2},
    {-1, 1, -2, 2},
    {-1, -1, -2, -2},
  };

  public Elephant(Color color) {
    super(color);
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
      if ((color == Color.RED && !blockingPosition.isInRedCamp())
          || (color == Color.BLACK && !blockingPosition.isInBlackCamp())) {
        continue;
      }
      candidatePositions.add(position.add(dir[2], dir[3]));
    }
    return candidatePositions;
  }

  @Override
  public char getSymbol() {
    return '象';
  }

  @Override
  public double getRawHeuristicValue() {
    return 2.0;
  }
}