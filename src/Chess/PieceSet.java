package Chess;

import java.util.*;

import Piece.Bishop;
import Piece.King;
import Piece.Knight;
import Piece.Pawn;
import Piece.Piece;
import Piece.PieceColor;
import Piece.PieceType;
import Piece.Queen;
import Piece.Rook;

/**
 * This class is for responsible for managing all team information.
 * Each team has 16 pieces and a list of all the captured information.
 * @author Radhir
 *
 */
public class PieceSet {
	
	/**
	 * PieceSet member variables
	 */
	private List<Piece> pieces;
	private Square[][] board;
	private List<Piece> capturedPieces;
	private PieceColor setColor;
	private static final int NUMBER_OF_PAWNS = 8;
	private static final int NUMBER_OF_ROOKS_BISHOPS_KNIGHTS = 2;
	private static final int NUMBER_OF_QUEENS_KINGS = 1;
	
	/**
	 * PieceSet constructor
	 * @param setColor
	 * @param board
	 */
	public PieceSet(PieceColor setColor, Square[][] board) {
		pieces = new ArrayList<Piece>();
		capturedPieces = new ArrayList<Piece>();
		this.setColor = setColor;
		this.board = board;
		createPieces();
		if(setColor == PieceColor.WHITE) {
			// Set the position for each of the white pieces
			setPositionForPieces(6, 7, 0, 2, 1, 3, 4);
		} else {
			// Set the position for each of the black pieces
			setPositionForPieces(1, 0, 0, 2, 1, 3, 4);
		}
	}
	
	/**
	 * Find and return the king from the list of pieces
	 */
	public Piece findKing() {
		for(Piece piece : pieces) {
			if(piece.getPieceType() == PieceType.KING) {
				return piece;
			}
		}
		return null;
	}
	
	/**
	 * Insert captured piece to list of captured pieces for this team
	 * @param capturedPiece
	 */
	public void addCapturedPiece(Piece capturedPiece) {
		capturedPieces.add(capturedPiece);
	}
	
	/**
	 * Get the pieces for this PieceSet
	 * @return the pieces for this piece set
	 */
	public List<Piece> getPieces() {
		return pieces;
	}
	
	/**
	 * Set the color for this PieceSet
	 * @return the setColor for this piece set 
	 */
	public PieceColor getSetColor() {
		return setColor;
	}
	
	/**
	 * @return list of captured pieces
	 */
	public List<Piece> getCapturedPieces() {
		return capturedPieces;
	}
	
	/**
	 * Create all the appropriate pieces necessary for Black side
	 * and add to pieces list
	 * 8 pawns,
	 * 2 rooks,
	 * 2 bishops,
	 * 2 knights,
	 * 1 queen,
	 * 1 king
	 */
	public void createPieces() {
		for(int i = 0; i < NUMBER_OF_PAWNS; i++) {
			Pawn newPawn = new Pawn(setColor, PieceType.PAWN);
			pieces.add(newPawn);
		}
		
		for(int i = 0; i < NUMBER_OF_ROOKS_BISHOPS_KNIGHTS; i++) {
			Rook newRook = new Rook(setColor, PieceType.ROOK);
			Knight newKnight = new Knight(setColor, PieceType.KNIGHT);
			Bishop newBishop = new Bishop(setColor, PieceType.BISHOP);
			
			pieces.add(newRook);
			pieces.add(newKnight);
			pieces.add(newBishop);
		}
		
		for(int i = 0; i < NUMBER_OF_QUEENS_KINGS; i++) {
			Queen newQueen = new Queen(setColor, PieceType.QUEEN);
			King newKing = new King(setColor, PieceType.KING);
			
			pieces.add(newQueen);
			pieces.add(newKing);
		}
	}
	
	/**
	 * Set the initial respective position for each of the pieces
	 */
	public void setPositionForPieces(int pawnRow, int otherPieceRows,
									 int rookColumn, int bishopColumn, int knightColumn,
									 int queenColumn, int kingColumn) {
		
		int pawnColumn = 0;
		for(Piece piece : pieces) {
			if(piece.getPieceType() == PieceType.PAWN) {
				piece.setCurrentPosition(board[pawnRow][pawnColumn]);
				pawnColumn ++;
			} else if(piece.getPieceType() == PieceType.ROOK) {
				piece.setCurrentPosition(board[otherPieceRows][rookColumn]);
				rookColumn = 7;
			} else if(piece.getPieceType() == PieceType.BISHOP) {
				piece.setCurrentPosition(board[otherPieceRows][bishopColumn]);
				bishopColumn = 5;
			} else if(piece.getPieceType() == PieceType.KNIGHT) {
				piece.setCurrentPosition(board[otherPieceRows][knightColumn]);
				knightColumn = 6;
			} else if(piece.getPieceType() == PieceType.QUEEN) {
				piece.setCurrentPosition(board[otherPieceRows][queenColumn]);
			} else if(piece.getPieceType() == PieceType.KING) {
				piece.setCurrentPosition(board[otherPieceRows][kingColumn]);
			}
		}
	}
	
}
