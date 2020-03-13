package Piece;

import java.util.ArrayList;
import java.util.List;
import Chess.Path;
import Chess.Move;

public class Queen extends Piece {
	
	/**
	 * Queen constructor
	 * @param pieceColor
	 * @param pieceType
	 */
	public Queen(PieceColor pieceColor, PieceType pieceType) {
		super(pieceColor, pieceType);
	}
	
	/**
	 * Check if the move from startX,startY to endX,endY is a valid move
	 * for the Queen
	 */
	@Override
	public boolean checkIfValidMove(int startX, int startY, int endX, int endY) {
		
		int columnDistance = Math.abs(endX - startX);
		int rowDistance = Math.abs(endY - startY);
		
		//rowDistance must be equal columnDistance in a diagonal
		//If not equal, must be either or vertical or horizontal move
		if(rowDistance != columnDistance) {
			// Check if the path is either vertical or horizontal
			// If it is and there are no obstacles, then move in the respective direction
			if(Path.checkVerticalHorizontal(startX, startY, endX, endY)) {
				return true;
		}
		// Else check if it is a diagonal move and then move in the respective direction
		} else if(Path.checkDiagonal(startX, startY, endX, endY)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Get all possibleMoves for the Queen from startX,startY
	 */
	@Override
	public List<Move> getPossibleMoves(int startX, int startY) {
		
		List<Move> possibleMoves = new ArrayList<Move>();
		
		/*
		 * Since a queen can travel as a rook and a bishop,
		 * just call the possibleMoves function for rook and bishop
		 */
		possibleMoves.addAll(Path.getRookPossibleMoves(startX, startY, getColor()));
		possibleMoves.addAll(Path.getDiagonalPossibleMoves(startX, startY, getColor()));

		return possibleMoves;
	}

}
