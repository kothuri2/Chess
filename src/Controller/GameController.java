package Controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Chess.Board;
import Chess.Game;
import Chess.PieceSet;
import Piece.Empress;
import Piece.Piece;
import Piece.PieceColor;
import Piece.PieceType;
import Piece.Princess;
import Views.GameView;

/**
 * Game Controller that is responsible for controlling
 * the entire Chess Game View
 * @author Radhir
 *
 */
public class GameController {
	
	/**
	 * Game controller member variables
	 */
    private static int whiteScore, blackScore = 0;
    private static String playerOne, playerTwo;
    private static boolean customBoard;
	private static Game game;
	private JFrame frame;
	private JPanel masterPanel, chessboardPanel, infoPanel;
	private JMenuBar menuBar;
	
	/**
	 * public constructor
	 */
	public GameController() {
		//Ask for Player's names and type of chess board
        playerOne = JOptionPane.showInputDialog("Please enter player one's name.");
        playerTwo = JOptionPane.showInputDialog("Please enter player two's name.");
        int boardChoice = JOptionPane.showConfirmDialog(null, "Do you want to play with custom pieces (Princess/Empress)?");
        if(boardChoice == 0) {
        	customBoard = true;
        } else if(boardChoice == 1) {
        	customBoard = false;
        } else {
        	System.exit(0);
        }
        
        //Get all the panels needed for updating
        masterPanel = GameView.getMasterPanel();
        chessboardPanel = GameView.getMasterPanel();
        infoPanel = GameView.getInfoPanel();
        menuBar = GameView.getMenuBar();
        frame = GameView.getFrame();

        //Start new game and keep track of board on console and turn
        game = new Game();
        if(customBoard) {
        	game.getCurrentBoardState().getSquare(6, 3).setPieceAtSquare(new Princess(PieceColor.WHITE, PieceType.PRINCESS));
        	game.getCurrentBoardState().getSquare(6, 4).setPieceAtSquare(new Empress(PieceColor.WHITE, PieceType.EMPRESS));
        	game.getCurrentBoardState().getSquare(1, 3).setPieceAtSquare(new Princess(PieceColor.BLACK, PieceType.PRINCESS));
        	game.getCurrentBoardState().getSquare(1, 4).setPieceAtSquare(new Empress(PieceColor.BLACK, PieceType.EMPRESS));
        }
        game.getCurrentBoardState().printBoard();
        System.out.println("It's " + game.getCurrentBoardState().getCurrentTurn().toString() + "'s turn!");
	}
	
	/**
     * Reset the Board to a new board because
     * either a player forefeited, both players wanted to restart, or the game ended and a player won
     * @param winningTeam
     * @param restart
     */
    public void resetBoard(PieceColor winningTeam, boolean restart) {
    	if(!restart) {
	    	if(winningTeam == PieceColor.WHITE) {
	    		whiteScore += 1;
	    	} else {
	    		blackScore += 1;
	    	}
    	} else {
    		//If actual restart
    		//if winningTeam != null, then staleMate just keep current score
    		if(winningTeam == null) {
    			whiteScore = 0;
        		blackScore = 0;	
    		}
    	}
    	//Remove the current board and recreate a new game and redraw the board
    	//Get the most up to date panels
    	masterPanel = GameView.getMasterPanel();
    	chessboardPanel = GameView.getChessboardPanel();
    	
    	masterPanel.remove(chessboardPanel);
    	this.game.createNewBoard();
    	chessboardPanel = GameView.initializeChessboardPanel();
    	GameView.getBoardController().initBoard();
        masterPanel.add(chessboardPanel, BorderLayout.CENTER);
        updateInfoPanel();
    	updateScoreBoard();
        frame.revalidate();
        frame.repaint();
        System.out.println("Resetted board");
        game.getCurrentBoardState().printBoard();
    }
    
    /**
     * Depending on the action performed (restart, forfeit, undo)
     * perform the appropriate action
     * @param e
     */
    public void gameAction(ActionEvent e) {
    	if(e.getActionCommand().equals("Restart")) {
    		String agreeOne = JOptionPane.showInputDialog("Does player one agree to restart the game? If so, player one type name in.");
    		String agreeTwo = JOptionPane.showInputDialog("Does player two agree to restart the game? If so, player two type name in.");
    		if(agreeOne.equals(playerOne) && agreeTwo.equals(playerTwo))  {
    			resetBoard(null, true);
    		} else {
    			JOptionPane.showMessageDialog(null, "Both players have not agreed to a game restart!");
    		}
    	} else if(e.getActionCommand().equals("Forfeit")) {
    		PieceColor currentTeamColor = game.getCurrentBoardState().getCurrentTurn();
    		resetBoard(game.getCurrentBoardState().getOtherTeamColor(currentTeamColor), false);
    	} else if(e.getActionCommand().equals("Undo")) {
    		GameView.getBoardController().undoMoveOnBoard();
    		updateInfoPanel();
    	}
    }
    
    /**
     * Update the score board whenever someone wins
     * or the game is restarted
     */
    public void updateScoreBoard() {
    	menuBar = GameView.getMenuBar();
    	JLabel newScoreBoardLabel = (JLabel)menuBar.getComponent(3);
    	newScoreBoardLabel.setText(playerOne + " (White): " + whiteScore + " | " + playerTwo + " (Black): " + blackScore);
    }
    
    /**
     * Update the current turn status bar
     * to display the team who is supposed to move currently
     */
    public void updateInfoPanel() {
    	infoPanel = GameView.getInfoPanel();
    	JLabel newCurrentTurnLabel = (JLabel)infoPanel.getComponent(0);
    	newCurrentTurnLabel.setText("Current Turn: " + getCurrentTurn() + " (" + getCurrentPlayer() + ")");
    	JLabel newWhiteCapturedPiecesLabel = (JLabel)infoPanel.getComponent(1);
    	newWhiteCapturedPiecesLabel.setText("White (" + playerOne + ") Captured Pieces: " + getCapturedPiecesAsString(PieceColor.WHITE));
    	JLabel newBlackCapturedPiecesLabel = (JLabel)infoPanel.getComponent(2);
    	newBlackCapturedPiecesLabel.setText("Black (" + playerTwo + ") Captured Pieces: " + getCapturedPiecesAsString(PieceColor.BLACK));
    }
    
    /**
     * Return the name of the current player
     * @return
     */
    public String getCurrentPlayer() {
    	if(getCurrentTurn().equals("WHITE")) {
    		return playerOne;
    	}
    	return playerTwo;
    }
    
    /**
     * Get captured pieces for team as a string
     * @param color
     * @return
     */
    public String getCapturedPiecesAsString(PieceColor color) {
    	Board currentBoard = getCurrentBoardState();
    	PieceSet pieceSet = currentBoard.getPieceSet(color);
    	List<Piece> capturedPieces = pieceSet.getCapturedPieces();
    	String capturedPiecesString = "";
    	for(Piece piece : capturedPieces) {
    		capturedPiecesString += (piece.getPieceType().toString() + ", ");
    	}
    	return capturedPiecesString;
    }
   
    /**
     * Get the player one's name
     * @return
     */
    public String getPlayerOne() {
    	return playerOne;
    }
    
    /**
     * Get the player two's name
     * @return
     */
    public String getPlayerTwo() {
    	return playerTwo;
    }
    
    /**
     * Get white team's score
     * @return
     */
    public int getWhiteScore() {
    	return whiteScore;
    }
    
    /**
     * Get black team's name
     * @return
     */
    public int getBlackScore() {
    	return blackScore;
    }
    
    /**
     * Return true or false if the current board is
     * a custom board or not
     * @return
     */
    public boolean getCustomBoard() {
    	return customBoard;
    }
    
    /**
     * Get the current board's state
     * @return
     */
    public Board getCurrentBoardState() {
    	return game.getCurrentBoardState();
    }
    
    /**
     * Get the team who's current turn it is
     * @return
     */
    public String getCurrentTurn() {
    	return game.getCurrentBoardState().getCurrentTurn().toString();
    }
}
