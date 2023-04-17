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
 */
public class Cardlayout extends JFrame implements MouseListener {
 
    // Declaration of objects of CardLayout class.
    public CardLayout card;
 
    // Declaration of objects of JButton class.
    public WelcomePanel welcome;
    public InstructionsPanel instructions;
    public SettingsPanel settings;
    public PlayPanel play;
    public EndGamePanel end;
    private boolean gameOver;
 
    // Declaration of objects
    // of Container class.
    public Container c;
 
    /**
     * Constructor for the cardlayout.
     */
    Cardlayout(){
 
        // to get the content
        c = getContentPane();
 
        // Initialization of object "card"
        card = new CardLayout(40, 30);
        card.setHgap(0);
        card.setVgap(0);
 
        // set the layout
        c.setLayout(card);
 
        welcome = new WelcomePanel();
        welcome.playGame.addMouseListener(this);
        welcome.instructions.addMouseListener(this);
        welcome.quitGame.addMouseListener(this);
        c.add("welcome", welcome);
 

        instructions = new InstructionsPanel();
        instructions.playGame.addMouseListener(this);
        instructions.home.addMouseListener(this);
        instructions.quitGame.addMouseListener(this);
        c.add("instructions", instructions);

     

 
        settings = new SettingsPanel();
        settings.home.addMouseListener(this);
        settings.singlePlayer.addMouseListener(this);
        settings.twoPlayer.addMouseListener(this);
        settings.quitGame.addMouseListener(this);
        c.add("settings", settings);
        
        
        play = new PlayPanel();
        for (RoundButton button : play.pitButtons) {
        	button.addMouseListener(this);
        }
        c.add("play", play);
        
    }
    
     

    /**
     * Creates the cards
     */
    public void createCard() {

            
            // Creating Object of CardLayout class.
            Cardlayout cl = new Cardlayout();
     
            // Function to set size of JFrame.
            cl.setSize(800, 600);
     
            // Function to set visibility of JFrame.
            cl.setVisible(true);
     
            // Function to set default operation of JFrame.
            cl.setDefaultCloseOperation(EXIT_ON_CLOSE);
    	
    }
    
    public void createEndGame() {
            end = new EndGamePanel(play.getGame().getWinner(), play.getGame().getStoreList().get(6).getMarbleList().size(), play.getGame().getStoreList().get(13).getMarbleList().size() );
            end.exitGameButton.addMouseListener(this);
            end.playAgainButton.addMouseListener(this);
            c.add("end", end);
    		card.show(c, "end");
    }
    
    
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

	@Override
	public void mouseClicked(MouseEvent e) {
		JButton bclicked = (JButton) e.getSource();
    	if (bclicked.getText() == "Instructions") {
    		card.show(c, "instructions");
    	}
    	else if (bclicked.getText() == "Play Game") {
    		card.show(c, "settings");
    	}
    	else if (bclicked.getText() == "Quit") {
    		System.exit(1);
    	}
    	else if (bclicked.getText() == "Home") {
    		card.show(c, "welcome");
    	}
    	else if (bclicked.getText() == "Two Player") {
    		card.show(c, "play");
    	}
    	else if (bclicked.getText() == "Single Player") {
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			createEndGame();
    		}
    	}
    	else if (bclicked.getText() == "Play Again"){
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
	
	public static void main(String[] args) {		
		Cardlayout c = new Cardlayout();
		c.createCard();
		

		}
 
}