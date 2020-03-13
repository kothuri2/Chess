package Piece;

import Chess.Square;
import Chess.Move;

import java.util.*;

/**
 * This is the abstract class Piece that defines more of the general behavior
 * of each of the individual Piece classes. It also contains helper functions
 * for moving diagonal, vertical, and horizontal that the Queen, Bishop, and Rook
 * classes use.
 * @author Radhir
 *
 */
public abstract class Piece {
	
	/**
	 * Piece class member variables
	 */
	private PieceColor color;
	private Square currentPosition;
	private boolean hasMoved;
	private PieceType pieceType;
	
	public Piece(PieceColor pieceColor, PieceType pieceType) {
		this.hasMoved = false;
		this.color = pieceColor;
		this.pieceType = pieceType;
	}
	
	/**
	 * @return the piece type for the Piece being considered
	 */
	public PieceType getPieceType() {
		return pieceType;
	}
	
	/**
	 * @return the color for the Piece being considered
	 */
	public PieceColor getColor() {
		return color;
	}
	
	/**
	 * Set the current position for the piece
	 * @param currentPosition
	 */
	public void setCurrentPosition(Square currentPosition) {
		this.currentPosition = currentPosition;
	}
	
	/**
	 * @return the current position for the Piece being considered
	 */
	public Square getCurrentPosition() {
		return currentPosition;
	}
	
	/**
	 * Check if the Piece can be moved to the desired location
	 * This method will be overridden for each specific Piece
	 * 
	 * @param startX - current row position of the Piece
	 * @param startY - current column position of the Piece
	 * @param endX - desired row position of the Piece
	 * @param endY - desired column position of the Piece
	 * @return boolean variable that determines if the Piece can be moved from
	 * the current position to the desired position
	 */
	public abstract boolean checkIfValidMove(int startX, int startY, int endX, int endY);
	
	/**
	 * Mark the piece as moved from it's initial position
	 */
	public void markPieceAsMoved() {
		hasMoved = true;
	}
	
	/**
	 * Mark the piece as not moved from it's initial position
	 */
	public void markPieceAsNotMoved() {
		hasMoved = false;
	}
	
	/**
	 * Get the hasMoved property for a Piece
	 */
	public boolean getHasMoved() {
		return hasMoved;
	}
	
	/**
	 * An abstract method whose behavior will be overriden by the 
	 * individual Piece classes.
	 * @param startX
	 * @param startY
	 * @return
	 */
	public abstract List<Move> getPossibleMoves(int startX, int startY); 

}
