/*DemoBoard.java*/

/**
 *  Demo showing how StdDrawPlus works for the Checkers61BL
 *  project
 *  
 *  Represents a simple Tic-Tac-Toe board
 *  
 * @author Daniel Nguyen
 */

public class DemoBoard {
 
  /**
   * pieces is the internal representation of the board
   *         0 is X, 1 is O, -1 is an empty space
   */
  private int[][] pieces;

  /**
   * Constructor for a DemoBoard
   * @param  N size of the board
   */
  public DemoBoard(int N) {
    pieces = new int[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        pieces[i][j] = -1;
      }
    }
  }

  /**
   * Draws the board based on the configuration of Pieces
   */
  private void drawBoard() {
    for (int i = 0; i < pieces.length; i++) {
      for (int j = 0; j < pieces[0].length; j++) {
        if ((i + j) % 2 == 0) {
          StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
        } else {
          StdDrawPlus.setPenColor(StdDrawPlus.CYAN);
        }
        StdDrawPlus.filledSquare(i + .5, j + .5, .5);
        if (pieces[i][j] == 0) {
          StdDrawPlus.picture(i + .5, j + .5, "img/x.png", 1, 1);
        } else if (pieces[i][j] == 1) {
          StdDrawPlus.picture(i + .5, j + .5, "img/o.png", 1, 1);
        }
      }
    }
  }

  /**
   * Runs the Demo. Watches for mouse clicks and changes the Board state
   * Does not prevent you from clicking on a square that has already been pressed
   * Changes sides when space is pressed
   */
  public static void main(String[] args) {
    DemoBoard b = new DemoBoard(3);
    StdDrawPlus.setScale(0, 3);
    int side = 0;
    while (true) {
      b.drawBoard();
      if (StdDrawPlus.mousePressed()) {
        int x = (int) StdDrawPlus.mouseX();
        int y = (int) StdDrawPlus.mouseY();
        b.pieces[x][y] = side;
      }
      if (StdDrawPlus.isSpacePressed()) {
        side = 1 - side;
      }
      StdDrawPlus.show(100);
    }
  }

}
