package Tests;

import static org.junit.Assert.*;

import java.util.*;
import Chess.CheckMateException;
import Chess.CommonFunctions;
import Chess.Board;
import Chess.Move;
import Chess.StaleMateException;
import Piece.Piece;
import Piece.Knight;
import Piece.PieceColor;
import Piece.PieceType;

import org.junit.Test;

public class KnightTest {
	
	Board board = new Board(8, 8);
	@Test
	/**
	 * Test that the Knight Constructor is valid
	 * @throws Exception
	 */
	public void testValidConstructor() {
		PieceColor pieceColor = PieceColor.BLACK;
		PieceType pieceType = PieceType.KNIGHT;
		Piece knight = new Knight(pieceColor, pieceType);
		assertEquals(pieceColor, knight.getColor());
		assertEquals(pieceType, knight.getPieceType());
	}
	
	/**
	 * Test that a valid move is actually considered valid
	 * @throws Exception
	 */
	@Test
	public void testValidMoves() {
		Piece knightOneWhite = board.getPiece(7, 1);
		Piece knightTwoWhite = board.getPiece(7, 6);
		Piece knightOneBlack = board.getPiece(0, 1);
		Piece knightTwoBlack = board.getPiece(0, 6);
		
		boolean isValidOneWhite = knightOneWhite.checkIfValidMove(7, 1, 5, 2);
		boolean isValidTwoWhite = knightTwoWhite.checkIfValidMove(7, 6, 5, 5);
		boolean isValidOneBlack = knightOneBlack.checkIfValidMove(0, 1, 2, 0);
		boolean isValidTwoBlack = knightTwoBlack.checkIfValidMove(0, 6, 2, 7);
		
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
		
		Piece knightOneWhite = board.getPiece(7, 1);
		Piece knightTwoWhite = board.getPiece(7, 6);
		Piece knightOneBlack = board.getPiece(0, 1);
		Piece knightTwoBlack = board.getPiece(0, 6);
		
		boolean isValidOneWhite = knightOneWhite.checkIfValidMove(7, 1, 2, 5);
		boolean isValidTwoWhite = knightTwoWhite.checkIfValidMove(7, 6, 8, 5);
		boolean isValidOneBlack = knightOneBlack.checkIfValidMove(0, 1, 0, 2);
		boolean isValidTwoBlack = knightTwoBlack.checkIfValidMove(0, 6, 7, 2);
		
		assertEquals(false, isValidOneWhite);
		assertEquals(false, isValidTwoWhite);
		assertEquals(false, isValidOneBlack);
		assertEquals(false, isValidTwoBlack);
	}
	
	/**
	 * Test that getPossibleMoves() returns all the valid moves from a given position
	 * @throws StaleMateException 
	 * @throws CheckMateException 
	 * @throws InvalidMoveException 
	 */
	@Test
	public void testGetPossibleMoves() throws CheckMateException, StaleMateException {
		// Move 2 pawns
		Move newMove = new Move(6, 3, 4, 3, PieceColor.WHITE);
		newMove.checkValidMove(false);
		Move newMove2 = new Move(6, 4, 4, 4, PieceColor.WHITE);
		newMove2.checkValidMove(false);
		Piece whiteKnightOne = board.getPiece(7, 6);
		
		List<Move> possibleMovesExpected = new ArrayList<Move>();
		possibleMovesExpected.add(new Move(7, 6, 5, 5, PieceColor.WHITE));
		possibleMovesExpected.add(new Move(7, 6, 5, 7, PieceColor.WHITE));
		
		List<Move> actualMoves = whiteKnightOne.getPossibleMoves(7, 6);
		assertEquals(true, CommonFunctions.checkIfMoveListsEqual(possibleMovesExpected, actualMoves));
	}
}
