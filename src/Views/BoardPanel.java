package Views;

/**
 * Board Panel class that displays the 
 * white/gray checkered pattern and pieces
 * @author Radhir
 *
 */
public class BoardPanel {
	
	/**
	 * private member variables
	 */
	private Tile[][] chessBoard;
    
	/**
	 * Public constructor
	 * @param boardLength
	 * @param boardWidth
	 */
	public BoardPanel(int boardLength, int boardWidth) {
		this.chessBoard = new Tile[boardLength][boardWidth];
	}
    
	/**
	 * Get the current chessBoard
	 * @return
	 */
    public Tile[][] getChessBoard() {
    	return chessBoard;
    }

}
