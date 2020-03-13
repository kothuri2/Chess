package Tests;

import static org.junit.Assert.*;

import java.util.*;
import Chess.CheckMateException;
import Chess.CommonFunctions;
import Chess.Board;
import Chess.Move;
import Chess.StaleMateException;
import Piece.Piece;
import Piece.PieceType;
import Piece.Queen;
import Piece.PieceColor;
import org.junit.Test;

public class QueenTest {
	
	//Board for this test
	Board board = new Board(8,8);
	
	@Test
	/**
	 * Test that the Queen Constructor is valid
	 * @throws Exception
	 */
	public void testValidConstructor() {
		PieceColor pieceColor = PieceColor.BLACK;
		PieceType pieceType = PieceType.QUEEN;
		Piece bishop = new Queen(pieceColor, pieceType);
		assertEquals(pieceColor, bishop.getColor());
		assertEquals(pieceType, bishop.getPieceType());
	}
	
	/**
	 * Test that a valid vertical Queen move is actually considered valid
	 * @throws StaleMateException 
	 * @throws CheckMateException 
	 * @throws InvalidMoveException 
	 * @throws Exception
	 */
	@Test
	public void testValidVerticalMove() throws CheckMateException, StaleMateException {
		Board board = new Board(8, 8);
		Piece queenOneWhite = board.getPiece(7, 3);
		if(! (queenOneWhite instanceof Queen)) {
			fail("Piece was not a queen");
		}
		//Move the white pawn 2 spaces forward
		Move newMoveOne = new Move(6, 3, 4, 3, PieceColor.WHITE);
		newMoveOne.checkValidMove(true);
		Move newMoveTwo = new Move(1, 4, 3, 4, PieceColor.BLACK);
		newMoveTwo.checkValidMove(true);
		
		boolean isValidOneWhite = queenOneWhite.checkIfValidMove(7, 3, 5, 3);
		
		assertEquals(true, isValidOneWhite);
		
	}
	
	/**
	 * Test that a valid diagonal Queen move is actually considered valid
	 * @throws StaleMateException 
	 * @throws CheckMateException 
	 * @throws Exception
	 */
	@Test
	public void testValidDiagonalMove() throws CheckMateException, StaleMateException {
		Board board = new Board(8, 8);
		Piece queenOneWhite = board.getPiece(7, 3);
		if(! (queenOneWhite instanceof Queen)) {
			fail("Piece was not a queen");
		}
		//Move the white pawn 2 spaces forward
		board.movePiece(6, 3, 4, 3);
		board.movePiece(6, 4, 4, 4);
		
		boolean isValidOneWhite = queenOneWhite.checkIfValidMove(7, 3, 4, 6);
		
		assertEquals(true, isValidOneWhite);
		
	}
	
	/**
	 * Test that a valid horizontal Queen move is actually considered valid
	 * @throws StaleMateException 
	 * @throws CheckMateException 
	 * @throws Exception
	 */
	@Test
	public void testValidHorizontalMove() throws CheckMateException, StaleMateException {
		Board board = new Board(8, 8);
		Piece queenOneWhite = board.getPiece(7, 3);
		if(! (queenOneWhite instanceof Queen)) {
			fail("Piece was not a queen");
		}
		//Move the white knight out
		board.movePiece(7, 4, 5, 5);
		
		boolean isValidOneWhite = queenOneWhite.checkIfValidMove(7, 3, 7, 4);
		
		assertEquals(true, isValidOneWhite);
		
	}
	
	/**
	 * Test that an invalid move is actually considered invalid
	 * 
	 */
	@Test
	public void testInvalidMove() {
		board = new Board(8, 8);
		
		Piece queenOneWhite = board.getPiece(7, 3);
		
		boolean isValidOneWhite = queenOneWhite.checkIfValidMove(7, 3, 5, 4);

		assertEquals(false, isValidOneWhite);

	}
	
	/**
	 * Test that getPossibleMoves() returns all the valid moves from a given position
	 * @throws StaleMateException 
	 * @throws CheckMateException
	 */
	@Test
	public void testGetPossibleMoves() throws CheckMateException, StaleMateException {
		board = new Board(8, 8);
		
		Piece queenOneWhite = board.getPiece(7, 3);
		if(! (queenOneWhite instanceof Queen)) {
			fail("Piece was not a queen");
		}
		//Move 3 white pawns 2 spaces forward
		board.movePiece(6, 2, 4, 2);
		board.movePiece(6, 3, 4, 3);
		board.movePiece(6, 4, 4, 4);
		
		List<Move> possibleMovesExpected = new ArrayList<Move>();
		possibleMovesExpected.add(new Move(7, 3, 6, 3, PieceColor.WHITE));
		possibleMovesExpected.add(new Move(7, 3, 5, 3, PieceColor.WHITE));
		possibleMovesExpected.add(new Move(7, 3, 6, 4, PieceColor.WHITE));
		possibleMovesExpected.add(new Move(7, 3, 5, 5, PieceColor.WHITE));
		possibleMovesExpected.add(new Move(7, 3, 4, 6, PieceColor.WHITE));
		possibleMovesExpected.add(new Move(7, 3, 3, 7, PieceColor.WHITE));
		possibleMovesExpected.add(new Move(7, 3, 6, 2, PieceColor.WHITE));
		possibleMovesExpected.add(new Move(7, 3, 5, 1, PieceColor.WHITE));
		possibleMovesExpected.add(new Move(7, 3, 4, 0, PieceColor.WHITE));
		
		List<Move> actualMoves = queenOneWhite.getPossibleMoves(7, 3);
		assertEquals(true, CommonFunctions.checkIfMoveListsEqual(possibleMovesExpected, actualMoves));
	}
}
