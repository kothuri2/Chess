package Views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import Controller.BoardController;
import Controller.GameController;
 
/**
 * Game View that displays chessboard, scoreboard, current turn, and player options
 * @author Radhir
 *
 */
public class GameView implements ActionListener {
    private static JFrame frame;
    
    private static BoardPanel boardPanel;
    private static JMenuBar menuBar;
    private static JPanel masterPanel, chessBoardPanel, infoPanel;
    private static GameController gameController;
    private static BoardController boardController;
    /**
     * Public constructor
     */
    public GameView(){
    	frame = new JFrame("Chess");
        frame.setSize(700, 700);
        
        //Initialize all 3 panels
        gameController = new GameController();
        masterPanel = initializeMasterPanel();
        chessBoardPanel = initializeChessboardPanel();
        infoPanel = initializeInfoPanel();
        setUpMenu(frame);

        boardController = new BoardController(gameController);
        
        //Draw all panels to frame
        drawFrame(masterPanel, chessBoardPanel, infoPanel);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    /**
     * Set up the menu bar at the top
     * @param window
     */
    public void setUpMenu(JFrame window) {
    	String playerOne = gameController.getPlayerOne();
    	String playerTwo = gameController.getPlayerTwo();
    	int whiteScore = gameController.getWhiteScore();
    	int blackScore = gameController.getBlackScore();
    	
    	menuBar = new JMenuBar();
        JButton restart = new JButton();
        restart.setText("Restart");
        JButton forfeit = new JButton();
        forfeit.setText("Forfeit");
        JButton undo = new JButton();
        undo.setText("Undo");
        JLabel newScoreBoardLabel = new JLabel();
        newScoreBoardLabel.setText(playerOne + " (White): " + whiteScore + " | " + playerTwo + " (Black): " + blackScore);
        
        restart.addActionListener(this);
        forfeit.addActionListener(this);
        undo.addActionListener(this);
        
        menuBar.add(restart, BorderLayout.WEST);
        menuBar.add(forfeit, BorderLayout.CENTER);
        menuBar.add(undo, BorderLayout.CENTER);
        menuBar.add(newScoreBoardLabel, BorderLayout.EAST);
        window.setJMenuBar(menuBar);
    }
    
    /**
     * Override the behavior and depending on which
     * button was clicked (restart, undo, or forfeit), perform 
     * the appropriate action
     */
    @Override
	public void actionPerformed(ActionEvent e) {
    	gameController.gameAction(e);
	}
    
    /**
     * Initialize the master panel which contains the chessboardPanel
     * and the infoPanel
     * @return
     */
    public JPanel initializeMasterPanel() {
    	JPanel masterPanel = new JPanel(new BorderLayout());
    	return masterPanel;
    }
    
    /**
     * Initialize the chessboardPanel
     * @return
     */
    public static JPanel initializeChessboardPanel() {
    	JPanel newChessBoardPanel = new JPanel(new GridLayout(8, 8));
    	newChessBoardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	chessBoardPanel = newChessBoardPanel;
    	return newChessBoardPanel;
    }
    
    /**
     * Initialize the infoPanel
     * @return
     */
    public JPanel initializeInfoPanel() {
    	String currentTurnColor = gameController.getCurrentTurn();
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        JLabel newCurrentTurnLabel = new JLabel();
        newCurrentTurnLabel.setText("Current Turn: " + currentTurnColor + " (" + gameController.getCurrentPlayer() + ")");
        infoPanel.add(newCurrentTurnLabel, BorderLayout.NORTH);
        JLabel newWhiteCapturedPiecesLabel = new JLabel();
        newWhiteCapturedPiecesLabel.setText("White (" + gameController.getPlayerOne() +  ") Captured Pieces: ");
        infoPanel.add(newWhiteCapturedPiecesLabel, BorderLayout.CENTER);
        JLabel newBlackCapturedPiecesLabel = new JLabel();
        newBlackCapturedPiecesLabel.setText("Black (" + gameController.getPlayerTwo() + ") Captured Pieces: " );
        infoPanel.add(newBlackCapturedPiecesLabel, BorderLayout.SOUTH);
        return infoPanel;
    }
    
    /**
     * Draw the initial frame to the JFrame
     * @param masterPanel
     * @param chessBoardPanel
     * @param infoPanel
     */
    public static void drawFrame(JPanel masterPanel, JPanel chessBoardPanel, JPanel infoPanel) {
    	//Draw chess board to JFrame
    	boardPanel = boardController.getBoardPanel();
        masterPanel.add(chessBoardPanel, BorderLayout.CENTER);
        masterPanel.add(infoPanel, BorderLayout.SOUTH);
        frame.add(masterPanel);
    }
    
    /**
     * Get the master panel for the Game Controller
     * @return
     */
    public static JPanel getMasterPanel() {
    	return masterPanel;
    }
    
    /**
     * Get the chessboard panel for the Game Controller
     * @return
     */
    public static JPanel getChessboardPanel() {
    	return chessBoardPanel;
    }
    
    /**
     * Get the info panel for the Game Controller
     * @return
     */
    public static JPanel getInfoPanel() {
    	return infoPanel;
    }
    
    public static JMenuBar getMenuBar() {
    	return menuBar;
    }
    
    /**
     * Get the main windo frame for the Game Controller
     * @return
     */
    public static JFrame getFrame() {
    	return frame;
    }
    
    /**
     * Get the board controller
     * @return
     */
    public static BoardController getBoardController() {
    	return boardController;
    }
 
    /**
     * Run the game
     * @param args
     */
    public static void main(String[] args) {
        new GameView();
    }

}