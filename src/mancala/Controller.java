package mancala;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Allows a user to play the Mancala program. The program begins with a main
 * screen, giving the player the option of beginning play immediately or reading
 * the instructions. If choosing to read the instructions, the user is taken to
 * another screen before being returned to the main screen. Once the user has
 * chosen to begin the game, the user must choose whether to play the game
 * against the computer or another human player using the same device. Once play
 * has ended, the user is given the option to play the game again or return to
 * the main screen.
 * 
 * @author Ryan Johnson, Hank Rugg
 */
public class Controller extends JFrame {
	private JPanel playPanel;
	private JPanel welcomePanel;

	/**
	 * Creates a new controller for the Mancala program. This controller contains
	 * the JFrame, into which all the JPanels are placed. It also provides the logic
	 * for the transitions in the program, moving the program between various panels
	 * (such as the main menu, the instruction menu, and the Mancala game itself).
	 * Each screen is created as a separate JPanel.
	 */
	public Controller() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(300, 100);

		// Add all panels to this frame
		playPanel = new PlayPanel();
		// welcomePanel = new WelcomePanel();
		// this.add(welcomePanel);
		this.add(playPanel);
		this.pack();

		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Controller();
	}

}
