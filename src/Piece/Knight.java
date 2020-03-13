package Piece;

import java.util.*;
import Chess.Move;
import Chess.Path;

/**
 * The Knight class extends the abstract Piece class.
 * It still uses the Piece Constructor but overrides the checkIfValidMove()
 * with logic just pertaining to Knight trajectory rules.
 * @author Radhir
 *
 */
public class Knight extends Piece {
	
	/**
	 * Knight constructor
	 * @param pieceColor
	 * @param pieceType
	 */
	public Knight(PieceColor pieceColor, PieceType pieceType) {
		super(pieceColor, pieceType);
	}
	
	/**
	 * Check if it is possible to move the knight located at (startX, startY)
	 * to (endX, endY)
	 */
	@Override
	public boolean checkIfValidMove(int startX, int startY, int endX, int endY) {
		int columnDistance = Math.abs(endX - startX);
		int rowDistance = Math.abs(endY - startY);
		
		if( (columnDistance == 2 && rowDistance == 1) || 
			(rowDistance == 2 && columnDistance == 1) ) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * This method generates all possible moves for a knight located at
	 * Square (startX, startY). 
	 */
	@Override
	public List<Move> getPossibleMoves(int startX, int startY) {
		return Path.getKnightPossibleMoves(startX, startY, getColor());
	}

}
