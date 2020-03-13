package Piece;

import java.util.ArrayList;
import java.util.List;
import Chess.Move;
import Chess.Path;

/**
 * The Empress class extends the abstract Piece class.
 * It still uses the Piece Constructor but overrides the checkIfValidMove()
 * with logic just pertaining to Rook diagonal trajectory rules as well as Knight trajectory Rules.
 * @author Radhir
 *
 */
public class Empress extends Piece {
	
	/**
	 * Empress Constructor
	 * @param pieceColor
	 * @param pieceType
	 */
	public Empress(PieceColor pieceColor, PieceType pieceType) {
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
		
		boolean knightPossible = false;
		//Check for knight possible moves
		if( (columnDistance == 2 && rowDistance == 1) || 
			(rowDistance == 2 && columnDistance == 1) ) {
				knightPossible = true;
		}
	
		return Path.checkVerticalHorizontal(startX, startY, endX, endY) || knightPossible;
	}
	
	/**
	 * Return all the possible moves for the Empress
	 */
	@Override
	public List<Move> getPossibleMoves(int startX, int startY) {
		List<Move> possibleMoves = new ArrayList<Move>();
		
		//Get all vertical and horizontal Moves
		possibleMoves.addAll(Path.getRookPossibleMoves(startX, startY, getColor()));
		//Get all knight Moves
		possibleMoves.addAll(Path.getKnightPossibleMoves(startX, startY, getColor()));
		
		return possibleMoves;
	}

}
