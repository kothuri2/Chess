package Controller;

import java.awt.Color;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Chess.Board;
import Chess.CheckMateException;
import Chess.Move;
import Chess.StaleMateException;
import Piece.Piece;
import Piece.PieceColor;
import Piece.PieceType;
import Views.BoardPanel;
import Views.GameView;
import Views.Tile;

/**
 * Board Controller that is responsible for
 * controlling the board and piece moves
 * @author Radhir
 *
 */
public class BoardController {
	
	/**
	 * Class member variables
	 */
	private int boardLength, boardWidth;
	private BoardPanel boardPanel;
	private Tile[][] chessBoard;
	private GameController gameController;
    private int startX, startY, endX, endY, currentClickCounter;
    
    /**
     * Public constructor
     * @param game
     */
	public BoardController(GameController game) {
		boardLength = 8;
		boardWidth = 8;
		boardPanel = new BoardPanel(boardLength, boardWidth);
		chessBoard = boardPanel.getChessBoard();
		this.gameController = game;
		
		//Initialize the white/gray alternating pattern
		//and add all white and black pieces to the board
		initBoard();
	}
	
	/**
	 * Initializer function that calls helper functions
	 * to initialize the board
	 */
	public void initBoard() {
		//Create Black and White Pattern
        createBlackWhitePattern();
        // Set the position for each of the white pieces
     	addPiecesToBoard(PieceColor.WHITE);
     	// Set the position for each of the black pieces
     	addPiecesToBoard(PieceColor.BLACK);
	}
	
	/**
     * Create the alternating black and white squares
     * on the chessboard
     */
    public void createBlackWhitePattern() {
    	JPanel frame = GameView.getChessboardPanel();
    	for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardWidth; j++) {
                //This condition alternates the color since 
            	//it basically checks if odd or even and the parity of the 
            	//square changes.
            	if ((i + j) % 2 == 0) {
            		chessBoard[i][j] = new Tile(this, Color.GRAY, i, j);
                	// chessBoard[i][j].setBackground(Color.GRAY);
                } else {
                	chessBoard[i][j] = new Tile(this, Color.WHITE, i, j);
                	//chessBoard[i][j].setBackground(Color.WHITE);
                }
                frame.add(chessBoard[i][j].getPiecePanel());
            }
        }
    }
    
    /**
     * Add the respective pieces to the board
     */
    public void addPiecesToBoard(PieceColor pieceColor) {
    	if(pieceColor == PieceColor.WHITE) {
    		//Add pawns to board
    		for(int i = 0; i < boardWidth; i++) {
    			if(gameController.getCustomBoard() && (i == 3 || i== 4)) {
    				if(i == 3) {
    					createLabelAndAddPieceToBoard(PieceColor.WHITE, PieceType.PRINCESS, boardLength-2, i);
    				} else if(i == 4) {
    					createLabelAndAddPieceToBoard(PieceColor.WHITE, PieceType.EMPRESS, boardLength-2, i);
    				}
    			} else {
    				createLabelAndAddPieceToBoard(PieceColor.WHITE, PieceType.PAWN, boardLength-2, i);
    			}
    		}
    		//Add rooks to board
    		createLabelAndAddPieceToBoard(PieceColor.WHITE, PieceType.ROOK, boardLength-1, 0);
    		createLabelAndAddPieceToBoard(PieceColor.WHITE, PieceType.ROOK, boardLength-1, boardWidth - 1);
    		//Add knights to board
    		createLabelAndAddPieceToBoard(PieceColor.WHITE, PieceType.KNIGHT, boardLength-1, 1);
    		createLabelAndAddPieceToBoard(PieceColor.WHITE, PieceType.KNIGHT, boardLength-1, boardWidth - 2);
    		//Add bishops to board
    		createLabelAndAddPieceToBoard(PieceColor.WHITE, PieceType.BISHOP, boardLength-1, 2);
    		createLabelAndAddPieceToBoard(PieceColor.WHITE, PieceType.BISHOP, boardLength-1, boardWidth - 3);
    		//Add king and queen to board
    		createLabelAndAddPieceToBoard(PieceColor.WHITE, PieceType.QUEEN, boardLength-1, 3);
    		createLabelAndAddPieceToBoard(PieceColor.WHITE, PieceType.KING, boardLength-1, 4);
    		
    	} else {
    		//Add pawns to board
    		for(int i = 0; i < boardWidth; i++) {
    			if(gameController.getCustomBoard() && (i == 3 || i== 4)) {
    				if(i == 3) {
    					createLabelAndAddPieceToBoard(PieceColor.BLACK, PieceType.PRINCESS,1, i);
    				} else if(i == 4) {
    					createLabelAndAddPieceToBoard(PieceColor.BLACK, PieceType.EMPRESS, 1, i);
    				}
    			} else {
    				createLabelAndAddPieceToBoard(PieceColor.BLACK, PieceType.PAWN, 1, i);
    			}
    		}
    		//Add rooks to board
    		createLabelAndAddPieceToBoard(PieceColor.BLACK, PieceType.ROOK, 0, 0);
    		createLabelAndAddPieceToBoard(PieceColor.BLACK, PieceType.ROOK, 0, boardWidth - 1);
    		//Add knights to board
    		createLabelAndAddPieceToBoard(PieceColor.BLACK, PieceType.KNIGHT, 0, 1);
    		createLabelAndAddPieceToBoard(PieceColor.BLACK, PieceType.KNIGHT, 0, boardWidth - 2);
    		//Add bishops to board
    		createLabelAndAddPieceToBoard(PieceColor.BLACK, PieceType.BISHOP, 0, 2);
    		createLabelAndAddPieceToBoard(PieceColor.BLACK, PieceType.BISHOP, 0, boardWidth - 3);
    		//Add king and queen to board
    		createLabelAndAddPieceToBoard(PieceColor.BLACK, PieceType.QUEEN, 0, 3);
    		createLabelAndAddPieceToBoard(PieceColor.BLACK, PieceType.KING, 0, 4);
    	}
    }
    
    /**
     * This function is not called yet. But for future implementation,
     * if a user hovers over the piece, use this function to highlight all
     * potential moves possible by that piece. 
     * @param startX
     * @param startY
     */
    public void showPossibleMoves(int startX, int startY) {
    	Board board = gameController.getCurrentBoardState();
    	Piece pieceToBeMoved = board.getPiece(startX, startY);
    	if(pieceToBeMoved != null) {
    		List<Move> possibleMoves = pieceToBeMoved.getPossibleMoves(startX, startY);
    		for(Move potentialMove : possibleMoves) {
    			int endX = potentialMove.getEndX();
    			int endY = potentialMove.getEndY();
    			chessBoard[endX][endY].getPiecePanel().setBackground(Color.GREEN);
    		}
    	}
    }
    
    /**
     * Set the default color for all tiles
     */
    public void setDefaultColorForTiles() {
    	for(int i  = 0; i < boardLength; i++) {
    		for(int j = 0; j < boardWidth; j++) {
    			chessBoard[i][j].setDefaultColor();
    		}
    	}
    }
    
    /**
     * Handler to handle button clicks on pieces.
     * @param row
     * @param col
     */
    public void squareClicked(int row, int col) {
    	Board board = gameController.getCurrentBoardState();
    	if(currentClickCounter == 0 && board.getPiece(row, col) != null) {
    		this.startX = row;
    		this.startY = col;
    		currentClickCounter++;
    		showPossibleMoves(startX, startY);
    	} else {
    		if(row == this.startX && col == this.startY) {
    			currentClickCounter = 0;
    			setDefaultColorForTiles();
    			return;
    		}
    		this.endX = row;
    		this.endY = col;
    		currentClickCounter = 0;
    		setDefaultColorForTiles();
    		makeMove();
    	}
    }
    
    /**
     * Make the move on the board and handle
     * all board state changes by calling Chess API
     */
    public void makeMove() {
    	Board board = gameController.getCurrentBoardState();
    	Piece pieceToBeMoved = board.getPiece(startX, startY);
    	if(pieceToBeMoved == null) {
    		return;
    	}

    	PieceColor pieceColor = board.getPiece(startX, startY).getColor();
		String pieceType = board.getPiece(startX, startY).getPieceType().toString();
    	Move newMove = new Move(startX, startY, endX, endY, board.getCurrentTurn());
		boolean successfulMove;
		try {
			successfulMove = newMove.checkValidMove(true);
		} catch (CheckMateException e) {
			updatePieceLabel(pieceColor.toString(), pieceType);
			//Reset Board
			gameController.resetBoard(pieceColor, false);
			return;
		} catch (StaleMateException e) {
			updatePieceLabel(pieceColor.toString(), pieceType);
			//Reset Board and don't add any scores
			gameController.resetBoard(pieceColor, false);
			return;
		}
		if(!successfulMove) {
			chessBoard[startX][startY].setDefaultColor();
			chessBoard[endX][endY].setDefaultColor();
			JOptionPane.showMessageDialog(null, "Invalid Move!", "Invalid Move", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			chessBoard[startX][startY].setDefaultColor();
			chessBoard[endX][endY].setDefaultColor();
			updatePieceLabel(pieceColor.toString(), pieceType);
		}
		board.printBoard();
		gameController.updateInfoPanel();
		System.out.println("It's " + board.getCurrentTurn().toString() + "'s turn!");
    }
    
    /**
     * Undo the move on the board if the undo
     * button is pressed.
     */
    public void undoMoveOnBoard() {
    	Move move = gameController.getCurrentBoardState().undoMoveOnBoard();
    	if(move == null) {
    		JOptionPane.showMessageDialog(null, "No more moves to undo!");
    		return;
    	}
    	String pieceAtStart = move.getPieceAtStart().getColor().toString() + " " + move.getPieceAtStart().getPieceType().toString();
    	String pieceAtDestination = null;
    	if(move.getPieceAtDestination() != null) {
    		pieceAtDestination = move.getPieceAtDestination().getColor().toString() + " " + move.getPieceAtDestination().getPieceType().toString();
    	}
    	System.out.println(pieceAtDestination);
    	chessBoard[move.getStartX()][move.getStartY()].updatePieceLabel(pieceAtStart);
    	chessBoard[move.getEndX()][move.getEndY()].updatePieceLabel(pieceAtDestination);
    	System.out.println("Undoed Move!");
    	gameController.getCurrentBoardState().printBoard();
    	
    }
    
    /**
     * Update the piece label on the tile 
     * @param pieceColor
     * @param pieceType
     */
    public void updatePieceLabel(String pieceColor, String pieceType) {
    	chessBoard[startX][startY].updatePieceLabel(null);
		chessBoard[endX][endY].updatePieceLabel(pieceColor + " " +  pieceType);
    }
    
    /**
     * Add a jLabel to the chess board square at that specific row and column.
     * Set the text of the label to the unicode for that specific piece
     * @param chessUnicode
     * @param row
     * @param col
     */
    public void createLabelAndAddPieceToBoard(PieceColor pieceColor, PieceType pieceType, int row,int col) {
		chessBoard[row][col].addPieceLabel(pieceColor, pieceType);
    }
	
    /**
     * Get the board panel
     * @return
     */
	public BoardPanel getBoardPanel() {
		return boardPanel;
	}
}
