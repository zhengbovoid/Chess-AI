package base;

/** Moving a piece to a target position. The move must be valid. */
public final class Move {

  // The piece to be moved.
  public Piece piece;

  public Position from;
  public Position to;

  // Optional. The piece destroyed by this move.
  public Piece victim;

  public Move(Piece piece, Position from, Position to, Piece victim) {
    this.piece = piece;
    this.from = from;
    this.to = to;
    this.victim = victim;
  }

  public Move(Position from, Position to) {
    this.piece = Game.board.getPiece(from);
    if (this.piece == null) {
      throw new IllegalArgumentException("No piece at " + from);
    }
    this.from = from;
    this.to = to;
    this.victim = Game.board.getPiece(to);
  }

  @Override
  public String toString() {
    if (victim != null) {
      return String.format("%s from %s to %s and capture %s", piece, from, to, victim);
    } else {
      return String.format("%s from %s to %s", piece, from, to);
    }
  }
}