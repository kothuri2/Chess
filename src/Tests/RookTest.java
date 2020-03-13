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
import Piece.Rook;
import Piece.PieceColor;
import org.junit.Test;

public class RookTest {
	
	//Board for this test
	Board board = new Board(8,8);
	
	@Test
	/**
	 * Test that the Rook Constructor is valid
	 * @throws Exception
	 */
	public void testValidConstructor() {
		PieceColor pieceColor = PieceColor.BLACK;
		PieceType pieceType = PieceType.ROOK;
		Piece rook = new Rook(pieceColor, pieceType);
		assertEquals(pieceColor, rook.getColor());
		assertEquals(pieceType, rook.getPieceType());
	}
	
	/**
	 * Test that a valid Vertical move is actually considered valid
	 * @throws StaleMateException 
	 * @throws CheckMateException 
	 * @throws InvalidMoveException 
	 * @throws Exception
	 */
	@Test
	public void testValidMoveVertical() throws CheckMateException, StaleMateException {
	
		board = new Board(8, 8);
		Piece rookOneWhite = board.getPiece(7, 7);
		
		//Move white and black pawns 2 spaces forward
		Move newMoveOne = new Move(6, 7, 4, 7, PieceColor.WHITE);
		newMoveOne.checkValidMove(true);

		boolean isValidOneWhite = rookOneWhite.checkIfValidMove(7, 7, 5, 7);
		
		assertEquals(true, isValidOneWhite);
		
	}
	
	/**
	 * Test that a valid horizontal move is actually considered valid
	 * @throws CheckMateException
	 * @throws StaleMateException
	 * @throws InvalidMoveException 
	 */
	@Test
	public void testValidMoveHorizontal() throws CheckMateException, StaleMateException {
		
		board = new Board(8, 8);
		
		Piece rookOneWhite = board.getPiece(7,7);
		//Move knight out
		Move newMoveOne = new Move(7, 6, 5, 5, PieceColor.WHITE);
		newMoveOne.checkValidMove(true);
		
		boolean isValidMove = rookOneWhite.checkIfValidMove(7, 7, 7, 6);
		
		assertEquals(true, isValidMove);
		
	}
	
	/**
	 * Test that an invalid move is actually considered invalid
	 * 
	 */
	@Test
	public void testInvalidMoves() {
		board = new Board(8, 8);
		
		Piece rookWhite = board.getPiece(7, 7);
		Piece rookBlack = board.getPiece(0, 7);
		
		boolean isValidOneWhite = rookWhite.checkIfValidMove(7, 7, 5, 4);
		boolean isValidOneBlack = rookBlack.checkIfValidMove(0, 7, 10, 5);

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
		
		board.movePiece(6, 7, 4, 7);
		
		Piece rookWhite = board.getPiece(7, 7);
		
		List<Move> possibleMovesExpected = new ArrayList<Move>();
		possibleMovesExpected.add(new Move(7, 7, 6, 7, PieceColor.WHITE));
		possibleMovesExpected.add(new Move(7, 7, 5, 7, PieceColor.WHITE));
		
		List<Move> actualMoves = rookWhite.getPossibleMoves(7, 7);
		assertEquals(true, CommonFunctions.checkIfMoveListsEqual(possibleMovesExpected, actualMoves));
	}
}
