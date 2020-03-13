package Tests;

import static org.junit.Assert.*;

import java.util.*;
import Chess.CheckMateException;
import Chess.CommonFunctions;
import Chess.Board;
import Chess.Game;
import Chess.Move;
import Chess.StaleMateException;
import Piece.Piece;
import Piece.PieceType;
import Piece.Princess;
import Piece.PieceColor;
import org.junit.Test;

public class PrincessTest {
	
	Board board = new Board(8, 8);
	@Test
	/**
	 * Test that the Knight Constructor is valid
	 * @throws Exception
	 */
	public void testValidConstructor() {
		PieceColor pieceColor = PieceColor.BLACK;
		PieceType pieceType = PieceType.PRINCESS;
		Piece princess = new Princess(pieceColor, pieceType);
		assertEquals(pieceColor, princess.getColor());
		assertEquals(pieceType, princess.getPieceType());
	}
	
	/**
	 * Test that a valid move is actually considered valid
	 * @throws Exception
	 */
	@Test
	public void testValidMoves() {
		Piece princess = new Princess(PieceColor.BLACK, PieceType.PRINCESS);
		
		boolean isValidOneKnight = princess.checkIfValidMove(3, 3, 2, 5);
		boolean isValidTwoKnight = princess.checkIfValidMove(3, 3, 2, 1);
		boolean isValidThreeKnight = princess.checkIfValidMove(3, 3, 5, 2);
		
		boolean isValidOneDiagonal = princess.checkIfValidMove(3, 3, 2, 4);
		boolean isValidTwoDiagonal = princess.checkIfValidMove(3, 3, 5, 5);
		boolean isValidThreeDiagonal = princess.checkIfValidMove(3, 3, 5, 1);
		
		assertEquals(true, isValidOneKnight);
		assertEquals(true, isValidTwoKnight);
		assertEquals(true, isValidThreeKnight);
		assertEquals(true, isValidOneDiagonal);
		assertEquals(true, isValidTwoDiagonal);
		assertEquals(true, isValidThreeDiagonal);
		
	}
	
	/**
	 * Test that an invalid move is actually considered invalid
	 * 
	 */
	@Test
	public void testInvalidMoves() {	
		Piece princess = new Princess(PieceColor.BLACK, PieceType.PRINCESS);
		
		boolean notValidOne = princess.checkIfValidMove(3, 3, 5, 3);
		boolean notValidTwo = princess.checkIfValidMove(3, 3, 2, 3);
		boolean notValidThree = princess.checkIfValidMove(3, 3, 4, 3);
		boolean notValidFour = princess.checkIfValidMove(3, 3, 3, 0);
		boolean notValidFive = princess.checkIfValidMove(3, 3, 3, 7);
		boolean notValidSix = princess.checkIfValidMove(3, 3, 7, 3);
		
		assertEquals(false, notValidOne);
		assertEquals(false, notValidTwo);
		assertEquals(false, notValidThree);
		assertEquals(false, notValidFour);
		assertEquals(false, notValidFive);
		assertEquals(false, notValidSix);
	
	}
	
	/**
	 * Test that getPossibleMoves() returns all the valid moves from a given position
	 * @throws StaleMateException 
	 * @throws CheckMateException 
	 */
	@Test
	public void testGetPossibleMoves() throws CheckMateException, StaleMateException {
		
		Board board = Game.getCurrentBoardState();
		Piece princess = new Princess(PieceColor.BLACK, PieceType.PRINCESS);
		board.getSquare(3, 3).setPieceAtSquare(princess);
		princess.setCurrentPosition(board.getSquare(3,3));
		
		List<Move> possibleMovesExpected = new ArrayList<Move>();
		possibleMovesExpected.add(new Move(3,3,2,4,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,2,2,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,4,4,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,5,5,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,6,6,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,4,2,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,5,1,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,6,0,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,4,5,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,2,5,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,2,1,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,4,1,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,5,4,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,5,2,PieceColor.BLACK));
		
		List<Move> actualMoves = princess.getPossibleMoves(3, 3);
		assertEquals(true, CommonFunctions.checkIfMoveListsEqual(possibleMovesExpected, actualMoves));
	}
}
