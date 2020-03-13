package Piece;

import java.util.*;

import Chess.CheckMateException;
import Chess.Move;
import Chess.StaleMateException;

/**
 * The King class extends the abstract Piece class.
 * It still uses the King Constructor but overrides the checkIfValidMove()
 * with logic just pertaining to King trajectory rules.
 * @author Radhir
 *
 */
public class King extends Piece {
	
	/**
	 * King constructor
	 * @param pieceColor
	 * @param pieceType
	 */
	public King(PieceColor pieceColor, PieceType pieceType) {
		super(pieceColor, pieceType);
	}
	
	/**
	 * Check if it is possible to move the king located at (startX, startY)
	 * to (endX, endY)
	 */
	@Override
	public boolean checkIfValidMove(int startX, int startY, int endX, int endY) {
		int columnDistance = Math.abs(endX - startX);
		int rowDistance = Math.abs(endY - startY);
		
		if(columnDistance <= 1 && rowDistance <= 1) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Helper function for getPossibleMoves.
	 * Determines if it possible to make the move being considered. (i.e. moving a king from
	 * (startX, startY) to (endX, endY))
	 * @param possibleMoves
	 * @param startX
	 * @param startY
	 * @param xDelta
	 * @param yDelta
	 */
	public void checkIfPossibleMove(List<Move> possibleMoves, int startX, int startY, int xDelta, int yDelta) {
		Move newMove = new Move(startX, startY, startX + xDelta, startY + yDelta, getColor());
		boolean isValidMove = false;
		try {
			isValidMove = newMove.checkValidMove(false);
		} catch(CheckMateException e) {
			
		} catch(StaleMateException e) {
			
		}
		if(isValidMove) {
			possibleMoves.add(newMove);
		}
	}
	
	/**
	 * This method generates all possible moves for a king located at
	 * Square (startX, startY). It uses the helper function checkIfPossibleMove() to do so.
	 */
	@Override
	public List<Move> getPossibleMoves(int startX, int startY) {
		List<Move> possibleMoves = new ArrayList<Move>();
		
		//Move object to check if moving up is possible
		//endX = startX -1
		checkIfPossibleMove(possibleMoves, startX, startY, -1, 0);
		//Move object to check if moving down is possible
		//endX = startX + 1
		checkIfPossibleMove(possibleMoves, startX, startY, 1, 0);
		//Move object to check if moving right is possible
		//endY = startY + 1
		checkIfPossibleMove(possibleMoves, startX, startY, 0, 1);
		//Move object to check if moving left is possible
		//endY = startY - 1
		checkIfPossibleMove(possibleMoves, startX, startY, 0, -1);
		//Move object to check if moving top right is possible
		//xDelta = -1
		//yDelta = +1
		checkIfPossibleMove(possibleMoves, startX, startY, -1, 1);
		//Move object to check if moving top left is possible
		//xDelta = -1
		//yDelta = -1
		checkIfPossibleMove(possibleMoves, startX, startY, -1, -1);
		//Move object to check if moving bottom right is possible
		//xDelta = +1
		//yDelta = +1
		checkIfPossibleMove(possibleMoves, startX, startY, 1, 1);
		//Move object to check if moving bottom left is possible
		//xDelta = +1
		//yDelta = -1
		checkIfPossibleMove(possibleMoves, startX, startY, 1, -1);
		
		return possibleMoves;
	}

}
