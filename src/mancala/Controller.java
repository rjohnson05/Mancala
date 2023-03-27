package mancala;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @title Controller.java
 * @author Ryan Johnson, Hank Rugg
 * @description Allows a user to play the Mancala program. The program begins
 *              with a main screen, giving the player the option of beginning
 *              play immediately or reading the instructions. If choosing to
 *              read the instructions, the user is taken to another screen
 *              before being returned to the main screen. Once the user has
 *              chosen to begin the game, the user must choose whether to play
 *              the game against the computer or another human player using the
 *              same device. Once play has ended, the user is given the option
 *              to play the game again or return to the main screen.
 */
public class Controller extends JFrame {
	private JPanel playPanel;

	public Controller() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(300, 100);

		// Add all panels to this frame
		playPanel = new PlayPanel();
		this.add(playPanel);
		this.pack();

		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Controller();
	}

}
