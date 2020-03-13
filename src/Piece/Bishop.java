package Piece;

import java.util.*;
import Chess.Move;
import Chess.Path;

/**
 * The Bishop class extends the abstract Piece class.
 * It still uses the Piece Constructor but overrides the checkIfValidMove()
 * with logic just pertaining to Bishop diagonal trajectory rules.
 * @author Radhir
 *
 */
public class Bishop extends Piece {
	
	/**
	 * Bishop constructor
	 * @param pieceColor
	 * @param pieceType
	 */
	public Bishop(PieceColor pieceColor, PieceType pieceType) {
		super(pieceColor, pieceType);
	}

	/**
	 * Check if it is possible to move the bishop located at (startX, startY)
	 * to (endX, endY)
	 */
	@Override
	public boolean checkIfValidMove(int startX, int startY, int endX, int endY) {
		int columnDistance = Math.abs(endX - startX);
		int rowDistance = Math.abs(endY - startY);
		
		//rowDistance must be equal columnDistance in a diagonal
		if(rowDistance != columnDistance) {
			return false;
		}
		
		return Path.checkDiagonal(startX, startY, endX, endY);
	}
	
	/**
	 * This method generates all possible moves for a bishop located at
	 * Square (startX, startY). It uses the helper function getMoves() to do so.
	 */
	@Override
	public List<Move> getPossibleMoves(int startX, int startY) {
		return Path.getDiagonalPossibleMoves(startX, startY, getColor());
	}

}
