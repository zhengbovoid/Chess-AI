package base;

import java.util.ArrayList;
import java.util.List;

/** A piece in the game. Various pieces should extend this class. */
public abstract class Piece {

  protected Color color;

  public Piece(Color color) {
    this.color = color;
  }

  public Color getColor() {
    return color;
  }

  public Color getOppositeColor() {
    return color == Color.RED ? Color.BLACK : Color.RED;
  }

  // Where am I? Ask the board!
  // If null, I am not on the board... Sad...
  public Position getPosition() {
    return Game.board.getPiecePosition(this);
  }

  public List<Move> getValidMoves() {
    List<Move> validMoves = new ArrayList<>();
    for (Position position : getCandidatePositions()) {
      Move move = tryMove(position);
      if (move != null) {
        validMoves.add(move);
      }
    }
    return validMoves;
  }

  public abstract char getSymbol();

  public Move tryMove(Position position) {
    if (!position.isValid()) {
      return null;
    }
    Piece victim = Game.board.getPiece(position);
    if (victim == null) {
      return new Move(this, getPosition(), position, null);
    }
    if (victim.color == color) {
      // can not defeat your own piece
      return null;
    }
    return new Move(this, getPosition(), position, victim);
  }

  // The raw heuristic value without considering the color.
  public abstract double getRawHeuristicValue();

  public double getHeuristicValue() {
    if (color == Color.RED) {
      return getRawHeuristicValue();
    } else {
      return -getRawHeuristicValue();
    }
  }

  // Get the set of positions that the piece can move to.
  // Boundary check is not included.
  // Valid move check is not included.
  // Blocking piece is checked.
  public abstract List<Position> getCandidatePositions();

  @Override
  public String toString() {
    char symbol = getSymbol();
    if (color == Color.RED) {
      return "\u001B[31m" + symbol + "\u001B[0m";
    } else {
      return String.valueOf(symbol);
    }
  }

  public int getDistanceFromBase() {
    Position position = getPosition();
    if (color == Color.RED) {
      return position.getRow();
    } else {
      return Constants.ROW_COUNT - position.getRow() - 1;
    }
  }
}
