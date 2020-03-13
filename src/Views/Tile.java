package Views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.UIManager;

import Chess.CommonFunctions;
import Controller.BoardController;
import Piece.PieceColor;
import Piece.PieceType;

/**
 * Tile class that represents each individual tile
 * on a chess board. Implemented as a JButton and implements
 * ActionListener to listen for tile clicks
 * @author Radhir
 *
 */
public class Tile implements ActionListener {
	
	/**
	 * private member variables for class
	 */
	private static final long serialVersionUID = 1L;
	private JButton piecePanel;
	private int row, col;
	private HashMap<String, String> pieceTypeUnicodes;
	private BoardController boardController;
	private Color defaultColor;
	private boolean panelPressed = false;
	
	/**
	 * Public Constructor
	 * @param boardController
	 * @param color
	 * @param row
	 * @param col
	 */
	public Tile(BoardController boardController, Color color, int row, int col) {
		this.piecePanel = new JButton();
		this.row = row;
		this.col = col;
		this.defaultColor = color;
		this.boardController = boardController;
		//Set the background of the button and make it look
		//like a panel instead
		this.piecePanel.setBackground(color);
		this.piecePanel.setOpaque(true);
		this.piecePanel.setBorderPainted(false);
		this.piecePanel.addActionListener(this);
		
		//HashMap that maps "<PieceColor> <PieceType>" string to unicode value
        pieceTypeUnicodes = CommonFunctions.getPieceTypeUnicodes();
	}
	
	/**
	 * Set the color of the tile back to the
	 * default color
	 */
	public void setDefaultColor() {
		this.piecePanel.setBackground(defaultColor);
	}
	
	/**
	 * Action listener whenever a tile is clicked.
	 * Toggle the color, and perform the makeMove() function
	 * in the board controller
	 */
	public void actionPerformed(ActionEvent e) {
		toggleColor();
		boardController.squareClicked(row, col);
    }
	
	/**
	 * Toggle the color of the board
	 */
	public void toggleColor() {
		if(this.piecePanel.getBackground() != defaultColor) {
			setDefaultColor();
		} else {
			this.piecePanel.setBackground(Color.CYAN);
		}
		
	}
	
	/**
	 * Update the label of each individual piece
	 * @param newChessUnicode
	 */
	public void updatePieceLabel(String newChessUnicode) {
		if(newChessUnicode == null) {
			this.piecePanel.setText(null);
			return;
		}
		
		String chessUnicode = pieceTypeUnicodes.get(newChessUnicode);
		Font newFont = new Font("Arial", Font.PLAIN, 40);
		this.piecePanel.setFont(newFont);
		this.piecePanel.setMargin(new Insets(0, 0, 0, 0));
		this.piecePanel.setText(chessUnicode);
	}
	
	/**
	 * Add a label to the jbutton
	 * @param pieceColor
	 * @param pieceType
	 */
	public void addPieceLabel(PieceColor pieceColor, PieceType pieceType) {
		String chessUnicode = pieceTypeUnicodes.get(pieceColor.toString() + " " + pieceType.toString());
		Font newFont = new Font("Arial", Font.PLAIN, 40);
		this.piecePanel.setFont(newFont);
		this.piecePanel.setMargin(new Insets(0, 0, 0, 0));
		this.piecePanel.setText(chessUnicode);
	}
	
	/**
	 * Get the current piece panel
	 * @return
	 */
	public JButton getPiecePanel() {
		return piecePanel;
	}
}
