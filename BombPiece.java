/*BombPiece.java*/

/**
 *  Represents a BombPiece ins Checkers61bl
 * @author 
 */

public class BombPiece extends Piece {
 
  /**
   *  Define any variables associated with a BombPiece object here.  These
   *  variables MUST be private or package private.
   */

  /**
   * Constructs a new BombPiece
   * @param  side what side this BombPiece is on
   * @param  b    Board that this BombPiece belongs to
   */
  public BombPiece(int side, Board b) {
    super(side, b);
  }
  
  
  /* Returns type of piece
   * 0 - pawn
   * 1 - bomb
   * 2 - shield
   */
  @Override
  public int getType(){
	  return 1;
  }
  
  
  @Override
  public String getImage(){
	  String side = "fire";
	  if (side() == 1){
		  side = "water";
	  }
	  if (isKing()){
		  return "img/bomb-" + side + "-crowned.png";
	  }
	  return "img/bomb-" + side + ".png";
  }
  
  @Override
  void explode(int x, int y) {
	  for (int i = -1; i < 2; i += 2){
		  for (int j = -1; j < 2; j += 2){
			  if(getBoard().pieceAt(x + i, y + j) != null){
				  getBoard().pieceAt(x + i, y + j).getBlownUp(x + i, y + j);
			  }
		  }
	  }
	  this.getBlownUp(x, y);
  }

}
