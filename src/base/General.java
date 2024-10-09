package base;

import java.util.ArrayList;
import java.util.List;

public class General extends Piece {
  private static final int[][] DIRS = {
    {1, 0},
    {-1, 0},
    {0, 1},
    {0, -1},
  };

  public General(Color color) {
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
    return '将';
  }

  @Override
  public List<Move> getValidMoves() {
    Position position = getPosition();
    List<Move> validMoves = super.getValidMoves();

    General otherGeneral = Game.board.getGeneral(getOppositeColor());
    if (otherGeneral == null) {
      return validMoves;
    }
    Position otherGeneralPosition = otherGeneral.getPosition();
    if (otherGeneralPosition.getColumn() != position.getColumn()) {
      return validMoves;
    }

    int lower = Math.min(position.getRow(), otherGeneralPosition.getRow()) + 1;
    int upper = Math.max(position.getRow(), otherGeneralPosition.getRow()) - 1;
    for (int row = lower; row <= upper; row++) {
      if (Game.board.getPiece(row, position.getColumn()) != null) {
        return validMoves;
      }
    }
    validMoves.add(new Move(this, position, otherGeneralPosition, otherGeneral));
    return validMoves;
  }

  @Override
  public double getRawHeuristicValue() {
    return Constants.GENERAL_VALUE;
  }
}