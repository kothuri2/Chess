package Chess;

import java.util.HashMap;
import java.util.Stack;
import javax.swing.JOptionPane;
import Piece.Piece;
import Piece.PieceColor;

/**
 * Board class that is responsible for initializing the board by 
 * laying the pieces, moving pieces, checking for check, checking for checkmate,
 * and checking for stalemate
 * @author Radhir
 */
public class Board {
	
	/**
	 * Board Class member variables
	 */
	private static Square[][] board;
	private PieceSet[] pieceSets;
	private PieceColor currentTurn;
	private int boardWidth, boardLength;
	private static final int NUMBER_OF_PLAYERS = 2;
	private Stack<Move> allMovesMade;
	
	public Board(int boardWidth, int boardLength) {
		
		this.boardWidth = boardWidth;
		this.boardLength = boardLength;
		board = new Square[boardWidth][boardLength];
		allMovesMade = new Stack<Move>();

		// Initialize the board
		initializeBoard();
		// Create the pieces and players
		createPieceSets();
		// Set the pieces for the board
		setPiecesToBoard();
		// Assume White always goes first
		currentTurn = PieceColor.WHITE;
	}
	
	/**
	 * Move a piece across the board. Check if there is a piece in the destination cell.
	 * If so, remove that piece from that cell and add it to the list of pieces that have been captured
	 * for that team. Also check if the other team's king is in check. If so, then display that the other team's
	 * king in in check.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @throws CheckMateException 
	 */
	public boolean movePiece(int startX,int startY, int endX, int endY) throws CheckMateException, StaleMateException {
		
		Piece pieceToBeMoved = board[startX][startY].getPieceAtSquare();
		Piece pieceAtDestination = board[endX][endY].getPieceAtSquare();
		
		//Capture the piece at destination if it exists
		capturePieceIfExists(startX, startY, endX, endY, pieceToBeMoved, pieceAtDestination);
		//Move the piece that needs to be moved and update the location for the piece
		updateLocationOnBoard(startX, startY, endX, endY, pieceToBeMoved, pieceAtDestination);
		//Check if moving this piece puts own king in check. if so, undo the move and return
		if(!checkMovingPiecePutsOwnKingInCheck(startX, startY, endX, endY, pieceToBeMoved, pieceAtDestination)) {
			return false;
		}
		
		//Check if the opposing team is in check.
		//Also check if opposing team is in check, then check for checkmate
		try {
			checkOpposingTeamKingInCheck(startX, startY, endX, endY, pieceToBeMoved, pieceAtDestination);
		} catch (CheckMateException e) {
			throw new CheckMateException();
		}
		
		//Toggle the turn to the next time
		toggleTurn();
		
		//Check if team just switched to is in staleMate
		boolean staleMate = GameEndingConditions.checkForStaleMateCheckMate(this, false);
		if(staleMate) {
			JOptionPane.showMessageDialog(null, "THERE IS A STALEMATE!");
			throw new StaleMateException();
		}
		return true;
	}
	
	/**
	 * Add newMove to all moves made so far
	 * @param newMove
	 */
	public void addMove(Move newMove) {
		allMovesMade.push(newMove);
	}
	
	/**
	 * Undo move on board
	 */
	public Move undoMoveOnBoard() {
		if(allMovesMade.size() == 0) {
			return null;
		}
		Move mostRecentMove = allMovesMade.pop();
		int startX = mostRecentMove.getStartX();
		int startY = mostRecentMove.getStartY();
		int endX = mostRecentMove.getEndX();
		int endY = mostRecentMove.getEndY();
		Piece pieceAtStart = mostRecentMove.getPieceAtStart();
		Piece pieceAtDestination = mostRecentMove.getPieceAtDestination();
		if(mostRecentMove.getMarkPieceAsMovedOnThisMove()) {
			pieceAtStart.markPieceAsNotMoved();
		}
		undoMove(startX, startY, endX, endY, pieceAtStart, pieceAtDestination, true);
		currentTurn = getOtherTeamColor(getCurrentTurn());
		return mostRecentMove;
	}
	
	/**
	 * Get Square at row, col on board
	 */
	public Square getSquare(int row, int col) {
		return board[row][col];
	}
	
	/**
	 * Get the pieces that are being moved
	 * and the pieces at the destination if it exists
	 * Capture the piece at the destination and add it to the list of
	 * captured pieces for the piece that is moving.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param pieceToBeMoved
	 * @param pieceAtDestination
	 */
	public void capturePieceIfExists(int startX, int startY, int endX, int endY, Piece pieceToBeMoved, Piece pieceAtDestination) {
		if(pieceAtDestination != null) {
			if(pieceToBeMoved.getColor() == PieceColor.BLACK) {
				pieceSets[0].addCapturedPiece(pieceAtDestination);
				pieceSets[1].getPieces().remove(pieceAtDestination);
			} else {
				pieceSets[1].addCapturedPiece(pieceAtDestination);
				pieceSets[0].getPieces().remove(pieceAtDestination);
			}
		}
	}
	
	/**
	 * Actual moving of piece on board and update of square information
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param pieceToBeMoved
	 */
	public void updateLocationOnBoard(int startX, int startY, int endX, int endY, Piece pieceToBeMoved, Piece pieceAtDestination) {
		if(pieceAtDestination != null) {
			pieceAtDestination.setCurrentPosition(null);
		}
		pieceToBeMoved.setCurrentPosition(board[endX][endY]);
		board[endX][endY].setPieceAtSquare(pieceToBeMoved);
		board[startX][startY].setPieceAtSquare(null);
	}
	
	/**
	 * Check if moving piece puts own team's king in check
	 * If so, undo the move and then ask the player to retry
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param pieceToBeMoved
	 * @param pieceAtDestination
	 * @return
	 */
	public boolean checkMovingPiecePutsOwnKingInCheck(int startX, int startY, int endX, int endY, Piece pieceToBeMoved, Piece pieceAtDestination) {
		boolean ownTeamCheckStatus = GameEndingConditions.checkIfKingIsInCheck(this, false);
		if(ownTeamCheckStatus) {
			JOptionPane.showMessageDialog(null, "Moving this piece puts your own king in check");
			undoMove(startX, startY, endX, endY, pieceToBeMoved, pieceAtDestination, true);
			return false;
		}
		return true;
	}
	
	/**
	 * Check if after moving piece, then the opposing team is in check
	 * Also check for checkMate in this case for the opposing team's king
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param pieceToBeMoved
	 * @param pieceAtDestination
	 * @throws CheckMateException
	 */
	public void checkOpposingTeamKingInCheck(int startX, int startY, int endX, int endY, Piece pieceToBeMoved, Piece pieceAtDestination) throws CheckMateException {
		boolean opposingTeamCheckStatus = GameEndingConditions.checkIfKingIsInCheck(this, true);
		if(opposingTeamCheckStatus) {
			boolean checkMate = GameEndingConditions.checkForStaleMateCheckMate(this, true);
			if(checkMate) {
				JOptionPane.showMessageDialog(null, pieceToBeMoved.getColor() + " team wins!");
				throw new CheckMateException();
			}
			JOptionPane.showMessageDialog(null, getOtherTeamColor(pieceToBeMoved.getColor()) + "'s king is in Check!");
		}
	}
	
	/**
	 * Return the current piece set (current players turn)
	 */
	public PieceSet getCurrentPieceSet() {
		if(currentTurn == PieceColor.BLACK) {
			return pieceSets[0];
		}
		return pieceSets[1];
	}
	
	/**
	 * Reset the properties of the pieces and the board
	 * pieceToBeMoved - Set this piece's square to the initial startX, startY square
	 * pieceAtDestination - Set this piece's square to the final endX, endY square
	 * Remove the piece from the list of captured pieces and add it back to the
	 * appropriate piece set
	 * Set board[startX][startY] to the pieceToBeMoved
	 * Set board[endX][endY] to the pieceAtDestination
	 */
	public void undoMove(int startX, int startY, int endX, int endY, Piece pieceToBeMoved, Piece pieceAtDestination, boolean actualMove) {
		board[startX][startY].setPieceAtSquare(pieceToBeMoved);
		pieceToBeMoved.setCurrentPosition(board[startX][startY]);
		if(pieceAtDestination != null) {
			board[endX][endY].setPieceAtSquare(pieceAtDestination);
			pieceAtDestination.setCurrentPosition(board[endX][endY]);
			if(actualMove) {
				// Remove the pieceAtDestination from the list of captured pieces of the current piece set
				getCurrentPieceSet().getPieces().remove(pieceAtDestination);
				// And Add it to the original piece set
				getOtherTeamPieceSet(currentTurn).getPieces().add(pieceAtDestination);
			}
		} else {
			board[endX][endY].setPieceAtSquare(null);
		}
	}
	
	/**
	 * Toggle the current player to the other team
	 */
	public void toggleTurn() {
		if(currentTurn == PieceColor.BLACK) {
			currentTurn = PieceColor.WHITE;
		} else {
			currentTurn = PieceColor.BLACK;
		}
	}
	
	/**
	 * Toggle the current player to the other team
	 */
	public PieceColor getCurrentTurn() {
		return currentTurn;
	}
	
	/**
	 * Return the pieceSet based on color as input
	 * @param color
	 * @return
	 */
	public PieceSet getPieceSet(PieceColor color) {
		return color == PieceColor.BLACK ? pieceSets[0] : pieceSets[1];
	}
	
	/**
	 * Return the other team's piece set
	 * @param color
	 * @return
	 */
	public PieceSet getOtherTeamPieceSet(PieceColor color) {
		return color == PieceColor.BLACK ? pieceSets[1] : pieceSets[0];
	}
	
	/**
	 * Return the team color of the team that is not yours
	 * @param color
	 */
	public PieceColor getOtherTeamColor(PieceColor color) {
		return color == PieceColor.BLACK ? PieceColor.WHITE : PieceColor.BLACK;
	}
	
	/**
	 * Create the pieces and the players
	 */
	public void createPieceSets() {
		pieceSets = new PieceSet[NUMBER_OF_PLAYERS];
		pieceSets[0] = new PieceSet(PieceColor.BLACK, board);
		pieceSets[1] = new PieceSet(PieceColor.WHITE, board);
	}
	
	/**
	 * Initialize board as array of Squares
	 */
	public void initializeBoard() {
		for(int row = 0; row < boardLength; row++) {
			for(int col = 0; col < boardWidth; col++) {
				board[row][col] = new Square(row, col);
			}
		}
	}
	
	/**
	 * Iterate through the piece sets and set each of the pieces to the board
	 */
	public void setPiecesToBoard() {
		for(int i = 0; i < NUMBER_OF_PLAYERS; i++) {
			for(Piece p : pieceSets[i].getPieces()) {
				Square temp = p.getCurrentPosition();
				board[temp.getRow()][temp.getCol()].setPieceAtSquare(p);
			}
		}
	}
	
	/**
	 * Get the Piece located at the row, col pair
	 * @return the Piece at the given (row, col) pair
	 */
	public Piece getPiece(int row, int col) {
		if(board[row][col] == null) {
			return null;
		}
		return board[row][col].getPieceAtSquare();
	}
	
	/**
	 * Get the Piece located at the row, col pair
	 * @return the Piece at the given (row, col) pair
	 */
	public PieceSet[] getPieceSets() {
		return pieceSets;
	}
	
	/**
	 * Print the current state of the board
	 * @param boardWidth
	 * @param boardLength
	 */
	public void printBoard() {
		HashMap<String, String> pieceMap = CommonFunctions.getPieceTypeUnicodes();
		System.out.print("   ");
		for(int row = 0; row < boardLength; row++) {
			System.out.print(row + "   ");
		}
		System.out.println();
		for(int row = 0; row < boardLength; row++) {
			System.out.print(row + " ");
			for(int col = 0; col < boardWidth; col++) {
				System.out.print('|');
				Piece p = board[row][col].getPieceAtSquare();
				if(p != null) {
					String pieceCode = pieceMap.get(p.getColor().toString() + " " + p.getPieceType().toString());
					System.out.print(" " + pieceCode);
				} else {
					System.out.print("  ");
				}
				System.out.print('|');
			}
			System.out.println();
		}
	}
}
