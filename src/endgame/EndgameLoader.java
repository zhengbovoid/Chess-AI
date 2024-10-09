package endgame;

import base.Advisor;
import base.Board;
import base.Cannon;
import base.Chariot;
import base.Color;
import base.Constants;
import base.Elephant;
import base.General;
import base.Horse;
import base.Position;
import base.Soldier;

/** A loader to load endgames into a board. */
public final class EndgameLoader {

  public static Board loadEndGame1() {
    // 投石问路
    Board board = new Board();
    board.putPiece(new General(Color.RED), new Position(0, 4));
    board.putPiece(new Chariot(Color.BLACK), new Position(1, 3));
    board.putPiece(new Soldier(Color.BLACK), new Position(1, 5));
    board.putPiece(new Soldier(Color.BLACK), new Position(2, 0));
    board.putPiece(new Cannon(Color.RED), new Position(3, 8));
    board.putPiece(new Chariot(Color.RED), new Position(4, 8));
    board.putPiece(new Elephant(Color.BLACK), new Position(5, 2));
    board.putPiece(new Cannon(Color.BLACK), new Position(6, 2));
    board.putPiece(new Chariot(Color.RED), new Position(7, 8));
    board.putPiece(new Soldier(Color.RED), new Position(8, 4));
    board.putPiece(new Soldier(Color.RED), new Position(8, 5));
    board.putPiece(new Horse(Color.BLACK), new Position(9, 2));
    board.putPiece(new General(Color.BLACK), new Position(9, 3));
    board.putPiece(new Soldier(Color.RED), new Position(9, 7));
    return board;
  }

  public static Board loadDefault() {
    // The beginning is also an end.
    Board board = new Board();

    // Chariots.
    board.putPiece(new Chariot(Color.RED), new Position(0, 0));
    board.putPiece(new Chariot(Color.RED), new Position(0, 8));
    board.putPiece(new Chariot(Color.BLACK), new Position(9, 0));
    board.putPiece(new Chariot(Color.BLACK), new Position(9, 8));

    // Horses.
    board.putPiece(new Horse(Color.RED), new Position(0, 1));
    board.putPiece(new Horse(Color.RED), new Position(0, 7));
    board.putPiece(new Horse(Color.BLACK), new Position(9, 1));
    board.putPiece(new Horse(Color.BLACK), new Position(9, 7));

    // Elephants.
    board.putPiece(new Elephant(Color.RED), new Position(0, 2));
    board.putPiece(new Elephant(Color.RED), new Position(0, 6));
    board.putPiece(new Elephant(Color.BLACK), new Position(9, 2));
    board.putPiece(new Elephant(Color.BLACK), new Position(9, 6));

    // Advisors.
    board.putPiece(new Advisor(Color.RED), new Position(0, 3));
    board.putPiece(new Advisor(Color.RED), new Position(0, 5));
    board.putPiece(new Advisor(Color.BLACK), new Position(9, 3));
    board.putPiece(new Advisor(Color.BLACK), new Position(9, 5));

    // Generals.
    board.putPiece(new General(Color.RED), new Position(0, 4));
    board.putPiece(new General(Color.BLACK), new Position(9, 4));

    // Cannons.
    board.putPiece(new Cannon(Color.RED), new Position(2, 1));
    board.putPiece(new Cannon(Color.RED), new Position(2, 7));
    board.putPiece(new Cannon(Color.BLACK), new Position(7, 1));
    board.putPiece(new Cannon(Color.BLACK), new Position(7, 7));

    // Soldiers.
    for (int j = 0; j < Constants.COLUMN_COUNT; j += 2) {
      board.putPiece(new Soldier(Color.RED), new Position(3, j));
      board.putPiece(new Soldier(Color.BLACK), new Position(6, j));
    }

    return board;
  }

  private EndgameLoader() {}
}