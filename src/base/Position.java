package base;

public final class Position {
  // starting from the bottom right corner
  // index of the row, range from 0 to 9
  private int row;
  // index of the column, range from 0 to 8
  private int column;

  public Position(int row, int column) {
    this.row = row;
    this.column = column;
  }

  public Position add(int row, int column) {
    return new Position(this.row + row, this.column + column);
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public boolean isValid() {
    return row >= 0 && row < Constants.ROW_COUNT && column >= 0 && column < Constants.COLUMN_COUNT;
  }

  public boolean isInRedPalace() {
    return row >= 0 && row <= 2 && column >= 3 && column <= 5;
  }

  public boolean isInBlackPalace() {
    return row >= 7 && row <= 9 && column >= 3 && column <= 5;
  }

  public boolean isInRedCamp() {
    return row >= 0 && row <= Constants.RED_CAMP_ROW;
  }

  public boolean isInBlackCamp() {
    return row >= Constants.BLACK_CAMP_ROW && row <= 9;
  }

  @Override
  public String toString() {
    return "[" + row + ", " + column + "]";
  }

  public void print() {
    System.out.print(toString());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj instanceof Position) {
      Position other = (Position) obj;
      return this.row == other.row && this.column == other.column;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return row * Constants.COLUMN_COUNT + column;
  }
}