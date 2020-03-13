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
import Piece.Empress;
import Piece.PieceColor;
import Piece.PieceType;

import org.junit.Test;

public class EmpressTest {
	
	Board board = new Board(8, 8);
	@Test
	/**
	 * Test that the Knight Constructor is valid
	 * @throws Exception
	 */
	public void testValidConstructor() {
		PieceColor pieceColor = PieceColor.BLACK;
		PieceType pieceType = PieceType.EMPRESS;
		Piece empress = new Empress(pieceColor, pieceType);
		assertEquals(pieceColor, empress.getColor());
		assertEquals(pieceType, empress.getPieceType());
	}
	
	/**
	 * Test that a valid move is actually considered valid
	 * @throws Exception
	 */
	@Test
	public void testValidMoves() {
		Piece empress = new Empress(PieceColor.BLACK, PieceType.EMPRESS);
		
		boolean isValidOneKnight = empress.checkIfValidMove(3, 3, 2, 5);
		boolean isValidTwoKnight = empress.checkIfValidMove(3, 3, 2, 1);
		boolean isValidThreeKnight = empress.checkIfValidMove(3, 3, 5, 2);
		
		boolean isValidOneVertical = empress.checkIfValidMove(3, 3, 5, 3);
		boolean isValidOneHorizontal = empress.checkIfValidMove(3, 3, 2, 3);
		boolean isValidTwoVertical = empress.checkIfValidMove(3, 3, 4, 3);
		boolean isValidTwoHorizontal = empress.checkIfValidMove(3, 3, 3, 0);
		boolean isValidThreeHorizontal = empress.checkIfValidMove(3, 3, 3, 7);
		
		assertEquals(true, isValidOneKnight);
		assertEquals(true, isValidTwoKnight);
		assertEquals(true, isValidThreeKnight);
		assertEquals(true, isValidOneHorizontal);
		assertEquals(true, isValidTwoHorizontal);
		assertEquals(true, isValidOneVertical);
		assertEquals(true, isValidThreeHorizontal);
		assertEquals(true, isValidTwoVertical);
		
	}
	
	/**
	 * Test that an invalid move is actually considered invalid
	 * 
	 */
	@Test
	public void testInvalidMoves() {	
		Piece empress = new Empress(PieceColor.BLACK, PieceType.EMPRESS);
		
		boolean notValidOneDiagonal = empress.checkIfValidMove(3, 3, 2, 4);
		boolean notValidTwoDiagonal = empress.checkIfValidMove(3, 3, 5, 5);
		boolean notValidThreeDiagonal = empress.checkIfValidMove(3, 3, 5, 1);
		
		assertEquals(false, notValidOneDiagonal);
		assertEquals(false, notValidTwoDiagonal);
		assertEquals(false, notValidThreeDiagonal);
	
	}
	
	/**
	 * Test that getPossibleMoves() returns all the valid moves from a given position
	 * @throws StaleMateException 
	 * @throws CheckMateException 
	 */
	@Test
	public void testGetPossibleMoves() throws CheckMateException, StaleMateException {
		
		Board board = Game.getCurrentBoardState();
		Piece empress = new Empress(PieceColor.BLACK, PieceType.EMPRESS);
		board.getSquare(3, 3).setPieceAtSquare(empress);
		empress.setCurrentPosition(board.getSquare(3,3));
		
		List<Move> possibleMovesExpected = new ArrayList<Move>();
		possibleMovesExpected.add(new Move(3,3,4,3,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,5,3,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,6,3,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,3,4,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,3,5,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,3,6,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,3,7,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,2,3,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,3,2,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,3,1,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,3,0,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,4,5,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,2,5,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,2,1,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,4,1,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,5,4,PieceColor.BLACK));
		possibleMovesExpected.add(new Move(3,3,5,2,PieceColor.BLACK));
		
		List<Move> actualMoves = empress.getPossibleMoves(3, 3);
		assertEquals(true, CommonFunctions.checkIfMoveListsEqual(possibleMovesExpected, actualMoves));
	}
}
