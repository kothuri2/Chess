package Piece;

import java.util.ArrayList;
import java.util.List;
import Chess.Move;
import Chess.Path;

/**
 * The Princess class extends the abstract Piece class.
 * It still uses the Piece Constructor but overrides the checkIfValidMove()
 * with logic just pertaining to Bishop diagonal trajectory rules as well as Knight trajectory Rules.
 * @author Radhir
 *
 */
public class Princess extends Piece {

	/**
	 * Princess constructor
	 * @param pieceColor
	 * @param pieceType
	 */
	public Princess(PieceColor pieceColor, PieceType pieceType) {
		super(pieceColor, pieceType);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Check if it is possible to move the Princess located at (startX, startY)
	 * to (endX, endY)
	 */
	@Override
	public boolean checkIfValidMove(int startX, int startY, int endX, int endY) {
		// TODO Auto-generated method stub
		int columnDistance = Math.abs(endX - startX);
		int rowDistance = Math.abs(endY - startY);
		
		boolean diagonalPossible = true;
		boolean knightPossible = false;
		//rowDistance must be equal columnDistance in a diagonal
		if(rowDistance != columnDistance) {
			// Can't move diagonally so check for Knight moves
			diagonalPossible = false;
		}
		
		//Check for knight possible moves
		if( (columnDistance == 2 && rowDistance == 1) || 
			(rowDistance == 2 && columnDistance == 1) ) {
				knightPossible = true;
		}
		
		//If diagonal possible check if diagonal is possible or if knight is possible
		if(diagonalPossible) {
			return Path.checkDiagonal(startX, startY, endX, endY) || knightPossible;
		}
		//Diagonal not possible so just return knight possible
		return knightPossible;
	}

	@Override
	public List<Move> getPossibleMoves(int startX, int startY) {
		// TODO Auto-generated method stub
		List<Move> possibleMoves = new ArrayList<Move>();
		
		//Get all diagonal Moves
		possibleMoves.addAll(Path.getDiagonalPossibleMoves(startX, startY, getColor()));
		//Get all knight Moves
		possibleMoves.addAll(Path.getKnightPossibleMoves(startX, startY, getColor()));
		
		return possibleMoves;
	}

}
