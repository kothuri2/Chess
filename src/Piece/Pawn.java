package Piece;

import java.util.ArrayList;
import java.util.List;

import Chess.Game;
import Chess.Move;
import Chess.Board;
import Chess.Path;

/**
 * The Pawn class extends the abstract Piece class.
 * It still uses the Piece Constructor but overrides the checkIfValidMove()
 * with logic just pertaining to Pawn trajectory rules.
 * @author Radhir
 *
 */
public class Pawn extends Piece {
	
	/**
	 * Pawn constructor
	 * @param pieceColor
	 * @param pieceType
	 */
	public Pawn(PieceColor pieceColor, PieceType pieceType) {
		super(pieceColor, pieceType);
	}
	
	/**
	 * Check if it is possible to move the pawn located at (startX, startY)
	 * to (endX, endY)
	 */
	@Override
	public boolean checkIfValidMove(int startX, int startY, int endX, int endY) {
		
		Board board = Game.getCurrentBoardState();
		
		// Calculate row and column distance from current location to destination
		int columnDistance = endX - startX;
		int rowDistance = endY - startY;
		Piece enemy = board.getPiece(endX, endY);
		//Check if there is an enemy in the destination of the pawn
		boolean enemyInDestination = false;
		if(enemy != null) {
			enemyInDestination = enemy.getColor() != getColor() ? true : false;
		}
		//Check if the pawn hasn't moved yet.
		boolean hasMoved = this.getHasMoved();
		
		/* *
		 * 1st case - Black Pawn hasn't moved yet and can move 2 or 1 spaces (only if there is no enemy piece)
		 * 2nd case - White Pawn hasn't moved yet and can move 2 or 1 spaces (only if there is no enemy piece)
		 * 3rd case - White pawn can move diagonally move space to the left or right if there is an enemy in that location
		 * 4th case - Black pawn can move diagonally move space to the left or right if there is an enemy in that location
		 * 5th case - White pawn can move one space up if it has moved already if there is no enemy in that location
		 * 6th case - Black pawn can move one space up if it has moved already if there is no enemy in that location
		 */
		/*System.out.println(columnDistance);
		System.out.println(rowDistance);
		System.out.println(getColor().toString());
		System.out.println(hasMoved);
		System.out.println(enemyInDestination);*/
		if( (columnDistance == 1 || columnDistance == 2) && rowDistance == 0 && getColor() == PieceColor.BLACK && !hasMoved && !enemyInDestination) {
			//Make sure there is no piece at columnDistance = 1
			if(columnDistance == 2) {
				if(board.getPiece(startX+1, endY) != null) {
					return false;
				}
			}
			return true;
		} else if( (columnDistance == -1 || columnDistance == -2) && rowDistance == 0 && getColor() == PieceColor.WHITE && !hasMoved && !enemyInDestination) {
			//Make sure there is no piece at columnDistance = -1
			//System.out.println("inside here");
			if(columnDistance == -2) {
				if(board.getPiece(startX-1, endY) != null) {
					return false;
				}
			}
			return true;
		} else if(getColor() == PieceColor.WHITE && columnDistance == -1  && (rowDistance == 1 || rowDistance == -1) && enemyInDestination) {
			return true;
		} else if(getColor() == PieceColor.BLACK && columnDistance == 1 && (rowDistance == 1 || rowDistance == -1) && enemyInDestination) {
			return true;
		} else if(hasMoved && getColor() == PieceColor.WHITE && columnDistance == -1 && rowDistance == 0 && !enemyInDestination) {
			return true;
		} else if(hasMoved && getColor() == PieceColor.BLACK && columnDistance == 1 && rowDistance == 0 && !enemyInDestination) {
			return true;
		}

		return false;
		
	}
	
	/**
	 * This method generates all possible moves for a pawn located at
	 * Square (startX, startY). It uses the helper function getMoves() to do so.
	 */
	@Override
	public List<Move> getPossibleMoves(int startX, int startY) {
		
		List<Move> possibleMoves = new ArrayList<Move>();
		Board board = Game.getCurrentBoardState();
		
		int xDelta = 0;
		
		//Depending on what the color of the team is,
		//this determines the xDelta.
		if(getColor() == PieceColor.BLACK) {
			xDelta = 1;
		} else {
			xDelta = -1;
		}
		
		possibleMoves.addAll(Path.getMoves(startX, startY, xDelta, 0, getColor()));
		possibleMoves.addAll(Path.getMoves(startX, startY, xDelta, 1, getColor()));
		possibleMoves.addAll(Path.getMoves(startX, startY, xDelta, -1, getColor()));
		
		//Remove all moves that have a column distance of 2 or -2 if
		//the piece has already moved
		//System.out.println(possibleMoves.size());
		for(Move potentialMove : possibleMoves) {
			int moveEndX = potentialMove.getEndX();
			int moveStartX = potentialMove.getStartX();
			int columnDistance = moveEndX - moveStartX;
			if((columnDistance == 2 || columnDistance == -2) &&
					board.getPiece(startX, startY).getHasMoved() == true)
			{
				possibleMoves.remove(potentialMove);
			}
		}
		return possibleMoves;
	}

}
