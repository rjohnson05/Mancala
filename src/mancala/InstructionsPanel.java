package mancala;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class InstructionsPanel extends JPanel {

	private Game game = new Game();
	private Random rand = new Random();

	public InstructionsPanel() {
		this.setPreferredSize(new Dimension(800, 500));
		this.setLayout(null);
        JPanel panel=new JPanel();  
        panel.setBounds(40,80,200,200);    
		this.setBackground(new Color(228, 218, 199));
		
		JLabel welcomeLabel = new JLabel("Welcome to");
		JButton home = new JButton("Home");
		JButton playGame = new JButton("Play Game");
        JButton quitGame = new JButton("Quit");
		JTextArea rules = new JTextArea("Rules:");
		JTextArea rulesListed = new JTextArea("1. There are two players for the game, each with six of their own pits and one of their own store.\n"
				+ "2. The player who is up selects one of the pits on their side. The marbles in that pit are then \n" 
				+ "    distributed to the following pits.\n"
				+ "3. If the last marble moved lands in that players store, that player gets another turn.\n"
				+ "4. If the last marble moved lands in an empty pit on their side of the board, and there are\n"
				+ "    marbles in the pit across from the final pit, that player captures their final marble \n"
				+ "    and the marbles across from their pit.\n"
				+ "5. The game is over when all of the pits on one side of the board are completely empty.\n"
				+ "6. The winner is determined by the player who has the most marbles in their store.\n");
		
		

		
		rules.setEditable(false);
		rules.setBackground(new Color(228, 218, 199));
		
		
		rulesListed.setEditable(false);
		rulesListed.setBackground(new Color(228, 218, 199));
		
		welcomeLabel.setFont(new Font("DialogInput", Font.BOLD, 20));
		rules.setFont(new Font("DialogInput", Font.BOLD, 15));
		quitGame.setFont(new Font("DialogInput", Font.BOLD, 10));
		playGame.setFont(new Font("DialogInput", Font.BOLD, 10));
		home.setFont(new Font("DialogInput", Font.BOLD, 10));
		rulesListed.setFont(new Font("DialogInput", Font.BOLD, 10));

		
		welcomeLabel.setBounds(325, 40, 200, 25);
		rulesListed.setBounds(200, 220, 500, 150);
		rules.setBounds(150, 200, 50, 55);
		quitGame.setBounds(450, 450, 100, 35);
		playGame.setBounds(350, 450, 100, 35);
		home.setBounds(250, 450, 100, 35);

		
		this.add(welcomeLabel);
		this.add(rules);
		this.add(quitGame);
		this.add(playGame);
		this.add(home);
		this.add(rulesListed);
  
		
		addButtonListeners(quitGame);
		addButtonListeners(playGame);
		addButtonListeners(home);

	}

	/**
	 * 
	 */
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		final Graphics2D g = (Graphics2D) graphics;
		
		// Create the Mancala title image
		BufferedImage titleImage;
		try {
			titleImage = ImageIO.read(new File("mancalaTitle.png"));
			Image titleImageIcon = new ImageIcon(titleImage).getImage();
			Image resizedTitleImage = titleImageIcon.getScaledInstance(400, 60, Image.SCALE_SMOOTH);
			g.drawImage(resizedTitleImage, 200, 100, null);

		} catch (IOException e) {
			e.printStackTrace();
		}

 
	}


	public void addButtonListeners(JButton nextPage) {
		MouseListener buttonListener = new MouseListener() {
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		};

		nextPage.addMouseListener(buttonListener);
	}

}