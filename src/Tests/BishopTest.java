package Tests;

import static org.junit.Assert.*;

import java.util.*;
import Chess.CheckMateException;
import Chess.CommonFunctions;
import Chess.Board;
import Chess.Move;
import Chess.StaleMateException;
import Piece.Piece;
import Piece.Bishop;
import Piece.PieceColor;
import Piece.PieceType;
import org.junit.Test;

public class BishopTest {
	
	//Board for this test
	Board board = new Board(8,8);
	
	@Test
	/**
	 * Test that the Bishop Constructor is valid
	 * @throws Exception
	 */
	public void testValidConstructor() {
		PieceColor pieceColor = PieceColor.BLACK;
		PieceType pieceType = PieceType.BISHOP;
		Piece bishop = new Bishop(pieceColor, PieceType.BISHOP);
		assertEquals(pieceColor, bishop.getColor());
		assertEquals(pieceType, bishop.getPieceType());
	}
	
	/**
	 * Test that a valid move is actually considered valid
	 * @throws StaleMateException 
	 * @throws CheckMateException 
	 * @throws InvalidMoveException 
	 * @throws Exception
	 */
	public void testValidMoveDiagonal() throws CheckMateException, StaleMateException {

		Piece bishopOneWhite = board.getPiece(7, 5);
		
		//Move white pawn at (6, 6) two spaces forward
		Move newMoveOne = new Move(6, 6, 4, 6, PieceColor.WHITE);
		newMoveOne.checkValidMove(true);
		
		boolean isValidOneWhite = bishopOneWhite.checkIfValidMove(7, 5, 5, 7);
		
		assertEquals(true, isValidOneWhite);
		
	}
	
	/**
	 * Test that an invalid move is actually considered invalid
	 * 
	 */
	@Test
	public void testInvalidMove() {
		board = new Board(8, 8);
		
		Piece bishopWhite = board.getPiece(7, 5);
		Piece bishopBlack = board.getPiece(0, 5);
		
		boolean isValidOneWhite = bishopWhite.checkIfValidMove(7, 5, 5, 4);
		boolean isValidOneBlack = bishopBlack.checkIfValidMove(0, 5, 10, 5);

		assertEquals(false, isValidOneWhite);
		assertEquals(false, isValidOneBlack);

	}
	
	/**
	 * Test that getPossibleMoves() returns all the valid moves from a given position
	 * @throws StaleMateException 
	 * @throws CheckMateException
	 * @throws InvalidMoveException 
	 */
	@Test
	public void testGetPossibleMoves() throws CheckMateException, StaleMateException {
		board = new Board(8, 8);
		
		Piece bishopOneWhite = board.getPiece(7, 5);
		
		//Move white and black pawns 2 spaces forward
		Move newMoveOne = new Move(6, 5, 4, 5, PieceColor.WHITE);
		newMoveOne.checkValidMove(true);
		Move newMoveTwo = new Move(1, 5, 3, 5, PieceColor.BLACK);
		newMoveTwo.checkValidMove(true);
		Move newMoveThree = new Move(6, 6, 4, 6, PieceColor.WHITE);
		newMoveThree.checkValidMove(true);
		Move newMoveFour = new Move(1, 6, 3, 6, PieceColor.BLACK);
		newMoveFour.checkValidMove(true);
		
		List<Move> possibleMovesExpected = new ArrayList<Move>();
		possibleMovesExpected.add(new Move(7, 5, 6, 6, PieceColor.WHITE));
		possibleMovesExpected.add(new Move(7, 5, 5, 7, PieceColor.WHITE));
		
		List<Move> actualMoves = bishopOneWhite.getPossibleMoves(7, 5);
		assertEquals(true, CommonFunctions.checkIfMoveListsEqual(possibleMovesExpected, actualMoves));
	}
}
