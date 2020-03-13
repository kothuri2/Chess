package Chess;

import Piece.Piece;

/**
 * Square class is used to build the Board class.
 * The 2D array of Squares represents the board.
 * @author Radhir
 *
 */
public class Square {
	
	/**
	 * Square class member variables
	 */
	private int row;
	private int column;
	private Piece piece;
	
	/**
	 * Square constructor to initialize row and column number for square
	 * @param row
	 * @param column
	 */
	public Square(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * 
	 * @param set piece at current square
	 */
	public void setPieceAtSquare(Piece piece) {
		this.piece = piece;
	}
	
	/**
	 * 
	 * @return row for current square
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * 
	 * @return column for current square
	 */
	public int getCol() {
		return column;
	}
	
	/**
	 * 
	 * @return get the piece at current square
	 */
	public Piece getPieceAtSquare() {
		return piece;
	}
}
