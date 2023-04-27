package mancala;

import java.awt.Color;
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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Contains the graphics and buttons for the end-game screen.
 * 
 * @author Ryan Johnson, Hank Rugg
 */
@SuppressWarnings("serial")
public class EndGamePanel extends JPanel {
	private int winner;
	private boolean playAgain;
	private boolean singlePlayer;
	
	private JButton homeButton;
	private JButton playAgainButton;
	private JButton exitGameButton;

	private Image resizedBackgroundImage;
	private Image resizedWinningImage;
	private Image resizedLosingImage;
	private Image resizedVictoryImage;
	
	private ImageIcon playAgainIcon;
	private ImageIcon playAgainHoverIcon;
	private ImageIcon exitGameIcon;
	private ImageIcon exitGameHoverIcon;
	private ImageIcon homeIcon;
	private ImageIcon homeHoverIcon;

	/**
	 * Creates a new end-screen panel for the Mancala game. This JPanel is
	 * initialized once a player has won the game and disappears once either of its
	 * two buttons are clicked, either to replay the game or exit the game.
	 * 
	 * @param winner an integer designating the winning player of the game
	 */
	public EndGamePanel(int winner, int score1, int score2, boolean singlePlayer) {
		this.setPreferredSize(new Dimension(400, 250));
		this.setLayout(null);
		this.setBackground(new Color(242, 234, 218));

		this.winner = winner;
		this.playAgain = false;
		this.singlePlayer = singlePlayer;

		// Create the labels
		JLabel loseLabel = new JLabel("You Lost!");
		JLabel winLabel = new JLabel("You Won!");
		JLabel player1winLabel = new JLabel("Player 1 Won!");
		JLabel player2winLabel = new JLabel("Player 2 Won!");
		JLabel topHorizontalLine = new JLabel("------------------------");
		JLabel bottomHorizontalLine = new JLabel("------------------------");
		JLabel score = new JLabel("Player 1: " + score1 + "     Player 2: " + score2);
		JLabel restartLabel = new JLabel("Would you like to play again?");
		loseLabel.setBounds(295, 280, 380, 50);
		winLabel.setBounds(305, 280, 380, 50);
		player1winLabel.setBounds(260, 280, 450, 50);
		player2winLabel.setBounds(280, 280, 450, 50);
		topHorizontalLine.setBounds(200, 305, 450, 50);
		bottomHorizontalLine.setBounds(200, 360, 450, 50);
		score.setBounds(155, 330, 500 ,50);
		restartLabel.setBounds(120, 390, 600, 50);

		loseLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
		winLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
		player1winLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
		player2winLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
		topHorizontalLine.setFont(new Font("Tahoma", Font.BOLD, 35));
		bottomHorizontalLine.setFont(new Font("Tahoma", Font.BOLD, 35));
		score.setFont(new Font("Tahoma", Font.BOLD, 35));
		restartLabel.setFont(new Font("Tahoma", Font.BOLD, 35));

		// Create the "Play Again" button
		playAgainButton = new JButton();
		playAgainButton.setBounds(130, 490, 180, 50);
		playAgainButton.setBorderPainted(false);
		playAgainButton.setContentAreaFilled(false);
		playAgainButton.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
				playAgainButton.setIcon(playAgainHoverIcon);
			}

			public void mouseExited(MouseEvent e) {
				playAgainButton.setIcon(playAgainIcon);
			}
		});
		

		// Create the "Home" Button
		homeButton = new JButton();
		homeButton.setBounds(320, 490, 150,50);
		homeButton.setBorderPainted(false);
		homeButton.setContentAreaFilled(false);
		homeButton.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
				homeButton.setIcon(homeHoverIcon);
			}

			public void mouseExited(MouseEvent e) {
				homeButton.setIcon(homeIcon);
			}
		});

		// Create the "Exit Game" button
		exitGameButton = new JButton();
		exitGameButton.setBounds(480, 490, 180, 50);
		exitGameButton.setBorderPainted(false);
		exitGameButton.setContentAreaFilled(false);
		exitGameButton.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
				exitGameButton.setIcon(exitGameHoverIcon);
			}

			public void mouseExited(MouseEvent e) {
				exitGameButton.setIcon(exitGameIcon);
			}
		});

		if (singlePlayer) {
			if (winner == 0) {
				this.add(winLabel);
			} else {
				this.add(loseLabel);
			}
		} else {
			if (winner == 0) {
				this.add(player1winLabel);
			} else {
				this.add(player2winLabel);
			}
		}
		
		this.add(restartLabel);
		this.add(topHorizontalLine);
		this.add(bottomHorizontalLine);
		this.add(score);
		this.add(playAgainButton);
		this.add(exitGameButton);
		this.add(homeButton);

		try {
			// Create the background image
			Image backgroundImage = ImageIO.read(new File("images/wood.jpg"));
			Image backgroundImageIcon = new ImageIcon(backgroundImage).getImage();
			resizedBackgroundImage = backgroundImageIcon.getScaledInstance(800,565, Image.SCALE_SMOOTH);
			
			// Create the images, including the button images
			BufferedImage winningBufferedImage = ImageIO.read(new File("images/winning.png"));
			BufferedImage losingBufferedImage = ImageIO.read(new File("images/losing.png"));
			BufferedImage victoryBufferedImage = ImageIO.read(new File("images/victory.png"));
			BufferedImage playAgainBufferedImage = ImageIO.read(new File("images/playAgainButton.png"));
			BufferedImage playAgainHoverBufferedImage = ImageIO.read(new File("images/playAgainHover.png"));
			BufferedImage exitGameBufferedImage = ImageIO.read(new File("images/exitGame.png"));
			BufferedImage exitGameHoverBufferedImage = ImageIO.read(new File("images/exitGameHover.png"));
			BufferedImage homeBufferedImage = ImageIO.read(new File("images/home.png"));
			BufferedImage homeHoverBufferedImage = ImageIO.read(new File("images/homeHover.png"));


			// Resize the images to correct sizes
			Image winningImage = new ImageIcon(winningBufferedImage).getImage();
			Image losingImage = new ImageIcon(losingBufferedImage).getImage();
			Image victoryImage = new ImageIcon(victoryBufferedImage).getImage();
			resizedWinningImage = winningImage.getScaledInstance(700, 280, Image.SCALE_SMOOTH);
			resizedLosingImage = losingImage.getScaledInstance(800, 300, Image.SCALE_SMOOTH);
			resizedVictoryImage = victoryImage.getScaledInstance(700, 450, Image.SCALE_SMOOTH);
			Image homeImage = new ImageIcon(homeBufferedImage).getImage();
			Image homeHoverImage = new ImageIcon(homeHoverBufferedImage).getImage();
		


			Image playAgainImage = new ImageIcon(playAgainBufferedImage).getImage();
			Image playAgainHoverImage = new ImageIcon(playAgainHoverBufferedImage).getImage();
			Image exitGameImage = new ImageIcon(exitGameBufferedImage).getImage();
			Image exitGameHoverImage = new ImageIcon(exitGameHoverBufferedImage).getImage();
			Image resizedPlayAgainImage = playAgainImage.getScaledInstance(180, 50, Image.SCALE_SMOOTH);
			Image resizedPlayAgainHoverImage = playAgainHoverImage.getScaledInstance(180, 50, Image.SCALE_SMOOTH);
			Image resizedExitGameImage = exitGameImage.getScaledInstance(180, 50, Image.SCALE_SMOOTH);
			Image resizedExitGameHoverImage = exitGameHoverImage.getScaledInstance(180, 50, Image.SCALE_SMOOTH);
			Image resizedHomeImage = homeImage.getScaledInstance(150, 50, Image.SCALE_SMOOTH);
			Image resizedHomeHoverImage = homeHoverImage.getScaledInstance(150, 50, Image.SCALE_SMOOTH);


			// Creating icons for the button images
			playAgainIcon = new ImageIcon(resizedPlayAgainImage);
			playAgainHoverIcon = new ImageIcon(resizedPlayAgainHoverImage);
			exitGameIcon = new ImageIcon(resizedExitGameImage);
			exitGameHoverIcon = new ImageIcon(resizedExitGameHoverImage);
			homeIcon = new ImageIcon(resizedHomeImage);
			homeHoverIcon = new ImageIcon(resizedHomeHoverImage);

			// Set the button images to the buttons
			playAgainButton.setIcon(playAgainIcon);
			exitGameButton.setIcon(exitGameIcon);
			homeButton.setIcon(homeIcon);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Renders all images on the end-game screen.
	 * 
	 * @param graphics a Graphics object that draws the end-game screen images to
	 *                 the JPanel
	 */
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		final Graphics2D g = (Graphics2D) graphics;

		g.drawImage(resizedBackgroundImage, 0,0, null);
		if (singlePlayer) {
			if (winner == 0) {
				g.drawImage(resizedWinningImage, 30, 20, null);
			} else {
				g.drawImage(resizedLosingImage, 0, -10, null);
			}
		} else {
			g.drawImage(resizedVictoryImage, 25,-60, null);
		}
	}

	/**
	 * Returns whether the "Play Again" button has been clicked.
	 * 
	 * @return boolean returns true if the "Play Again" button has been clicked;
	 *         false otherwise
	 */
	public boolean getPlayAgain() {
		return playAgain;
	}
	
	/**
	 * Returns the "Home" button.
	 * 
	 * @return a JButton with the "Home" image
	 */
	public JButton getHomeButton() {
		return homeButton;
	}
	
	/**
	 * Returns the "Play Again" button.
	 * 
	 * @return a JButton with the "Play Again" image
	 */
	public JButton getPlayAgainButton() {
		return playAgainButton;
	}
	
	/**
	 * Returns the "Exit Game" button.
	 * 
	 * @return a JButton with the "Exit Game" image
	 */
	public JButton getExitGameButton() {
		return exitGameButton;
	}
}
