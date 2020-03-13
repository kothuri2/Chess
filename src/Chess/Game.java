package Chess;

import java.util.*;

/**
 * Main Game class that is used for the Game Loop.
 * @author Radhir
 *
 */
public class Game {
	/*
	 * 6242, 1737, 6747, 1030, 7340, 0020, 4030, 2027, 3012, 1525, 1213,
	 * 0415, 1311, 0353, 1101, 5317, 0102, 1526, 0224
	 */
	/**
	 * Constants that dictate the board length and width
	 * A static board that is shared across the entire gameplay.
	 */
	private static final int BOARD_LENGTH = 8;
	private static final int BOARD_WIDTH = 8;
	private static Board board = new Board(BOARD_WIDTH, BOARD_LENGTH);
	
	/**
	 * Main game play loop.
	 * Start with white team first and toggle between teams to move pieces and 
	 * play the game.
	 */
	
	public static void main(String[] args) { 
		Scanner scan = new Scanner(System.in);
		while(true) {
			board.printBoard();
			System.out.println("It's " + board.getCurrentTurn().toString() + "'s Turn.");
			System.out.print("Start X: ");
			int startX = scan.nextInt();
			System.out.print("Start Y: ");
			int startY = scan.nextInt();
			
			if(board.getPiece(startX, startY) == null) {
				System.out.println("No piece exists at this coordinate!");
				continue;
			}
			
			System.out.print("End X: ");
			int endX = scan.nextInt();
			System.out.print("End Y: ");
			int endY = scan.nextInt();
			
			Move newMove = new Move(startX, startY, endX, endY, board.getCurrentTurn());
			boolean successfulMove;
			try {
				successfulMove = newMove.checkValidMove(true);
			} catch (CheckMateException e) {
				break;
			} catch (StaleMateException e) {
				break;
			} 
			if(!successfulMove) {
				System.out.println("Invalid Move!");
			}
		}
		scan.close();
	}
	
	
	/**
	 * Get the Board width
	 * @return BOARD_LENGTH
	 */
	public static int getBoardLength() {
		return BOARD_LENGTH;
	}
	
	/**
	 * Get the Board Width
	 * @return BOARD_WIDTH
	 */
	public static int getBoardWidth() {
		return BOARD_WIDTH;
	}
	
	/**
	 * Get the current board and all the respective moves
	 * that have occured
	 * @return board
	 */
	public static Board getCurrentBoardState() {
		return board;
	}
	
	public static void createNewBoard() {
		board = new Board(BOARD_WIDTH, BOARD_LENGTH);
	}

}
