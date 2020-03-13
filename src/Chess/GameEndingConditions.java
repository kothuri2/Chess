package Chess;

import java.util.List;
import Chess.Board;
import Piece.Piece;
import Piece.PieceColor;

public class GameEndingConditions {

	/**
	 * Method that checks for either staleMate or checkMate
	 * if @param checkMate is true, then method checks for checkMate
	 * if @param checkMate is false, then method checks for staleMate
	 * @param checkMate
	 * @return
	 */
	public static boolean checkForStaleMateCheckMate(Board board, boolean checkMate) {
		PieceSet pieceSetToConsider = null;
		if(checkMate) {
			pieceSetToConsider = board.getOtherTeamPieceSet(board.getCurrentTurn());
		} else {
			pieceSetToConsider = board.getCurrentPieceSet();
		}
		
		for(Piece piece : pieceSetToConsider.getPieces()) {
			int currentPieceRow = piece.getCurrentPosition().getRow();
			int currentPieceCol = piece.getCurrentPosition().getCol();
			List<Move> possibleMoves = piece.getPossibleMoves(currentPieceRow, currentPieceCol);
			for(Move potentialMove : possibleMoves) {
				//Check if making this move makes puts own team's king in check
				Piece pieceAtDestination = board.getPiece(potentialMove.getEndX(), potentialMove.getEndY());
				board.updateLocationOnBoard(currentPieceRow, currentPieceCol, potentialMove.getEndX(), potentialMove.getEndY(), piece, pieceAtDestination);
				boolean inCheck = checkIfKingIsInCheck(board, checkMate);
				board.undoMove(currentPieceRow, currentPieceCol, potentialMove.getEndX(), potentialMove.getEndY(), piece, pieceAtDestination, false);
				if(!inCheck) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Depending on if otherSide is true or not, this method will
	 * check if your own team's king is in check status or if the other team's king is
	 * in check status.
	 * Otherside is true - check if other team is in check after your team finished making a move
	 * Otheside is false - check if you just made a move that put your team in check.
	 * @param otherSide
	 * @return
	 */
	public static boolean checkIfKingIsInCheck(Board board, boolean otherSide) {
		PieceSet pieceSetToConsider = null;
		Piece king = null;
		PieceSet[] pieceSets = board.getPieceSets();
		//Checking if the other team's king is in check status
		if(otherSide) {
			if(board.getCurrentTurn() == PieceColor.BLACK) {
				pieceSetToConsider = pieceSets[0];
				king = pieceSets[1].findKing();
			} else {
				pieceSetToConsider = pieceSets[1];
				king = pieceSets[0].findKing();
			}
		//Checking if own team's king is in check status
		} else {
			//Get own king and other team's pieceSet
			if(board.getCurrentTurn() == PieceColor.BLACK) {
				pieceSetToConsider = pieceSets[1];
				king = pieceSets[0].findKing();
			} else {
				pieceSetToConsider = pieceSets[0];
				king = pieceSets[1].findKing();
			}
		}
		
		// Iterate through all the pieces on the piece set to consider and
		// evaluate whether this piece is in line with the king being considered
		int kingRow = king.getCurrentPosition().getRow();
		int kingCol = king.getCurrentPosition().getCol();
		for(Piece piece : pieceSetToConsider.getPieces()) {
			if(piece.getCurrentPosition() != null) {
				int pieceRow = piece.getCurrentPosition().getRow();
				int pieceCol = piece.getCurrentPosition().getCol();
				boolean check = piece.checkIfValidMove(pieceRow, pieceCol, kingRow, kingCol);
				if(check) {
					return true;
				}
			}
		}
		
		return false;
	}

}
