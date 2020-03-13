package Chess;

import javax.swing.JOptionPane;

import Piece.Piece;
import Piece.PieceColor;

/**
 * Move class is responsible for each move that happens on the board.
 * All general validation of the specific move being happened occurs here.
 * Piece trajectory validation happens in the actual piece's class
 * @author Radhir
 *
 */
public class Move {
	
	/**
	 * Move class member variables
	 */
	private int startX, startY;
	private int endX, endY;
	private PieceColor pieceColor;
	private Piece pieceAtStart, pieceAtDestination;
	private boolean markPieceAsMovedOnThisMove;
	
	/**
	 * Move Constructor
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param pieceColor
	 */
	public Move(int startX, int startY, int endX, int endY, PieceColor pieceColor) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.pieceColor = pieceColor;
		this.markPieceAsMovedOnThisMove = false;
	}
	
	/**
	 * Get piece at square that is at (startX, startY)
	 * @return
	 */
	public Piece getPieceAtStart() {
		return pieceAtStart;
	}
	
	/**
	 * Get piece at square that is at (endX, endY)
	 * @return
	 */
	public Piece getPieceAtDestination() {
		return pieceAtDestination;
	}
	
	/**
	 * Calls helper functions to validate if piece can be moved
	 * 1st case - Check if the team that is supposed to move moved their piece
	 * 2nd case - Check if input startX, startY coordinates of current piece are not out of bounds
	 * 3rd case - Check if destination endX, endY coordinates of destination are not out of bounds
	 * 4th case - Check that the location doesn't contain a piece of your own team
	 * 5th case - Check if that specific piece being moved can be moved in that direction 
	 * 	(validation happens in each specific Piece class)
	 * @param actualMove - used to determine whether to actually move the piece on the board
	 * or to just fake this call and just check the validation logic
	 * @return true if the piece has been moved, false otherwise
	 * @throws CheckMateException 
	 * @throws InvalidMoveException 
	 */
	public boolean checkValidMove(boolean actualMove) throws CheckMateException, StaleMateException {
		Board currentBoard = Game.getCurrentBoardState();
		Piece pieceMoved = currentBoard.getPiece(startX, startY);
		PieceColor pieceMovedColor = pieceMoved.getColor();
		if(!checkTeamUsedTurn(pieceMovedColor)) {
			if(actualMove) {
				JOptionPane.showMessageDialog(null, "It's " + currentBoard.getCurrentTurn().toString() + "'s turn!");
				return false;
			}
		}
		if(checkForOutOfBounds(startX, startY) && checkForOutOfBounds(endX, endY)
				&& checkEndLocationIsNotOwnTeam() &&
				pieceMoved.checkIfValidMove(startX, startY, endX, endY))
		{
			this.pieceAtStart = Game.getCurrentBoardState().getPiece(startX, startY);
			this.pieceAtDestination = Game.getCurrentBoardState().getPiece(endX, endY);
				if(actualMove) {
					try {
						boolean successfulMove = currentBoard.movePiece(startX, startY, endX, endY);
						if(!successfulMove) {
							return false;
						}
					} catch(CheckMateException checkMate) {
						throw new CheckMateException();
					} catch(StaleMateException staleMate) {
						throw new StaleMateException();
					}
					if(!pieceMoved.getHasMoved()) {
						pieceMoved.markPieceAsMoved();
						this.markPieceAsMovedOnThisMove = true;
					}
					currentBoard.addMove(this);
				}
				return true;
		}
		return false;
	}
	
	/**
	 * Check if the team that is supposed to move actually moved
	 * (i.e.) It is Black's turn, but a white pawn moves instead.
	 */
	public boolean checkTeamUsedTurn(PieceColor pieceMovedColor) {
		Board currentBoard = Game.getCurrentBoardState();
		if(pieceMovedColor == (currentBoard.getCurrentTurn())) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Check if the row, col is a valid location on the board
	 * Return false if any of the constraints are out of the grid
	 * Return true if the row, col pair is in the grid
	 * @return true or false depending if coordinates are valid location
	 */
	public boolean checkForOutOfBounds(int row, int col) {
		if(row < Game.getBoardWidth() && col >= 0 && col < Game.getBoardLength() && row >= 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if the end location to move to doesn't contain a piece of own team
	 * @return true or false depending on condition stated above
	 */
	public boolean checkEndLocationIsNotOwnTeam() {
		Board currentBoard = Game.getCurrentBoardState();
		PieceColor startLocationColor = currentBoard.getPiece(startX, startY).getColor();
		Piece endLocationPiece = currentBoard.getPiece(endX, endY);
		if(endLocationPiece == null) {
			return true;
		}
		PieceColor endLocationColor = endLocationPiece.getColor();
		if(startLocationColor != endLocationColor) {
			return true;
		}
		return false;
	}
	
	/**
	 * Get the startX location for the Move being considered
	 */
	public int getStartX() {
		return startX;
	}
	
	/**
	 * Get the startY location for the Move being considered
	 */
	public int getStartY() {
		return startY;
	}
	
	/**
	 * Get the endX location for the Move being considered
	 */
	public int getEndX() {
		return endX;
	}
	
	/**
	 * Get the endY location for the Move being considered
	 */
	public int getEndY() {
		return endY;
	}
	
	/**
	 * Get the pieceColor location for the Move being considered
	 */
	public PieceColor getPieceColor() {
		return pieceColor;
	}
	
	/**
	 * Get the markPieceAsMovedOnThisMove variable
	 */
	public boolean getMarkPieceAsMovedOnThisMove() {
		return markPieceAsMovedOnThisMove;
	}
	
	@Override
	public String toString() {
		return startX + "," + startY + "," + endX + "," + endY + "," + pieceColor;
	}
	
}
