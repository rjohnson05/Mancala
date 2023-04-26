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
		welcome.playGameButton.addMouseListener(this);
		welcome.instructionsButton.addMouseListener(this);
		welcome.exitGameButton.addMouseListener(this);
		c.add("welcome", welcome);

		// create instructions panel and adds mouse listeners
		instructions = new InstructionsPanel();
		instructions.homeButton.addMouseListener(this);
		c.add("instructions", instructions);

		// create settings panel and adds mouse listeners
		settings = new SettingsPanel();
		settings.homeButton.addMouseListener(this);
		settings.singlePlayerButton.addMouseListener(this);
		settings.twoPlayerButton.addMouseListener(this);
		c.add("settings", settings);
		
		// create play panel and adds mouse listeners to all the pit buttons
		play = new PlayPanel();
		for (RoundButton button : play.pitButtons) {
			button.addMouseListener(this);
		}
		play.homeButton.addMouseListener(this);
		play.helpButton.addMouseListener(this);
		c.add("play", play);
		
		help = new HelpPanel();
		help.resumeButton.addMouseListener(this);
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
	 * Creates the end game panel that is shown once the game is over.
	 */
	public void createEndGame() {
		end = new EndGamePanel(play.getGame().getWinner(), play.getGame().getStoreList().get(6).getMarbleList().size(),
				play.getGame().getStoreList().get(13).getMarbleList().size(), play.getSinglePlayer());
		end.exitGameButton.addMouseListener(this);
		end.playAgainButton.addMouseListener(this);
		end.homeButton.addMouseListener(this);
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
		if (bclicked == welcome.instructionsButton) {
    		card.show(c, "instructions");
    	}
    	else if (bclicked == welcome.playGameButton ) {
    		card.show(c, "settings");
    	}
    	else if (bclicked == welcome.exitGameButton) {
    		System.exit(1);
    	}
    	else if (bclicked == instructions.homeButton || bclicked == settings.homeButton) {
    		card.show(c, "welcome");
    	}
    	else if (bclicked == settings.twoPlayerButton || bclicked == help.resumeButton) {
    		card.show(c, "play");
    	}
    	else if (bclicked == settings.singlePlayerButton) {
    		play.singlePlayer = true;
    		card.show(c, "play");
    	}
    	else if (bclicked.getText() == " ") {
    		if (play.singlePlayer) {
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
    			play.resetBoardGraphics();
    			card.show(c, "end");
    		}
    	} else if (bclicked == play.homeButton) {
    		play.resetBoardGraphics();
    		card.show(c, "welcome");
    	} else if (bclicked == play.helpButton) {
    		card.show(c, "help");
    	} else if (bclicked == end.homeButton) {
    		play.singlePlayer = false;
    		card.show(c, "welcome");
    	} else if (bclicked == end.playAgainButton) {
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

	/**
	 * Executes when the program runs and creates an instance of the controller.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Controller c = new Controller();
		c.createCard();
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
			System.out.println("Cheat 1");
			for (int i = 0; i < 51; i++) {
				Marble newMarble = new Marble();
				play.getGame().getStoreList().get(6).addMarble(newMarble);
			};
			for (int i = 7; i < 13; i++) {
				Pit pit = play.getGame().getStoreList().get(i);
				pit.getMarbleList().clear();
			}
		}

		String cheatLoseArray = "[W, I, N, P, 2]";
		if (keysTyped.toString().equals(cheatLoseArray)) {
			System.out.println("Cheat 2");
			for (int i = 0; i < 51; i++) {
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
}