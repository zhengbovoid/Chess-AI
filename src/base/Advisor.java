package base;

import java.util.ArrayList;
import java.util.List;

public class Advisor extends Piece {
  private static final int[][] DIRS = {
    {1, 1},
    {1, -1},
    {-1, 1},
    {-1, -1},
  };

  public Advisor(Color color) {
    super(color);
  }

  @Override
  public List<Position> getCandidatePositions() {
    Position position = getPosition();
    List<Position> candidatePositions = new ArrayList<>();
    for (int[] dir : DIRS) {
      Position candidatePosition = position.add(dir[0], dir[1]);
      if ((color == Color.RED && candidatePosition.isInRedPalace())
          || (color == Color.BLACK && candidatePosition.isInBlackPalace())) {
        candidatePositions.add(candidatePosition);
      }
    }
    return candidatePositions;
  }

  @Override
  public char getSymbol() {
    return '士';
  }

  @Override
  public double getRawHeuristicValue() {
    return 2.0;
  }
}