package mancala;

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
	private JPanel welcomePanel;
	private JPanel instructionsPanel;
	private JPanel settingsPanel;
	private JFrame playFrame;
	private JFrame endGameFrame;
	private PlayPanel playPanel;
	private EndGamePanel endGamePanel;
	private int winner;
	private volatile int winnerState;
	private volatile boolean playAgainState;

	/**
	 * Creates a new controller for the Mancala program. This controller contains
	 * the JFrame, into which all the JPanels are placed. It also provides the logic
	 * for the transitions in the program, moving the program between various panels
	 * (such as the main menu, the instruction menu, and the Mancala game itself).
	 * Each screen is created as a separate JPanel.
	 */
	public Controller() {
		playFrame = new JFrame();
		playPanel = new PlayPanel();
		welcomePanel = new WelcomePanel();
		instructionsPanel = new InstructionsPanel();
		settingsPanel = new SettingsPanel();
//		this.add(welcomePanel);
//		this.add(playPanel);
//		this.add(settingsPanel);
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
	 * Adds the end game panel to the frame, giving the player the option to replay
	 * or exit the program.
	 */
	public void createEndGamePanel() {
		endGameFrame = new JFrame();
		endGameFrame.setLocation(500, 200);
		winner = playPanel.getWinner();

		endGamePanel = new EndGamePanel(winner);
		endGameFrame.add(endGamePanel);

		endGameFrame.pack();
		endGameFrame.setVisible(true);
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
	public EndGamePanel getEndGamePanel() {
		return endGamePanel;
	}

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

	public static void main(String[] args) {
		Controller controller = new Controller();

		controller.createPlayFrame();

		while (controller.getPlayPanel().isVisible()) {
			controller.setWinnerState(controller.getPlayPanel().getWinner());
			if (controller.getWinnerState() != -1) {
				controller.createEndGamePanel();
				while (controller.getEndGamePanel().isVisible()) {
					controller.setPlayAgainState(controller.getEndGamePanel().getPlayAgain());
					if (controller.getPlayAgainState()) {
						controller.getPlayPanel().resetBoardGraphics();
						break;
					}
				}
			}
		}
	}

}