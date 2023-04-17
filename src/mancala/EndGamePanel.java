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
public class EndGamePanel extends JPanel {
	private int winner;
	private boolean playAgain;

	Image resizedWinningImage;
	Image resizedLosingImage;

	public JButton playAgainButton;
	public JButton exitGameButton;
	ImageIcon playAgainIcon;
	ImageIcon playAgainHoverIcon;
	ImageIcon exitGameIcon;
	ImageIcon exitGameHoverIcon;
	

	/**
	 * Creates a new end-screen panel for the Mancala game. This JPanel is
	 * initialized once a player has won the game and disappears once either of its
	 * two buttons are clicked, either to replay the game or exit the game.
	 * 
	 * @param winner an integer designating the winning player of the game
	 */
	public EndGamePanel(int winner, int score1, int score2) {
		this.setPreferredSize(new Dimension(400, 250));
		this.setLayout(null);
		this.setBackground(new Color(242, 234, 218));

		this.winner = winner;
		this.playAgain = false;

		// Create the labels
		JLabel loseLabel = new JLabel("You Lost!");
		JLabel winLabel = new JLabel("You Won!");
		JLabel restartLabel = new JLabel("Would you like to play again?");
		loseLabel.setBounds(155, 110, 380, 50);
		winLabel.setBounds(150, 110, 380, 50);
		restartLabel.setBounds(55, 135, 380, 50);
		loseLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		winLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		restartLabel.setFont(new Font("Tahoma", Font.BOLD, 20));

		// Create the "Play Again" button
		playAgainButton = new JButton();
		playAgainButton.setBounds(40, 185, 140, 40);
		playAgainButton.setBorderPainted(false);
		playAgainButton.setText("Play Again");
		playAgainButton.setContentAreaFilled(false);
		playAgainButton.addMouseListener(new MouseListener(){
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

		// Create the "Exit Game" button
		exitGameButton = new JButton();
		exitGameButton.setBounds(220, 185, 140, 40);
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

		if (winner == 0) {
			this.add(winLabel);
		} else {
			this.add(loseLabel);
		}
		this.add(restartLabel);
		this.add(playAgainButton);
		this.add(exitGameButton);

		try {
			// Create the images, including the button images
			BufferedImage winningBufferedImage = ImageIO.read(new File("winning.png"));
			BufferedImage losingBufferedImage = ImageIO.read(new File("losing.png"));
			BufferedImage playAgainBufferedImage = ImageIO.read(new File("playAgainButton.png"));
			BufferedImage playAgainHoverBufferedImage = ImageIO.read(new File("playAgainHover.png"));
			BufferedImage exitGameBufferedImage = ImageIO.read(new File("exitGameButton.png"));
			BufferedImage exitGameHoverBufferedImage = ImageIO.read(new File("exitGameHoverButton.png"));

			// Resize the images to correct sizes
			Image winningImage = new ImageIcon(winningBufferedImage).getImage();
			Image losingImage = new ImageIcon(losingBufferedImage).getImage();
			resizedWinningImage = winningImage.getScaledInstance(350, 140, Image.SCALE_SMOOTH);
			resizedLosingImage = losingImage.getScaledInstance(400, 150, Image.SCALE_SMOOTH);

			Image playAgainImage = new ImageIcon(playAgainBufferedImage).getImage();
			Image playAgainHoverImage = new ImageIcon(playAgainHoverBufferedImage).getImage();
			Image exitGameImage = new ImageIcon(exitGameBufferedImage).getImage();
			Image exitGameHoverImage = new ImageIcon(exitGameHoverBufferedImage).getImage();
			Image resizedPlayAgainImage = playAgainImage.getScaledInstance(140, 40, Image.SCALE_SMOOTH);
			Image resizedPlayAgainHoverImage = playAgainHoverImage.getScaledInstance(140, 40, Image.SCALE_SMOOTH);
			Image resizedExitGameImage = exitGameImage.getScaledInstance(140, 40, Image.SCALE_SMOOTH);
			Image resizedExitGameHoverImage = exitGameHoverImage.getScaledInstance(140, 40, Image.SCALE_SMOOTH);

			// Creating icons for the button images
			playAgainIcon = new ImageIcon(resizedPlayAgainImage);
			playAgainHoverIcon = new ImageIcon(resizedPlayAgainHoverImage);
			exitGameIcon = new ImageIcon(resizedExitGameImage);
			exitGameHoverIcon = new ImageIcon(resizedExitGameHoverImage);

			// Set the button images to the buttons
			playAgainButton.setIcon(playAgainIcon);
			exitGameButton.setIcon(exitGameIcon);

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

		if (winner == 0) {
			g.drawImage(resizedWinningImage, 30, 0, null);
		} else {
			g.drawImage(resizedLosingImage, 0, -10, null);
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
}
