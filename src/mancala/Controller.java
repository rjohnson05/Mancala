package mancala;

import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

/**
 * This is the controller for the program and allows the user to switch between
 * each panel to create a cohesive program.
 *
 * @author Ryan Johnson, Hank Rugg
 */
@SuppressWarnings("serial")
public class Controller extends JFrame implements MouseListener {

	public Container c;
	public CardLayout card;
	public WelcomePanel welcome;
	public InstructionsPanel instructions;
	public SettingsPanel settings;
	public PlayPanel play;
	public EndGamePanel end;
	public HelpPanel help;

	/**
	 * Constructor for the Controller. This is how to program switches screens and
	 * allows the user to play multiple games without
	 */
	public Controller() {

		// to get the content
		c = getContentPane();

		// Initialization of object "card"
		card = new CardLayout(40, 30);
		card.setHgap(0);
		card.setVgap(0);

		// set the layout
		c.setLayout(card);

		// create welcome panel and adds mouse listeners
		welcome = new WelcomePanel();
		welcome.playGame.addMouseListener(this);
		welcome.instructions.addMouseListener(this);
		welcome.quitGame.addMouseListener(this);
		c.add("welcome", welcome);

		// create instructions panel and adds mouse listeners
		instructions = new InstructionsPanel();
		instructions.playGame.addMouseListener(this);
		instructions.home.addMouseListener(this);
		instructions.quitGame.addMouseListener(this);
		c.add("instructions", instructions);

		// create settings panel and adds mouse listeners
		settings = new SettingsPanel();
		settings.home.addMouseListener(this);
		settings.singlePlayer.addMouseListener(this);
		settings.twoPlayer.addMouseListener(this);
		settings.quitGame.addMouseListener(this);
		c.add("settings", settings);
		
		help = new HelpPanel();
		help.quitGame.addMouseListener(this);
		help.resume.addMouseListener(this);
		c.add("help", help);

		// create play panel and adds mouse listeners to all the pit buttons
		play = new PlayPanel();
		for (RoundButton button : play.pitButtons) {
			button.addMouseListener(this);
		}
		play.home.addMouseListener(this);
		play.quit.addMouseListener(this);
		play.help.addMouseListener(this);
		c.add("play", play);

	}

	/**
	 * Creates the card and allows for the panels to be switched.
	 */
	public void createCard() {

		// Creating Object of CardLayout class.
		Controller cl = new Controller();

		// Function to set size of JFrame.
		cl.setSize(800, 600);

		// Function to set visibility of JFrame.
		cl.setVisible(true);

		// Function to set default operation of JFrame.
		cl.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
	
	public void createPlayGame() {
		
	}

	/**
	 * Creates the end game panel that is shown once the game is over.
	 */
	public void createEndGame() {
		end = new EndGamePanel(play.getGame().getWinner(), play.getGame().getStoreList().get(6).getMarbleList().size(),
				play.getGame().getStoreList().get(13).getMarbleList().size());
		end.exitGameButton.addMouseListener(this);
		end.playAgainButton.addMouseListener(this);
		c.add("end", end);
		card.show(c, "end");
	}

	/**
	 * Makes the computer select a pit to move. This is called after the user makes
	 * a move in single player mode.
	 */
	public void singlePlayerMove() {
		Timer timer = new Timer();
		TimerTask action = new TimerTask() {
			public void run() {

				int bestPitIndex = play.chooseOpponentPit();
				play.playerMove(bestPitIndex);
				if (play.getGame().hasWinner()) {
					play.getGame().setWinner();
					try {
						Thread.sleep(200);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					createEndGame();
				}

				// If the computer opponent doesn't land in their store, the timer is
				// stopped and prevented from moving the player again
				if (!play.getGame().getsAnotherMove()) {
					timer.cancel();
				}

			}
		};
		if (play.getGame().hasWinner()) {
			play.getGame().setWinner();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			createEndGame();

		}
		// The computer opponent's timer is started as soon as the human player has
		// finished their turn
		if (play.getGame().getCurrentPlayer() == 1 && !play.getGame().hasWinner()) {
			timer.schedule(action, 1500, 1500);
		}

	}

	/*
	 * Switches the panels when the buttons are clicked. This is how the program
	 * controls what the user is seeing.
	 * 
	 * @param MouseEvent e is the event that occurs to call this method.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		JButton bclicked = (JButton) e.getSource();
		if (bclicked.getText() == "Instructions") {
			card.show(c, "instructions");
		} else if (bclicked.getText() == "Resume") {
			card.show(c, "play");
		} else if (bclicked.getText() == "Help") {
			card.show(c, "help");
		} else if (bclicked.getText() == "Play Game") {
			card.show(c, "settings");
		} else if (bclicked.getText() == "Quit") {
			System.exit(1);
		} else if (bclicked.getText() == "Home") {
			play.singlePlayer = false;
			play.resetBoardGraphics();
			card.show(c, "welcome");
		} else if (bclicked.getText() == "Two Player") {
			card.show(c, "play");
		} else if (bclicked.getText() == "Single Player") {
			play.singlePlayer = true;
			card.show(c, "play");
		} else if (bclicked.getText() == " ") {
			if (play.singlePlayer) {
				singlePlayerMove();
			}
			if (play.getGame().hasWinner()) {
				play.getGame().setWinner();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				createEndGame();
			}
		} else if (bclicked.getText() == "Play Again") {
			play.singlePlayer = false;
			play.resetBoardGraphics();
			card.show(c, "settings");
		}

	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseEntered(MouseEvent e) {
		setCursor(new Cursor(Cursor.HAND_CURSOR));

	}


	@Override
	public void mouseExited(MouseEvent e) {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

	}

	/**
	 * Executes when the program runs and creates an instance of the controller.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Controller c = new Controller();
		c.createCard();

	}

}