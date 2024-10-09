package base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** The board of the Chinese Chess game. */
public final class Board {
  private Map<Piece, Position> pieces;

  public Board() {
    pieces = new HashMap<>();
  }

  public void putPiece(Piece piece, Position position) {
    pieces.put(piece, position);
  }

  public void removePiece(Piece piece) {
    pieces.remove(piece);
  }

  // Assuming the move is valid.
  public void executeMove(Move move) {
    if (move.victim != null) {
      removePiece(move.victim);
    }
    movePiece(move.piece, move.to);
  }

  // Assuming the move is valid.
  public void revertMove(Move move) {
    movePiece(move.piece, move.from);
    if (move.victim != null) {
      putPiece(move.victim, move.to);
    }
  }

  public void initializeBoardForTest() {
    putPiece(new General(Color.RED), new Position(0, 3));
    putPiece(new Chariot(Color.RED), new Position(1, 2));
    putPiece(new Horse(Color.RED), new Position(4, 5));
    putPiece(new Chariot(Color.RED), new Position(3, 5));
    putPiece(new General(Color.BLACK), new Position(9, 4));
  }

  public General getGeneral(Color color) {
    for (Piece piece : pieces.keySet()) {
      if (piece instanceof General && piece.getColor() == color) {
        return (General) piece;
      }
    }
    return null;
  }

  public void display() {
    for (int i = Constants.ROW_COUNT - 1; i >= 0; --i) {
      System.out.print(i + " ");
      for (int j = 0; j < Constants.COLUMN_COUNT; j++) {
        Piece piece = getPiece(i, j);
        if (piece == null) {
          System.out.print("——");
        } else {
          System.out.print(piece.toString());
        }
      }
      System.out.println();
    }
    System.out.print(". ");
    for (int j = 0; j < Constants.COLUMN_COUNT; j++) {
      System.out.print(String.format("%1d", j) + " ");
    }
    System.out.println();
  }

  public Piece getPiece(Position position) {
    if (!position.isValid()) {
      return null;
    }
    for (Piece piece : pieces.keySet()) {
      if (piece.getPosition().equals(position)) {
        return piece;
      }
    }
    return null;
  }

  public Piece getPiece(int row, int column) {
    return getPiece(new Position(row, column));
  }

  public void movePiece(Piece piece, Position to) {
    if (!pieces.containsKey(piece)) {
      throw new IllegalArgumentException("No piece " + piece);
    }
    pieces.put(piece, to);
  }

  public Position getPiecePosition(Piece piece) {
    if (pieces.containsKey(piece)) {
      return pieces.get(piece);
    } else {
      return null;
    }
  }

  public double getHeuristicValue() {
    // This can be improved by ML models.
    double value = 0;
    for (Piece piece : pieces.keySet()) {
      value += piece.getHeuristicValue();
    }
    return value;
  }

  public double getHeuristicValue(Color turn) {
    double value = getHeuristicValue();
    if (turn == Color.RED) {
      value += Constants.TURN_VALUE;
    } else {
      value -= Constants.TURN_VALUE;
    }
    return value;
  }

  public List<Move> getAllValidMoves(Color turn) {
    List<Move> moves = new ArrayList<>();
    for (Piece piece : pieces.keySet()) {
      if (piece.getColor() == turn) {
        moves.addAll(piece.getValidMoves());
      }
    }
    return moves;
  }
}