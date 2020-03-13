package Piece;

import java.util.*;
import Chess.Path;
import Chess.Move;

/**
 * The Rook class extends the abstract Piece class.
 * It still uses the Piece Constructor but overrides the checkIfValidMove()
 * with logic just pertaining to Rook vertical, horizontal trajectory rules.
 * @author Radhir
 *
 */
public class Rook extends Piece {
	
	/**
	 * Rook constructor
	 * @param pieceColor
	 * @param pieceType
	 */
	public Rook(PieceColor pieceColor, PieceType pieceType) {
		super(pieceColor, pieceType);
	}
	
	/**
	 * Check if it is possible to move the rook located at (startX, startY)
	 * to (endX, endY)
	 */
	@Override
	public boolean checkIfValidMove(int startX, int startY, int endX, int endY) {
		return Path.checkVerticalHorizontal(startX, startY, endX, endY);
	}
	
	/**
	 * This method generates all possible moves for a rook located at
	 * Square (startX, startY). It uses the helper function getMoves() to do so.
	 */
	@Override
	public List<Move> getPossibleMoves(int startX, int startY) {
		return Path.getRookPossibleMoves(startX, startY, getColor());
	}

}
