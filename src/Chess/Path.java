package Chess;

import java.util.ArrayList;
import java.util.List;
import Piece.PieceColor;
/**
 * This class is responsible for checking vertical, horizontal, and diagonal moves for pieces.
 * It checks 
 * It also generates the possibeMoves depending on the directions inputted for that piece.
 * @author Radhir
 *
 */
public class Path {

	/**
	 * Check whether to traverse vertically or horizontally and then call
	 * a helper function that checks if there are any other obstacles in the way
	 */
	public static boolean checkVerticalHorizontal(int startX, int startY, int endX, int endY) {
		//Calculate column and row distance
		int columnDistance = Math.abs(endX - startX);
		int rowDistance = Math.abs(endY - startY);
		
		// Traveling vertically
		if(columnDistance > 0 && rowDistance == 0) {
			//Moving down
			if(endX > startX) {
				return checkIfPiecesExistInPathVertical(startX, startY, endX, endY, 1);
			//Moving up
			} else {
				return checkIfPiecesExistInPathVertical(startX, startY, endX, endY, -1);
			}
		//Traveling horizontally
		} else if (rowDistance > 0 && columnDistance == 0) {
			//Traveling right
			if(endY > startY) {
				return checkIfPiecesExistInPathHorizontal(startX, startY, endX, endY, 1);
			//Traveling left
			} else {
				return checkIfPiecesExistInPathHorizontal(startX, startY, endX, endY, -1);
			}
		}
		
		return false;
	}
	
	/**
	 * Check whether to traverse diagonally in which x and y deltas and then call
	 * a helper function that checks if there are any other obstacles in that specific path
	 */
	public static boolean checkDiagonal(int startX, int startY, int endX, int endY) {
		int xDelta = 0;
		int yDelta = 0;
		//Determine which diagonal direction the bishop wants to travel in
		//Piece is moving in the topRight direction
		if(endX < startX && endY > startY) {
			xDelta = -1;
			yDelta = 1;
			return checkIfPathExistsDiagonal(startX, startY, endX, endY, xDelta, yDelta);
		//Piece is moving in the topLeft direction
		} else if(endX < startX && endY < startY) {
			xDelta = -1;
			yDelta = -1;
			return checkIfPathExistsDiagonal(startX, startY, endX, endY, xDelta, yDelta);
		//Piece is moving in bottomRight direction 
		} else if(endX > startX && endY > startY) {
			xDelta = 1;
			yDelta = 1;
			return checkIfPathExistsDiagonal(startX, startY, endX, endY, xDelta, yDelta);
		//Piece is moving in bottomLeft direction
		} else if(endX > startX && endY < startY) {
			xDelta = 1;
			yDelta = -1;
			return checkIfPathExistsDiagonal(startX, startY, endX, endY, xDelta, yDelta);
		}
		
		return false;
	}
	
	/**
	 * Check whether there are no other pieces in the way in the diagonal direction
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param xDelta
	 * @param yDelta
	 * @return
	 */
	public static boolean checkIfPathExistsDiagonal(int startX, int startY, int endX, int endY, int xDelta, int yDelta) {
		startX += xDelta;
		startY += yDelta;
		
		Board board = Game.getCurrentBoardState();
		while(startX != endX && startY != endY) {
			if(board.getPiece(startX, startY) != null) {
				return false;
			}
			startX += xDelta;
			startY += yDelta;
		}
		return true;
	}
	
	/**
	 * Check whether there are no other pieces in the way in the vertical direction
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return
	 */
	public static boolean checkIfPiecesExistInPathVertical(int startX, int startY, int endX, int endY, int xDelta) {
		Board board = Game.getCurrentBoardState();
		startX += xDelta;
		while(startX != endX) {
			if(board.getPiece(startX, endY) != null) {
				return false;
			}
			startX += xDelta;
		}

		return true;
	}
	
	/**
	 * This method generates all possible diagonal moves for a piece located at
	 * Square (startX, startY). It uses the helper function getMoves() to do so.
	 */
	public static List<Move> getDiagonalPossibleMoves(int startX, int startY, PieceColor color) {
		List<Move> possibleMoves = new ArrayList<Move>();
		
		//Move topRight direction
		possibleMoves.addAll(getMoves(startX, startY, -1, 1, color));
		//Move topLeft direction
		possibleMoves.addAll(getMoves(startX, startY, -1, -1, color));
		//Move bottomRight direction
		possibleMoves.addAll(getMoves(startX, startY, 1, 1, color));
		//Move bottomLeft direction
		possibleMoves.addAll(getMoves(startX, startY, 1, -1, color));
		
		return possibleMoves;
	}
	
	/**
	 * This method generates all possible moves for a knight located at
	 * Square (startX, startY).
	 */
	public static List<Move> getKnightPossibleMoves(int startX, int startY, PieceColor color) {
		List<Move> possibleMoves = new ArrayList<Move>();
		
		//All 8 possible moves that the knight can make
		int[][] possibleChanges = {{1,2}, {-1, 2}, {-1, -2}, {1, -2}, {2, 1}, {-2, 1}, {2, -1}, {-2,-1}};
		for(int i = 0; i < possibleChanges.length; i++) {
			int endX = startX + possibleChanges[i][0];
			int endY = startY + possibleChanges[i][1];
			
			Move newMove = new Move(startX, startY, endX, endY, color);
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
		return possibleMoves;
	}
	
	/**
	 * Check whether there are no other pieces in the way in the horizontal direction
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return
	 */
	public static boolean checkIfPiecesExistInPathHorizontal(int startX, int startY, int endX, int endY, int yDelta) {
		Board board = Game.getCurrentBoardState();
		startY += yDelta;
		while(startY != endY) {
			if(board.getPiece(startX, startY) != null) {
				return false;
			}
			startY += yDelta;
		}

		return true;
	}
	
	/**
	 * This method generates all possible moves for a rook located at
	 * Square (startX, startY). It uses the helper function getMoves() to do so.
	 */
	public static List<Move> getRookPossibleMoves(int startX, int startY, PieceColor color) {
		List<Move> possibleMoves = new ArrayList<Move>();
		
		//Moving in the yDirection by 1 - moving vertically - down
		possibleMoves.addAll(getMoves(startX, startY, 1, 0, color));
		//Moving in the xDirection by 1 - moving horizontally - right
		possibleMoves.addAll(getMoves(startX, startY, 0, 1, color));
		//Moving in the yDirection by -1 - moving vertically - up
		possibleMoves.addAll(getMoves(startX, startY, -1, 0, color));
		//Moving in the xDirection by -1 - moving horizontally - left
		possibleMoves.addAll(getMoves(startX, startY, 0, -1, color));
		
		return possibleMoves;
	}
	
	/**
	 * Determine all the possible moves a piece can make without going
	 * off the board. Use the xDelta and yDelta to determine in which directions 
	 * the piece can move.
	 */
	public static List<Move> getMoves(int startX, int startY, int xDelta, int yDelta, PieceColor color) {
		List<Move> possibleMoves = new ArrayList<Move>();
		
		int newX = startX + xDelta;
		int newY = startY + yDelta;

		// While newX, newY are not off the board, then create a new Move
		// and check if it is a valid move
		while(newX < Game.getBoardWidth() && newX >= 0 && newY < Game.getBoardLength() && newY >= 0) {
			Move newMove = new Move(startX, startY, newX, newY, color);
			//System.out.println(newMove.toString());
			boolean isValidMove = false;
			try {
				isValidMove = newMove.checkValidMove(false);
			} catch(CheckMateException e) {
				
			} catch(StaleMateException e) {
				
			}
			if(isValidMove) {
				possibleMoves.add(newMove);
			}
			
			newX += xDelta;
			newY += yDelta;
			
		}

		return possibleMoves;
	}

}
