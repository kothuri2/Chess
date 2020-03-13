package Tests;

import static org.junit.Assert.*;
import org.junit.Test;

import Chess.CheckMateException;
import Chess.Move;
import Chess.Board;
import Piece.Piece;
import Piece.PieceColor;
import Piece.PieceType;
import Chess.StaleMateException;

public class MoveTest {
	
	Board board = new Board(8,8);
	/**
	 * Test Move Class Constructor
	 */
	@Test
	public void testValidConstructor() {
		int startX = 4;
		int startY = 10;
		int endX = 2;
		int endY = 6;
		PieceColor color = PieceColor.BLACK;
		Move newMove = new Move(startX, startY, endX, endY, color);
		assertEquals(startX, newMove.getStartX());
		assertEquals(startY, newMove.getStartY());
		assertEquals(endX, newMove.getEndX());
		assertEquals(endY, newMove.getEndY());
		assertEquals(color, newMove.getPieceColor());
	}
	
	/**
	 * Test Out of Bounds Move
	 */
	@Test
	public void testOutOfBoundsMove() {
		int coordinate1 = 4;
		int coordinate2 = 10;
		int coordinate3 = 2;
		int coordinate4 = -3;
		Move newMove = new Move(coordinate1, coordinate2, coordinate3, coordinate4, PieceColor.WHITE);
		assertEquals(false, newMove.checkForOutOfBounds(coordinate4, coordinate2));
		assertEquals(false, newMove.checkForOutOfBounds(coordinate2, coordinate1));
		assertEquals(true, newMove.checkForOutOfBounds(coordinate3, coordinate1));
	}
	
	/**
	 * Test that the team that is supposed to move actually moved
	 * @throws StaleMateException 
	 * @throws CheckMateException 
	 * @throws InvalidMoveException 
	 */
	@Test
	public void testTeamUsedTurn() throws CheckMateException, StaleMateException {
		board = new Board(8, 8);
		Move newMove = new Move(1, 2, 3, 2, PieceColor.BLACK);
		newMove.checkValidMove(true);
		Move newMove2 = new Move(1, 3, 3, 3, PieceColor.BLACK);
		newMove2.checkValidMove(true);

		// if checkPiece is null, then that means no piece is at that square
		// which means the last move was invalid b/c the same team moved twice
		Piece checkPiece = board.getPiece(3, 3);
		
		assertEquals(null, checkPiece);
	}
	
	/**
	 * Check that a team doesn't move to a location where
	 * his own piece is on
	 * @throws StaleMateException 
	 * @throws CheckMateException 
	 */
	@Test
	public void testEndLocationIsNotOwnTeam() throws CheckMateException, StaleMateException {
		board = new Board(8, 8);
		
		Move newMove = new Move(0, 0, 1, 0, PieceColor.BLACK);
		boolean validMove = newMove.checkEndLocationIsNotOwnTeam();
		assertEquals(false, validMove);
	
	}
	
	/**
	 * Test that piece has been captured when in a situation
	 * that it can be captured
	 * @throws StaleMateException 
	 * @throws CheckMateException 
	 */
	@Test
	public void testPieceCanBeCaptured() throws CheckMateException, StaleMateException {
		board = new Board(8, 8);
		
		board.movePiece(6, 3, 4, 3);
		board.movePiece(1, 2, 3, 2);
		board.movePiece(4, 3, 3, 2);
		
		Piece piece = board.getPiece(3, 2);
		Piece capturedPiece = board.getOtherTeamPieceSet(PieceColor.BLACK).getCapturedPieces().get(0);
		assertEquals(PieceColor.BLACK, capturedPiece.getColor());
		assertEquals(PieceType.PAWN, capturedPiece.getPieceType());
		assertEquals(PieceColor.WHITE ,piece.getColor());
		assertEquals(PieceType.PAWN, piece.getPieceType());
	}
}
