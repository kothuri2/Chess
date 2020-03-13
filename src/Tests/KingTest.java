package Tests;

import static org.junit.Assert.*;

import java.util.*;
import Chess.CheckMateException;
import Chess.CommonFunctions;
import Chess.Board;
import Chess.Move;
import Chess.StaleMateException;
import Piece.Piece;
import Piece.King;
import Piece.PieceColor;
import Piece.PieceType;

import org.junit.Test;

public class KingTest {
	
	//Board for this test
	Board board = new Board(8,8);
	
	@Test
	/**
	 * Test that the Knight Constructor is valid
	 * @throws Exception
	 */
	public void testValidConstructor() {
		PieceColor pieceColor = PieceColor.BLACK;
		PieceType pieceType = PieceType.KING;
		Piece king = new King(pieceColor, pieceType);
		assertEquals(pieceColor, king.getColor());
		assertEquals(pieceType, king.getPieceType());
	}
	
	/**
	 * Test that a valid move is actually considered valid
	 * @throws StaleMateException 
	 * @throws CheckMateException 
	 * @throws InvalidMoveException 
	 * @throws Exception
	 */
	@Test
	public void testValidMoves() throws CheckMateException, StaleMateException {
		Piece kingOneWhite = board.getPiece(0, 4);
		Piece kingTwoBlack = board.getPiece(7, 4);
		
		Move newMoveOne = new Move(6, 4, 3, 4, PieceColor.WHITE);
		newMoveOne.checkValidMove(false);
		Move newMoveTwo = new Move(1, 4, 3, 4, PieceColor.BLACK);
		newMoveTwo.checkValidMove(false);
		
		boolean isValidOneWhite = kingOneWhite.checkIfValidMove(0, 4, 1, 4);
		boolean isValidTwoWhite = kingOneWhite.checkIfValidMove(1, 4, 2, 5);
		boolean isValidOneBlack = kingTwoBlack.checkIfValidMove(7, 4, 6, 4);
		boolean isValidTwoBlack = kingTwoBlack.checkIfValidMove(6, 4, 5, 5);
		
		assertEquals(true, isValidOneWhite);
		assertEquals(true, isValidTwoWhite);
		assertEquals(true, isValidOneBlack);
		assertEquals(true, isValidTwoBlack);
		
	}
	
	/**
	 * Test that an invalid move is actually considered invalid
	 * 
	 */
	@Test
	public void testInvalidMoves() {
		board = new Board(8, 8);
		
		Piece kingWhite = board.getPiece(0, 4);
		Piece kingBlack = board.getPiece(7, 4);
		
		boolean isValidOneWhite = kingWhite.checkIfValidMove(0, 4, 5, 4);
		boolean isValidOneBlack = kingBlack.checkIfValidMove(7, 4, 10, 5);

		assertEquals(false, isValidOneWhite);
		assertEquals(false, isValidOneBlack);

	}
	
	/**
	 * Test that getPossibleMoves() returns all the valid moves from a given position
	 * @throws StaleMateException 
	 * @throws CheckMateException 
	 */
	@Test
	public void testGetPossibleMoves() throws CheckMateException, StaleMateException {
		board = new Board(8, 8);

		// Move 4 pawns and a queen
		board.movePiece(6, 4, 3, 4);
		board.movePiece(1, 4, 3, 4);
		board.movePiece(6,  3,  4,  3);
		board.movePiece(1, 2, 2, 2);
		board.movePiece(7, 3, 3, 7);

		Piece whiteKing = board.getPiece(7, 4);
		
		List<Move> possibleMovesExpected = new ArrayList<Move>();
		possibleMovesExpected.add(new Move(7, 4, 6, 4, PieceColor.WHITE));
		possibleMovesExpected.add(new Move(7, 4, 7, 3, PieceColor.WHITE));
		possibleMovesExpected.add(new Move(7, 4, 6, 3, PieceColor.WHITE));
		
		List<Move> actualMoves = whiteKing.getPossibleMoves(7, 4);
		assertEquals(true, CommonFunctions.checkIfMoveListsEqual(possibleMovesExpected, actualMoves));
	}
}
