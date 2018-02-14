import java.lang.Math;

/*Board.java*/

/**
 * Represents a Board configuration of a game of Checkers61bl
 * @author
 */

public class Board {

  /**
   *  Define any variables associated with a Board object here.  These
   *  variables MUST be private.
   */
	private Piece [][] myBoard;
	private int whosTurn, activeX, activeY, waterPieces = 12, firePieces = 12, currentState;
	private Piece activePiece;
	private boolean moved, game;
	private Board [] boardStates = new Board[30];

  /**
   * Constructs a new Board
   * @param  shouldBeEmpty if true, add no pieces
   */
  public Board(boolean shouldBeEmpty) {
	int boardSize = 8;
	Piece initPiece;
	int side = 0;
    myBoard = new Piece[boardSize][boardSize];
	if(!shouldBeEmpty){
		for(int i = 0; i < boardSize; i++){
    		for(int j = 0; j < boardSize; j++){
    			if ((i+j) % 2 == 0){
    				if (i < 3){
    					side = 0;
    				} else{
    					side = 1;
    				}
    				if (i == 0 || i == 7){
    					initPiece = new Piece(side, this);
    				} else if (i == 1 || i == 6){
    					initPiece = new ShieldPiece(side, this);
    				} else if (i == 2 || i == 5){
    					initPiece = new BombPiece(side, this);
    				} else{
    					initPiece = null;
    				}
    				
    			} else{
    				initPiece = null;
    			}
    			place(initPiece, j, i);
    		}
    	}
	}
  }
  
  public Board(Board saveState){
	  this.whosTurn = saveState.whosTurn;
	  this.myBoard = new Piece[8][8];
	  this.activeX = saveState.activeX;
	  this.activeY = saveState.activeY;
	  this.waterPieces = saveState.waterPieces;
	  this.firePieces = saveState.firePieces;
	  this.moved = saveState.moved;
	  this.game = saveState.game;
	  this.currentState = saveState.currentState;
	  this.boardStates = new Board[30];
	  for (int i = 0; i < 8; i++){
    		for(int j = 0; j < 8; j++){
    			if (saveState.myBoard[j][i] != null){
    				if(saveState.myBoard[j][i].getType() == 0){
    					myBoard[j][i] = new Piece(saveState.myBoard[j][i].side(), this);
    				} else if(saveState.myBoard[j][i].getType() == 1){
    					myBoard[j][i] = new BombPiece(saveState.myBoard[j][i].side(), this);
    				} else{
    					myBoard[j][i] = new ShieldPiece(saveState.myBoard[j][i].side(), this);
    				}
    			}
    		}
	  }
	  for (int k = 0; k <= currentState; k++){
		  boardStates[k] = saveState.boardStates[k];
	  }
	  this.activePiece = myBoard[activeY][activeX];
  }

  /**
   * gets the Piece at coordinates (x, y)
   * @param  x X-coordinate of Piece to get
   * @param  y Y-coordinate of Piece to get
   * @return   the Piece at (x, y)
   */
  public Piece pieceAt(int x, int y) {
	  if (x > 7 || x < 0 || y > 7 || y < 0){
		  return null;
	  }
	  return myBoard[y][x];
  }

  /**
   * Places a Piece at coordinate (x, y)
   * @param p Piece to place
   * @param x X coordinate of Piece to place
   * @param y Y coordinate of Piece to place
   */
  public void place(Piece p, int x, int y) {
	  if(p != null && x < 8 && x >= 0 && y < 8 && y >= 0){
		  myBoard[y][x] = p;
	  }
  }

  /**
   * Removes a Piece at coordinate (x, y)
   * @param  x X coordinate of Piece to remove
   * @param  y Y coordinate of Piece to remove
   * @return   Piece that was removed
   */
  public Piece remove(int x, int y) {
	  if (x > 7 || x < 0 || y > 7 || y < 0){
		  System.out.println("Out of bounds, cannot remove");
		  return null;
	  }
	  if (myBoard[y][x] == null){
		  System.out.println("No piece to remove");
		  return null;
	  }
	  Piece removed = myBoard[y][x];
	  myBoard[y][x] = null;
	  if (activePiece.hasCaptured()){
		  if (removed.side() == 0){
			  firePieces -= 1;
		  } else{
			  waterPieces -= 1;
		  }
	  }
	  System.out.println("water = " + waterPieces + " fire = " + firePieces);
	  return removed;
  }

  /**
   * Determines if a Piece can be selected
   * @param  x X coordinate of Piece
   * @param  y Y coordinate of Piece to select
   * @return   true if the Piece can be selected
   */
  public boolean canSelect(int x, int y) {
	  if (x >= 0 && y >= 0 && x < 8 && y < 8){
		  if (myBoard[y][x] != null){
			  if(!moved){
				  Piece selectable = myBoard[y][x];
				  if (whosTurn == selectable.side()){
					  return true;
				  }
			  }
		  } else{
			  if (activePiece != null){
				  if (moved){
					  if(canJump(x, y)){
						  return true;
					  }
				  } else if (canMove(x,y) || canJump(x, y)){
					  return true;
				  }
			  }
		  }
	  }
	  return false;
  }

  /**
   * Selects a square. If no Piece is active, selects the Piece and
   * makes it active. If a Piece is active, performs a move if an empty
   * place is selected. Else, allows you to reselect Pieces
   * @param x X coordinate of place to select
   * @param y Y coordinate of place to select
   */
  public void select(int x, int y) {
	  if (myBoard[y][x] != null){
		  if(activePiece!=null){
			  if(canSelect(x, y)){
				  activePiece.deselectPiece();
			  } 
		  }
		  activePiece = pieceAt(x,y);
		  activePiece.selectPiece();
		  activeX = x;
		  activeY = y;
	  } else{
		  move(activeX, activeY, x, y);
	  }
	  	  
  }

  /**
   * Moves the active piece to coordinate (x, y)
   * @param p Piece to move
   * @param x1 Original X coordinate of p
   * @param y1 Origin Y coordinate of p
   * @param x X coordinate to move to
   * @param y Y coordinate to move to
   */
  public void move(int x1, int y1, int x2, int y2) {
	  place(activePiece, x2, y2);
	  myBoard[y1][x1] = null;
	  if(y2 == 7 || y2 == 0){
		  if (!activePiece.isKing()){
			  activePiece.kingMe();
		  }
	  }
	  if(canJump(x2, y2)){
		  activePiece.startCapturing();
		  remove((x2 + x1) / 2, (y2 + y1) / 2);
		  activePiece.explode(x2, y2);
		  if (myBoard[y2][x2] != null){
			  activeX = x2;
			  activeY = y2;
			  if (canJumpAgain()){
				  moved = true;
				  return;
			  }
		  }
	  }
	  moved = true;
	  activePiece.finishCapturing();
	  activePiece.deselectPiece();
	  activePiece = null;
  }

  /**
   * Determines if the turn can end
   * @return true if the turn can end
   */
  public boolean canEndTurn() {
	  if(moved){
		return true;
	  }
	  return false;
  }
  
  public boolean canMove(int x, int y){
	if (y == activeY + 1){
		if (x == activeX - 1 || x == activeX + 1){
			if (whosTurn == 0 || activePiece.isKing()){
				  return true;
			}
		}
	} else if (y == activeY - 1){
		if (x == activeX - 1 || x == activeX + 1){
			if (whosTurn == 1 || activePiece.isKing()){	  
				return true;
			}
		}
	}
	return false;
  }
  
  public boolean canJump(int x, int y){
	if (y == activeY + 2){
		if (x == activeX - 2 || x == activeX + 2){
			if (myBoard[(y + activeY)/2][(x + activeX)/2] != null){
				if (myBoard[(y + activeY)/2][(x + activeX)/2].side() != activePiece.side()){
					if (whosTurn == 0 || activePiece.isKing()){
						return true;
					}
				}
			}
		}
	} else if (y == activeY - 2){
		if (x == activeX - 2 || x == activeX + 2){
			if (myBoard[(y + activeY)/2][(x + activeX)/2] != null){
				if (myBoard[(y + activeY)/2][(x + activeX)/2].side() != activePiece.side()){
					if (whosTurn == 1 || activePiece.isKing()){	
						return true;
					}
				}
			}
		}
	}
	  return false;
  }
  
  public boolean canJumpAgain(){
	  if(activePiece != null){
		  if (activePiece.hasCaptured()){
			  for (int i = -1; i < 2; i += 2){
				  for(int j = -1; j < 2; j += 2){
					  if(!activePiece.isKing()){
						  if (whosTurn == 0 && j == -1){
							  continue;
						  } else if (whosTurn == 1 && j == 1){
							  continue;
						  }
					  }
					  if (pieceAt(activeX + i, activeY +j) != null && pieceAt(activeX + i, activeY +j).side() != whosTurn){
						  if (activeX + 2 * i < 8 && activeX + 2 * i > -1  && activeY + 2 * j < 8 && activeX + 2 * j > -1){
							  if (pieceAt(activeX + 2 * i, activeY + 2 * j) == null){
								  return true;
							  }
						  }
					  }
				  }
			  }
		  }
	  }
	  return false;
  }
  
  /**
   * Ends the current turn. Changes the player.
   */
  public void endTurn() {
	  if (activePiece != null){
		  activePiece.deselectPiece();
		  activePiece = null;
	  }
	  moved = false;
	  whosTurn = Math.abs(whosTurn - 1);
  }

  /**
   * Returns the winner of the game
   * @return The winner of this game
   */
  public String winner() {
	  game = true;
	  if (waterPieces == 0 && firePieces == 0){
		  return "Tie";
	  } else if (waterPieces == 0){
		  return "Fire";
	  } else if (firePieces == 0){
		  return "Water";
	  } else{
		  game = false;
		  return null;
	  }
  }
  
  public boolean isOver(){
	  return game;
  }
  /* Save is made after every move
   * 
   */
  public void saveCurrentState(Board b){
	  Board save = new Board(b);
	  boardStates[currentState] = save;
	  System.out.println("current save " + currentState);
	  currentState++;
	  System.out.println("next save state " + currentState);
  }
  /* Reverts board to last move
   * 
   */
  public Board revertBoard(){
	  if (currentState > 1){
		  currentState --;
		  return boardStates[currentState - 1];
	  } else{
		  return new Board(false);
	  }
  }
  
  public boolean didMove(){
	  return moved;
  }
   
  public void drawBoard(){
	  for (int i = 0; i < myBoard.length; i++) {
	      for (int j = 0; j < myBoard[0].length; j++) {
	        if ((i + j) % 2 == 0) {
	          StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
	        } else {
	          StdDrawPlus.setPenColor(StdDrawPlus.RED);
	        }
	        StdDrawPlus.filledSquare(j + .5, i + .5, .5);
	        if (myBoard[i][j]!=null) {
	        	if(myBoard[i][j].isSelected()){
	        		StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
	        		StdDrawPlus.filledSquare(j + .5, i + .5, .5);
	        	}
	        	StdDrawPlus.picture(j + .5, i + .5, myBoard[i][j].getImage(), 1, 1);
	        }
	      }
	  }
  }

  /**
   * Starts a game
   */
  public static void main(String[] args) {
	  Board initBoard = new Board(false);
	  StdDrawPlus.setScale(0, 8);
	  initBoard.drawBoard();
	  initBoard.saveCurrentState(initBoard);
	  while(!initBoard.isOver()){
		  if (StdDrawPlus.isNPressed()){
			  initBoard = new Board(false);
			  initBoard.drawBoard();
		  }
		  if (StdDrawPlus.mousePressed()) {
			  int x = (int) StdDrawPlus.mouseX();
		      int y = (int) StdDrawPlus.mouseY();
		      if(initBoard.canSelect(x, y)){
		    	  initBoard.select(x, y);
		    	  if (initBoard.didMove()){
		    		  initBoard.saveCurrentState(initBoard);
		    	  }
		    	  initBoard.drawBoard();
		      }
		  }
		  if (StdDrawPlus.isSpacePressed()) {
			  if(initBoard.canEndTurn()){
				  initBoard.endTurn();
				  initBoard.drawBoard();
				  if(initBoard.winner() != null){
					  System.out.println(initBoard.winner());
				  }
			  }
		  }
		  if (StdDrawPlus.isKeyPressed(85)){
			  initBoard = initBoard.revertBoard();
			  if(!initBoard.canJumpAgain()){
				  if(initBoard.canEndTurn()){
					  initBoard.endTurn();
				  }
			  }
			  initBoard.saveCurrentState(initBoard);
			  initBoard.drawBoard();
		  }
		  
	  }
  }
}