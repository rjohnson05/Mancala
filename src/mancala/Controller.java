package mancala;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Manages the Mancala game, switching between different screens throughout the
 * program.
 * 
 * @author Ryan Johnson, Hank Rugg
 */
public class Controller {
	/*
	 * Allows a user to play the Mancala program. The program begins with a main
	 * screen, giving the player the option of beginning play immediately or reading
	 * the instructions. If choosing to read the instructions, the user is taken to
	 * another screen before being returned to the main screen. Once the user has
	 * chosen to begin the game, the user must choose whether to play the game
	 * against the computer or another human player using the same device. Once play
	 * has ended, the user is given the option to play the game again or return to
	 * the main screen.
	 */
	private JFrame playFrame;
	private JFrame preGameFrame;
	private JFrame endGameFrame;
	private PlayPanel playPanel;
//	private EndGamePanel endGamePanel;
	private WelcomePanel welcomePanel;
	private SettingsPanel settingsPanel;
	private InstructionsPanel instructionsPanel;

	private int winner;
	private volatile int winnerState;
	private volatile boolean playAgainState;
	private volatile int showPage = 0;
	boolean playGame = false;


	/**
	 * Creates a new controller for the Mancala program. This controller contains
	 * the JFrame, into which all the JPanels are placed. It also provides the logic
	 * for the transitions in the program, moving the program between various panels
	 * (such as the main menu, the instruction menu, and the Mancala game itself).
	 * Each screen is created as a separate JPanel.
	 */
	public Controller() {
		preGameFrame = new JFrame();
		playFrame = new JFrame();
		playPanel = new PlayPanel();
		welcomePanel = new WelcomePanel();
		instructionsPanel = new InstructionsPanel();
		settingsPanel = new SettingsPanel();

	}

	/**
	 * Adds the main gameplay panel to the frame.
	 */
	public void createPlayFrame() {
		playFrame.add(playPanel);
		playFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		playFrame.setLocation(300, 100);

		playFrame.pack();
		playFrame.setVisible(true);
	}
	
	/**
	 * Adds the main gameplay panel to the frame.
	 */
	public void createPreGameFrame() {
		preGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		preGameFrame.setLocation(300, 100);

		preGameFrame.pack();
		preGameFrame.setVisible(true);
	}
	
	public JFrame getPreGameFrame() {
		return preGameFrame;
	}

	/**
	 * Adds the end game panel to the frame, giving the player the option to replay
	 * or exit the program.
	 */
	public void createEndGamePanel() {
		endGameFrame = new JFrame();
		endGameFrame.setLocation(500, 200);
		winner = playPanel.getWinner();

//		endGamePanel = new EndGamePanel(winner);
//		endGameFrame.add(endGamePanel);

		endGameFrame.pack();
		endGameFrame.setVisible(true);
	}
	
	public WelcomePanel createWelcomePanel() {
		welcomePanel = new WelcomePanel();
		preGameFrame.add(welcomePanel);
		preGameFrame.pack();
		preGameFrame.setVisible(true);
		return welcomePanel;

	}
	
	public WelcomePanel getWelcomepanel() {
		return welcomePanel;
	}
	
	
	public SettingsPanel createSettingsPanel() {
		settingsPanel = new SettingsPanel();
		preGameFrame.add(settingsPanel);
		preGameFrame.pack();
		preGameFrame.setVisible(true);
		return settingsPanel;

	}
	
	public SettingsPanel getSettingsPanel() {
		return settingsPanel;
	}

	
	public InstructionsPanel createInstructionsPanel() {
		instructionsPanel = new InstructionsPanel();
		preGameFrame.add(instructionsPanel);
		preGameFrame.pack();
		preGameFrame.setVisible(true);
		return instructionsPanel;
		

	}

	
	public InstructionsPanel getInstructionsPanel() {
		return instructionsPanel;
	}

	/**
	 * Returns the main gameplay panel.
	 * 
	 * @return JPanel a JPanel containing the main gameplay graphics
	 */
	public PlayPanel getPlayPanel() {
		return playPanel;
	}

	/**
	 * Returns the end game panel.
	 * 
	 * @return EndGamePanel a JPanel containing the end-game screen
	 */

	/**
	 * Sets the winner of the game.
	 * 
	 * @param winnerState sets winnerState to 0 if Player 1 has won, 1 if Player 2
	 *                    has won, and -1 if there is no winner
	 */
	public void setWinnerState(int winnerState) {
		this.winnerState = winnerState;
	}

	/**
	 * Returns the winner of the game.
	 * 
	 * @return int returns 0 if Player 1 has won, 1 if Player 2 has won, and -1 if
	 *         there is no winner
	 */
	public int getWinnerState() {
		return winnerState;
	}

	/**
	 * Sets the boolean specifying whether the user has clicked the "Play Again"
	 * button on the end-game screen
	 * 
	 * @param playAgainState sets playAgainState to true if the player has clicked
	 *                       the "Play Again" button; false otherwise
	 */
	public void setPlayAgainState(boolean playAgainState) {
		this.playAgainState = playAgainState;
	}

	/**
	 * Returns the boolean specifying whether the user has clicked the "Play Again"
	 * button on the end-game screen
	 * 
	 * @return boolean returns true if the player has clicked on the "Play Again"
	 *         button on the end-game screen; false otherwise
	 */
	public boolean getPlayAgainState() {
		return playAgainState;
	}
	
	public void getCorrectPanelWelcome(WelcomePanel panel) {
		showPage = panel.showPage;
	}
	
	public void getCorrectPanelInstructions(InstructionsPanel panel) {
		showPage = panel.showPage;
	}
	
	public void getCorrectPanelSettings(SettingsPanel panel) {
		showPage = panel.showPage;
	}
	



	}

