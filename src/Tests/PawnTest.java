package Tests;

import static org.junit.Assert.*;

import java.util.*;
import Chess.CheckMateException;
import Chess.CommonFunctions;
import Chess.Board;
import Chess.Move;
import Chess.StaleMateException;
import Piece.Piece;
import Piece.Pawn;
import Piece.PieceColor;
import Piece.PieceType;

import org.junit.Test;

public class PawnTest {
	
	//Board for this test
	Board board = new Board(8,8);
	
	@Test
	/**
	 * Test that the Pawn Constructor is valid
	 * @throws Exception
	 */
	public void testValidConstructor() {
		PieceColor pieceColor = PieceColor.BLACK;
		PieceType pieceType = PieceType.PAWN;
		Piece pawn = new Pawn(pieceColor, pieceType);
		assertEquals(pieceColor, pawn.getColor());
		assertEquals(pieceType, pawn.getPieceType());
	}
	
	/**
	 * Test that a valid move is actually considered valid
	 * @throws StaleMateException
	 * @throws CheckMateException 
	 * @throws Exception
	 */
	@Test
	public void testValidMoveTwoSpacesFromStart() throws CheckMateException, StaleMateException {
		Piece pawnOneWhite = board.getPiece(6, 3);
		
		if(! (pawnOneWhite instanceof Pawn)) {
			fail("Piece is not a pawn");
		}
		
		boolean checkIfValidTwoSpaces = pawnOneWhite.checkIfValidMove(6, 3, 4, 3);
		
		assertEquals(true, checkIfValidTwoSpaces);
		
	}
	
	/**
	 * Test that an invalid move is actually considered invalid
	 * @throws StaleMateException 
	 * @throws CheckMateException 
	 * @throws InvalidMoveException 
	 * 
	 */
	@Test
	public void testInvalidTwoSpacesMoveNotFromStart() throws CheckMateException, StaleMateException {
		board = new Board(8, 8);
		
		Piece pawnOneWhite = board.getPiece(6, 3);
		
		if(! (pawnOneWhite instanceof Pawn)) {
			fail("Piece is not a pawn");
		}
		
		Move newMoveOne = new Move(6, 3, 4, 3, PieceColor.WHITE);
		newMoveOne.checkValidMove(true);
		
		boolean checkIfValidMove = pawnOneWhite.checkIfValidMove(4, 3, 2, 3);
		
		assertEquals(false, checkIfValidMove);

	}
	
	/**
	 * Check if diagonal move to capture another piece works
	 */
	@Test
	public void testDiagonalMoveToCapturePiece() throws CheckMateException, StaleMateException{
		board = new Board(8,8);

		/*Move newMoveTwo = new Move(1, 3, 3, 3, "BLACK");
		newMoveTwo.movePiece(true);
		Move newMoveOne = new Move(6, 4, 4, 4, PieceColor.WHITE);
		newMoveOne.movePiece(true);
		Move newMoveThree = new Move(1, 0, 2, 0, "BLACK");
		newMoveThree.movePiece(true);
		
		Move newMoveFour = new Move(4, 4, 3, 3, PieceColor.WHITE);
		boolean checkValid = newMoveFour.movePiece(true);*/
		
		board.movePiece(6, 4, 4, 4);
		board.movePiece(1, 3, 3, 3);
		board.movePiece(4, 4, 3, 3);
		
		
		Piece newPawn = board.getPiece(3,3);
		assertEquals(PieceColor.WHITE, newPawn.getColor());
		
	}
	
	/**
	 * Test that getPossibleMoves() returns all the valid moves from a given position
	 * @throws StaleMateException 
	 * @throws CheckMateException
	 */
	@Test
	public void testGetPossibleMoves() throws CheckMateException, StaleMateException {
		board = new Board(8, 8);
		
		//Move white and black pawns 2 spaces forward
		// Move newMoveOne = new Move(6, 7, 4, 7, PieceColor.WHITE);
		// newMoveOne.movePiece(false);
		board.movePiece(6, 3, 4, 3);
		board.movePiece(1, 2, 3, 2);
		
		Piece pawnWhite = board.getPiece(4, 3);
		board.printBoard();
		List<Move> possibleMovesExpected = new ArrayList<Move>();
		possibleMovesExpected.add(new Move(4, 3, 3, 3, PieceColor.WHITE));
		possibleMovesExpected.add(new Move(4, 3, 3, 2, PieceColor.WHITE));
		possibleMovesExpected.add(new Move(4, 3, 2, 3, PieceColor.WHITE));
		
		List<Move> actualMoves = pawnWhite.getPossibleMoves(4, 3);
		for(Move move : actualMoves) {
			System.out.println(move.toString());
		}
		assertEquals(true, CommonFunctions.checkIfMoveListsEqual(possibleMovesExpected, actualMoves));
	}
}
