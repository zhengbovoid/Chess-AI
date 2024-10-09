package base;

import java.util.ArrayList;
import java.util.List;

public class Soldier extends Piece {
  public Soldier(Color color) {
    super(color);
  }

  @Override
  public List<Position> getCandidatePositions() {
    Position position = getPosition();
    List<Position> candidatePositions = new ArrayList<>();
    if (color == Color.RED) {
      candidatePositions.add(position.add(1, 0));
      if (position.isInBlackCamp()) {
        candidatePositions.add(position.add(0, -1));
        candidatePositions.add(position.add(0, 1));
      }
    } else {
      // color is black
      candidatePositions.add(position.add(-1, 0));
      if (position.isInRedCamp()) {
        candidatePositions.add(position.add(0, -1));
        candidatePositions.add(position.add(0, 1));
      }
    }
    return candidatePositions;
  }

  @Override
  public char getSymbol() {
    return '卒';
  }

  @Override
  public double getRawHeuristicValue() {
    if ((color == Color.RED && getPosition().isInRedCamp())
        || (color == Color.BLACK && getPosition().isInBlackCamp())) {
      return 1.0 + 0.05 * getDistanceFromBase();
    }
    return 2.0 + 0.1 * getDistanceFromBase();
  }
}