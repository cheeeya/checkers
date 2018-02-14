/*ShieldPiece.java*/

/**
 * Represents a ShieldPiece in Checkers61bl
 * @author 
 */

public class ShieldPiece extends Piece {

  /**
   *  Define any variables associated with a ShieldPiece object here.  These
   *  variables MUST be private or package private.
   */
  
  /**
   * Constructs a new ShieldPiece
   * @param  side what side this ShieldPiece is on
   * @param  b    Board that this ShieldPiece belongs to
   */
  public ShieldPiece(int side, Board b) {
    super(side, b);
  }
  
  /* Returns type of piece
   * 0 - pawn
   * 1 - bomb
   * 2 - shield
   */
  @Override
  public int getType(){
	  return 2;
  }
  
  @Override
  public String getImage(){
	  String side = "fire";
	  if (side() == 1){
		  side = "water";
	  }
	  if (isKing()){
		  return "img/shield-" + side + "-crowned.png";
	  }
	  return "img/shield-" + side + ".png";
  }
  
  @Override
  public void getBlownUp(int x, int y){
	  //DO NOTHING
  }
}