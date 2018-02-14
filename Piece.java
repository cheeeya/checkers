/*Piece.java*/

/**
 *  Represents a Normal Piece in Checkers61bl
 * @author 
 */

public class Piece {
  
  /**
   *  Define any variables associated with a Piece object here.  These
   *  variables MUST be private or package private.
   */
	private boolean kingStatus, selectStatus, captureStatus;
	private int team;
	private Board gameboard;
  /**
   * Returns the side that the piece is on
   * @return 0 if the piece is fire and 1 if the piece is water
   */
  public int side() {
	  return team;
  }
  
  public boolean isSelected(){
	  return selectStatus;
  }
  
  public void selectPiece(){
	  selectStatus = true;
  }
  
  public void deselectPiece(){
	  selectStatus = false;
  }

  public boolean isKing() {
	  return kingStatus;
  }
  
  public void kingMe(){
	  kingStatus = true;
  }
  
  public Board getBoard(){
	  return gameboard;
  }

  /**
   * Initializes a Piece
   * @param  side The side of the Piece
   * @param  b    The Board the Piece is on
   */
  Piece(int side, Board b) {
	  team = side;
	  gameboard = b;
  }
  /* Returns type of piece
   * 0 - pawn
   * 1 - bomb
   * 2 - shield
   */
  public int getType(){
	  return 0;
  }

  /**
   * Destroys the piece at x, y. ShieldPieces do not blow up
   * @param x The x position of Piece to destroy
   * @param y The y position of Piece to destroy
   */
  void getBlownUp(int x, int y) {
	  gameboard.remove(x, y);
  }

  /**
   * Does nothing. For bombs, destroys pieces adjacent to it
   * @param x The x position of the Piece that will explode
   * @param y The y position of the Piece that will explode
   */
  void explode(int x, int y) {
    //YOUR CODE HERE
  }

  /**
   * Signals that this Piece has begun to capture (as in it captured a Piece)
   */
  void startCapturing() {
	  captureStatus = true;
  }

  /**
   * Returns whether or not this piece has captured this turn
   * @return true if the Piece has captured
   */
  public boolean hasCaptured() {
	  return captureStatus;
  }

  /**
   * Resets the Piece for future turns
   */
  public void finishCapturing() {
	  captureStatus = false;
  }
  
  public void changeBoard(Board b){
	  gameboard = b;
  }
  
  public String getImage(){
	  String side = "fire";
	  if (side() == 1){
		  side = "water";
	  }
	  if (isKing()){
		  return "img/pawn-" + side + "-crowned.png";
	  }
	  return "img/pawn-" + side + ".png";
  }

}