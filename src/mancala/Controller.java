package mancala;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
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
public class Controller extends JFrame implements MouseListener, KeyListener {
	public Container c;
	public CardLayout card;
	public WelcomePanel welcome;
	public InstructionsPanel instructions;
	public SettingsPanel settings;
	public PlayPanel play;
	public EndGamePanel end;
	public HelpPanel help;
	
	public List<String> keysTyped = new ArrayList<String>();


	/**
	 * Constructor for the Controller. This is how to program switches screens and
	 * allows the user to play multiple games without
	 */
	public Controller() {
		this.setResizable(false);
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
		welcome.getPlayGameButton().addMouseListener(this);
		welcome.getInstructionsButton().addMouseListener(this);
		welcome.getExitGameButton().addMouseListener(this);
		c.add("welcome", welcome);

		// create instructions panel and adds mouse listeners
		instructions = new InstructionsPanel();
		instructions.getHomeButton().addMouseListener(this);
		c.add("instructions", instructions);

		// create settings panel and adds mouse listeners
		settings = new SettingsPanel();
		settings.getHomeButton().addMouseListener(this);
		settings.getSinglePlayerButton().addMouseListener(this);
		settings.getTwoPlayerButton().addMouseListener(this);
		c.add("settings", settings);
		
		// create play panel and adds mouse listeners to all the pit buttons
		play = new PlayPanel(false);
		for (RoundButton button : play.getPitButtons()) {
			button.addMouseListener(this);
		}
		play.getHomeButton().addMouseListener(this);
		play.getHelpButton().addMouseListener(this);
		c.add("play", play);
		
		help = new HelpPanel();
		help.getResumeButton().addMouseListener(this);
		c.add("help", help);
		
		addKeyListener(this);
		setFocusable(true);
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

	/**
	 * Creates the panel that houses the main gameplay.
	 * 
	 * @param singlePlayer true if the game is in "single player" mode and false if
	 *                     in "two player" mode
	 */
	public void createPlayPanel(boolean singlePlayer) {
		// create play panel and adds mouse listeners to all the pit buttons
		play = new PlayPanel(singlePlayer);

		for (int i = 0; i < play.getPitButtons().size(); i++) {
			play.getPitButtons().get(i).addMouseListener(this);
		}

		play.getHomeButton().addMouseListener(this);
		play.getHelpButton().addMouseListener(this);
		c.add("play", play);

	}

	/**
	 * Creates the end game panel that is shown once the game is over.
	 */
	public void createEndGame() {
		end = new EndGamePanel(play.getGame().getWinner(), play.getGame().getStoreList().get(6).getMarbleList().size(),
				play.getGame().getStoreList().get(13).getMarbleList().size(), play.getSinglePlayer());
		end.getExitGameButton().addMouseListener(this);
		end.getPlayAgainButton().addMouseListener(this);
		end.getHomeButton().addMouseListener(this);
		c.add("end", end);
	}

	/**
	 * Makes the computer select a pit to move. This is called after the user makes
	 * a move in single player mode.
	 */
	public void singlePlayerMove() {
		Timer timer = new Timer();
		TimerTask action = new TimerTask() {
			public void run() {
				/*
				 * Sets a timer for moving the computer opponent. After 2 seconds, a random pit
				 * index from its side of the board is chosen. If the selected pit is empty, a
				 * new random pit is selected until a non-empty pit is selected.
				 */			

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
					card.show(c, "end");
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
				e1.printStackTrace();
			}
			createEndGame();
			card.show(c, "end");
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
		if (bclicked == welcome.getInstructionsButton()) {
    		card.show(c, "instructions");
    	}
    	else if (bclicked == welcome.getPlayGameButton() ) {
    		card.show(c, "settings");
    	}
    	else if (bclicked == welcome.getExitGameButton()) {
    		System.exit(1);
    	}
    	else if (bclicked == instructions.getHomeButton() || bclicked == settings.getHomeButton()) {
    		card.show(c, "welcome");
    	}
    	else if (bclicked == settings.getTwoPlayerButton() || bclicked == help.getResumeButton()) {
    		card.show(c, "play");
    	}
    	else if (bclicked == settings.getSinglePlayerButton()) {
    		play.setSinglePlayer(true);
    		card.show(c, "play");
    	}
    	else if (bclicked.getText() == " ") {
    		if (play.getSinglePlayer()) {
    			singlePlayerMove();
    		}
    		if (play.getGame().hasWinner()) {
    			play.getGame().setWinner();
    			try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				createEndGame();
				card.show(c, "end");
			}
		} else if (bclicked == play.getHomeButton()) {
			play.setSinglePlayer(false);
			play.resetBoardGraphics();
			card.show(c, "welcome");
		} else if (bclicked == play.getHelpButton()) {
			card.show(c, "help");
		} else if (bclicked == end.getHomeButton()) {
			play.resetBoardGraphics();
			play.setSinglePlayer(false);
			card.show(c, "welcome");
		} else if (bclicked == end.getPlayAgainButton()) {
			play.resetBoardGraphics();
			card.show(c, "play");
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

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		String keyPressed = KeyEvent.getKeyText(e.getKeyCode());
		// Remove the first recorded key if longer than 'cheat'
		if (keysTyped.size() >= 5) {
			keysTyped.remove(0);
		}
		// Add the pressed key to the list of pressed keys
		keysTyped.add(keyPressed);

		// Adds an extra miss is the word 'cheat' has been typed
		String cheatWinArray = "[W, I, N, P, 1]";
		if (keysTyped.toString().equals(cheatWinArray)) {
			for (int i = 0; i < 24; i++) {
				Marble newMarble = new Marble();
				play.getGame().getStoreList().get(6).addMarble(newMarble);
			}
			for (int i = 7; i < 13; i++) {
				Pit pit = play.getGame().getStoreList().get(i);
				pit.getMarbleList().clear();
			}
		}

		String cheatLoseArray = "[W, I, N, P, 2]";
		if (keysTyped.toString().equals(cheatLoseArray)) {
			for (int i = 0; i < 24; i++) {
				Marble newMarble = new Marble();
				play.getGame().getStoreList().get(13).addMarble(newMarble);
			}
			for (int i = 0; i < 6; i++) {
				Pit pit = play.getGame().getStoreList().get(i);
				pit.getMarbleList().clear();
			}
		}

		if (play.getGame().hasWinner()) {
			play.getGame().setWinner();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			createEndGame();
			play.resetBoardGraphics();
			card.show(c, "end");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
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