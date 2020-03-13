package Tests;

import static org.junit.Assert.*;
import Piece.PieceColor;
import Chess.GameEndingConditions;
import Chess.Board;
import Chess.Move;
import Chess.CheckMateException;
import Chess.Square;
import Piece.Piece;
import Piece.Pawn;
import Chess.StaleMateException;

import org.junit.Test;

public class BoardTest {

	/**
	 * Valid Constructor test for board to ensure that there are 32 pieces on board
	 * that are initialized and 32 null pieces.
	 */
	@Test
	public void testValidConstructor() {
		Board board = new Board(8, 8);
		int pieceCount = 0;
		int nullCount = 0;
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				Square tempSquare = board.getSquare(row, col);
				if(tempSquare != null  && tempSquare.getPieceAtSquare() != null) {
					pieceCount += 1;
				} else {
					nullCount += 1;
				}
			}
		}
		assertEquals(32, pieceCount);
		assertEquals(32, nullCount);
		assertEquals(PieceColor.WHITE, board.getCurrentTurn());
		
	}
	
	/**
	 * Test that a piece (pawn) can be successfully moved on the board
	 * @throws StaleMateException 
	 * @throws CheckMateException 
	 */
	@Test
	public void testSuccessfulMove() throws CheckMateException, StaleMateException {
		Board board = new Board(8, 8);
		board.movePiece(6, 3, 4, 3);
		Piece pieceMoved = board.getPiece(4, 3);
		if(pieceMoved instanceof Pawn && pieceMoved.getColor() == PieceColor.WHITE) {
			assert(true);
		} else {
			assert(false);
		}
	}
	
	/**
	 * Test Simple check
	 * @throws StaleMateException 
	 * @throws CheckMateException 
	 */
	@Test
	public void testSuccessfulCheck() throws CheckMateException, StaleMateException {
		Board board = new Board(8, 8);
		
		board.movePiece(6, 4, 4, 4);
		board.movePiece(1, 5, 2, 5);
		board.movePiece(7, 3, 3, 7);
		board.toggleTurn();
		board.printBoard();
		
		boolean valid = GameEndingConditions.checkIfKingIsInCheck(board, true);
		assertEquals(true, valid);
		
	}
	
	/**
	 * Test Simple not check
	 * @throws StaleMateException 
	 * @throws CheckMateException 
	 */
	@Test
	public void testUnsuccessfulCheck() throws CheckMateException, StaleMateException {
		Board board = new Board(8, 8);
		
		board.movePiece(6, 4, 4, 4);
		board.movePiece(1, 5, 2, 5);
		board.movePiece(7, 3, 5, 5);
		
		board.toggleTurn();
		boolean valid = GameEndingConditions.checkIfKingIsInCheck(board, true);
		assertEquals(false, valid);
		
	}
	
	/**
	 * Test fools checkMate (simple checkmate)
	 *    0   1   2   3   4   5   6   7   
		0 |BR||BN||BB||  ||BK||BB||BN||BR|
		1 |BP||BP||BP||BP||  ||BP||BP||BP|
		2 |  ||  ||  ||  ||  ||  ||  ||  |
		3 |  ||  ||  ||  ||BP||  ||  ||  |
		4 |  ||  ||  ||  ||  ||  ||WP||BQ|
		5 |  ||  ||  ||  ||  ||WP||  ||  |
		6 |WP||WP||WP||WP||WP||  ||  ||WP|
		7 |WR||WN||WB||WQ||WK||WB||WN||WR|
	 */
	@Test
	public void testSimpleCheckMate() throws CheckMateException, StaleMateException {
		Board board = new Board(8, 8);
		
		Move moveOne = new Move(6, 5, 5, 5, PieceColor.WHITE);
		moveOne.checkValidMove(true);
		Move moveTwo = new Move(1, 4, 3, 4, PieceColor.BLACK);
		moveTwo.checkValidMove(true);
		Move moveThree = new Move(6, 6, 4, 6, PieceColor.WHITE);
		moveThree.checkValidMove(true);
		Move moveFour = new Move(0, 3, 4, 7, PieceColor.BLACK);
		moveFour.checkValidMove(true);
		
		board.toggleTurn();
		boolean checkMate = GameEndingConditions.checkForStaleMateCheckMate(board, true);
		assertEquals(true, checkMate);
		
	}
	
	/**
	 * Test simple not in Checkmate
	 * @throws StaleMateException 
	 * @throws CheckMateException 
	 */
	@Test
	public void testNotInCheckMate() throws CheckMateException, StaleMateException {
		Board board = new Board(8, 8);
		
		board.movePiece(6, 3, 4, 3);
		board.toggleTurn();
		boolean checkMate = GameEndingConditions.checkForStaleMateCheckMate(board, true);
		assertEquals(false, checkMate);
	}
	
	/**
	 * Test Stalemate in board.
	 * Fastest Stalemate: https://www.chess.com/forum/view/fun-with-chess/fastest-stalemate
	 * 0   1   2   3   4   5   6   7   
	0 |  ||  ||  ||  ||  ||BB||BN||BR|
	1 |  ||  ||  ||  ||BP||  ||BP||BQ|
	2 |  ||  ||  ||  ||WQ||BP||BK||BR|
	3 |  ||  ||  ||  ||  ||  ||  ||BP|
	4 |  ||  ||WP||  ||  ||  ||  ||WP|
	5 |  ||  ||  ||  ||  ||  ||  ||  |
	6 |WP||WP||  ||WP||WP||WP||WP||  |
	7 |WR||WN||WB||  ||WK||WB||WN||WR|
	 */
	@Test(expected = StaleMateException.class)
	public void testStaleMate() throws CheckMateException, StaleMateException {
		Board board = new Board(8, 8);
		
		board.movePiece(6, 2, 4, 2);
		board.movePiece(1, 7, 3, 7);
		board.movePiece(6, 7, 4, 7);
		board.movePiece(1, 0, 3, 0);
		board.movePiece(7, 3, 4, 0);
		board.movePiece(0, 0, 2, 0);
		board.movePiece(4, 0, 3, 0);
		board.movePiece(2, 0, 2, 7);
		board.movePiece(3, 0, 1, 2);
		board.movePiece(1, 5, 2, 5);
		board.movePiece(1, 2, 1, 3);
		board.movePiece(0, 4, 1, 5);
		board.movePiece(1, 3, 1, 1);
		board.movePiece(0, 3, 5, 3);
		board.movePiece(1, 1, 0, 1);
		board.movePiece(5, 3, 1, 7);
		board.movePiece(0, 1, 0, 2);
		board.movePiece(1, 5, 2, 6);
		board.movePiece(0, 2, 2, 4);
		
		boolean staleMate = GameEndingConditions.checkForStaleMateCheckMate(board, false);
		assertEquals(true, staleMate);
		
	}
	
	/**
	 * Test that moving 4 pieces to locations
	 * and then undoing all 4 moves results in the board being in the initial
	 * configuration board state
	 * @throws CheckMateException
	 * @throws StaleMateException
	 */
	@Test
	public void testUndoMove() throws CheckMateException, StaleMateException {
		Board board = new Board(8, 8);
		
		Board beforeUndo = board;
		
		board.movePiece(6, 2, 4, 2);
		board.movePiece(1, 7, 3, 7);
		board.movePiece(6, 7, 4, 7);
		board.movePiece(1, 0, 3, 0);
		
		board.undoMoveOnBoard();
		board.undoMoveOnBoard();
		board.undoMoveOnBoard();
		board.undoMoveOnBoard();
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				Piece beforeUndoPiece = beforeUndo.getPiece(i, j);
				Piece currentPiece = board.getPiece(i, j);
				if(beforeUndoPiece != null && currentPiece != null) {
					assertEquals(beforeUndo.getPiece(i, j).getColor(), board.getPiece(i, j).getColor());
					assertEquals(beforeUndo.getPiece(i, j).getPieceType(), board.getPiece(i, j).getPieceType());
				}
			}
		}
	}
}
